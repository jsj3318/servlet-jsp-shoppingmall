package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.page.Page;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    int save(String category);
    int deletebyId(int id);
    int update(int id, String newName);
    int totalCount();

    Page<Category> findPage(int page, int pageSize);
}
