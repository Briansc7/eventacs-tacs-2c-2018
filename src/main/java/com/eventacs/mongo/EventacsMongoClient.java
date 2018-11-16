package com.eventacs.mongo;

import com.eventacs.event.model.EventList;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EventacsMongoClient {

    private MongoClient mongoClient;
    private DB database;
    private Morphia morphia;

    public EventacsMongoClient() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.database = mongoClient.getDB("eventacs");
        this.morphia = new Morphia();
    }


    public Datastore getDatastore(String dbName) {
        return morphia.createDatastore(mongoClient, dbName);
    }

    public DBCollection getCollection(String collection) {
        return database.getCollection(collection);
    }

    public <T> T getElementsAs(Class<T> clazz, Map<String, String> conditions) {
        List<DBObject> queryResult = new ArrayList<>();
        BasicDBObject searchQuery = new BasicDBObject();

        searchQuery.putAll(conditions);

        DBCursor cursor = collection.find(searchQuery);

        queryResult.add(cursor.getQuery());

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }
        queryResult.forEach(qr -> eventLists.add(morphia.fromDBObject(datastore, EventList .class, qr)));
        }
}