package com.eventacs.external.eventbrite.mapping;

import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.model.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event fromResponseToModel(EventResponse eventResponse) {
        return new Event(eventResponse.getId(), eventResponse.getName().getText());
    }

}
