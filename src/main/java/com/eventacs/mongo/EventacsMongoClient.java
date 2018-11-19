package com.eventacs.mongo;

import com.eventacs.event.model.EventList;
import com.eventacs.user.exception.EventListNotFound;
import com.eventacs.user.exception.UserNotFound;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
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


    private Datastore getDatastore(String dbName) {
        return morphia.createDatastore(mongoClient, dbName);
    }

    private DBCollection getCollection(String collection) {
        return database.getCollection(collection);
    }

    public <T> List<T> getElementsAs(Class<T> clazz, Map<String, String> conditions, String collectionName, String dbName) {
        List<DBObject> queryResult = new ArrayList<>();
        BasicDBObject searchQuery = new BasicDBObject();
        Datastore datastore = this.getDatastore(dbName);
        List<T> result = new ArrayList<>();

        DBCollection collection = this.getCollection(collectionName);

        searchQuery.putAll(conditions);

        DBCursor cursor = collection.find(searchQuery);

        queryResult.add(cursor.getQuery());

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }

        queryResult.forEach(qr -> result.add(morphia.fromDBObject(datastore, clazz, qr)));

        return result;
    }

    public void createDocument(String collectionName, Map<String, Object> documentElements) {
        BasicDBObject document = new BasicDBObject();
        DBCollection collection = this.getCollection(collectionName);

        document.putAll(documentElements);
        collection.insert(document);
    }

    public String deleteEventList(String listId) {
        Map<String, String> conditions = new HashMap<>();
        BasicDBObject deleteQuery = new BasicDBObject();
        DBCollection collection = this.getCollection("eventLists");

        conditions.put("listId", listId);
        List<EventList> eventlists = getElementsAs(EventList.class, conditions, "eventLists", "eventacs");

        deleteQuery.put("listId", listId);

        if(eventlists.size() != 0) {
            collection.remove(deleteQuery);
            return eventlists.get(0).getId();
        } else {
            throw new EventListNotFound("User not found for this event list Id" + listId);
        }
    }

    public String update(String idName, String id, Map<String, Object> documentElements, String collectionName) {
        BasicDBObject query = new BasicDBObject();
        DBCollection collection = this.getCollection(collectionName);
        BasicDBObject newDocument = new BasicDBObject();
        BasicDBObject updateObject = new BasicDBObject();

        query.put(idName, id);
        newDocument.putAll(documentElements);
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);

        return id;
    }

    public Integer listIdGenerator() {

        if(getCollection("eventLists").count() == 0){
            return 1;
        } else {
            List<DBObject> queryResult = new ArrayList<>();
            BasicDBObject searchQuery = new BasicDBObject();
            BasicDBObject sorting = new BasicDBObject();
            Datastore datastore = this.getDatastore("eventacs");
            DBCollection collection = this.getCollection("eventLists");

            sorting.put("listId", -1);

            searchQuery.put("$orderby", sorting);

            DBCursor cursor = collection.find(searchQuery);

            queryResult.add(cursor.getQuery());

            int lastId = Integer.parseInt(morphia.fromDBObject(datastore, EventList.class, queryResult.get(0)).getId());

            return lastId + 1;
        }
    }

    public Integer alarmIdGenerator() {
        if(getCollection("alarms").count() == 0){
            return 1;
        } else {
            List<DBObject> queryResult = new ArrayList<>();
            BasicDBObject searchQuery = new BasicDBObject();
            BasicDBObject sorting = new BasicDBObject();
            Datastore datastore = this.getDatastore("eventacs");
            DBCollection collection = this.getCollection("alarms");

            sorting.put("alarmId", -1);

            searchQuery.put("$orderby", sorting);

            DBCursor cursor = collection.find(searchQuery);

            queryResult.add(cursor.getQuery());

            int lastId = Integer.parseInt(morphia.fromDBObject(datastore, EventList.class, queryResult.get(0)).getId());

            return lastId + 1;
        }
    }
}