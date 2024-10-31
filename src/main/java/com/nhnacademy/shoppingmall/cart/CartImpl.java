package com.nhnacademy.shoppingmall.cart;

import java.util.ArrayList;
import java.util.List;

public class CartImpl implements Cart{
    //장바구니
    private List<CartItem> itemList = new ArrayList<>();


    @Override
    public void add(CartItem item) {
        itemList.add(item);
    }

    @Override
    public void remove(int product_id) {
        itemList.removeIf(i -> i.getProduct_id() == product_id);
    }

    @Override
    public int size() {
        return itemList.size();
    }

    @Override
    public void increase(int product_id) {
        for(CartItem item : itemList){
            if(item.getProduct_id() == product_id){
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
    }

    @Override
    public void decrease(int product_id) {
        for(CartItem item : itemList){
            if(item.getProduct_id() == product_id){
                if(item.getQuantity() == 1){
                    return;
                }

                item.setQuantity(item.getQuantity() - 1);
                return;
            }
        }
    }
}
