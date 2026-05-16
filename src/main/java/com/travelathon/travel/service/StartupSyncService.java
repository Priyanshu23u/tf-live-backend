package com.travelathon.travel.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.travelathon.travel.repository.EventRepository;

import java.time.LocalDate;

@Component
public class StartupSyncService implements CommandLineRunner {

    private final EventSyncService ticketmasterSync;
    private final OpenF1SyncService openF1Sync;
    private final CricApiSyncService cricketSync;
    private final EventRepository eventRepository;

    public StartupSyncService(
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

    @Override
    public void run(String... args) {

        try {

            System.out.println("Starting startup sync...");

            // Delete expired events
            eventRepository.deleteExpiredEvents(LocalDate.now());

            // Always sync fresh events
            ticketmasterSync.syncTicketmasterEvents();

            openF1Sync.syncRacingEvents();

            cricketSync.syncCricketMatches();

            System.out.println("Startup sync completed.");

        } catch (Exception e) {

            System.out.println("Startup sync failed.");

            e.printStackTrace();
        }
    }
}