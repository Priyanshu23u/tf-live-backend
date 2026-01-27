package com.travelathon.travel.specification;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventCategory;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EventSpecification {

    public static Specification<Event> hasCategory(EventCategory category) {
        return (root, query, cb) ->
                category == null ? null : cb.equal(root.get("category"), category);
    }

    public static Specification<Event> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null : cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    public static Specification<Event> hasCountry(String country) {
        return (root, query, cb) ->
                country == null ? null : cb.equal(cb.lower(root.get("country")), country.toLowerCase());
    }

    public static Specification<Event> startDateAfter(LocalDate startDate) {
        return (root, query, cb) ->
                startDate == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    public static Specification<Event> endDateBefore(LocalDate endDate) {
        return (root, query, cb) ->
                endDate == null ? null : cb.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    public static Specification<Event> priceGreaterThan(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("currentPrice"), minPrice);
    }

    public static Specification<Event> priceLessThan(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("currentPrice"), maxPrice);
    }

    public static Specification<Event> hasLeague(String league) {
    return (root, query, cb) ->
            league == null ? null :
            cb.equal(cb.lower(root.get("leagueName")), league.toLowerCase());
}

public static Specification<Event> hasSeason(Integer season) {
    return (root, query, cb) ->
            season == null ? null :
            cb.equal(root.get("season"), season);
}

}
