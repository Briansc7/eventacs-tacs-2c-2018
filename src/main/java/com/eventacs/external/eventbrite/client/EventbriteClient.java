package com.eventacs.external.eventbrite.client;

import com.eventacs.exception.ConectionErrorException;
import com.eventacs.external.eventbrite.model.EventResponse;
import com.eventacs.external.eventbrite.model.PaginatedEvents;
import com.eventacs.httpclient.RestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class EventbriteClient {

    @Autowired
    private RestClient restClient;
    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(EventbriteClient.class);
    private static final String BASE_PATH = "https://www.eventbriteapi.com/v3";

    public EventbriteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public PaginatedEvents getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate, Integer page) {

        String url = BASE_PATH + "/events/search?" + addQueryParams(keyWord, categories, startDate, endDate);
        String response = restClient.getAllPaginatedItems(url, page);

        try {

            PaginatedEvents paginatedEvent = mapper.readValue(response, new TypeReference<PaginatedEvents>() {});
            return checkIfResponseHasMoreItems(paginatedEvent, url);

        } catch (IOException e) {
            LOGGER.error("Error mapping this events: " + response);
            throw new IllegalArgumentException(e);
        }
    }

    private String addQueryParams(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate) {

        String qs = "";

        if (keyWord != null) qs += "q=" + keyWord;
        if (categories != null) qs += "&categories=" + categories;
        if (startDate != null) qs += "&start_date.range_start=" + startDate;
        if (endDate != null) qs += "&start_date.range_end=" + endDate;

        LOGGER.info("Query params: " + qs);
        return qs;
    }


    private PaginatedEvents checkIfResponseHasMoreItems(PaginatedEvents paginatedEvents, String url) {
        try {

            List<EventResponse> eventResponses = paginatedEvents.getEventsResponse();

            if (paginatedEvents.getHasMoreItems() || paginatedEvents.getPage() < paginatedEvents.getPageCount()) {

                Integer page = paginatedEvents.getPage();

                String json = restClient.getAllPaginatedItems(url, page + 1);

                PaginatedEvents paginatedEventsResponse = mapper.readValue(json, new TypeReference<PaginatedEvents>() {});
                paginatedEventsResponse.getEventsResponse().addAll(eventResponses);

                return checkIfResponseHasMoreItems(paginatedEventsResponse, url);
            } else {
                return paginatedEvents;
            }
        } catch (IOException e) {
            throw new ConectionErrorException("Error conecting to the client.", e);
        }
    }

    public EventResponse getEvent(String eventId) {
        String url = BASE_PATH + "/events/" + eventId;
        String response = restClient.get(url);
        try {
            return mapper.readValue(response, new TypeReference<EventResponse>() {});
        } catch (IOException e) {
            LOGGER.error("Error mapping this events: " + response);
            throw new IllegalArgumentException(e);
        }
    }
}
