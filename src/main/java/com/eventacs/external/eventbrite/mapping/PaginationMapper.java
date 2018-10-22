package com.eventacs.external.eventbrite.mapping;

import com.eventacs.event.model.EventsMetadata;
import com.eventacs.external.eventbrite.model.Pagination;

public class PaginationMapper {

    public EventsMetadata fromPaginationToEventsMetadata(Pagination pagination) {
        return new EventsMetadata(pagination.getObjectCount(), pagination.getPageCount());
    }

}
