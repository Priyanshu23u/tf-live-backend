package com.travelathon.travel.service;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventCategory;
import com.travelathon.travel.repository.EventRepository;
import com.travelathon.travel.specification.EventSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> filterEvents(
            EventCategory category,
            String city,
            String country,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {

        Specification<Event> spec = Specification.where(null);

        spec = spec.and(EventSpecification.hasCategory(category));
        spec = spec.and(EventSpecification.hasCity(city));
        spec = spec.and(EventSpecification.hasCountry(country));
        spec = spec.and(EventSpecification.startDateAfter(startDate));
        spec = spec.and(EventSpecification.endDateBefore(endDate));
        spec = spec.and(EventSpecification.priceGreaterThan(minPrice));
        spec = spec.and(EventSpecification.priceLessThan(maxPrice));

        return eventRepository.findAll(spec);
    }
}
