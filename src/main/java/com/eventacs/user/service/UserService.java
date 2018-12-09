package com.eventacs.user.service;

import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.exception.EventListNotFound;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.event.repository.AlarmsRepository;
import com.eventacs.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EventListsMapper eventListsMapper;

    @Autowired
    private AlarmsMapper alarmsMapper;

    @Autowired
    private EventListRepository eventListRepository;

    @Autowired
    private AlarmsRepository alarmsRepository;

    public UserService(UsersRepository usersRepository, UsersMapper usersMapper, AlarmsRepository alarmsRepository, AlarmsMapper alarmsMapper, EventListsMapper eventListsMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.alarmsRepository = alarmsRepository;
        this.alarmsMapper = alarmsMapper;
        this.eventListsMapper = eventListsMapper;
    }

    public UserInfoDTO getUser(String userId) {
        Optional<User> user = this.usersRepository.getByUserId(userId);
        return user.map(u -> this.usersMapper.fromModelToApi(u)).orElseThrow(() -> new UserNotFound("User " + userId + " not found"));
    }

    public List<UserInfoDTO> getUsers() {
        return this.usersRepository.getUsers().stream().map(user -> this.usersMapper.fromModelToApi(user)).collect(Collectors.toList());
    }

    public AlarmDTO createAlarm(SearchDTO searchDTO, String username) {
        // UserInfoDTO userInfo = this.getUsers().stream().filter(user -> user.getName().equals(username)).findFirst().orElseThrow(() -> new UserNotFound("User with name: " + username + " not found"));
        return alarmsRepository.createAlarm(searchDTO, username, alarmIdGenerator());
    }

    private String alarmIdGenerator() {
        return alarmsRepository.alarmIdGenerator().toString();
    }

    public void addEventList(EventListCreationDTO eventListCreation, String listId) {
        //Optional<User> user = this.usersRepository.getByUserId(eventListCreation.getUserId());

        //if (user.isPresent()) {
         //   this.usersRepository.update(user.get());
            this.eventListRepository.createEventList(eventListCreation, listId);
        //} else {
         //   throw new UserNotFound("User " + eventListCreation.getUserId() + " not found");
        //}
    }

    public void addEvent(String listId, Event event, String userId) {

        Optional<User> user = this.usersRepository.getByUserId(userId);

        //List<EventList> eventListList = user.orElseThrow(() -> new UserNotFound("User " + userId + " not found")).getEvents();

        //Optional<EventList> eventListOptional = eventListList.stream().filter(list -> list.getListId().equals(listId)).findFirst();

        //eventListOptional.orElseThrow(() -> new EventListNotFound("ListID " + listId + " not found for User " + userId)).getEvents().add(event);

        this.eventListRepository.addEventsToEventList(event, listId);
    }

    public String changeListName(String listId, String listName) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida
        this.usersRepository.getUsers().stream().flatMap(user -> user.getEvents().stream().filter(list -> list.getListId().equals(listId))).forEach(list -> list.setListName(listName));
        return this.eventListRepository.changeListName(listId, listName);
    }

    public String deleteEventList(String listId) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida

        /*List<User> filteredUsers = this.usersRepository.getUsers().stream().filter(u -> u.getEvents().stream().anyMatch(el -> el.getListId().equals(listId))).collect(Collectors.toList());
        List<EventList> eventListsToBeRemoved = filteredUsers.stream().flatMap(u -> u.getEvents().stream().filter(el -> el.getListId().contains(listId))).collect(Collectors.toList());

        if(filteredUsers.size() == 0 || eventListsToBeRemoved.size() == 0){
            throw new UserNotFound("User not found for this event list Id" + listId);
        } else {*/
            return this.eventListRepository.deleteEventList(listId);
        //}
    }

    public void setEventListRepository(EventListRepository eventListRepository) {
        this.eventListRepository = eventListRepository;
    }

    public BigDecimal countAlarms(String username) {
        return BigDecimal.valueOf(this.alarmsRepository.findAllByUserId(username).size());
    }

}