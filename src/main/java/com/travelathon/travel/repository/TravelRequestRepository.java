package com.travelathon.travel.repository;

import com.travelathon.travel.entity.TravelRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TravelRequestRepository
        extends JpaRepository<TravelRequest, UUID> {
                List<TravelRequest> findByEmail(String email);
}
