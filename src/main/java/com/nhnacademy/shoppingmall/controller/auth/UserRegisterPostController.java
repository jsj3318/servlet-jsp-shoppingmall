package com.nhnacademy.shoppingmall.controller.auth;

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

@RequestMapping(method = RequestMapping.Method.POST, value = {"/userRegisterAction.do"})
public class UserRegisterPostController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원가입 화면에서 정보 모두 입력 후 등록
        DbConnectionThreadLocal.initialize();
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());

        //아이디 중복검사는 jsp에서 해야겠다

        User user = new User(
                req.getParameter("user_id"),
                req.getParameter("user_name"),
                req.getParameter("user_password"),
                req.getParameter("user_birth"),
                User.Auth.ROLE_USER,
                100_0000,
                LocalDateTime.now(),
                null
        );

        userService.saveUser(user);

        DbConnectionThreadLocal.reset();

        return "shop/main/index";
    }
}
