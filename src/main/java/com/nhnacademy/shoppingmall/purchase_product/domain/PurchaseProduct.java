package com.nhnacademy.shoppingmall.purchase_product.domain;

public class PurchaseProduct {
    private int purchase_id;
    private int product_id;
    private int quantity;

    public PurchaseProduct(int purchase_id, int product_id, int quantity) {
        this.purchase_id = purchase_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
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
}
