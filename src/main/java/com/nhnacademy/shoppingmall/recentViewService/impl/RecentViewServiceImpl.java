package com.nhnacademy.shoppingmall.recentViewService.impl;

import com.nhnacademy.shoppingmall.common.util.CookieUtil;
import com.nhnacademy.shoppingmall.recentViewService.RecentViewService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RecentViewServiceImpl implements RecentViewService {

    public static final String RECENT_VIEW_COOKIE_NAME = "recent_view_product_list";

    @Override
    public List<Integer> getList(HttpServletRequest req) {
        // 쿠키로 저장되어있는 상품 목록을 리스트로 반환한다

        //쿠키 테스트
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                log.debug("Cookie Name: " + c.getName() + ", Value: " + c.getValue());
            }
        }


        Cookie cookie = CookieUtil.getCookie(req, RECENT_VIEW_COOKIE_NAME);
        if(cookie == null){
            return null;
        }

        List<Integer> res = toList(cookie.getValue());
        return res;
    }

    @Override
    public void add(int product_id, HttpServletRequest req, HttpServletResponse resp) {
        Cookie cookie = CookieUtil.getCookie(req, RECENT_VIEW_COOKIE_NAME);
        List<Integer> list;

        // 쿠키 존재여부 검사
        if(cookie == null){
            // 최근 본 상품이 없으면 null이다
            list = new ArrayList<>();
        }else{
            // null이 아니라면 본 상품이 하나는 존재한다
            list = toList(cookie.getValue());
        }

        // 리스트에 이미 해당 id가 존재할 경우 -> 해당 값을 지우고 다시 맨앞에 추가한다
        list.remove(Integer.valueOf(product_id));

        // 리스트의 맨 앞에 새 id를 추가하고, 5개가 넘을경우 꼬리를 없앤다
        list.addFirst(product_id);
        if(list.size() > 5){
            list.removeLast();
        }

        // 리스트를 문자열로 바꾸고 쿠키로 저장한다
        String value = toString(list);
        Cookie newCookie = new Cookie(RECENT_VIEW_COOKIE_NAME, value);
        newCookie.setMaxAge(Integer.MAX_VALUE);
        newCookie.setPath("/");
        resp.addCookie(newCookie);
    }

    @Override
    public String toString(List<Integer> list) {
        // product_id가 담긴 리스트를 문자열로 변환
        // {1, 2, 3} -> "1|2|3"

        String res = "";
        for(int i=0; i<list.size(); i++){
            res += list.get(i);
            if(i != list.size()-1){
                res += "|";
            }
        }

        return res;
    }

    @Override
    public List<Integer> toList(String str) {
        // 문자열을 리스트로 전환
        // "1|2|3|4|5" -> {1,2,3,4,5}
        List<Integer> res = new ArrayList<>();
        String[] arr = str.split("\\|");
        for(String s : arr){
            res.add(Integer.valueOf(s));
        }

        return res;
    }
}
