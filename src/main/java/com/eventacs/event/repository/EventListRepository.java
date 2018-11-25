package com.eventacs.event.repository;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.mongo.EventacsMongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EventListRepository {

    @Autowired
    private EventacsMongoClient eventacsMongoClient;

    @Autowired
    private EventListMapper eventListMapper;

    public EventListRepository() {
    }

    public EventListRepository(EventacsMongoClient eventacsMongoClient, EventListMapper eventListMapper) {
        this.eventacsMongoClient = eventacsMongoClient;
        this.eventListMapper = eventListMapper;
    }

    public List<EventList> getEventListByUserId(String userId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("userId", userId);

        List<EventListDTO> eventLists = eventacsMongoClient.getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");
        return eventLists.stream().map(el -> eventListMapper.toModel(el)).collect(Collectors.toList());
    }

    public void createEventList(EventListCreationDTO eventListCreationDTO, String listId) {
        Map<String, Object> documentElements =  new HashMap<>();

        documentElements.put("userId", eventListCreationDTO.getUserId());
        documentElements.put("listName", eventListCreationDTO.getListName());
        documentElements.put("listId", listId);
        documentElements.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea

        eventacsMongoClient.createDocument("eventLists", documentElements);
    }

    public void addEventsToEventList(Event event, String listId) {
        Map<String, Map> documentElements =  new HashMap<>();
        Map<String, String> eventJson =  new HashMap<>();

        eventJson.put("id", event.getId());
        eventJson.put("name", event.getName());
        eventJson.put("description", event.getDescription());
        eventJson.put("category", event.getCategory());
        eventJson.put("logoUrl", event.getLogoUrl());
        eventJson.put("end", Date.from(event.getEnd().atZone(ZoneId.systemDefault()).toInstant()).toString());
        eventJson.put("start", Date.from(event.getEnd().atZone(ZoneId.systemDefault()).toInstant()).toString());

        documentElements.put("events", eventJson);

        eventacsMongoClient.addEvents("listId", listId, documentElements, "eventLists");
    }


    public String deleteEventList(String listId) {
        return eventacsMongoClient.deleteEventList(listId);
    }

    public String changeListName(String listId, String listName) {
        Map<String, Object> documentElements =  new HashMap<>();
        documentElements.put("listName", listName);
        return eventacsMongoClient.update("listId", listId, documentElements, "eventLists");
    }


    public Integer listIdGenerator() {
        return eventacsMongoClient.listIdGenerator();
    }

    public List<Event> getEventListByListId(String listId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("listId", listId);

        List<EventListDTO> eventLists = eventacsMongoClient.getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");

        if(!eventLists.isEmpty()){
            return eventLists.stream().map(el -> eventListMapper.toModel(el)).flatMap(list -> list.getEvents().stream()).collect(Collectors.toList());

        } else {
            throw new EventListNotFound("EventList not found for this listId:" + listId);
        }
    }
}