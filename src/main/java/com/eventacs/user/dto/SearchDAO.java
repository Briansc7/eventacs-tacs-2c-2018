package com.eventacs.user.dto;

import java.util.Date;
import java.util.List;

public class SearchDAO {

  private String keyword;
  private List<String> categories;

  private Date modifiedStartDate;
  private Date startDate;
  private Date endDate;

  public SearchDAO() {
  }

  public SearchDAO(String keyword, List<String> categories, Date modifiedStartDate, Date startDate, Date endDate) {
    this.keyword = keyword;
    this.categories = categories;
    this.modifiedStartDate = modifiedStartDate;
    this.startDate = startDate;
    this.endDate = endDate;
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

  public Date getModifiedStartDate() {
    return modifiedStartDate;
  }

  public void setModifiedStartDate(Date modifiedStartDate) {
    this.modifiedStartDate = modifiedStartDate;
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

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
