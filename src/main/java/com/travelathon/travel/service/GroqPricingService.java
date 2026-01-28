package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.GroqClient;
import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventPackage;
import com.travelathon.travel.repository.EventPackageRepository;
import com.travelathon.travel.util.PricingPromptBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GroqPricingService {

    private final GroqClient groqClient;
    private final EventPackageRepository eventPackageRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public GroqPricingService(GroqClient groqClient,
                              EventPackageRepository eventPackageRepository) {
        this.groqClient = groqClient;
        this.eventPackageRepository = eventPackageRepository;
    }

    public void estimateAndUpdate(Event event) throws Exception {

        EventPackage eventPackage = eventPackageRepository
                .findByEvent(event)
                .orElseThrow(() -> new IllegalStateException("EventPackage missing"));

        String prompt = PricingPromptBuilder.build(event);
        String raw = groqClient.generateItinerary(prompt);

        JsonNode root = mapper.readTree(raw);
        String content = root.path("choices")
                             .get(0)
                             .path("message")
                             .path("content")
                             .asText();

        JsonNode json = mapper.readTree(content);

        eventPackage.setFlightPriceEstimate(
                BigDecimal.valueOf(json.path("flight").asDouble())
        );

        eventPackage.setHotelPriceEstimate(
                BigDecimal.valueOf(json.path("hotel").asDouble())
        );

        eventPackage.setNights(json.path("nights").asInt());

        eventPackage.setTotalPackagePrice(
                BigDecimal.valueOf(json.path("total").asDouble())
        );

        eventPackageRepository.save(eventPackage);
    }
}
