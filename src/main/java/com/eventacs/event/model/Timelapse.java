package com.eventacs.event.model;

public enum Timelapse {
    TODAY(1), FEW_DAYS(4), WEEK(8), MONTH(31), ALL(4000);

    private int value;

    public int getValue(){
        return this.value;
    }

    Timelapse(int value) {
        this.value = value;
    }
}
