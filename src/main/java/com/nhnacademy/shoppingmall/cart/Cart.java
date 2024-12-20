package com.nhnacademy.shoppingmall.cart;

import java.math.BigInteger;
import java.util.List;

public interface Cart {
    public void add(CartItem item);
    public void remove(int product_id);
    public int size();
    public void increase(int product_id);
    public void decrease(int product_id);
    public BigInteger getTotal();
    public boolean hasProduct(int product_id);
    public List<CartItem> getItemList();
    public void clear();
}
