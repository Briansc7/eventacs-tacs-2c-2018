package com.eventacs.user.service;

import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.event.model.EventListCreationDTO;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserService {

    //TODO para suplantar base de usuarios por el momento
    public List<User> users = new ArrayList<>();

    public UserInfoDTO getUser(String userId) {
        List<User>  filteredUsers = users.stream().filter(u -> u.getId().contains(userId)).collect(Collectors.toList());

        if(filteredUsers.size() != 0){
            return toUserModel(filteredUsers.get(0));
        } else {
            throw new UserNotFound("User " + userId + " not found");
        }
    }

    private UserInfoDTO toUserModel(User user) {
        return new UserInfoDTO(user.getId(), user.getName(), user.getLastName(), user.getEvents());
    }

    public List<UserInfoDTO> getUsers() {

        List<UserInfoDTO> users = new ArrayList<>();
        users.add(new UserInfoDTO("testname1", "testpassword1", "lastName", new ArrayList<>()));
        users.add(new UserInfoDTO("testname2", "testpassword2", "lastName", new ArrayList<>()));

        return users;

    }

    public AlarmDTO createAlarm(String userId, SearchDTO searchDTO) {
        return new AlarmDTO("testid", userId, searchDTO);
    }

    public void addEventList(EventListCreationDTO eventListCreation, String listId) {
        //to have some users
        users.add(new User("1", "figo", "figo", new ArrayList<>()));
        users.add(new User("2", "figo", "figo", new ArrayList<>()));

        List<User> filteredUsers = users.stream().filter(u -> u.getId().contains(eventListCreation.getUserId())).collect(Collectors.toList());

        if(filteredUsers.size() > 0) {
            filteredUsers.forEach(user -> user.addEventList(eventListCreation.getListName(), listId));
        } else {
           throw new UserNotFound("User " + eventListCreation.getUserId() + " not found");
        }
    }

    public void addEvent(String listId, Event event, String userId) {
        List<EventList> eventListsList = users.stream().filter(u -> u.getId().contains(userId))
                                        .flatMap(user -> user.getEvents().stream()).collect(Collectors.toList());

        eventListsList.stream().filter(el -> el.getId().contains(listId)).forEach(el -> el.getEvents().add(event));
    }

    public String changeListName(String listId, String listName) {
        //TODO más adelante al manejar lo de sesion verificar que el listId que se cambia pertenece al userId que lo pida

        users.stream().flatMap(user -> user.getEvents().stream().filter(el -> el.getId().contains(listId))).forEach(list -> list.setListName(listName));
        return listId;
    }
}