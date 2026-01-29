package com.travelathon.travel.util;

import com.travelathon.travel.dto.StructuredItineraryRequest;

public class StructuredItineraryPromptBuilder {

    public static String build(StructuredItineraryRequest r) {

        return """
                You are a professional travel itinerary planner.

                Generate a day-wise itinerary in STRICT JSON.

                Rules:
                - Output only JSON
                - Include travel, hotel, events, food, sightseeing
                - Costs in INR
                - Cost per activity
                - dayTotal per day
                - grandTotal

                Trip:
                From: %s
                To: %s
                Days: %d
                Travelers: %d
                Event Type: %s

                JSON schema:
                {
                  "summary": {
                    "destination": string,
                    "totalDays": number,
                    "travelers": number,
                    "currency": "INR",
                    "grandTotal": number
                  },
                  "days": [
                    {
                      "day": number,
                      "activities": [
                        {
                          "title": string,
                          "type": string,
                          "cost": number
                        }
                      ],
                      "dayTotal": number
                    }
                  ]
                }
                  Goal: produce minimum possible travel cost.

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
