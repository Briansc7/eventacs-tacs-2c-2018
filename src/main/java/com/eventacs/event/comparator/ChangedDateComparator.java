package com.eventacs.event.comparator;

import com.eventacs.event.model.Event;

import java.util.Comparator;

public class ChangedDateComparator implements Comparator<Event> {

    private static final ChangedDateComparator CHANGED_DATE_COMPARATOR = new ChangedDateComparator();

    public static ChangedDateComparator getInstance() {
        return CHANGED_DATE_COMPARATOR;
    }

    private ChangedDateComparator() {
    }

    @Override
    public int compare(Event search, Event anotherSearch) {
      return search.getChanged().compareTo(anotherSearch.getChanged());
    }

}