package com.eventacs.event.repository;

import com.eventacs.event.dao.EventDao;
import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.model.EventList;
import com.mongodb.*;
import org.jongo.Jongo;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

@Entity(value = "eventLists", noClassnameStored = true)
public class EventListRepository {
//    @Id
//    private String id;
//    private String userId;
//    private String listName;
//    private List<EventDao> events;

    public EventListRepository(){}

    public List<EventList> getEventListByUserId(String userId) {
        Morphia morphia = new Morphia();
        List<DBObject> queryResult = new ArrayList<>();
        List<EventList> eventLists = new ArrayList<>();

        MongoClient mongoClient = new MongoClient("localhost", 27017); // tengo que ver como hacer un modulito de conexión
        Datastore datastore = morphia.createDatastore(mongoClient, "eventList");
        DB database = mongoClient.getDB("eventacs");
        database.createCollection("eventLists", null);

        DBCollection collection = database.getCollection("eventLists");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("userId", userId);
        DBCursor cursor = collection.find(searchQuery);

        queryResult.add(cursor.getQuery());

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }

        queryResult.forEach(qr -> eventLists.add(morphia.fromDBObject(datastore, EventList.class, qr)));

        return eventLists;
    }

    public void createEventList(EventListCreationDTO eventListCreationDTO) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("eventacs");
        DBCollection collection = database.getCollection("eventList");
        BasicDBObject document = new BasicDBObject();
        document.put("userId", eventListCreationDTO.getUserId());
        document.put("listName", eventListCreationDTO.getListName());
        document.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea
        collection.insert(document);
    }
}