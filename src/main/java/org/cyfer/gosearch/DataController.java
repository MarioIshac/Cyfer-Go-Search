package org.cyfer.gosearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import okhttp3.*;
import org.cyfer.gosearch.searchresponse.Properties;
import org.cyfer.gosearch.searchresponse.SearchResponse;
import org.cyfer.gosearch.searchresponse.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class DataController {
    private final ObjectMapper objectMapper;
    private final OkHttpClient httpClient;

    private static final String IMAGE_METADATA_TEMPLATE;

    static {
        try {
            val imageMetaDataTemplateStream = DataController.class.getClassLoader().getResourceAsStream("image_metadata.json");
            IMAGE_METADATA_TEMPLATE = new String(imageMetaDataTemplateStream.readAllBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static final Map<String, Function<ImageForm, Object>> META_DATA_TEMPLATES = Map.of(
        "description", ImageForm::getDescription,
        "weight", ImageForm::getWeight,
        "location", ImageForm::getLocation,
        "user", ImageForm::getUser,
        "timestamp", ImageForm::getTimestamp
    );

    private static String toJSON(final ImageForm imageForm) {
        var metaData = IMAGE_METADATA_TEMPLATE;

        for (val metaDataTemplate : META_DATA_TEMPLATES.entrySet()) {
            val metaDataTemplateKey = metaDataTemplate.getKey();
            val metaDataTemplater = metaDataTemplate.getValue();

            metaData = metaData.replace("${" + metaDataTemplateKey + "}", metaDataTemplater.apply(imageForm).toString());
        }

        return metaData;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType PLAINTEXT = MediaType.parse("text/plain; charset=utf-8");
    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @GetMapping("data/download")
    public ResponseEntity<String> downloadData() {
        val headerMap = Map.of("Ocp-Apim-Subscription-Key", "b019ec95392146ec885cc9be94e2d8f9");
        val headers = Headers.of(headerMap);

        val searchQueryStream = getClass().getClassLoader().getResourceAsStream("images_search.json");

        try {
            val requestBody = RequestBody.create(searchQueryStream.readAllBytes(), JSON);

            val request = new Request.Builder()
                    .headers(headers)
                    .url("https://api.yuuvis.io/dms/objects/search")
                    .post(requestBody)
                    .build();

            val response = getHttpClient().newCall(request).execute();
            val responseJSON = response.body().string();

            val searchResponse = getObjectMapper().readValue(responseJSON, SearchResponse.class);

            //noinspection OptionalGetWithoutIsPresent
            val csvContent = Stream.concat(
                Stream.of("description,weight,location,user,timestamp"),
                searchResponse.getStoredObjects().stream().map(StoredObject::getProperties).map(DataController::createCSVRowFromProperties)
            ).reduce((formerLine, latterline) -> formerLine + '\n' + latterline).get();

            return ResponseEntity.ok(csvContent);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String createCSVRowFromProperties(final Properties properties) {
        return properties.getDescription() + "," + properties.getWeight() + "," + properties.getLocation() + "," + properties.getUsername() + "," + properties.getTimestamp();
    }

    private Response uploadOriginalVersion(final List<ImageForm> equalImageForms) throws IOException {
        val versionedImage = equalImageForms.get(0);
        val imageMetaData = toJSON(versionedImage);
        val requestImage = RequestBody.create(imageMetaData.getBytes(), JSON);
        val requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("data", "image_metadata.json", requestImage).build();
        val request = new Request.Builder().headers(headers).url("https://api.yuuvis.io/dms/objects").post(requestBody).build();

        return getHttpClient().newCall(request).execute();
    }

    private void uploadUpdatedVersion(final List<ImageForm> equalImageForms, final String originalEqualImageID, final int version) throws IOException {
        val imageIndex = version - 1;

        val versionedImage = equalImageForms.get(imageIndex);
        val imageMetaData = toJSON(versionedImage);
        val requestImage = RequestBody.create(imageMetaData.getBytes(), JSON);

        val requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                                                     .addFormDataPart("data", "image_metadata.json", requestImage)
                                                     .build();

        val updatedImageURL = String.format("https://api.yuuvis.io/dms/objects/%s", originalEqualImageID);

        val request = new Request.Builder().headers(headers)
                                           .url(updatedImageURL)
                                           .post(requestBody)
                                           .build();

        getHttpClient().newCall(request).execute();
    }

    private static final Map<String, String> headerMap = Map.of(
            "Content-Type", "multipart/form-data, application/x-www-form-urlencoded",
            "Ocp-Apim-Subscription-Key", "b019ec95392146ec885cc9be94e2d8f9"
    );

    private static final Headers headers = Headers.of(headerMap);

    @PostMapping("data/upload")
    public void uploadData(final @RequestParam("file") MultipartFile imageMetaDatasAsCSV) throws IOException {
        val csvData = new String(imageMetaDatasAsCSV.getBytes());
        val imageForms = ImageForm.getImageForms(csvData);

        imageForms.collect(Collectors.groupingBy(ImageForm::getDisambiguator))
                  .entrySet()
                  .stream()
                  .map(Map.Entry::getValue)
                  .forEach(this::uploadEqualImages);
    }

    private void uploadEqualImages(final List<ImageForm> equalImages) {
        // Ensures images that come are older in time are older/before in version
        equalImages.sort(Comparator.comparing(ImageForm::getDisambiguator));

        try {
            val httpResponse = uploadOriginalVersion(equalImages);
            val responseJSON = httpResponse.body().string();
            val objectsResponse = getObjectMapper().readValue(responseJSON, SearchResponse.class);
            val uploadedObjects = objectsResponse.getStoredObjects();

            if (uploadedObjects.size() != 1) {
                throw new AssertionError("Only one object should be uploaded at a time.");
            }

            val objectResponse = objectsResponse.getStoredObjects().get(0);
            val objectProperties = objectResponse.getProperties();
            val objectID = objectProperties.getObjectId().getValue();
            val versionCount = equalImages.size();

            for (int version = 2; version <= versionCount; version++) {
                uploadUpdatedVersion(equalImages, objectID, version);
            }
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
