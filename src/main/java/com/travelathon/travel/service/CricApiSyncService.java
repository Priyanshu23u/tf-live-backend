package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.CricApiClient;
import com.travelathon.travel.entity.*;
import com.travelathon.travel.repository.EventRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CricApiSyncService {

    private static final Logger logger =
            LoggerFactory.getLogger(CricApiSyncService.class);

    private final EventPackageService eventPackageService;

    private final CricApiClient client;
    private final EventRepository repository;
    private final ObjectMapper mapper;

    public CricApiSyncService(
            CricApiClient client,
            EventRepository repository,
            EventPackageService eventPackageService,
            ObjectMapper mapper
    ) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
        this.mapper = mapper;
    }

    public int syncCricketMatches() throws Exception {

        logger.info("Starting CricAPI sync...");

        String response = client.fetchMatches();

        JsonNode root = mapper.readTree(response);

        JsonNode data = root.path("data");

        if (!data.isArray()) {

            logger.warn("CricAPI data is not an array");

            return 0;
        }

        int count = 0;

        for (JsonNode node : data) {

            String externalId = node.path("id").asText(null);

            if (externalId == null)
                continue;

            // UPSERT LOGIC
            Event event = repository.findByExternalIdAndSource(
                    externalId,
                    EventProvider.CRICAPI
            ).orElse(new Event());

            event.setExternalId(externalId);

            event.setSource(EventProvider.CRICAPI);

            event.setCategory(EventCategory.SPORTS);

            event.setTitle(
                    node.path("name")
                            .asText("Cricket Match")
            );

            String venue = node.path("venue")
                    .asText("Unknown");

            event.setVenue(venue);

            if (venue.contains(",")) {

                event.setCity(
                        venue.split(",")[1].trim()
                );

            } else {

                event.setCity("Unknown");
            }

            event.setCountry("India");

            String date = node.path("date").asText(null);

            if (date == null)
                continue;

            event.setStartDate(LocalDate.parse(date));

            event.setEndDate(event.getStartDate());

            // SKIP EXPIRED EVENTS
            if (event.getEndDate().isBefore(LocalDate.now())) {
                continue;
            }

            event.setCurrentPrice(BigDecimal.ZERO);

            repository.save(event);

            count++;
        }

        logger.info("Cricket events synced successfully. Count: {}", count);

        return count;
    }
}