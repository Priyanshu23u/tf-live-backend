package com.travelathon.travel.service;

import com.travelathon.travel.repository.EventRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ScheduledSyncService {

    private final EventSyncService ticketmasterSync;
    private final OpenF1SyncService openF1Sync;
    private final CricApiSyncService cricketSync;
    private final EventRepository eventRepository;

    public ScheduledSyncService(
            EventSyncService ticketmasterSync,
            OpenF1SyncService openF1Sync,
            CricApiSyncService cricketSync,
            EventRepository eventRepository
    ) {
        this.ticketmasterSync = ticketmasterSync;
        this.openF1Sync = openF1Sync;
        this.cricketSync = cricketSync;
        this.eventRepository = eventRepository;
    }

    /**
     * Runs every 6 hours
     *
     * Cron format:
     * second minute hour day month weekday
     */
    @Scheduled(cron = "0 0 */6 * * *")
    public void refreshEvents() {

        try {

            System.out.println("====================================");
            System.out.println("Starting scheduled event sync...");
            System.out.println("====================================");

            // Remove expired events
            eventRepository.deleteExpiredEvents(LocalDate.now());

            // Fetch fresh events
            ticketmasterSync.syncTicketmasterEvents();

            openF1Sync.syncRacingEvents();

            cricketSync.syncCricketMatches();

            System.out.println("====================================");
            System.out.println("Scheduled event sync completed.");
            System.out.println("====================================");

        } catch (Exception e) {

            System.out.println("====================================");
            System.out.println("Scheduled sync failed.");
            System.out.println("====================================");

            e.printStackTrace();
        }
    }
}