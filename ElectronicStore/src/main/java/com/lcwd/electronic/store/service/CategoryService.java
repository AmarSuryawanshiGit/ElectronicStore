package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponce;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, String categoryId);
    void deleteCategory(String categoryId);
    PageableResponce<CategoryDto>   getAllCategory(int pageNumber, int pageSize , String sortBy ,String sortDir);
    CategoryDto getCategory(String categoryId);
}
