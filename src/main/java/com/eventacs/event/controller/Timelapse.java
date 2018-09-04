package com.eventacs.event.controller;

public enum Timelapse {
    TODAY("TODAY"), FEW_DAYS("FEW_DAYS"), WEEK("WEEK"), MONTH("MONTH"), ALL("ALL");

    private String value;

    public String getValue(){
        return this.value;
    }

    Timelapse(String value) {
        this.value = value;
    }
}
