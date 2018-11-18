package com.eventacs.external.eventbrite.model;

import com.eventacs.external.eventbrite.model.EventResponse;
import com.eventacs.external.eventbrite.model.Pagination;

import java.util.List;

public class EventbriteEventsResponse {

    private Pagination pagination;
    private List<EventResponse> eventResponses;

    public EventbriteEventsResponse() {

    }

    public EventbriteEventsResponse(Pagination pagination, List<EventResponse> eventResponses) {
        this.pagination = pagination;
        this.eventResponses = eventResponses;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<EventResponse> getEventResponses() {
        return eventResponses;
    }

    public void setEventResponses(List<EventResponse> eventResponses) {
        this.eventResponses = eventResponses;
    }
}
