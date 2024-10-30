package com.nhnacademy.shoppingmall.product.domain;

import java.math.BigInteger;
import java.util.Objects;

public class Product {
    private final int product_id;
    private String product_name;
    private BigInteger price;
    private String thumnail_uri;
    private String description;
    private String image_uri;
    private int quantity;

    public Product(int product_id, String product_name, BigInteger price, String thumnail_uri, String description, String image_uri, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.thumnail_uri = thumnail_uri;
        this.description = description;
        this.image_uri = image_uri;
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

    public String getThumnail_uri() {
        return thumnail_uri;
    }

    public void setThumnail_uri(String thumnail_uri) {
        this.thumnail_uri = thumnail_uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
