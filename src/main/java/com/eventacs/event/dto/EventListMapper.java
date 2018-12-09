package com.eventacs.event.dto;

import com.eventacs.event.model.Event;
import com.eventacs.event.model.EventList;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventListMapper {
    public EventList toModel(EventListDTO eventList) {

        List<Event> events = new ArrayList<>();

        if(eventList.getEvents() != null){
        eventList.getEvents().forEach(dto -> events.add(toEvent(dto)));
        }

        return new EventList(eventList.getListId(), eventList.getListName(), events);
    }

    private Event toEvent(EventDTO dto) {
        LocalDateTime start = dto.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dto.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime registerDate = dto.getRegisterDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return new Event(dto.getId(), dto.getName(), dto.getDescription(), dto.getCategory(), start, end, dto.getLogoUrl(), registerDate);
    }

    public EventListMapper() {
    }
}
