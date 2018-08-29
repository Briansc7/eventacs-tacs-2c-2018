package com.eventacs.service;

import com.eventacs.httpclient.HttpClientImpl;

import java.util.List;

public class EventbriteClient {

    private HttpClientImpl httpClient;
    private EventMapper eventMapper;

    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    private List<EventResponse> getEvents() {
        return httpClient.get("").parseAs(EventResponse.class);
    }

}
