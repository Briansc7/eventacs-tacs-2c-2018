package com.eventacs.mongo;

import com.eventacs.event.dto.EventListDTO;
import com.eventacs.httpclient.LocalDateTimeConverter;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.exception.EventListNotFound;
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
        this.mongoClient = new MongoClient("mongo", 27017);
        this.database = mongoClient.getDB("eventacs");
        this.morphia = new Morphia();
        morphia.getMapper().getConverters().addConverter(new LocalDateTimeConverter());
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

        cursor.getQuery();

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }

        morphia.map(clazz);
        queryResult.forEach(qr -> result.add(morphia.fromDBObject(datastore, clazz, qr)));

        return result;
    }

    public <T> List<T> getAllElements(Class<T> clazz, String collectionName, String dbName) {
        List<DBObject> queryResult = new ArrayList<>();
        Datastore datastore = this.getDatastore(dbName);
        List<T> result = new ArrayList<>();

        DBCollection collection = this.getCollection(collectionName);

        DBCursor cursor = collection.find();

        cursor.getQuery();

        while (cursor.hasNext()) {
            queryResult.add(cursor.next());
        }

        morphia.map(clazz);
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
        List<EventListDTO> eventlists = getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");

        deleteQuery.put("listId", listId);

        if(eventlists.size() != 0) {
            collection.remove(deleteQuery);
            return eventlists.get(0).getListId();
        } else {
            throw new EventListNotFound("User not found for this event list Id: " + listId);
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

    public String addEvents(String idName, String id, BasicDBList documentElements, String collectionName) {
        BasicDBObject query = new BasicDBObject();
        DBCollection collection = this.getCollection(collectionName);
        BasicDBObject updateObject = new BasicDBObject();

        query.put(idName, id);

        updateObject.append("$set", new BasicDBObject("events", documentElements));

        collection.update(query, updateObject);

        return id;
    }

    public Integer listIdGenerator() {

        if(getCollection("eventLists").count() == 0){
            return 1;
        } else {
            List<DBObject> queryResult = new ArrayList<>();
            BasicDBObject sorting = new BasicDBObject();
            Datastore datastore = this.getDatastore("eventacs");
            DBCollection collection = this.getCollection("eventLists");

            sorting.put("listId", -1);

            DBCursor cursor = collection.find().sort(sorting);

            cursor.getQuery();

            while (cursor.hasNext()) {
                queryResult.add(cursor.next());
            }

            morphia.map(EventListDTO.class);

            EventListDTO eventListDTO = morphia.fromDBObject(datastore, EventListDTO.class, queryResult.get(0));

            String id = eventListDTO.getListId();

            int lastId = Integer.parseInt(id);
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

            int lastId = Integer.parseInt(morphia.fromDBObject(datastore, AlarmDTO.class, queryResult.get(0)).getAlarmId().get());

            return lastId + 1;
        }
    }

    public void dropDatabase(){
        this.database.dropDatabase();
    }
}