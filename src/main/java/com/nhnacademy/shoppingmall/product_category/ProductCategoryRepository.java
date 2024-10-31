package com.nhnacademy.shoppingmall.product_category;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface ProductCategoryRepository {
    public int save(int productId, int categoryId);
    public int deleteByProductId(int productId);

    public List<Integer> findByProductId(int productId);
    public List<Category> findCategoryByProductId(int productId);
}
