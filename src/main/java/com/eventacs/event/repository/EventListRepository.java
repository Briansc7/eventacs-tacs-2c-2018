package com.eventacs.event.repository;

import com.eventacs.event.dto.EventDTO;
import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.exception.EventAlreadyExistInEventList;
import com.eventacs.event.exception.EventListAlreadyExists;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.dto.UserInfoDTO;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

        try {
            getEventListByListId(listId);
            throw new EventListAlreadyExists("Event List already created for this user with this id! " + listId + ". This shouldn't happen, are you in a test?");
        } catch (EventListNotFound e) {
        Map<String, Object> documentElements =  new HashMap<>();

        documentElements.put("userId", eventListCreationDTO.getUserId());
        documentElements.put("listName", eventListCreationDTO.getListName());
        documentElements.put("listId", listId);
        documentElements.put("events", new ArrayList<>()); // no va a tener eventos la primera vez q la crea

        eventacsMongoClient.createDocument("eventLists", documentElements);
        }
    }

    public void addEventsToEventList(Event event, String listId) {
        BasicDBList dbEvents = new BasicDBList();
        List<Event> events = getEventsListByListId(listId);

        if(events == null){
            dbEvents.add(toJson(event));
        } else if(getEventsListByListId(listId).stream().noneMatch(e -> e.getId().equals(event.getId()))){
            events.add(event);
            events.forEach(e -> dbEvents.add(toJson(e)));
        } else {
            throw new EventAlreadyExistInEventList("This event already is in your event list! Event id: " + event.getId());
        }

        eventacsMongoClient.addEvents("listId", listId, dbEvents, "eventLists");
    }

    private BasicDBObject toJson(Event e) {
        BasicDBObject dbEvent =  new BasicDBObject();

        dbEvent.append("id", e.getId());
        dbEvent.append("name", e.getName());
        dbEvent.append("description", e.getDescription());
        dbEvent.append("category", e.getCategory());
        dbEvent.append("logoUrl", e.getLogoUrl());
        dbEvent.append("end", Date.from(e.getEnd().atZone(ZoneId.systemDefault()).toInstant()).toString());
        dbEvent.append("start", Date.from(e.getEnd().atZone(ZoneId.systemDefault()).toInstant()).toString());

        return dbEvent;
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

    public List<Event> getEventsListByListId(String listId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("listId", listId);

        List<EventListDTO> eventLists = eventacsMongoClient.getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");

        if(!eventLists.isEmpty()){
            return eventLists.stream().map(el -> eventListMapper.toModel(el)).flatMap(list -> list.getEvents().stream()).collect(Collectors.toList());

        } else {
            throw new EventListNotFound("EventList not found for this listId:" + listId);
        }
    }

    public EventList getEventListByListId(String listId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("listId", listId);

        List<EventListDTO> eventLists = eventacsMongoClient.getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");

        if(!eventLists.isEmpty()){
            return eventLists.stream().map(el -> eventListMapper.toModel(el)).collect(Collectors.toList()).get(0);

        } else {
            throw new EventListNotFound("EventList not found for this listId: " + listId);
        }
    }

    public int getEventsCountByTime(String listId) {
        List<EventListDTO> eventLists = eventacsMongoClient.getAllElements(EventListDTO.class, "eventLists", "eventacs");

        if(!eventLists.isEmpty()){
            return eventLists.stream().map(el -> el.getEvents()).flatMap(List::stream).filter(event-> getEventtWithCondition(event)).collect(Collectors.toList()).size();

        } else {
            throw new EventListNotFound("EventList not found for this listId: " + listId);
        }
    }

    private boolean getEventtWithCondition(EventDTO event) {
        return true;//event.getRegisterDate().after(LocalDate.now().minusDays(7));
    }

    public void dropDatabase(){
        this.eventacsMongoClient.dropDatabase();
    }

    public List<EventList> getEventsListsByEventId(String eventId) {

        //List<EventListDTO> eventLists = eventacsMongoClient.getElementsAs(EventListDTO.class, conditions, "eventLists", "eventacs");

        //List<EventListDTO> eventLists = eventacsMongoClient.getAllElements();
        List<EventListDTO> eventListsDTO = eventacsMongoClient.getAllElements(EventListDTO.class, "eventLists", "eventacs");
        List<EventListDTO> eventListDTOMatch = eventListsDTO.stream().filter(eventListDTO -> eventListDTO.existEventInList(eventListDTO.getEvents(),eventId)).collect(Collectors.toList());
        List<EventList> eventList = eventListDTOMatch.stream().map(el -> eventListMapper.toModel(el)).collect(Collectors.toList());

        if(!eventList.isEmpty()){

             //stream().filter(eventDTO -> eventDTO.getId().equals(eventId)));
            //eventLists.stream().map(el -> eventListMapper.toModel(el)).collect(Collectors.toList());
            return eventList;

        } else {
            throw new EventListNotFound("Event not found for this eventId: " + eventId);
        }
    }

}