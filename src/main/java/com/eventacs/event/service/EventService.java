package com.eventacs.event.service;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListDTO;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Category;
import com.eventacs.event.model.EventList;
import com.eventacs.event.model.Timelapse;
import com.eventacs.event.model.*;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.model.User;
import com.eventacs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Event> getEvents(Optional<String> keyWord, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
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
        return userService.changeListName(listId, listName);
    }

    public String deleteEventList(String listId) {
        return userService.deleteEventList(listId);
    }

    public BigDecimal count(Timelapse timelapse) {
        // TODO Consultar si se quiere saber la cantidad de eventos registrados en las listas de los usuarios.

        /*
        List<UserInfoDTO> users = this.userService.getUsers();
        List<EventList> eventLists = users.stream().flatMap(user -> user.getEvents()).collect(Collectors.toList());


        return this.userService.getUsers().stream().map(u -> u.getEvents()).collect(Collectors.toList()).size();
        */
        return BigDecimal.ONE;
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

    private Event getEvent(String eventId) {
        return this.eventbriteFacade.getEvent(eventId);
    }

    public List<Category> getCategories() {
        return this.eventbriteFacade.getCategories();
    }

    public EventList getEventList(String listId) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida

        UserInfoDTO user = this.userService.getUsers().stream().findFirst().orElseThrow(() -> new UserNotFound("Repository without users"));

        return user.getEvents().stream().filter(list -> list.getId().equals(listId)).findFirst().orElseThrow(() -> new EventListNotFound("EventList " + listId + " not found"));

    }

}
