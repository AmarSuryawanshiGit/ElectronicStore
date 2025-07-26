package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponce;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto, String categoryId);
    void delete(String categoryId);
    PageableResponce<CategoryDto>   getAll(int pageNumber, int pageSize , String sortBy ,String sortDir);
    CategoryDto get(String categoryId);
}
