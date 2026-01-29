package com.travelathon.travel.service;

import com.travelathon.travel.dto.TravelRequestDTO;
import com.travelathon.travel.entity.TravelRequest;
import com.travelathon.travel.repository.TravelRequestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TravelRequestService {

    private final TravelRequestRepository repository;

    public TravelRequestService(TravelRequestRepository repository) {
        this.repository = repository;
    }

    public TravelRequest saveConfirmed(
            TravelRequestDTO dto,
            BigDecimal estimatedPrice
    ) {

        TravelRequest r = new TravelRequest();

        r.setName(dto.name);
        r.setEmail(dto.email);
        r.setPhone(dto.phone);
        r.setFromCity(dto.fromCity);
        r.setToCity(dto.toCity);
        r.setDays(dto.days);
        r.setTravelers(dto.travelers);
        r.setEventType(dto.eventType);
        r.setEstimatedPrice(estimatedPrice);
        r.setConfirmed(true);

        return repository.save(r);
    }
}
