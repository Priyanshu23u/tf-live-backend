package com.travelathon.travel.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenF1Client {

    private static final String OPENF1_URL =
            "https://api.openf1.org/v1/meetings?year=2026";

    private final RestTemplate restTemplate;

    public OpenF1Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchMeetings() {
        return restTemplate.getForObject(OPENF1_URL, String.class);
    }
}
