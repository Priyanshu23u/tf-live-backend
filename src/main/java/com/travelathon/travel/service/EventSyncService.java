package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.TicketmasterClient;
import com.travelathon.travel.entity.*;
import com.travelathon.travel.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventSyncService {

    private final EventPackageService eventPackageService;

    private final TicketmasterClient client;
    private final EventRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public EventSyncService(TicketmasterClient client, EventRepository repository, EventPackageService eventPackageService) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
    }

    public int syncTicketmasterEvents() throws Exception {
    String response = client.fetchRawEvents();

    JsonNode root = mapper.readTree(response);

    JsonNode embedded = root.path("_embedded");
    if (embedded.isMissingNode() || embedded.isNull()) {
        System.out.println("No _embedded section in response");
        System.out.println(response);
        return 0;
    }

    JsonNode eventsNode = embedded.path("events");
    if (!eventsNode.isArray()) {
        System.out.println("No events array found");
        System.out.println(response);
        return 0;
    }

    int count = 0;

    for (JsonNode node : eventsNode) {

        String externalId = node.path("id").asText(null);
        if (externalId == null) continue;

        // avoid duplicates
        if (repository.findByExternalIdAndSource(
                externalId, EventProvider.TICKETMASTER).isPresent()) {
            continue;
        }

        JsonNode venueNode = node.path("_embedded")
                                 .path("venues");

        if (!venueNode.isArray() || venueNode.isEmpty()) continue;

        Event event = new Event();
        event.setExternalId(externalId);
        event.setSource(EventProvider.TICKETMASTER);
        event.setCategory(EventCategory.CONCERT);
        event.setTitle(node.path("name").asText("Unknown Event"));

        event.setCity(
            venueNode.get(0).path("city").path("name").asText("Unknown")
        );

        event.setCountry(
            venueNode.get(0).path("country").path("name").asText("Unknown")
        );

        String dateStr = node.path("dates")
                             .path("start")
                             .path("localDate")
                             .asText(null);

        if (dateStr == null) continue;

        event.setStartDate(LocalDate.parse(dateStr));
        event.setEndDate(event.getStartDate());

        event.setCurrentPrice(new BigDecimal("9999"));

        repository.save(event);
        count++;
    }

    return count;
}

}
