package com.eventacs.event.repository;

import com.eventacs.event.dao.EventDao;
import com.eventacs.event.model.EventList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

@Entity(value = "eventLists", noClassnameStored = true)
public class EventListRepository {
    @Id
    private String id;
    private String userId;
    private String listName;
    private List<EventDao> events;

    public EventListRepository(String id, String userId, String listName, List<EventDao> events) {
        this.id = id;
        this.userId = userId;
        this.listName = listName;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<EventDao> getEvents() {
        return events;
    }

    public void setEvents(List<EventDao> events) {
        this.events = events;
    }

    public void getEventListByUserId(String userId) {
        Morphia morphia = new Morphia();
        Datastore datastore = morphia.createDatastore(new MongoClient(), "eventList"); //TODO Cree la collection pero la base como? al ponrele en dbName la crea?
        morphia.mapPackage("com.eventacs.event.repository");
        datastore.ensureIndexes();

        BasicDBObject query = new BasicDBObject("userId", userId);
        DBCursor cursor = datastore.getCollection(EventListRepository.class).find(query);


    }
}
