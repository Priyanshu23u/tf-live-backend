package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class SportMonksClient {

    @Value("${sportmonks.api.url}")
    private String baseUrl;

    @Value("${sportmonks.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchFutureFixtures() {

        String today = LocalDate.now().toString();

        String url = baseUrl +
                "/fixtures" +
                "?api_token=" + apiKey +
                "&filters=starts_after:" + today +
                "&include=league,season,venue,participants" +
                "&per_page=20";

        return restTemplate.getForObject(url, String.class);
    }
}
