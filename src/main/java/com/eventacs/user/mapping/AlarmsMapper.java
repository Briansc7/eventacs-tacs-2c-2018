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
                                                                                         search.getEndDate()));
    }

    public Alarm fromApiToModel(AlarmDTO alarmDTO) {
        SearchDTO searchDTO = alarmDTO.getSearchDTO();
        return new Alarm(alarmDTO.getId().orElse(""), alarmDTO.getUserId(), new Search(searchDTO.getKeyword(),
                                                                                       searchDTO.getCategories(),
                                                                                       searchDTO.getStartDate(),
                                                                                       searchDTO.getEndDate()));
    }

}






