package org.cyfer.gosearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Getter
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class RecommendationController {
    private final OkHttpClient httpClient;

    @GetMapping("/recommendation")
    public ResponseEntity<String> getRecommendation(final @RequestParam("hashtag") String query, final @RequestParam("topLocationsCount") int limit) {
        try {
            val headerMap = Map.of("Ocp-Apim-Subscription-Key", "b019ec95392146ec885cc9be94e2d8f9");
            val headers = Headers.of(headerMap);

            val pythonRecommenderCode = getClass().getClassLoader().getResourceAsStream("gosearch.py").readAllBytes();
            val pythonRecommenderInstallPath = Paths.get("/gosearch.py");

            if (!Files.exists(pythonRecommenderInstallPath)) {
                Files.createFile(pythonRecommenderInstallPath);
            }

            Files.write(pythonRecommenderInstallPath, pythonRecommenderCode);

            val request = new Request.Builder()
                                     .headers(headers)
                                     .url("https://cyfer-go-search.herokuapp.com/data/download")
                                     .get()
                                     .build();

            val response = getHttpClient().newCall(request).execute();
            val csvBytes = response.body().bytes();

            val recommenderInputPath = Path.of("/input.csv");
            val queryPath = Path.of("/query.txt");

            Files.write(recommenderInputPath, csvBytes);
            Files.write(queryPath, String.format("%s\n%s", query, limit).getBytes());

            Runtime.getRuntime().exec("python3 /gosearch.py");

            // val graph = Paths.get("~/graph.png");
            val locations = Paths.get("/locations.txt");
            // val users = Paths.get("~/users.txt");

            val locationsStr = Files.readString(locations);
            return ResponseEntity.ok(locationsStr);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
