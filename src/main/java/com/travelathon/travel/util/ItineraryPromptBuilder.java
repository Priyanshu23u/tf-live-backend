package com.travelathon.travel.util;

import com.travelathon.travel.dto.ItineraryRequest;

public class ItineraryPromptBuilder {

    public static String build(ItineraryRequest r) {

        return """
        Create a professional travel itinerary with estimated pricing.

        Event:
        - Name: %s
        - City: %s
        - Country: %s
        - Date: %s

        Traveler details:
        - Origin city: %s
        - Number of travelers: %d
        - Trip duration: %d days
        - Budget range: %d to %d INR

        Assumptions:
        - Economy flights
        - 3-star hotel
        - Local transport included
        - Food included
        - No real booking, estimates only

        Return STRICT JSON only in this format:

        {
          "summary": {},
          "pickup_point": "",
          "dropoff_point": "",
          "total_days": number,
          "daily_itinerary": [
            {
              "day": number,
              "activities": [],
              "hotel": "",
              "estimated_cost": number
            }
          ],
          "cost_breakdown": {
            "flight": number,
            "hotel": number,
            "local_transport": number,
            "food": number,
            "event_ticket": number,
            "total": number
          },
          "notes": ""
        }

        Do NOT include explanations or markdown.
        """
        .formatted(
            r.eventName,
            r.eventCity,
            r.eventCountry,
            r.eventDate,
            r.originCity,
            r.travelers,
            r.days,
            r.budgetMin,
            r.budgetMax
        );
    }
}
