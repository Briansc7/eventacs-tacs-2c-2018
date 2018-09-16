package com.eventacs.external.eventbrite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDate {

     private Date local;

    public Date getLocal() {
        return local;
    }

    public void setLocal(Date local) {
        this.local = local;
    }

    public EventDate() {

    }
}
