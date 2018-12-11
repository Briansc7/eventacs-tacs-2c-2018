package com.eventacs.user.repository;

import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.model.Alarm;
import com.eventacs.user.model.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlarmsRepository {

    private List<Alarm> alarms;

    public AlarmsRepository() {
        this.alarms = new ArrayList<>();
        this.alarms.add(new Alarm("alarmId1", "userId1", new Search(Optional.of("KeywordTest1"), Optional.of(new ArrayList<>()), Optional.empty(), Optional.empty(), Optional.empty())));
        this.alarms.add(new Alarm("alarmId2", "userId2", new Search(Optional.of("KeywordTest2"), Optional.of(new ArrayList<>()), Optional.empty(), Optional.empty(), Optional.empty())));
    }

    public Optional<Alarm> createAlarm(String userId, SearchDTO searchDTO) {
        // TODO verificar si se agrego correctamente, caso contrario devolver empty
        Alarm alarm = new Alarm("", userId, new Search(searchDTO.getKeyword(),
                                                       searchDTO.getCategories(),
                                                       searchDTO.getStartDate(),
                                                       searchDTO.getEndDate(),
                                                       searchDTO.getChanged()));
        this.alarms.add(alarm);
        return Optional.of(alarm);
    }

}
