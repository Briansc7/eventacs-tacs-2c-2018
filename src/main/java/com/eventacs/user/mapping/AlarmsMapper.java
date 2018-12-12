package com.eventacs.user.mapping;

import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.model.Alarm;
import com.eventacs.user.model.Search;

import java.util.Optional;

public class AlarmsMapper {

    public AlarmDTO fromModelToApi(Alarm alarm) {
        Search search = alarm.getSearch();
        return new AlarmDTO(Optional.of(alarm.getId()), alarm.getUserId(), new SearchDTO(search.getKeyword(),
                                                                                         search.getCategories(),
                                                                                         search.getStartDate(),
                                                                                         search.getEndDate(),
                                                                                         search.getChanged(),
                                                                                         search.getAlarmName()));
    }

    public Alarm fromApiToModel(AlarmDTO alarmDTO) {
        SearchDTO searchDTO = alarmDTO.getSearch();
        return new Alarm(alarmDTO.getAlarmId().orElse(0L), alarmDTO.getUserId(), new Search(searchDTO.getKeyword(),
                                                                                       searchDTO.getCategories(),
                                                                                       searchDTO.getStartDate(),
                                                                                       searchDTO.getEndDate(),
                                                                                       searchDTO.getChanged(),
                                                                                       searchDTO.getAlarmName()));
    }

}






