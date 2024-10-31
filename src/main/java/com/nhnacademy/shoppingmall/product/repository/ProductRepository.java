package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    int saveAndGetId(String name, BigInteger price, String description, int quantity);

    int update(Product product);
    int deletebyId(int id);

    long countAll();
    long countbyCategoryId(int categoryId);

    Optional<Product> findbyId(int productId);

    List<Product> findAll();
    List<Product> findbyCategoryId(int categoryId);

    Page<Product> pageAll(int page, int pageSize);
    Page<Product> pagebyCategoryId(int categoryId, int page, int pageSize);

    long countbyQuery(String query);
    Page<Product> pagebyquery(String query, int page, int pageSize);
}
