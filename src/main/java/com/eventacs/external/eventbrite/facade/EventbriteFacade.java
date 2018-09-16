package com.eventacs.external.eventbrite.facade;

import com.eventacs.event.model.Category;
import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.model.CategoryResponse;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    public EventbriteFacade(EventbriteClient eventbriteClient, EventMapper eventMapper, CategoryMapper categoryMapper) {
        this.eventbriteClient = eventbriteClient;
        this.eventMapper = eventMapper;
        this.categoryMapper = categoryMapper;
    }

    public List<Event> getEvents(Optional<String> keyWord, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        List<EventResponse> eventResponses = this.eventbriteClient.getEvents(keyWord,
                                                                             categories,
                                                                             startDate,
                                                                             endDate);
        return eventResponses.stream().map(event -> this.eventMapper.fromResponseToModel(event)).collect(Collectors.toList());
    }

    public List<Category> getCategories() {
        List<CategoryResponse> categories = this.eventbriteClient.getCategories();
        return categories.stream().map(category -> this.categoryMapper.fromResponseToModel(category)).collect(Collectors.toList());
    }

}