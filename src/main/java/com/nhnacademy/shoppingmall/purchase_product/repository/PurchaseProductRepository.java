package com.nhnacademy.shoppingmall.purchase_product.repository;

public interface PurchaseProductRepository {
    public int save(int purchase_id, int product_id, int quantity);

    public long countByPurchaseId(int purchase_id);

}
