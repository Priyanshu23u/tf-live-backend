package com.travelathon.travel.service;

import com.travelathon.travel.entity.Event;
import com.travelathon.travel.entity.EventPackage;
import com.travelathon.travel.repository.EventPackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class EventPackageService {

    private final EventPackageRepository repository;

    public EventPackageService(EventPackageRepository repository) {
        this.repository = repository;
    }

    public EventPackage createIfNotExists(Event event) {

        return repository.findByEvent(event)
                .orElseGet(() -> {
                    EventPackage pkg = new EventPackage();
                    pkg.setEvent(event);

                    pkg.setFlightPriceEstimate(BigDecimal.valueOf(5000));
                    pkg.setHotelPriceEstimate(BigDecimal.valueOf(3000));
                    pkg.setNights(1);
                    pkg.setTotalPackagePrice(BigDecimal.valueOf(9000));

                    return repository.save(pkg);
                });
    }
}
