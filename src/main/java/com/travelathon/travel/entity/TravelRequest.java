package com.travelathon.travel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "travel_requests")
public class TravelRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /* USER INFO */
    private String name;
    private String email;
    private String phone;

    /* TRAVEL INFO */
    private String fromCity;
    private String toCity;

    private Integer days;
    private Integer travelers;

    @Enumerated(EnumType.STRING)
    private EventCategory eventType;

    /* PRICING */
    @Column(precision = 10, scale = 2)
    private BigDecimal estimatedPrice;

    private String currency = "INR";

    private Boolean confirmed = false;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /* Getters & Setters */

    public UUID getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getFromCity() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }

    public String getToCity() { return toCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }

    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }

    public Integer getTravelers() { return travelers; }
    public void setTravelers(Integer travelers) { this.travelers = travelers; }

    public EventCategory getEventType() { return eventType; }
    public void setEventType(EventCategory eventType) { this.eventType = eventType; }

    public BigDecimal getEstimatedPrice() { return estimatedPrice; }
    public void setEstimatedPrice(BigDecimal estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Boolean getConfirmed() { return confirmed; }
    public void setConfirmed(Boolean confirmed) { this.confirmed = confirmed; }
}
