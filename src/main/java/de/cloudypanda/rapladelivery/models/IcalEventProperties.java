package de.cloudypanda.rapladelivery.models;

import net.fortuna.ical4j.model.property.Uid;

import java.rmi.server.UID;
import java.time.LocalDateTime;

public class IcalEventProperties {

    /**
     * Id of the event
     */
    private Uid id;

    /**
     * Starttime in millis
     */
    private LocalDateTime startTime;

    /**
     * Endtime in millis
     */
    private LocalDateTime endTime;

    public Uid getId() {
        return id;
    }

    /**
     * Name of the event
     */
    private String name;

    /**
     * Description of the event
     */
    private String description;

    public IcalEventProperties(Uid id, LocalDateTime startTime, LocalDateTime endTime, String name, String description) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
