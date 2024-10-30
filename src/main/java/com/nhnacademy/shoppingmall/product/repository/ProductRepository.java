package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductRepository {

    int save(Product product);
    int update(Product product);
    int deletebyId(int id);

    long countAll();
    long countbyCategoryId(int categoryId);

    List<Product> findAll();
    List<Product> findbyCategoryId(int categoryId);

    Page<Product> pageAll(int page, int pageSize);
    Page<Product> pagebyCategoryId(int categoryId, int page, int pageSize);

}