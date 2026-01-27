package com.travelathon.travel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "event_packages")
public class EventPackage {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    @Column(name = "flight_price_estimate", precision = 10, scale = 2)
    private BigDecimal flightPriceEstimate;

    @Column(name = "hotel_price_estimate", precision = 10, scale = 2)
    private BigDecimal hotelPriceEstimate;

    private Integer nights = 1;

    @Column(name = "total_package_price", precision = 10, scale = 2)
    private BigDecimal totalPackagePrice;

    private String currency = "INR";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /* ==============================
       Lifecycle Hooks
       ============================== */

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* ==============================
       Getters & Setters
       ============================== */

    public UUID getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public BigDecimal getFlightPriceEstimate() {
        return flightPriceEstimate;
    }

    public void setFlightPriceEstimate(BigDecimal flightPriceEstimate) {
        this.flightPriceEstimate = flightPriceEstimate;
    }

    public BigDecimal getHotelPriceEstimate() {
        return hotelPriceEstimate;
    }

    public void setHotelPriceEstimate(BigDecimal hotelPriceEstimate) {
        this.hotelPriceEstimate = hotelPriceEstimate;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public BigDecimal getTotalPackagePrice() {
        return totalPackagePrice;
    }

    public void setTotalPackagePrice(BigDecimal totalPackagePrice) {
        this.totalPackagePrice = totalPackagePrice;
    }

    public String getCurrency() {
        return currency;
    }
}
