package com.eventacs.user.dto;

import java.util.Date;
import java.util.List;

public class SearchDAO {

  private String keyword;
  private List<String> categories;

  private Date changed;
  private Date startDate;
  private Date endDate;
  private String alarmName;

  public SearchDAO() {
  }

  public SearchDAO(String keyword, List<String> categories, Date changed, Date startDate, Date endDate, String alarmName) {
    this.keyword = keyword;
    this.categories = categories;
    this.changed = changed;
    this.startDate = startDate;
    this.endDate = endDate;
    this.alarmName = alarmName;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public Date getChanged() {
    return changed;
  }

  public void setChanged(Date changed) {
    this.changed = changed;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getAlarmName() {
    return alarmName;
  }

  public void setAlarmName(String alarmName) {
    this.alarmName = alarmName;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
