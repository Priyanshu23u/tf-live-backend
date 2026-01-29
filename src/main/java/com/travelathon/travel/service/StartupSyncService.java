package com.travelathon.travel.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.travelathon.travel.entity.EventProvider;
import com.travelathon.travel.repository.EventRepository;

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
        if (eventRepository.countBySource(EventProvider.TICKETMASTER) == 0) {
            ticketmasterSync.syncTicketmasterEvents();
        }
    } catch (Exception e) {
        System.out.println("Ticketmaster sync failed: " + e.getMessage());
    }

    try {
        if (eventRepository.countBySource(EventProvider.OPENF1) == 0) {
            openF1Sync.syncRacingEvents();
        }
    } catch (Exception e) {
        System.out.println("OpenF1 sync failed: " + e.getMessage());
    }

    try {
        if (eventRepository.countBySource(EventProvider.CRICAPI) == 0) {
            cricketSync.syncCricketMatches();
        }
    } catch (Exception e) {
        System.out.println("CricAPI sync failed: " + e.getMessage());
    }

    

    System.out.println("Startup sync completed.");
}

}
