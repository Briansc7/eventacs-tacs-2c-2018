package com.eventacs.external.eventbrite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pagination {

    @JsonProperty("page_number")
    private Integer page = 0;

    @JsonProperty("page_count")
    private Integer pageCount;

    @JsonProperty("has_more_items")
    private Boolean hasMoreItems = false;

    public Pagination() {

    }

    public Pagination(Integer page, Integer pageCount, Boolean hasMoreItems) {
        this.page = page;
        this.pageCount = pageCount;
        this.hasMoreItems = hasMoreItems;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Boolean getHasMoreItems() {
        return hasMoreItems;
    }

    public void setHasMoreItems(Boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
    }

}
