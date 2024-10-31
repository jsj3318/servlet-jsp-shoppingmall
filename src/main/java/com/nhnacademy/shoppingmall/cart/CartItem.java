package com.nhnacademy.shoppingmall.cart;

import java.math.BigInteger;

public class CartItem {
    // 장바구니에 담기는 상품의 정보
    private int product_id;
    private BigInteger price;
    private int quantity;

    public CartItem(int product_id, BigInteger price, int quantity) {
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
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
}
