package com.travelathon.travel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.travelathon.travel.dto.PromptRequest;
import com.travelathon.travel.service.ItineraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class ItineraryController {

    private final ItineraryService service;

    public ItineraryController(ItineraryService service) {
        this.service = service;
    }

    @PostMapping("/prompt")
    public JsonNode ask(@RequestBody PromptRequest request) throws Exception {
        return service.generateFromPrompt(request);
    }
}
