package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.dto.PromptRequest;
import org.springframework.stereotype.Service;

@Service
public class ItineraryService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ItineraryService(GroqClient groqClient) {
        this.groqClient = groqClient;
    }

    public JsonNode generateFromPrompt(PromptRequest request) throws Exception {

    String raw = groqClient.generateItinerary(request.getPrompt());

    JsonNode root = mapper.readTree(raw);

    // Extract Groq message content
    String content = root.path("choices")
                         .get(0)
                         .path("message")
                         .path("content")
                         .asText();

    // 🔥 IMPORTANT: parse content as JSON, not string
    return mapper.readTree(content);
}

}
