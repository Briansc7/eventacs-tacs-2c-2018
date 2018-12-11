package com.eventacs.event.service;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListDTO;
import com.eventacs.event.model.Category;
import com.eventacs.event.model.EventList;
import com.eventacs.event.model.Timelapse;
import com.eventacs.event.model.*;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EventService {

    @Autowired
    private UserService userService;

    @Autowired
    private EventbriteFacade eventbriteFacade;

    @Autowired
    private EventListRepository eventListRepository;

    public EventService(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public EventsResponse getEvents(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<BigInteger> page) {
        return this.eventbriteFacade.getEvents(keyword, categories, startDate, endDate, page);
    }

    public EventsResponse getEventsByChangedDate(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate, Optional<BigInteger> page, LocalDate changedDate) {
        return this.eventbriteFacade.getEventsByChangedDate(keyword, categories, startDate, endDate, changedDate, page);
    }

    public String createEventList(EventListCreationDTO eventListCreation) {
        String listId = listIdGenerator();
        try {
            userService.addEventList(eventListCreation, listId);
            return listId;
        } catch (UserNotFound e){
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

    public int count(Timelapse timelapse) {
        // TODO Consultar si se quiere saber la cantidad de eventos registrados en las listas de los usuarios.
        // TODO falta filtrar por TIMELAPSE
        return eventListRepository.getEventsCountByTime(timelapse.getValue());
    }


    public int getWatchers(String eventId) {
        // TODO Buscar todos los users que tengan ese eventId en alguna de sus listas de eventos
        Set<String> hs = new HashSet<>();

        List<EventListDTO> eventList = eventListRepository.getEventsListsByEventId(eventId);
        List<String> listNames = eventList.stream().map(eventListID -> eventListID.getUserId()).collect(Collectors.toList());

        hs.addAll(listNames);
        listNames.clear();
        listNames.addAll(hs);

        return listNames.size();

       /* List<UserInfoDTO> watchers = new ArrayList<>();
        watchers.add(new UserInfoDTO("id1", "name1", "lastname1", new ArrayList<>()));
        watchers.add(new UserInfoDTO("id2", "name2", "lastname2", new ArrayList<>()));
        eventListRepository.getEventListByListId(listId);
        return watchers;*/
    }


    public List<Event> getSharedEvents(String listId, String anotherListId) {
        // TODO por ahora usar este user generico
        //UserInfoDTO user = this.userService.getUsers().stream().findFirst().orElseThrow(() -> new UserNotFound("Repository without users"));

        List<Event> events = eventListRepository.getEventsListByListId(listId);
        List<Event> moreEvents =eventListRepository.getEventsListByListId(anotherListId);



        List<Event> shared = new ArrayList<>();

        //por cada evento de una lista, si esta en la otra lista lo agrega a la lista shared
        events.forEach(e -> {

            if(moreEvents.stream().anyMatch(m->m.getId().equalsIgnoreCase(e.getId()))) {
                    shared.add(e);
                }
        }
        );


        return shared;

        //return events.stream().filter(moreEvents::contains).collect(Collectors.toList());

    }

    private String listIdGenerator() {
        return eventListRepository.listIdGenerator().toString();
    }

    public Event getEvent(String eventId) {
        return this.eventbriteFacade.getEvent(eventId);
    }

    public List<Category> getCategories() {
        return this.eventbriteFacade.getCategories();
    }

    public EventList getEventList(String listId) {
        //return eventListRepository.getEventListByUserId(userId);
        return eventListRepository.getEventListByListId(listId);
      // Si no devuelve nada el front debería decirle al chabon que cree una eventlist
    }

    public List<EventList> getEventLists(String userId) {
        return eventListRepository.getEventListByUserId(userId);

    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public EventbriteFacade getEventbriteFacade() {
        return eventbriteFacade;
    }

    public void setEventbriteFacade(EventbriteFacade eventbriteFacade) {
        this.eventbriteFacade = eventbriteFacade;
    }

    public EventListRepository getEventListRepository() {
        return eventListRepository;
    }

    public void setEventListRepository(EventListRepository eventListRepository) {
        this.eventListRepository = eventListRepository;
    }
}
