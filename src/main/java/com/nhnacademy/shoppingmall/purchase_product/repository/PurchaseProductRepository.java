package com.nhnacademy.shoppingmall.purchase_product.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.purchase_product.domain.PurchaseProduct;

import java.util.List;

public interface PurchaseProductRepository {
    public int save(int purchase_id, int product_id, int quantity);

    public long countByPurchaseId(int purchase_id);
    public Page<PurchaseProduct> pageByPurchaseId(int purchase_id, int page, int pageSize);
}
