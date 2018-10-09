package com.eventacs.user.repository;

import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import com.eventacs.external.telegram.client.Encriptador;
import com.eventacs.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepository {

    private List<User> users;

    public UsersRepository() {
        this.initRepository();
    }

    private void initRepository() {

        this.users = new ArrayList<>();

        Encriptador encriptador = new Encriptador();

        Event eventOne = new Event("id1", "name", "description", "category", LocalDateTime.now(), LocalDateTime.now(), "logo url");

        List<Event> eventsOne = new ArrayList<>();
        eventsOne.add(eventOne);

        EventList eventListOne = new EventList("id1", "name1", eventsOne);
        EventList eventListTwo = new EventList("id1", "name1", new ArrayList<>());

        List<EventList> eventListListOne = new ArrayList<>();
        List<EventList> eventListListTwo = new ArrayList<>();

        eventListListOne.add(eventListOne);
        eventListListTwo.add(eventListTwo);

        String password1 = encriptador.toShae256("Pw1");
        String password2 = encriptador.toShae256("Pw2");

        this.users.add(new User("User1", "name", "lastName", eventListListOne, password1));
        this.users.add(new User("User2", "name", "lastName", eventListListTwo, password2));

    }

    public Optional<User> getByUserId(String userId) {
        return this.users.stream().filter(user -> user.getId().equals(userId)).findFirst();
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void update(User user) {
        // TODO hacer el update a la base
    }

}
