package com.eventacs.external.eventbrite.mapping;

import com.eventacs.event.model.Event;
import com.eventacs.external.eventbrite.model.EventResponse;
import com.eventacs.external.eventbrite.model.Logo;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event fromResponseToModel(EventResponse eventResponse) {
        return new Event(eventResponse.getId(),
                         eventResponse.getName().getText(),
                         eventResponse.getDescription().getText(),
                         eventResponse.getCategory(),
                         eventResponse.getStart().getLocal(),
                         eventResponse.getEnd().getLocal(),
                         getLogoUrl(eventResponse));
    }

    private String getLogoUrl(EventResponse eventResponse) {
        if (eventResponse.getLogo() != null) {
            return eventResponse.getLogo().getUrl();
        } else {
            return "Logo url is not present";
        }
    }

}
