package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TicketmasterClient {

    @Value("${ticketmaster.api.key}")
    private String apiKey;

    @Value("${ticketmaster.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchRawEvents() {
    String url = apiUrl +
            "?apikey=" + apiKey +
            "&size=20";
    return restTemplate.getForObject(url, String.class);
    }

}
