package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class SportMonksClient {

    private final RestTemplate restTemplate;

    @Value("${sportmonks.api.url}")
    private String baseUrl;

    @Value("${sportmonks.api.key}")
    private String apiKey;

    public SportMonksClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchFutureFixtures() {

        String today = LocalDate.now().toString();

        String url = baseUrl +
                "/fixtures" +
                "?api_token=" + apiKey +
                "&filters=starts_after:" + today +
                "&include=league,season,venue,participants" +
                "&per_page=50";

        return restTemplate.getForObject(url, String.class);
    }
}
