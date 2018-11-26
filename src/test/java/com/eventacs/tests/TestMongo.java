package com.eventacs.tests;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.exception.EventListNotFound;
import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.mongo.EventacsMongoClient;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class TestMongo {


    EventListRepository repository;
    EventListCreationDTO eventListCreationDTO;


    @Test
    public void CreatingTwoEventlistAndAddingEventsShouldReturnNonEmptyEventsTest() {
        repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        eventListCreationDTO = new EventListCreationDTO("Figo", "Tiraste gas");

        repository.createEventList(eventListCreationDTO, "1");
        repository.createEventList(new EventListCreationDTO("brian", "lista"), "2");

        repository.addEventsToEventList(new Event("98765", "alto event", "un re evento", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "1");
        repository.addEventsToEventList(new Event("999", "coco", "teatro", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "1");
        repository.addEventsToEventList(new Event("0000", "avangers", "cine", "900", LocalDateTime.now(), LocalDateTime.now(), "http"), "2");
        List<Event> list = repository.getEventsListByListId("1");
        List<Event> list2 = repository.getEventsListByListId("2");

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

       // Acá debería romper porque no enceuntra la event list
        repository.getEventListByListId("99");

    }
}
