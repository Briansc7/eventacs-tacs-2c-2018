package com.eventacs.external.eventbrite.client;

import com.eventacs.external.eventbrite.model.NameResponse;
import com.eventacs.httpclient.GetRequest;
import com.eventacs.httpclient.Request;
import com.eventacs.httpclient.RestClient;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EventbriteClient {

    @Autowired
    private RestClient restClient;

    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    public EventbriteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<EventResponse> getEvents(List<String> criterias) {

        List<String> queryParams = new ArrayList<>();
        List<String> pathVariables = new ArrayList<>();

        return new GetRequest<List<EventResponse>>(queryParams, pathVariables, BASE_PATH, restClient, Optional.empty()).execute();

    }

}
