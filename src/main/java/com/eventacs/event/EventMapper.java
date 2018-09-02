package com.eventacs.event;

import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.model.EventResponse;

public class EventMapper {

    public Event fromResponseToModel(EventResponse eventResponse) {
        return new Event(eventResponse.getId(), eventResponse.getName().getText());
    }

}
