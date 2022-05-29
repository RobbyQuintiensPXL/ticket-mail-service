package be.jevent.mailservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketDTO {

    private final Long id;
    private final Long eventId;
    private final String ticketUserName;
    private final String firstName;
    private final String name;
    private final String email;
    private final String eventName;
    private final String eventType;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private final LocalDate eventDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private final LocalTime eventTime;
    private final double price;
    private final String buildingName;

    public TicketDTO(Long id, Long eventId, String ticketUserName, String firstName,
                     String name, String email, String eventName, String eventType,
                     LocalDate eventDate, LocalTime eventTime, double price, String buildingName) {
        this.id = id;
        this.eventId = eventId;
        this.ticketUserName = ticketUserName;
        this.firstName = firstName;
        this.name = name;
        this.email = email;
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.price = price;
        this.buildingName = buildingName;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getTicketUserName() {
        return ticketUserName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public double getPrice() {
        return price;
    }

    public String getBuildingName() {
        return buildingName;
    }
}
