package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.util.ItineraryJsonPromptBuilder;
import org.springframework.stereotype.Service;

@Service
public class ItineraryJsonService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ItineraryJsonService(GroqClient groqClient) {
        this.groqClient = groqClient;
    }

    public JsonNode generate(String userPrompt) throws Exception {

        String prompt = ItineraryJsonPromptBuilder.build(userPrompt);
        String raw = groqClient.generateItinerary(prompt);

        JsonNode root = mapper.readTree(raw);

        String content = root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        // content must already be JSON
        return mapper.readTree(content);
    }
}
