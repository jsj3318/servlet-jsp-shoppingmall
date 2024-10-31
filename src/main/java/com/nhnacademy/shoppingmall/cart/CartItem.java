package com.nhnacademy.shoppingmall.cart;

import java.math.BigInteger;

public class CartItem {
    // 장바구니에 담기는 상품의 정보
    private int product_id;
    private String product_name;
    private BigInteger price;
    private int quantity;
    private String thumbnailUri;

    public CartItem(int product_id, String productName, BigInteger price, int quantity, String thumbnailUri) {
        this.product_id = product_id;
        product_name = productName;
        this.price = price;
        this.quantity = quantity;
        this.thumbnailUri = thumbnailUri;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }
}
