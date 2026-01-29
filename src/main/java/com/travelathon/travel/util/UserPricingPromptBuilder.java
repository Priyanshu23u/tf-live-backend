package com.travelathon.travel.util;

import com.travelathon.travel.dto.TravelRequestDTO;

public class UserPricingPromptBuilder {

    public static String build(TravelRequestDTO r) {

        return """
        Estimate realistic travel cost in INR.

        Trip details:
        - From: %s
        - To: %s
        - Days: %d
        - Travelers: %d
        - Event type: %s

        Include:
        - Transport
        - Hotel
        - Food
        - Event cost

        Return ONLY valid JSON:

        {
          "total": number,
          "notes": string
        }
        """
        .formatted(
            r.fromCity,
            r.toCity,
            r.days,
            r.travelers,
            r.eventType
        );
    }
}
