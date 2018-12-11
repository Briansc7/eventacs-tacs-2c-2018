package com.eventacs.external.eventbrite.facade;

import com.eventacs.event.model.Category;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventsMetadata;
import com.eventacs.event.model.EventsResponse;
import com.eventacs.external.eventbrite.mapping.PaginationMapper;
import com.eventacs.external.eventbrite.model.EventbriteEventsResponse;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.model.CategoryResponse;
import com.eventacs.external.eventbrite.model.EventResponse;
import com.eventacs.external.eventbrite.model.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventbriteFacade {

    @Autowired
    private EventbriteClient eventbriteClient;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private PaginationMapper paginationMapper;

    public EventbriteFacade(EventbriteClient eventbriteClient, EventMapper eventMapper, CategoryMapper categoryMapper, PaginationMapper paginationMapper) {
        this.eventbriteClient = eventbriteClient;
        this.eventMapper = eventMapper;
        this.categoryMapper = categoryMapper;
        this.paginationMapper = paginationMapper;
    }

    public EventsResponse getEvents(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<BigInteger> page) {
        EventbriteEventsResponse eventResponse = page.map(p -> this.eventbriteClient.getEventsByPage(keyword, categories, startDate, endDate, p))
                                                                .orElseGet(() -> this.eventbriteClient.getEvents(keyword, categories, startDate, endDate));

        return new EventsResponse(this.paginationMapper.fromPaginationToEventsMetadata(eventResponse.getPagination()),
                           eventResponse.getEventResponses().stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList()));
    }

    public EventsResponse getEventsByChangedDate(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, LocalDate changedDate, Optional<BigInteger> page) {
        EventbriteEventsResponse eventResponse = page.map(p -> this.eventbriteClient.getEventsByChangedDate(keyword, categories, startDate, endDate, changedDate, p))
                .orElseGet(() -> this.eventbriteClient.getEvents(keyword, categories, startDate, endDate));

        return new EventsResponse(this.paginationMapper.fromPaginationToEventsMetadata(eventResponse.getPagination()),
                eventResponse.getEventResponses().stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList()));
    }

    public Event getEvent(String eventId) {
        EventResponse eventResponse = this.eventbriteClient.getEvent(eventId);
        eventResponse.setRegisterDate(LocalDateTime.now());
        return eventMapper.fromResponseToModel(eventResponse);
    }

    public List<Category> getCategories() {
        List<CategoryResponse> categories = this.eventbriteClient.getCategories();
        return categories.stream().map(category -> this.categoryMapper.fromResponseToModel(category)).collect(Collectors.toList());
    }

}