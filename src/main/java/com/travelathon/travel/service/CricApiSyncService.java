package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.CricApiClient;
import com.travelathon.travel.entity.*;
import com.travelathon.travel.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CricApiSyncService {

    private final EventPackageService eventPackageService;

    private final CricApiClient client;
    private final EventRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public CricApiSyncService(CricApiClient client, EventRepository repository, EventPackageService eventPackageService) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
    }

    public int syncCricketMatches() throws Exception {

        String response = client.fetchMatches();
        JsonNode root = mapper.readTree(response);
        JsonNode data = root.path("data");

        if (!data.isArray()) return 0;

        int count = 0;

        for (JsonNode node : data) {

            String externalId = node.path("id").asText(null);
            if (externalId == null) continue;

            // avoid duplicates
            if (repository.findByExternalIdAndSource(
                    externalId, EventProvider.CRICAPI).isPresent()) {
                continue;
            }

            Event event = new Event();
            event.setExternalId(externalId);
            event.setSource(EventProvider.CRICAPI);
            event.setCategory(EventCategory.SPORTS);

            event.setTitle(
                    node.path("name").asText("Cricket Match")
            );

            // Venue parsing
            String venue = node.path("venue").asText("Unknown");
            event.setVenue(venue);

            // Try to extract city from venue
            if (venue.contains(",")) {
                event.setCity(venue.split(",")[1].trim());
            } else {
                event.setCity("Unknown");
            }

            event.setCountry("India"); // CricAPI mostly India-centric

            String date = node.path("date").asText(null);
            if (date == null) continue;

            event.setStartDate(LocalDate.parse(date));
            event.setEndDate(event.getStartDate());

            // Cricket tickets vary
            event.setCurrentPrice(BigDecimal.ZERO);

            repository.save(event);
            count++;
        }

        return count;
    }
}
