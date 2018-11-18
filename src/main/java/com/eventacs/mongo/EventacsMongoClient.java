package com.eventacs.mongo;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.stereotype.Component;

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

    public void addEventToEventList(Map<String, Object> documentElements, String listId) {
        BasicDBObject query = new BasicDBObject();
        DBCollection collection = this.getCollection("eventLists");
        BasicDBObject newDocument = new BasicDBObject();
        BasicDBObject updateObject = new BasicDBObject();

        query.put("listId", listId);
        newDocument.putAll(documentElements);
        updateObject.put("$set", newDocument);

        collection.update(query, updateObject);
    }
}