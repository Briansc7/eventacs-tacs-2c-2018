package com.eventacs.external.eventbrite.facade;

import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EventbriteFacade {

    @Autowired
    private EventbriteClient eventbriteClient;

    public EventbriteFacade(EventbriteClient eventbriteClient) {
        this.eventbriteClient = eventbriteClient;
    }

    public List<Event> getEvents(List<String> criterias) {
        return this.eventbriteClient.getEvents(criterias);
    }

}
