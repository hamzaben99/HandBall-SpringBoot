package com.api.handball.utils;

import com.api.handball.entity.Category;
import com.api.handball.entity.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoMapper {
    public Category map(CategoryDto dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public CategoryDto map(Category category) {
        return CategoryDto.builder()
                .CategoryName(category.getCategoryName())
                .build();
    }
}
