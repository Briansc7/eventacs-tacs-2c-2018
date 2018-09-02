package com.eventacs.event.service;

import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class EventService {

    @Autowired
    private EventbriteFacade eventbriteFacade;

    public EventService(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public List<Event> getEvents(List<String> criterias) {
        return this.eventbriteFacade.getEvents(criterias);
    }

}
