package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.OpenF1Client;
import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventCategory;
import com.travelathon.travel.entity.EventProvider;
import com.travelathon.travel.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

@Service
public class OpenF1SyncService {

    private final EventPackageService eventPackageService;

    private final OpenF1Client client;
    private final EventRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public OpenF1SyncService(OpenF1Client client, EventRepository repository, EventPackageService eventPackageService) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
    }

    public int syncRacingEvents() throws Exception {

        String response = client.fetchMeetings();
        JsonNode root = mapper.readTree(response);

        if (!root.isArray()) {
            System.out.println("OpenF1 response is not an array");
            return 0;
        }

        int count = 0;
        Iterator<JsonNode> iterator = root.iterator();

        while (iterator.hasNext()) {
            JsonNode node = iterator.next();

            String externalId = node.path("meeting_key").asText(null);
            if (externalId == null) continue;

            // avoid duplicates
            if (repository.findByExternalIdAndSource(
                    externalId, EventProvider.OPENF1).isPresent()) {
                continue;
            }

            Event event = new Event();
            event.setExternalId(externalId);
            event.setSource(EventProvider.OPENF1);
            event.setCategory(EventCategory.RACING);

            event.setTitle(
                node.path("meeting_name").asText("F1 Race")
            );

            event.setCity(
                node.path("location").asText("Unknown")
            );

            event.setCountry(
                node.path("country_name").asText("Unknown")
            );

            String startDate = node.path("date_start").asText(null);
            String endDate = node.path("date_end").asText(null);

            if (startDate != null) {
                event.setStartDate(LocalDate.parse(startDate.substring(0, 10)));
            }

            if (endDate != null) {
                event.setEndDate(LocalDate.parse(endDate.substring(0, 10)));
            } else {
                event.setEndDate(event.getStartDate());
            }

            event.setCurrentPrice(BigDecimal.ZERO);
 // racing tickets vary
            repository.save(event);
            count++;
        }

        return count;
    }
}
