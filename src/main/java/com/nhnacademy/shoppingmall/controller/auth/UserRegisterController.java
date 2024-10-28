package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = {"/userRegister.do"})
public class UserRegisterController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원 가입 눌러서 가입 화면으로 이동

        return "shop/login/register_form";
    }
}
