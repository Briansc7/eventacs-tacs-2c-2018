package com.eventacs.user.dto;

public class SearchDTO {

    private String searchParameters;

    public SearchDTO() {

    }

    public SearchDTO(String searchParameters) {
        this.searchParameters = searchParameters;
    }

    public String getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(String searchParameters) {
        this.searchParameters = searchParameters;
    }

}
