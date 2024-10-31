package com.nhnacademy.shoppingmall.product_category;

import java.util.List;

public interface ProductCategoryRepository {
    public int save(int productId, int categoryId);

    public List<Integer> findByProductId(int productId);
}
