package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.dto.PricingPreviewResponse;
import com.travelathon.travel.dto.TravelRequestDTO;
import com.travelathon.travel.util.GroqJsonExtractor;
import com.travelathon.travel.util.UserPricingPromptBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TravelPricingService {

    private final GroqClient groqClient;
    private final ObjectMapper mapper ;

    public TravelPricingService(GroqClient groqClient, ObjectMapper mapper){

        this.groqClient = groqClient;
        this.mapper = mapper;
    }

    public PricingPreviewResponse estimate(TravelRequestDTO dto) throws Exception {

        String prompt = UserPricingPromptBuilder.build(dto);
        String raw = groqClient.generateItinerary(prompt);

        JsonNode json = GroqJsonExtractor.extract(raw);

        PricingPreviewResponse res = new PricingPreviewResponse();
        res.estimatedPrice = BigDecimal.valueOf(json.path("total").asDouble());
        res.currency = "INR";
        res.notes = json.path("notes").asText();

        return res;
    }
}
