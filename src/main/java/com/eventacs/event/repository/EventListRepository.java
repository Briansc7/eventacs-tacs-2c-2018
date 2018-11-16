package com.eventacs.event.repository;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.mongo.EventacsMongoClient;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
        List<EventList> eventLists = new ArrayList<>();

        Datastore datastore = eventacsMongoClient.getDatastore("eventacs");

        DBCollection collection = eventacsMongoClient.getCollection("eventLists");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("userId", userId);
        DBCursor cursor = collection.find(searchQuery);

        queryResult.add(cursor.getQuery());

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }

        eventacsMongoClient.getElementsAs();

        return eventLists;
    }

    public void createEventList(EventListCreationDTO eventListCreationDTO) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("eventacs");
        DBCollection collection = database.getCollection("eventList");
        BasicDBObject document = new BasicDBObject();
        document.put("userId", eventListCreationDTO.getUserId());
        document.put("listName", eventListCreationDTO.getListName());
        document.put("listId", ""); //ver como hacer para que sea incremental y se fije cual es la última lista que se creo de todas para que el id no se pise.
        document.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea
        collection.insert(document);
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