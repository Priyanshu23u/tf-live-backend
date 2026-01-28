package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.SportMonksClient;
import com.travelathon.travel.entity.*;
import com.travelathon.travel.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SportMonksFootballSyncService {

    private final EventPackageService eventPackageService;

    private final SportMonksClient client;
    private final EventRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public SportMonksFootballSyncService(SportMonksClient client,
            EventRepository repository, EventPackageService eventPackageService) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
    }

    public int syncFutureFootballMatches() throws Exception {

        String response = client.fetchFutureFixtures();
        JsonNode root = mapper.readTree(response);
        JsonNode data = root.path("data");

        if (!data.isArray())
            return 0;

        int count = 0;

        for (JsonNode node : data) {

            String externalId = node.path("id").asText(null);
            if (externalId == null)
                continue;

            if (repository.findByExternalIdAndSource(
                    externalId, EventProvider.SPORTMONKS).isPresent()) {
                continue;
            }

            Event event = new Event();
            event.setExternalId(externalId);
            event.setSource(EventProvider.SPORTMONKS);
            event.setCategory(EventCategory.SPORTS);

            // Teams
            String home = "TBD";
            String away = "TBD";

            JsonNode participants = node.path("participants");
            if (participants.isArray()) {
                for (JsonNode team : participants) {
                    if ("home".equals(team.path("meta").path("location").asText())) {
                        home = team.path("name").asText(home);
                    }
                    if ("away".equals(team.path("meta").path("location").asText())) {
                        away = team.path("name").asText(away);
                    }
                }
            }

            event.setTitle(home + " vs " + away);

            // Venue
            event.setVenue(
                    node.path("venue").path("name").asText("Unknown"));

            event.setCity(
                    node.path("venue").path("city_name").asText("Unknown"));

            event.setCountry(
                    node.path("venue").path("country_name").asText("Unknown"));

            // Date
            String date = node.path("starting_at").asText(null);
            if (date == null)
                continue;

            LocalDate matchDate = LocalDate.parse(date.substring(0, 10));
            event.setStartDate(matchDate);
            event.setEndDate(matchDate);

            event.setCurrentPrice(BigDecimal.ZERO);

            repository.save(event);
            count++;
        }

        return count;
    }
}
