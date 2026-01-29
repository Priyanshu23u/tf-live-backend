package com.travelathon.travel.util;

import com.travelathon.travel.dto.TravelRequestDTO;

public class UserPricingPromptBuilder {

  public static String build(TravelRequestDTO r) {

    return """
    Estimate LOW POSSIBLE travel cost in INR.

    Trip details:
    - From: %s
    - To: %s
    - Days: %d
    - Travelers: %d
    - Event type: %s

    Optimization rules:
    - Use cheapest flights or trains
    - Budget hotel or hostel
    - Local public transport only
    - Affordable food options
    - Cheapest event tickets
    - Optimize cost per traveler

    Goal:
    Return  reasonable total cost.

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
