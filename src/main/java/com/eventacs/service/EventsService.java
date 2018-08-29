package com.eventacs.service;

import java.util.ArrayList;
import java.util.List;

public class EventsService {

    private EventsbriteFacade eventsbriteFacade;

    public List<Event> getEvents(List<String> criterias) {
        return eventsbriteFacade.getEvents(criterias);
    }

}
