package com.travelathon.travel.util;

public class ItineraryJsonPromptBuilder {

    public static String build(String userPrompt) {

        return """
                You are a professional travel itinerary planner.

                Generate a day-wise travel itinerary STRICTLY in valid JSON format.

                Rules:
                - Return ONLY valid JSON
                - Do NOT add markdown
                - Do NOT add explanations
                - JSON size can vary depending on number of days
                - Include realistic estimated costs in INR

                JSON Schema:
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
                      "date": string,
                      "activities": [
                        {
                          "title": string,
                          "type": "travel | sightseeing | event | food | hotel | transport | misc",
                          "cost": number
                        }
                      ],
                      "dayTotal": number
                    }
                  ]
                }

                User Request:
                """ + userPrompt;
    }
}
