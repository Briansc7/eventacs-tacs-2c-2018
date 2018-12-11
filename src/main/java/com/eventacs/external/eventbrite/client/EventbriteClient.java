package com.eventacs.external.eventbrite.client;

import com.eventacs.external.eventbrite.model.EventbriteEventsResponse;
import com.eventacs.external.eventbrite.model.*;
import com.eventacs.httpclient.RestClient;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public EventbriteEventsResponse getEvents(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {

        Pagination pagination = new Pagination();

        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();

        pathParts.add("/v3");
        pathParts.add("/events");
        pathParts.add("/search");

        keyword.map(k -> parameters.put("q", k));
        categories.map(c -> parameters.put("categories", String.join(",", c)));
        startDate.map(this::toLocalDateTime).map(s -> parameters.put("start_date.range_start", s.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        endDate.map(this::toLocalDateTime).map(e -> parameters.put("start_date.range_end", e.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));

        List<EventResponse> events = new ArrayList<>();

        do {

            parameters.put("page", String.valueOf(pagination.getPage() + 1));

            PaginatedEvents paginatedEvents = this.restClient.get(this.buildURI(pathParts, parameters), PaginatedEvents.class);

            events.addAll(paginatedEvents.getEventsResponse());

            pagination = paginatedEvents.getPagination();

        } while (pagination.getHasMoreItems());

        return new EventbriteEventsResponse(pagination, events);

    }

    public EventbriteEventsResponse getEventsByPage(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, BigInteger page) {

        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();

        pathParts.add("/v3");
        pathParts.add("/events");
        pathParts.add("/search");

        keyword.map(k -> parameters.put("q", k));
        categories.map(c -> parameters.put("categories", String.join(",", c)));
        startDate.map(this::toLocalDateTime).map(s -> parameters.put("start_date.range_start", s.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        endDate.map(this::toLocalDateTime).map(e -> parameters.put("start_date.range_end", e.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        parameters.put("page", String.valueOf(page));

        PaginatedEvents events = this.restClient.get(this.buildURI(pathParts, parameters), PaginatedEvents.class);

        return new EventbriteEventsResponse(events.getPagination(), events.getEventsResponse());

    }

    public EventbriteEventsResponse getEventsByChangedDate(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, LocalDate changedDate, BigInteger page) {

        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();

        pathParts.add("/v3");
        pathParts.add("/events");
        pathParts.add("/search");

        keyword.map(k -> parameters.put("q", k));
        categories.map(c -> parameters.put("categories", String.join(",", c)));
        startDate.map(this::toLocalDateTime).map(s -> parameters.put("start_date.range_start", s.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        endDate.map(this::toLocalDateTime).map(e -> parameters.put("start_date.range_end", e.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        endDate.map(this::toLocalDateTime).map(e -> parameters.put("date_modified.range_start", e.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':'mm':'ss"))));
        parameters.put("page", String.valueOf(page));

        PaginatedEvents events = this.restClient.get(this.buildURI(pathParts, parameters), PaginatedEvents.class);

        return new EventbriteEventsResponse(events.getPagination(), events.getEventsResponse());

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

    public EventResponse getEvent(String eventId) {
        List<String> pathParts = new ArrayList<>();
        Map<String, String> parameters = new HashMap<>();
        pathParts.add("/v3");
        pathParts.add("/events/" + eventId);

        EventResponse response = restClient.get(this.buildURI(pathParts, parameters),
                                        EventResponse.class);
        return response;
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

    private LocalDateTime toLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
    }

}
