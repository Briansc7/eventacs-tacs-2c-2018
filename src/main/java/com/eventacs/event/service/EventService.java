package com.eventacs.event.service;

import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.event.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventService {

    @Autowired
    private EventbriteFacade eventbriteFacade;

    public EventService(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public List<Event> getEvents(List<String> criterias) {
        return this.eventbriteFacade.getEvents(criterias);
    }

    public String createEventList(String userId, String listName) {

        //TODO agregar un list generator para ver que id de lista darle y voclarlo en la base.
        //TODO hacerle un add al userId que vien esta listId con ese listName

        return "U" + userId + "L1";
    }

    public void addEvent(String listId, String eventId) {
        //TODO agregar el evento ese en la lista esa.
    }

    public String changeListName(String listId, String listName) {
        //TODO a este listId cambiarle el nombre por listName

        return listId;
    }

    public String deleteEventList(String listId) {
        return listId;
    }
}
