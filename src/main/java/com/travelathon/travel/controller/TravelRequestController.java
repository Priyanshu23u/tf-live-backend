package com.travelathon.travel.controller;

import org.springframework.web.bind.annotation.*;

import com.travelathon.travel.entity.TravelRequest;
import com.travelathon.travel.service.TravelRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class TravelRequestController {

    private final TravelRequestService service;

    public TravelRequestController(TravelRequestService service) {
        this.service = service;
    }

    @GetMapping("/requests")
    public List<TravelRequest> getRequestsByEmail(
            @RequestParam String email
    ) {
        return service.getRequestsByEmail(email);
    }
}

