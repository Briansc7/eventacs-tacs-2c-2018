package com.eventacs.server;

import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.dto.EventListMapper;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.mongo.EventacsMongoClient;
import org.springframework.stereotype.Component;

@Component
public class MongoContext {

    public MongoContext() {
    }

    public static void init() {

        EventListRepository repository = new EventListRepository(new EventacsMongoClient(), new EventListMapper());

        EventListCreationDTO eventList = new EventListCreationDTO("User1", "Favoritos");
        EventListCreationDTO otherEventList = new EventListCreationDTO("User2", "Teatros");

        repository.createEventList(eventList, "1");
        repository.createEventList(otherEventList, "2");
    }

}
