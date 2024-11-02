package com.nhnacademy.shoppingmall.purchase_product.repository;

import com.nhnacademy.shoppingmall.purchase_product.domain.PurchaseProduct;

import java.util.List;

public interface PurchaseProductRepository {
    public int save(int purchase_id, int product_id, int quantity);

    public long countByPurchaseId(int purchase_id);

    List<PurchaseProduct> findByPurchaseId(int id);

}
