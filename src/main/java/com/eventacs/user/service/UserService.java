package com.eventacs.user.service;

import com.eventacs.event.model.Event;
import com.eventacs.event.dto.EventListCreationDTO;
import com.eventacs.event.repository.EventListRepository;
import com.eventacs.user.dto.AlarmDAO;
import com.eventacs.external.telegram.client.JdbcDao.JdbcDaoUserData;
import com.eventacs.user.dto.AlarmDTO;
import com.eventacs.user.dto.SearchDTO;
import com.eventacs.user.mapping.AlarmsMapper;
import com.eventacs.user.mapping.EventListsMapper;
import com.eventacs.user.mapping.UsersMapper;
import com.eventacs.event.repository.AlarmsRepository;
import com.eventacs.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.eventacs.user.exception.UserNotFound;
import com.eventacs.user.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.eventacs.user.dto.UserDataDTO;

@Component
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private EventListsMapper eventListsMapper;

    @Autowired
    private AlarmsMapper alarmsMapper;

    @Autowired
    private EventListRepository eventListRepository;

    @Autowired
    private AlarmsRepository alarmsRepository;

    public UserService(UsersRepository usersRepository, UsersMapper usersMapper, AlarmsRepository alarmsRepository, AlarmsMapper alarmsMapper, EventListsMapper eventListsMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.alarmsRepository = alarmsRepository;
        this.alarmsMapper = alarmsMapper;
        this.eventListsMapper = eventListsMapper;
    }

    public UserDataDTO getUser(String userId) {
        UserDataDTO userData = this.usersRepository.getUserDataByUserId(userId);
        userData.setAlarmsCount(this.countAlarms(userId));
        userData.setListCount(this.countEventList(userId));
        return userData;
    }

//    public List<UserDataDTO> getUsers() {
//        return this.usersRepository.getUsers().stream().map(user -> this.usersMapper.fromModelToApi(user)).collect(Collectors.toList());
//    }

    public AlarmDTO createAlarm(SearchDTO searchDTO, String username) {
        return alarmsRepository.createAlarm(searchDTO, username, alarmIdGenerator());
    }

    public List<AlarmDAO> getAllAlarms(){
        return alarmsRepository.findAll();
    }

    private Long alarmIdGenerator() {
        return alarmsRepository.alarmIdGenerator();
    }

    public void addEventList(EventListCreationDTO eventListCreation, Long listId) {
        this.eventListRepository.createEventList(eventListCreation, listId);
    }

    public void addEvent(Long listId, Event event, String userId) {
        this.eventListRepository.addEventsToEventList(event, listId);
    }

    public Long changeListName(Long listId, String listName) {
        return this.eventListRepository.changeListName(listId, listName);
    }

    public Long deleteEventList(Long listId) {
            return this.eventListRepository.deleteEventList(listId);
    }

    public void setEventListRepository(EventListRepository eventListRepository) {
        this.eventListRepository = eventListRepository;
    }

    public BigDecimal countAlarms(String username) {
        return BigDecimal.valueOf(this.alarmsRepository.findAllByUserId(username).size());
    }

    public BigDecimal countEventList(String username) {
        return BigDecimal.valueOf(this.eventListRepository.getEventListByUserId(username).size());
    }

    public void deleteAlarm(Long alarmId) {
        alarmsRepository.deleteAlarm(alarmId);
    }

    public void updateAlarm(AlarmDTO alarmDTO) {
        alarmsRepository.updateAlarm(alarmDTO);
    }

}