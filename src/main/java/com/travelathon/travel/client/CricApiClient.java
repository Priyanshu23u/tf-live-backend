package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CricApiClient {

    private final RestTemplate restTemplate;

    @Value("${cricapi.api.key}")
    private String apiKey;

    @Value("${cricapi.api.url}")
    private String apiUrl;

    public CricApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchMatches() {
        String url = apiUrl + "?apikey=" + apiKey + "&offset=0";
        return restTemplate.getForObject(url, String.class);
    }
}
