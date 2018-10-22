package com.eventacs.event.model;

public class EventsMetadata {

    private Integer objectCount;
    private Integer pageCount;

    public EventsMetadata() {

    }

    public EventsMetadata(Integer objectCount, Integer pageCount) {
        this.objectCount = objectCount;
        this.pageCount = pageCount;
    }

    public Integer getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(Integer objectCount) {
        this.objectCount = objectCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

}
