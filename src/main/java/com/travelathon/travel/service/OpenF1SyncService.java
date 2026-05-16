package com.travelathon.travel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelathon.travel.client.OpenF1Client;
import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventCategory;
import com.travelathon.travel.entity.EventProvider;
import com.travelathon.travel.repository.EventRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

@Service
public class OpenF1SyncService {

    private static final Logger logger =
            LoggerFactory.getLogger(OpenF1SyncService.class);

    private final EventPackageService eventPackageService;

    private final OpenF1Client client;
    private final EventRepository repository;
    private final ObjectMapper mapper;

    public OpenF1SyncService(
            OpenF1Client client,
            EventRepository repository,
            EventPackageService eventPackageService,
            ObjectMapper mapper
    ) {
        this.client = client;
        this.repository = repository;
        this.eventPackageService = eventPackageService;
        this.mapper = mapper;
    }

    public int syncRacingEvents() throws Exception {

        logger.info("Starting OpenF1 sync...");

        String response = client.fetchMeetings();

        JsonNode root = mapper.readTree(response);

        if (!root.isArray()) {

            logger.warn("OpenF1 response is not an array");

            return 0;
        }

        int count = 0;

        Iterator<JsonNode> iterator = root.iterator();

        while (iterator.hasNext()) {

            JsonNode node = iterator.next();

            String externalId = node.path("meeting_key").asText(null);

            if (externalId == null)
                continue;

            // UPSERT LOGIC
            Event event = repository.findByExternalIdAndSource(
                    externalId,
                    EventProvider.OPENF1
            ).orElse(new Event());

            event.setExternalId(externalId);

            event.setSource(EventProvider.OPENF1);

            event.setCategory(EventCategory.RACING);

            event.setTitle(
                    node.path("meeting_name")
                            .asText("F1 Race")
            );

            event.setCity(
                    node.path("location")
                            .asText("Unknown")
            );

            event.setCountry(
                    node.path("country_name")
                            .asText("Unknown")
            );

            String startDate = node.path("date_start").asText(null);

            String endDate = node.path("date_end").asText(null);

            if (startDate != null) {

                event.setStartDate(
                        LocalDate.parse(startDate.substring(0, 10))
                );
            }

            if (endDate != null) {

                event.setEndDate(
                        LocalDate.parse(endDate.substring(0, 10))
                );

            } else {

                event.setEndDate(event.getStartDate());
            }

            // SKIP EXPIRED EVENTS
            if (event.getEndDate().isBefore(LocalDate.now())) {
                continue;
            }

            event.setCurrentPrice(BigDecimal.ZERO);

            repository.save(event);

            count++;
        }

        logger.info("OpenF1 events synced successfully. Count: {}", count);

        return count;
    }
}