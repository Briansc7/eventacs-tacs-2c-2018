package com.eventacs.event.repository;

import com.eventacs.mongo.EventacsMongoClient;
import com.eventacs.user.dto.AlarmDAO;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDAO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.exception.AlarmCreationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
        Map<String, Object> searchJson =  new HashMap<>();

        documentElements.put("userId", userId);
        documentElements.put("alarmId", alarmId);
        searchJson.put("keyword", searchDTO.getKeyword().orElse(""));
        searchJson.put("categories", searchDTO.getCategories().orElseGet(ArrayList::new));
        searchJson.put("endDate", Date.from(searchDTO.getEndDate().get().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        searchJson.put("startDate", Date.from(searchDTO.getStartDate().get().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        documentElements.put("search", searchJson);

        conditions.put("alarmId", alarmId);

        eventacsMongoClient.createDocument("alarms", documentElements);

        List<AlarmDAO> result = eventacsMongoClient.getElementsAs(AlarmDAO.class, conditions, "alarms", "eventacs");

        if(result.size() != 1) {
            throw new AlarmCreationError("Error creation error dor this userId: " + userId + " and alarmId: " + alarmId);
        } else {
            AlarmDAO alarmDAO = result.get(0);
            SearchDAO searchDAO = alarmDAO.getSearch();
            return new AlarmDTO(Optional.ofNullable(alarmId), alarmDAO.getUserId(), new SearchDTO(Optional.ofNullable(searchDAO.getKeyword()),
                                                                                                  Optional.ofNullable(searchDAO.getCategories()),
                                                                                                  Optional.of(LocalDate.from(searchDAO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())),
                                                                                                  Optional.of(LocalDate.from(searchDAO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()))));
        }

    }

    public List<AlarmDAO> findAllByUserId(String userId) {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("userId", userId);
        return this.eventacsMongoClient.getElementsAs(AlarmDAO.class, conditions, "alarms", "eventacs");
    }

    public Integer alarmIdGenerator() {
        return eventacsMongoClient.alarmIdGenerator();
    }
}
