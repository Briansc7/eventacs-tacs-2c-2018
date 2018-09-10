package com.eventacs.external.eventbrite.facade;

import com.eventacs.event.model.Event;
import com.eventacs.exception.ConectionErrorException;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.model.EventResponse;
import com.eventacs.external.eventbrite.model.PaginatedEvents;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventbriteFacade {

    @Autowired
    private EventbriteClient eventbriteClient;

    @Autowired
    private EventMapper eventMapper;

    private ObjectMapper mapper = new ObjectMapper();

    public EventbriteFacade(EventbriteClient eventbriteClient, EventMapper eventMapper) {
        this.eventbriteClient = eventbriteClient;
        this.eventMapper = eventMapper;
    }

    public List<Event> getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate) {

        List<EventResponse> eventResponses = this.eventbriteClient.getEvents(keyWord, categories, startDate, endDate, 1).getEventsResponse();
        return eventResponses.stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList());
    }

}