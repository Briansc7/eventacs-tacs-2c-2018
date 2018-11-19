package com.eventacs.event.repository;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.mongo.EventacsMongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EventListRepository {

    @Autowired
    private EventacsMongoClient eventacsMongoClient;

    public EventListRepository() {
    }

    public EventListRepository(EventacsMongoClient eventacsMongoClient) {
        this.eventacsMongoClient = eventacsMongoClient;
    }

    public List<EventList> getEventListByUserId(String userId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("userId", userId);

        return eventacsMongoClient.getElementsAs(EventList.class, conditions, "eventLists", "eventacs");
    }

    public void createEventList(EventListCreationDTO eventListCreationDTO, String listId) {
        Map<String, Object> documentElements =  new HashMap<>();

        documentElements.put("userId", eventListCreationDTO.getUserId());
        documentElements.put("listName", eventListCreationDTO.getListName());
        documentElements.put("listId", listId);
        documentElements.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea

        eventacsMongoClient.createDocument("eventList", documentElements);
    }

    public void addEventsToEventList(Event event, String listId) {
        Map<String, Object> documentElements =  new HashMap<>();
        documentElements.put("events", event);

        eventacsMongoClient.update("listId", listId,documentElements, "eventLists");
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

        List<EventList> eventLists = eventacsMongoClient.getElementsAs(EventList.class, conditions, "eventLists", "eventacs");

        if(eventLists.size() != 0){
            return eventLists.get(0).getEvents();
        } else {
            throw new EventListNotFound("EventList not found for this listId:" + listId);
        }
    }
}