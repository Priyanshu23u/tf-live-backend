package com.travelathon.travel.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GroqJsonExtractor {

    private static final ObjectMapper mapper = new ObjectMapper();

    private GroqJsonExtractor() {}

    public static JsonNode extract(String rawGroqResponse) throws Exception {

        JsonNode root = mapper.readTree(rawGroqResponse);

        String content = root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText()
                .trim();

        int start = content.indexOf("{");
        int end = content.lastIndexOf("}");

        if (start == -1 || end == -1 || end <= start) {
            throw new IllegalStateException(
                    "Groq response does not contain valid JSON:\n" + content
            );
        }

        String jsonOnly = content.substring(start, end + 1);

        return mapper.readTree(jsonOnly);
    }
}
