package com.travelathon.travel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.dto.PromptRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/chat")
@CrossOrigin
public class GroqChatController {

    private final GroqClient groqClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public GroqChatController(GroqClient groqClient) {
        this.groqClient = groqClient;
    }

    @PostMapping
    public JsonNode chat(@RequestBody PromptRequest request) throws Exception {

        String raw = groqClient.generateItinerary(request.getPrompt());

        JsonNode root = mapper.readTree(raw);
        String content = root.path("choices")
                             .get(0)
                             .path("message")
                             .path("content")
                             .asText();

        return mapper.createObjectNode()
                     .put("reply", content);
    }
}
