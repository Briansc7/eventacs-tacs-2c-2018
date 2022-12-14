package com.eventacs.user.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Search {

    private Optional<String> keyword;
    private Optional<List<String>> categories;
    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;

    public Search(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        this.keyword = keyword;
        this.categories = categories;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Optional<String> getKeyword() {
        return keyword;
    }

    public void setKeyword(Optional<String> keyword) {
        this.keyword = keyword;
    }

    public Optional<List<String>> getCategories() {
        return categories;
    }

    public void setCategories(Optional<List<String>> categories) {
        this.categories = categories;
    }

    public Optional<LocalDate> getStartDate() {
        return startDate;
    }

    public void setStartDate(Optional<LocalDate> startDate) {
        this.startDate = startDate;
    }

    public Optional<LocalDate> getEndDate() {
        return endDate;
    }

    public void setEndDate(Optional<LocalDate> endDate) {
        this.endDate = endDate;
    }

}
