package org.cyfer.gosearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GoSearch {
    public static void main(final String[] args) {
        SpringApplication.run(GoSearch.class, args);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public OkHttpClient getHTTPClient() {
        return new OkHttpClient.Builder().build();
    }
}
