package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.dto.StructuredItineraryRequest;
import com.travelathon.travel.util.GroqJsonExtractor;
import com.travelathon.travel.util.ItineraryJsonPromptBuilder;
import com.travelathon.travel.util.StructuredItineraryPromptBuilder;

import org.springframework.stereotype.Service;

@Service
public class ItineraryJsonService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper ;

    public ItineraryJsonService(GroqClient groqClient,ObjectMapper mapper) {
        this.groqClient = groqClient;
        this.mapper = mapper;
    }

 public JsonNode generate(StructuredItineraryRequest req) throws Exception {

    String prompt =
            StructuredItineraryPromptBuilder.build(req);

    String raw = groqClient.generateItinerary(prompt);

    return GroqJsonExtractor.extract(raw);
}

}
