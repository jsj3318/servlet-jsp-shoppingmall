package com.nhnacademy.shoppingmall.controller.user.address;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = {"/addAddress.do"})
public class AddAddressPostController implements BaseController {
//    private final

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        DbConnectionThreadLocal.initialize();





        DbConnectionThreadLocal.reset();

        return "shop/user/mypage";
    }
}
