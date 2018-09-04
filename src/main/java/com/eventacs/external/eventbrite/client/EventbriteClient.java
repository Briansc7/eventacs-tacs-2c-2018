package com.eventacs.external.eventbrite.client;

import com.eventacs.external.eventbrite.model.NameResponse;
import com.eventacs.httpclient.RestClient;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventbriteClient {

    @Autowired
    private RestClient restClient;

    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    public EventbriteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<EventResponse> getEvents(List<String> criterias) {

        // TODO aca hay que armar un request y llamar via REST con el restClient
        ArrayList<EventResponse> events = new ArrayList<>();

        events.add(new EventResponse(new NameResponse("nametest1"), "idtest1", "CINE", "someCategory", "someStartDate", "someendDate","logoUrl"));
        events.add(new EventResponse(new NameResponse("nametest2"), "idtest2", "TEATRO", "someCategory", "someStartDate", "someendDate","logoUrl"));

        return events;

    }

}
