package com.eventacs.external.eventbrite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PaginatedEvents {

    private Pagination pagination;

    @JsonProperty("events")
    private List<EventResponse> eventsResponse;

    public PaginatedEvents() { }

    public PaginatedEvents(Pagination pagination, List<EventResponse> eventsResponse) {
        this.pagination = pagination;
        this.eventsResponse = eventsResponse;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<EventResponse> getEventsResponse() {
        return eventsResponse;
    }

    public void setEventsResponse(List<EventResponse> eventsResponse) {
        this.eventsResponse = eventsResponse;
    }

    public Boolean getHasMoreItems() {
        return pagination.getHasMoreItems();
    }

    public Integer getPageCount() {
        return pagination.getPageCount();
    }

    public Integer getPage() {
        return pagination.getPage();
    }

}
