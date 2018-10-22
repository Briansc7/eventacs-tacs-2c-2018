package com.eventacs.event.model;

import java.util.List;

public class EventsResponse {

    private EventsMetadata metadata;
    private List<Event> events;

    public EventsResponse() {
    }

    public EventsResponse(EventsMetadata metadata, List<Event> events) {
        this.metadata = metadata;
        this.events = events;
    }

    public EventsMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(EventsMetadata metadata) {
        this.metadata = metadata;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
