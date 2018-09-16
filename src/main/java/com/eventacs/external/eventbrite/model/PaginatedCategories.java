package com.eventacs.external.eventbrite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PaginatedCategories {

    private Pagination pagination;

    @JsonProperty("categories")
    private List<CategoryResponse> categoryResponses;

    public PaginatedCategories() {}

    public PaginatedCategories(Pagination pagination, List<CategoryResponse> categoryResponses) {
        this.pagination = pagination;
        this.categoryResponses = categoryResponses;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<CategoryResponse> getCategoryResponses() {
        return categoryResponses;
    }

    public void setCategoryResponses(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
    }

}