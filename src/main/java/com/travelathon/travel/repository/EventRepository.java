package com.travelathon.travel.repository;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository
        extends JpaRepository<Event, UUID>,
        JpaSpecificationExecutor<Event> {

    Optional<Event> findByExternalIdAndSource(
            String externalId,
            EventProvider source
    );

    long countBySource(EventProvider source);

    @Modifying
    @Transactional
    @Query("DELETE FROM Event e WHERE e.endDate < :today")
    void deleteExpiredEvents(@Param("today") LocalDate today);
}