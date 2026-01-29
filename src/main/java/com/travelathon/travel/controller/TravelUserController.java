package com.travelathon.travel.controller;

import com.travelathon.travel.dto.PricingPreviewResponse;
import com.travelathon.travel.dto.TravelRequestDTO;
import com.travelathon.travel.entity.TravelRequest;
import com.travelathon.travel.service.TravelPricingService;
import com.travelathon.travel.service.TravelRequestService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class TravelUserController {

    private final TravelPricingService pricingService;
    private final TravelRequestService requestService;

    public TravelUserController(
            TravelPricingService pricingService,
            TravelRequestService requestService
    ) {
        this.pricingService = pricingService;
        this.requestService = requestService;
    }

    /* STEP 1 — PRICE PREVIEW */
    @PostMapping("/estimate")
    public PricingPreviewResponse estimate(
            @RequestBody TravelRequestDTO dto
    ) throws Exception {
        return pricingService.estimate(dto);
    }

    /* STEP 2 — FINAL SUBMIT */
    @PostMapping("/submit")
    public TravelRequest submit(
            @RequestBody TravelRequestDTO dto,
            @RequestParam BigDecimal estimatedPrice
    ) {
        return requestService.saveConfirmed(dto, estimatedPrice);
    }
}
