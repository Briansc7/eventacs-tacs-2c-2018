package com.eventacs.event.repository;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.mongo.EventacsMongoClient;
import com.mongodb.*;
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

    public void createEventList(EventListCreationDTO eventListCreationDTO) {
        Map<String, Object> documentElements =  new HashMap<>();

        documentElements.put("userId", eventListCreationDTO.getUserId());
        documentElements.put("listName", eventListCreationDTO.getListName());
        documentElements.put("listId", ""); //ver como hacer para que sea incremental y se fije cual es la última lista que se creo de todas para que el id no se pise.
        documentElements.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea

        eventacsMongoClient.createDocument("eventList", documentElements);
    }

    public void addEventsToEventList(Event event, String listId) {
        //borrar event dao, que reciba el evento a agregar como Event y que lo inserte como put en la base tipo update!


        private String eventId;
        private String name;
        private String description;
        private String category;
        private String logoUrl;
        private LocalDateTime start;
        private LocalDateTime end;
    }
}