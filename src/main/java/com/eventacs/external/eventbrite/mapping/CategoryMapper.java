package com.eventacs.external.eventbrite.mapping;

import com.eventacs.event.model.Category;
import com.eventacs.external.eventbrite.model.CategoryResponse;

public class CategoryMapper {

    public Category fromResponseToModel(CategoryResponse categoryResponse) {
        return new Category(categoryResponse.getId(), categoryResponse.getName());
    }

}
