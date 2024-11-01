package com.nhnacademy.shoppingmall.address.domain;

import java.util.Objects;

public class Address {
    private int address_id;
    private String userId;
    private String address;

    public Address(int addressId, String userId, String address) {
        address_id = addressId;
        this.userId = userId;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(userId, address1.userId) && Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, address);
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }
}
