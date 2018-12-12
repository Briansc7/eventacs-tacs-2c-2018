package com.eventacs.event.dto;

import com.mysql.cj.util.StringUtils;
import org.mongodb.morphia.annotations.Id;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.api.client.repackaged.com.google.common.base.Strings.nullToEmpty;

public class EventListDTO {

    @Id
    private String _id;
    private Long listId;
    private String listName;
    private String userId;
    private List<EventDTO> events;

    public EventListDTO() {

    }

    public EventListDTO(Long listId, String listName, List<EventDTO> events) {
        this.listId = listId;
        this.listName = listName;
        this.events = events;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean existEventInList(List<EventDTO> events, String eventId) {

        //List<EventDTO>events.stream().filter(eventDTO -> !(eventDTO.isEmpty());
             try{
                 return events.stream().anyMatch(eventDTO -> eventId.contentEquals(eventDTO.getId()));}
             catch (NullPointerException npe)
             {
                 return false;
             }

    }
}
