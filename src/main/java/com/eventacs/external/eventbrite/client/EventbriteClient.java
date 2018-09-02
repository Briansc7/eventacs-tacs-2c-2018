package com.eventacs.external.eventbrite.client;

import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.model.NameResponse;
import com.eventacs.httpclient.RestClient;
import com.eventacs.event.EventMapper;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventbriteClient {

    @Autowired
    private RestClient restClient;

    @Autowired
    private EventMapper eventMapper;

    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    public EventbriteClient(RestClient restClient, EventMapper eventMapper) {
        this.restClient = restClient;
        this.eventMapper = eventMapper;
    }

    public List<Event> getEvents(List<String> criterias) {

        // TODO aca hay que armar un request y llamar via REST con el restClient
        ArrayList<EventResponse> events = new ArrayList<>();

        events.add(new EventResponse(new NameResponse("nametest1"), "idtest1"));
        events.add(new EventResponse(new NameResponse("nametest2"), "idtest2"));

        return events.stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList());

    }

}
