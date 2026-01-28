package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketmasterClient {

    private final RestTemplate restTemplate;

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.api.url}")
    private String apiUrl;

    public TicketmasterClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchRawEvents() {
        String url = apiUrl +
                "?apikey=" + apiKey +
                "&classificationName=music" +
                "&size=20";
        return restTemplate.getForObject(url, String.class);
    }
}
