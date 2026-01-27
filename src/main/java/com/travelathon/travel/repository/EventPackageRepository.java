package com.travelathon.travel.repository;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventPackageRepository
        extends JpaRepository<EventPackage, UUID> {

    Optional<EventPackage> findByEvent(Event event);
}