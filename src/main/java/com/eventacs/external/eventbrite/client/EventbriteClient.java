package com.eventacs.external.eventbrite.client;

import com.eventacs.exception.ConectionErrorException;
import com.eventacs.external.eventbrite.model.*;
import com.eventacs.httpclient.RestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;

@Component
public class EventbriteClient {

    @Autowired
    private RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventbriteClient.class);
    private static final String HOST = "www.eventbriteapi.com"; // TODO Pasar host a archivo de configuracion
    private static final String PROTOCOL = "https";

    public EventbriteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<EventResponse> getEvents(Optional<String> keyWord, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {

        Pagination pagination = new Pagination();

        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();

        pathParts.add("/v3");
        pathParts.add("/events");
        pathParts.add("/search");

        keyWord.map(k -> parameters.put("q", k));
        categories.map(c -> parameters.put("categories", c.toString()));
        startDate.map(s -> parameters.put("start_date.range_start", s.toString()));
        endDate.map(e -> parameters.put("start_date.range_end", e.toString()));

        List<EventResponse> events = new ArrayList<>();

        do {

            parameters.put("page", String.valueOf(pagination.getPage() + 1));

            PaginatedEvents paginatedEvents = this.restClient.get(this.buildURI(pathParts, parameters),
                                                                       PaginatedEvents.class);

            events.addAll(paginatedEvents.getEventsResponse());

            pagination = paginatedEvents.getPagination();

        } while (pagination.getHasMoreItems());

        return events;

    }

    public List<CategoryResponse> getCategories() {

        Pagination pagination = new Pagination();

        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();

        pathParts.add("/v3");
        pathParts.add("/categories");

        List<CategoryResponse> categories = new ArrayList<>();

        do {

            parameters.put("page", String.valueOf(pagination.getPage() + 1));

            PaginatedCategories paginatedCategories = this.restClient.get(this.buildURI(pathParts, parameters),
                                                                                 PaginatedCategories.class);

            categories.addAll(paginatedCategories.getCategoryResponses());

            pagination = paginatedCategories.getPagination();

        } while (pagination.getHasMoreItems());

        return categories;

    }

    private URI buildURI(List<String> pathParts, Map<String, String> parameters) {

        URI uri = null;

        try {

            URIBuilder uriBuilder = new URIBuilder().setScheme(PROTOCOL)
                                                    .setHost(HOST)
                                                    .setPath(pathParts.stream().reduce("", (sem, part) -> sem.concat(part)));

            parameters.forEach((parameter, value) -> uriBuilder.setParameter(parameter, value));

            uri = uriBuilder.build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;

    }

<<<<<<< HEAD
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
=======
>>>>>>> cde28761a6d87f1e6cb69fe58bf65d85b05fa1d8
}
