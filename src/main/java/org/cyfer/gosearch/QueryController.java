package org.cyfer.gosearch;

import lombok.val;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class QueryController {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType PLAINTEXT = MediaType.parse("text/plain; charset=utf-8");
    public static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    @GetMapping("/query")
    public void mapping() {
        val headerMap = Map.of(
            "Content-Type", "multipart/form-data, application/x-www-form-urlencoded",
            "Ocp-Apim-Subscription-Key", "b019ec95392146ec885cc9be94e2d8f9"
        );

        val parametersMap = Map.<String, String>of();

        try {
            val builder = new OkHttpClient.Builder();
            val client = builder.build();

            val headers = Headers.of(headerMap);

            val metadataStream = getClass().getClassLoader().getResourceAsStream("metadata.json");
            val dataStream = getClass().getClassLoader().getResourceAsStream("data.txt");

            val body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("data", "metadata.json",  RequestBody.create(metadataStream.readAllBytes(), JSON))
                    .addFormDataPart("cid_63apple", "data.txt", RequestBody.create(dataStream.readAllBytes(), PLAINTEXT))
                    .build();;

            val request = new Request.Builder()
                    .headers(headers)
                    .url("https://api.yuuvis.io/dms/objects")
                    .post(body)
                    .build();

            val response = client.newCall(request).execute();

            System.out.println(response.toString());
            System.out.println(response.body().string());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
