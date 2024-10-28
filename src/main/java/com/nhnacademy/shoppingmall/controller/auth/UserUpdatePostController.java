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
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = {"/userUpdateAction.do"})
public class UserUpdatePostController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원 정보 수정 에서 정보 입력 후 수정 버튼 누름
        DbConnectionThreadLocal.initialize();
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        user.setUserName(req.getParameter("user_name"));
        user.setUserPassword(req.getParameter("user_password"));
        user.setUserBirth(req.getParameter("user_birth"));

        userService.updateUser(user);

        DbConnectionThreadLocal.reset();

        // 현재 세션의 유저정보 재 설정
        session.setAttribute("user", user);

        return "shop/main/index";
    }
}
