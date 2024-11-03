package com.nhnacademy.shoppingmall.recentViewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface RecentViewService {
    List<Integer> getList(HttpServletRequest req);
    void add(int product_id, HttpServletRequest req, HttpServletResponse resp);

    String toString(List<Integer> list);
    List<Integer> toList(String str);
}
