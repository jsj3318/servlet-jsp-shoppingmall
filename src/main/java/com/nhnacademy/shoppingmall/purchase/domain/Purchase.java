package com.nhnacademy.shoppingmall.purchase.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Purchase {
    private final int purchase_id;
    private String user_id;
    private LocalDateTime purchased_at;
    private String destination;
    private BigInteger total_amount;

    public Purchase(int purchase_id, String user_id, LocalDateTime purchased_at, String destination, BigInteger total_amount) {
        this.purchase_id = purchase_id;
        this.user_id = user_id;
        this.purchased_at = purchased_at;
        this.destination = destination;
        this.total_amount = total_amount;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getPurchased_at() {
        return purchased_at;
    }

    public void setPurchased_at(LocalDateTime purchased_at) {
        this.purchased_at = purchased_at;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigInteger getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigInteger total_amount) {
        this.total_amount = total_amount;
    }
}
