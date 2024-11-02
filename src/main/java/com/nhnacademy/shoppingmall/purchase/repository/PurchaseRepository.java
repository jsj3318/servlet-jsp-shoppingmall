package com.nhnacademy.shoppingmall.purchase.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.purchase.domain.Purchase;

import java.math.BigInteger;
import java.util.List;

public interface PurchaseRepository {
    public int save(String user_id, String destination, BigInteger total_amount);
    public int saveAndGetId(String user_id, String destination, BigInteger total_amount);

    List<Purchase> findByUserId(String user_id);

    int countByUserId(String user_id);
    Page<Purchase> pagebyUserId(String user_id, int page, int pageSize);

}
