package com.travelathon.travel.util;

import com.travelathon.travel.entity.Event;

public class PricingPromptBuilder {

    public static String build(Event event) {

        return """
        Estimate realistic travel costs in INR.

        Event details:
        - Title: %s
        - City: %s
        - Country: %s
        - Start Date: %s

        Assumptions:
        - Economy round-trip flight
        - 3-star hotel
        - Average local transport
        - Basic food
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
            event.getStartDate()
        );
    }
}
