package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.dto.PromptRequest;
import com.travelathon.travel.util.GroqJsonExtractor;

import org.springframework.stereotype.Service;

@Service
public class ItineraryService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper;

    public ItineraryService(GroqClient groqClient,ObjectMapper mapper) {
        this.groqClient = groqClient;
        this.mapper = mapper;
    }

    public JsonNode generateFromPrompt(PromptRequest request) throws Exception {

        String raw = groqClient.generateItinerary(request.getPrompt());

        return GroqJsonExtractor.extract(raw);
    }

}
