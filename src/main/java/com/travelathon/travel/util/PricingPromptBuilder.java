package com.travelathon.travel.util;

import com.travelathon.travel.entity.Event;

public class PricingPromptBuilder {

    public static String build(Event event) {

        return """
                Estimate MINIMUM realistic travel costs in INR.

                Event details:
                - Title: %s
                - City: %s
                - Country: %s
                - Start Date: %s

                Assumptions:
                - Economy round-trip flight
                - Budget Hotel
                - Average local transport
                - Basic food have average cost(not so high not so low)
                - Event ticket included

                Return ONLY valid JSON:

                {
                "flight": number,
                  "hotel": number,
                  "local_transport": number,
                  "food": number,
                  "event_ticket": number,
                  "total": number,
                  "nights": number
                }

                No markdown. No explanation.
                """
                .formatted(
                        event.getTitle(),
                        event.getCity(),
                        event.getCountry(),
                        event.getStartDate());
    }
}
