package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = {"/userUpdate.do"})
public class UserUpdateController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원 정보 수정 눌러서 register (수정 버전) 으로 이동
        // user 어트리뷰트 설정해야함
        req.setAttribute("user", req.getSession(false).getAttribute("user"));

        return "shop/login/register_form";
    }
}
