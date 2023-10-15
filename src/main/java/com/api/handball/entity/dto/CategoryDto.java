package com.api.handball.entity.dto;

import lombok.Builder;

@Builder
public class CategoryDto {
    private String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
