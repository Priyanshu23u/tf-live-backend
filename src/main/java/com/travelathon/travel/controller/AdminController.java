package com.travelathon.travel.controller;

import com.travelathon.travel.client.TicketmasterClient;
import com.travelathon.travel.service.CricApiSyncService;
import com.travelathon.travel.service.EventSyncService;
import com.travelathon.travel.service.OpenF1SyncService;
import com.travelathon.travel.service.SportMonksFootballSyncService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    private final EventSyncService syncService;
    private final OpenF1SyncService openF1SyncService;
    private final CricApiSyncService cricApiSyncService;
    private final SportMonksFootballSyncService sportMonksFootballSyncService;
    private final TicketmasterClient ticketmasterClient;

    public AdminController(EventSyncService syncService,
                       OpenF1SyncService openF1SyncService,
                       CricApiSyncService cricApiSyncService,
                       SportMonksFootballSyncService sportMonksFootballSyncService,TicketmasterClient ticketmasterClient) {
        this.syncService = syncService;
        this.openF1SyncService = openF1SyncService;
        this.cricApiSyncService = cricApiSyncService;
        this.sportMonksFootballSyncService = sportMonksFootballSyncService;
        this.ticketmasterClient = ticketmasterClient;
    }
    

    @PostMapping("/sync/ticketmaster")
    public String syncTicketmaster() throws Exception {
        int count = syncService.syncTicketmasterEvents();
        return count + " events synced successfully";
    }

    @PostMapping("/sync/openf1")
    public String syncOpenF1() throws Exception {
        int count = openF1SyncService.syncRacingEvents();
        return count + " racing events synced successfully";
    }

    @PostMapping("/sync/cricket")
    public String syncCricket() throws Exception {
        int count = cricApiSyncService.syncCricketMatches();
        return count + " cricket matches synced successfully";
    }

    @PostMapping("/sync/football/sportmonks")
    public String syncFootballSportMonks() throws Exception {
        int count = sportMonksFootballSyncService.syncFutureFootballMatches();
        return count + " future football matches synced successfully";
    }

    @GetMapping("/debug/ticketmaster")
    public String inspectTicketmaster() {
        return ticketmasterClient.fetchRawEvents();
    }
}
