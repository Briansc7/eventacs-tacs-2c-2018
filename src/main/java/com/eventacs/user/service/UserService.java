package com.eventacs.user.service;

import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.exception.AlarmCreationError;
import com.eventacs.user.exception.EventListNotFound;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.user.repository.AlarmsRepository;
import com.eventacs.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AlarmsRepository alarmsRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EventListsMapper eventListsMapper;

    @Autowired
    private AlarmsMapper alarmsMapper;

    @Autowired
    private EventListRepository eventListRepository;

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

    public EventList getEventList(String eventListId, String userId) {
        Optional<User> user = this.usersRepository.getByUserId(userId);

        List<EventList> eventLists = user.orElseThrow(() -> new UserNotFound("User " + userId + " not found")).getEvents();

        return eventLists.stream().filter(list -> list.getId().equals(eventListId)).findFirst().orElseThrow(() -> new EventListNotFound("ListID " + eventListId + " not found"));
    }

    public AlarmDTO createAlarm(SearchDTO searchDTO) {
        // TODO luego utilizar el id de la session del user para saber de quien es la nueva alarma
        UserInfoDTO user = this.getUsers().stream().findFirst().orElseThrow(() -> new UserNotFound("Users repository without users"));
        return this.alarmsMapper.fromModelToApi(this.alarmsRepository.createAlarm(user.getId(), searchDTO).orElseThrow(() -> new AlarmCreationError("Error occurred while creating alarm for User " + user.getId())));
    }

    public void addEventList(EventListCreationDTO eventListCreation, String listId) {
        Optional<User> user = this.usersRepository.getByUserId(eventListCreation.getUserId());

        if (user.isPresent()) {
            user.get().addEventList(eventListCreation.getListName(), listId);
            this.usersRepository.update(user.get());
            this.eventListRepository.createEventList(eventListCreation);
        } else {
            throw new UserNotFound("User " + eventListCreation.getUserId() + " not found");
        }
    }

    public void addEvent(String listId, Event event, String userId) {

        Optional<User> user = this.usersRepository.getByUserId(userId);

        List<EventList> eventListList = user.orElseThrow(() -> new UserNotFound("User " + userId + " not found")).getEvents();

        Optional<EventList> eventListOptional = eventListList.stream().filter(list -> list.getId().equals(listId)).findFirst();

        eventListOptional.orElseThrow(() -> new EventListNotFound("ListID " + listId + " not found for User " + userId)).getEvents().add(event);

        this.eventListRepository.addEventsToEventList(event, listId);
    }

    public String changeListName(String listId, String listName) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida
        this.usersRepository.getUsers().stream().flatMap(user -> user.getEvents().stream().filter(list -> list.getId().equals(listId))).forEach(list -> list.setListName(listName));
        return listId;
    }

    public String deleteEventList(String listId) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida

        List<User> filteredUsers = this.usersRepository.getUsers().stream().filter(u -> u.getEvents().stream().anyMatch(el -> el.getId().equals(listId))).collect(Collectors.toList());

        List<EventList> eventListsToBeRemoved = filteredUsers.stream().flatMap(u -> u.getEvents().stream().filter(el -> el.getId().contains(listId))).collect(Collectors.toList());

        if(filteredUsers.size() == 0 || eventListsToBeRemoved.size() == 0){
            throw new UserNotFound("User not found for this event list Id" + listId);
        } else {
            filteredUsers.get(0).getEvents().remove(eventListsToBeRemoved.get(0));
            return eventListsToBeRemoved.get(0).getId();
        }
    }

}