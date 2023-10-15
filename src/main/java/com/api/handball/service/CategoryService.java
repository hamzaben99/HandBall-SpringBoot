package com.api.handball.service;

import com.api.handball.entity.Category;
import com.api.handball.entity.dto.CategoryDto;
import com.api.handball.repository.CategoryRepository;
import com.api.handball.utils.CategoryDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryDtoMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Transactional
    public Long addCategory(CategoryDto categoryDto) {
        return categoryRepository.save(mapper.map(categoryDto)).getCategoryId();
    }

    @Transactional
    public void deleteCategoryById(Long CategoryId) {
        if (categoryRepository.existsById(CategoryId))
            throw new RuntimeException("id " + CategoryId + " doesn't exist");

        categoryRepository.deleteById(CategoryId);
    }

    @Transactional
    public void updateCategory(CategoryDto updatedCategoryDto, Long categoryId) {
        Category updatedCategory = categoryRepository.findById(categoryId).orElseThrow();
        updatedCategory.setCategoryName(updatedCategoryDto.getCategoryName());
        categoryRepository.save(updatedCategory);
    }
}
