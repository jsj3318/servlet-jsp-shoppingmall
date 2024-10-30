package com.nhnacademy.shoppingmall.product.domain;

import java.math.BigInteger;

public class Product {
    private final int product_id;
    private String product_name;
    private BigInteger price;
    private String description;
    private int quantity;

    public Product(int product_id, String product_name, BigInteger price, String description, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
