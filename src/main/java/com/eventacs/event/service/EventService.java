package com.eventacs.event.service;

import com.eventacs.event.model.EventListCreationDTO;
import com.eventacs.event.model.Timelapse;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.event.model.Event;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventService {
    //TODO por ahora esto es para suplantar el tema de tener que buscar en base el último id.
    public Integer autoIncrementalListId = 1;

    @Autowired
    private UserService userService;

    @Autowired
    private EventbriteFacade eventbriteFacade;

    public EventService(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public List<Event> getEvents(String keyWord, List<String> categories, LocalDate startDate, LocalDate endDate) {
        return this.eventbriteFacade.getEvents(keyWord, categories, startDate, endDate);
    }

    public String createEventList(EventListCreationDTO eventListCreation) {
        String listId = listIdGenerator(eventListCreation.getUserId());
        try {
        userService.addEventList(eventListCreation, listId);
        return listId;
        } catch (UserNotFound e){
            autoIncrementalListId --;
            throw e;
        }
    }

    public void addEvent(String listId, String eventId, String userId) {
        Event event = getEvent(eventId);
        userService.addEvent(listId, event, userId);
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
        events.add(new Event("id1", "name1", "someDesc", "someCategory", LocalDateTime.now(), LocalDateTime.now(), "logoUrl"));
        events.add(new Event("id2", "name2", "someDesc", "someCategory", LocalDateTime.now(), LocalDateTime.now(),"logoUrl"));

        return events;
    }

    private String listIdGenerator(String userId) {
        //TODO Esto debería primero ir a la base para ver cual es el último id para darselo a ésta lista
        String id = (autoIncrementalListId ++).toString();
        return "U" + userId + "L" + id;
    }

    public Event getEvent(String eventId) {
        return this.eventbriteFacade.getEvent(eventId);
    }
}
