package com.nhnacademy.shoppingmall.cart;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CartImpl implements Cart{
    //장바구니
    private List<CartItem> itemList = new ArrayList<>();

    public List<CartItem> getItemList() {
        return itemList;
    }

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

    @Override
    public BigInteger getTotal() {
        BigInteger res = BigInteger.ZERO; // 초기값 설정
        for (CartItem item : itemList) {
            res = res.add(item.getPrice().multiply(BigInteger.valueOf(item.getQuantity()))); // 수량 반영
        }
        return res;
    }

    @Override
    public boolean hasProduct(int product_id) {
        for(CartItem item : itemList){
            if(item.getProduct_id() == product_id){
                return true;
            }
        }
        return false;
    }

}
