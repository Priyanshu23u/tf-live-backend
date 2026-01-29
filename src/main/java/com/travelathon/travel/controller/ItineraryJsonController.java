package com.travelathon.travel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelathon.travel.dto.PromptRequest;
import com.travelathon.travel.dto.StructuredItineraryRequest;
import com.travelathon.travel.service.ItineraryJsonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/itinerary")
@CrossOrigin
public class ItineraryJsonController {

    private final ItineraryJsonService service;

    public ItineraryJsonController(ItineraryJsonService service) {
        this.service = service;
    }

    @PostMapping("/json")
public JsonNode generate(
        @RequestBody StructuredItineraryRequest request
) throws Exception {
    return service.generate(request);
}

}
