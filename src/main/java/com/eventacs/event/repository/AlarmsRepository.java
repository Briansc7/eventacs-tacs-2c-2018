package com.eventacs.event.repository;

import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.exception.AlarmCreationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AlarmsRepository {

    @Autowired
    private EventacsMongoClient eventacsMongoClient;

    public AlarmsRepository() {
    }

    public AlarmsRepository(EventacsMongoClient eventacsMongoClient){
        this.eventacsMongoClient = eventacsMongoClient;
    }

    public AlarmDTO createAlarm(SearchDTO searchDTO, String userId, String alarmId) {
        Map<String, Object> documentElements =  new HashMap<>();
        Map<String, String> conditions =  new HashMap<>();

        documentElements.put("userId", userId);
        documentElements.put("alarmId", alarmId);
        documentElements.put("search", searchDTO);

        conditions.put("alarmId", alarmId);

        eventacsMongoClient.createDocument("alarms", documentElements);

        List<AlarmDTO> result = eventacsMongoClient.getElementsAs(AlarmDTO.class, conditions, "alarms", "eventacs");

        if(result.size() != 1) {
            throw new AlarmCreationError("Error creation error dor this userId: " + userId + " and alarmId: " + alarmId);
        } else {
            return result.get(0);
        }

    }

    public Integer alarmIdGenerator() {
        return eventacsMongoClient.alarmIdGenerator();
    }
}
