package com.eventacs.tests;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.repository.AlarmsRepository;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.event.service.EventService;
import com.eventacs.external.eventbrite.client.EventbriteClient;
import com.eventacs.external.eventbrite.facade.EventbriteFacade;
import com.eventacs.external.eventbrite.mapping.CategoryMapper;
import com.eventacs.external.eventbrite.mapping.EventMapper;
import com.eventacs.external.eventbrite.mapping.PaginationMapper;
import com.eventacs.httpclient.RestClient;
import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.user.repository.UsersRepository;
import com.eventacs.user.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class TestMongo {


    private EventListRepository repository;
    private EventListCreationDTO eventListCreationDTO;
    private EventService eventService;


    @Test
    public void CreatingTwoEventlistAndAddingEventsShouldReturnNonEmptyEventsTest() {
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("User3", "Tiraste gas");

        repository.createEventList(eventListCreationDTO, "100");
        repository.createEventList(new EventListCreationDTO("User2", "lista"), "200");

        repository.addEventsToEventList(new Event("98765", "alto event", "un re evento", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "100");
        repository.addEventsToEventList(new Event("999", "coco", "teatro", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "100");
        repository.addEventsToEventList(new Event("0000", "avangers", "cine", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "200");
        List<Event> list = repository.getEventsListByListId("100");
        List<Event> list2 = repository.getEventsListByListId("200");

        Assert.assertTrue(!list.isEmpty());
        Assert.assertTrue(!list2.isEmpty());
        Assert.assertEquals("0000", list2.get(0).getId());
    }
    
    @Test
    public void ChangeEventListNameTest(){
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("Figo", "Lista figo");

        repository.createEventList(eventListCreationDTO, "10");
        repository.changeListName("10", "CARP");

        EventList eventList = repository.getEventListByListId("10");

        Assert.assertEquals("CARP",eventList.getListName());
    }

    @Test(expected = EventListNotFound.class)
    public void DeleteEventListTest(){
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("Chebi", "Favoritos");

        repository.createEventList(eventListCreationDTO, "99");
        repository.deleteEventList("99");

       // Acá debería romper porque no encuentra la event list
        repository.getEventListByListId("99");

    }

//    @Test
//    public void CreateEventList(){
//        eventService = new EventService(new EventbriteFacade(new EventbriteClient(new RestClient()), new EventMapper(), new CategoryMapper(), new PaginationMapper()));
//        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());
//        UserService userService = new UserService(new UsersRepository(), new UsersMapper(), new AlarmsRepository(), new AlarmsMapper(), new EventListsMapper());
//        eventListCreationDTO = new EventListCreationDTO("User1", "Favoritos");
//
//        eventService.setEventListRepository(repository);
//        eventService.setUserService(userService);
//        userService.setEventListRepository(repository);
//
//        String id = eventService.createEventList(eventListCreationDTO);
//
//        eventService.createEventList(new EventListCreationDTO("User2", "alta lista"));
//
//        eventService.addEvent(id, "50567727434", "User2");
//
//        Assert.assertTrue(!eventService.getEventList(id).getEvents().isEmpty());
//    }

    @After
    public void after(){
        repository.dropDatabase();
    }

}
