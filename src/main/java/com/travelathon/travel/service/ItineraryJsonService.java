package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.util.GroqJsonExtractor;
import com.travelathon.travel.util.ItineraryJsonPromptBuilder;
import org.springframework.stereotype.Service;

@Service
public class ItineraryJsonService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper ;

    public ItineraryJsonService(GroqClient groqClient,ObjectMapper mapper) {
        this.groqClient = groqClient;
        this.mapper = mapper;
    }

    public JsonNode generate(String userPrompt) throws Exception {

        String prompt = ItineraryJsonPromptBuilder.build(userPrompt);
        String raw = groqClient.generateItinerary(prompt);

        return GroqJsonExtractor.extract(raw);
    }
}
