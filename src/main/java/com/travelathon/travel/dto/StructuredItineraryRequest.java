package com.travelathon.travel.dto;

import com.travelathon.travel.entity.EventCategory;

public class StructuredItineraryRequest {

    public String fromCity;
    public String toCity;
    public int days;
    public int travelers;
    public EventCategory eventType;
}
