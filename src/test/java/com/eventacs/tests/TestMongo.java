package com.eventacs.tests;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.repository.AlarmsRepository;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.event.service.EventService;
import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.dto.AlarmDAO;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TestMongo {


    private EventListRepository repository;
    private AlarmsRepository alarmsRepository;
    private EventListCreationDTO eventListCreationDTO;
    private AlarmDTO alarmDTO;
    private SearchDTO searchDTO;
    private EventService eventService;


    @Test
    public void CreatingTwoEventlistAndAddingEventsShouldReturnNonEmptyEventsTest() {
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("User3", "Tiraste gas");

        repository.createEventList(eventListCreationDTO, 100L);
        repository.createEventList(new EventListCreationDTO("User2", "lista"), 200L);

        repository.addEventsToEventList(new Event("98765", "alto event", "un re evento", "900", LocalDateTime.now(), LocalDateTime.now(), "http", LocalDateTime.now(), LocalDateTime.now()), 100L);
        repository.addEventsToEventList(new Event("999", "coco", "teatro", "900", LocalDateTime.now(), LocalDateTime.now(), "http", LocalDateTime.now(), LocalDateTime.now()), 100L);
        repository.addEventsToEventList(new Event("0000", "avangers", "cine", "900", LocalDateTime.now(), LocalDateTime.now(), "http", LocalDateTime.now(), LocalDateTime.now()), 200L);
        List<Event> list = repository.getEventsListByListId(100L);
        List<Event> list2 = repository.getEventsListByListId(200L);

        Assert.assertTrue(!list.isEmpty());
        Assert.assertTrue(!list2.isEmpty());
        Assert.assertEquals("0000", list2.get(0).getId());
    }
    
    @Test
    public void ChangeEventListNameTest(){
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("Figo", "ESTAMOS MELOS");

        repository.createEventList(eventListCreationDTO, 10L);
        repository.changeListName(10L, "CARP");

        EventList eventList = repository.getEventListByListId(10L);

        Assert.assertEquals("CARP",eventList.getListName());
    }

    @Test(expected = EventListNotFound.class)
    public void DeleteEventListTest(){
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("Chebi", "Favoritos");

        repository.createEventList(eventListCreationDTO, 99L);
        repository.deleteEventList(99L);

       // Acá debería romper porque no encuentra la event list
        repository.getEventListByListId(99L);

    }

    @Test
    public void alarmTest(){
        alarmsRepository = new AlarmsRepository(new EventacsMongoClient());

        searchDTO = new SearchDTO(Optional.empty(), Optional.empty(), Optional.of(LocalDate.now()), Optional.of(LocalDate.now()), Optional.of(LocalDate.now()), "AlarmaFigo");

        alarmsRepository.createAlarm(searchDTO,"User1", 1L);
        List<AlarmDAO> alarms = alarmsRepository.findAll();
        Assert.assertEquals(1, alarms.size());
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
        if(repository != null) {
            repository.dropDatabase();
        } else {
            alarmsRepository.dropDatabase();
        }
    }

}
