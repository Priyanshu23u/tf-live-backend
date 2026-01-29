package com.travelathon.travel.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GroqClient {

        private final RestTemplate restTemplate;

        @Value("${groq.api.key}")
        private String apiKey;

        @Value("${groq.api.url}")
        private String apiUrl;

        public GroqClient(RestTemplate restTemplate) {
                this.restTemplate = restTemplate;
        }

        public String generateItinerary(String prompt) {

                Map<String, Object> body = new HashMap<>();
                body.put("model", "llama-3.1-8b-instant");

                body.put("messages", List.of(
    Map.of(
        "role", "system",
        "content",
        "You are an API. You MUST respond with ONLY valid JSON. " +
        "Do NOT include explanations, text, markdown, or prefixes. " +
        "If you cannot comply, return {}"
    ),
    Map.of("role", "user", "content", prompt)
));


                body.put("temperature", 0.4);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

                ResponseEntity<String> response = restTemplate.exchange(
                                apiUrl,
                                HttpMethod.POST,
                                entity,
                                String.class);

                return response.getBody();
        }
}
