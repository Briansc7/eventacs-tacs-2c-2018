package com.eventacs.user.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Search {

    private Optional<String> keyword;
    private Optional<List<String>> categories;
    private Optional<LocalDateTime> startDate;
    private Optional<LocalDateTime> endDate;

    public Search(Optional<String> keyword, Optional<List<String>> categories, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate) {
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

    public Optional<LocalDateTime> getStartDate() {
        return startDate;
    }

    public void setStartDate(Optional<LocalDateTime> startDate) {
        this.startDate = startDate;
    }

    public Optional<LocalDateTime> getEndDate() {
        return endDate;
    }

    public void setEndDate(Optional<LocalDateTime> endDate) {
        this.endDate = endDate;
    }

}
