package com.eventacs.event.model;

import java.util.List;

public class EventsResponse {

    private EventsMetadata eventsMetadata;
    private List<Event> events;

    public EventsResponse() {

    }

    public EventsResponse(EventsMetadata eventsMetadata, List<Event> events) {
        this.eventsMetadata = eventsMetadata;
        this.events = events;
    }

    public EventsMetadata getEventsMetadata() {
        return eventsMetadata;
    }

    public void setEventsMetadata(EventsMetadata eventsMetadata) {
        this.eventsMetadata = eventsMetadata;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
