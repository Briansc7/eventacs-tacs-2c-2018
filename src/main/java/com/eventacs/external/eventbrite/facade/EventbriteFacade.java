package com.eventacs.external.eventbrite.facade;

import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventbriteFacade {

    @Autowired
    private EventbriteClient eventbriteClient;

    @Autowired
    private EventMapper eventMapper;

    public EventbriteFacade(EventbriteClient eventbriteClient, EventMapper eventMapper) {
        this.eventbriteClient = eventbriteClient;
        this.eventMapper = eventMapper;
    }

    public List<Event> getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate) {

        List<EventResponse> events = this.eventbriteClient.getEvents(keyWord, categories, startDate, endDate);

        return events.stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList());
    }

}
