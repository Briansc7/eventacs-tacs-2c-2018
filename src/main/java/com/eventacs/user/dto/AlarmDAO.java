package com.eventacs.user.dto;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class AlarmDAO {

  @Id
  private String _id;

  private String alarmId;
  private String userId;
  private SearchDAO search;

  public AlarmDAO() {
  }

  public AlarmDAO(String alarmId, String userId, SearchDAO search) {
    this.alarmId = alarmId;
    this.userId = userId;
    this.search = search;
  }

  public String getAlarmId() {
    return alarmId;
  }

  public void setAlarmId(String alarmId) {
    this.alarmId = alarmId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public SearchDAO getSearch() {
    return search;
  }

  public void setSearch(SearchDAO search) {
    this.search = search;
  }
}
