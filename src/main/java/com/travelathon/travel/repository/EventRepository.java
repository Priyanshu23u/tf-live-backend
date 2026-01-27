package com.travelathon.travel.repository;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventCategory;
import com.travelathon.travel.entity.EventProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository
        extends JpaRepository<Event, UUID>,
                JpaSpecificationExecutor<Event> {

    Optional<Event> findByExternalIdAndSource(String externalId, EventProvider source);

}
