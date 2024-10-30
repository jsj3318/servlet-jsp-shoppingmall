package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    int save(Category category);
    int deletebyId(int id);
    int update(int id, String newName);
    int totalCount();
}
