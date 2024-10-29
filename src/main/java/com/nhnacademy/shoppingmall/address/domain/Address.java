package com.nhnacademy.shoppingmall.address.domain;

public class Address {
    private String userId;
    private String address;

    public Address(String userId, String address) {
        this.userId = userId;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

}
