package com.eventacs.event.service;

import com.eventacs.event.model.Timelapse;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.event.model.Event;
import com.eventacs.user.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class EventService {

    @Autowired
    private EventbriteFacade eventbriteFacade;

    public EventService(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public List<Event> getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate) {
        return this.eventbriteFacade.getEvents(keyWord, categories, startDate, endDate);
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

    public BigDecimal count(Timelapse timelapse) {
        // TODO Consultar si se quiere saber la cantidad de eventos registrados en las listas de los usuarios.

        return new BigDecimal(50);
    }

    public List<UserInfoDTO> getWatchers(String eventId) {
        // TODO Buscar todos los users que tengan ese eventId en alguna de sus listas de eventos

        List<UserInfoDTO> watchers = new ArrayList<>();
        watchers.add(new UserInfoDTO("id1", "name1", "lastname1", new ArrayList<>()));
        watchers.add(new UserInfoDTO("id2", "name2", "lastname2", new ArrayList<>()));

        return watchers;
    }

    public List<Event> getSharedEvents(String listId, String anotherListId) {
        // TODO buscar cada lista y encontrar eventos en comun

        List<Event> events = new ArrayList<>();
        events.add(new Event("id1", "name1", "someDesc", "someCategory",new Date(), new Date(), "logoUrl"));
        events.add(new Event("id2", "name2", "someDesc", "someCategory", new Date(), new Date(),"logoUrl"));

        return events;
    }

}
