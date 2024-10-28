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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = {"/userDeleteAction.do"})
public class UserDeleteController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원 탈퇴 버튼 클릭 시 이벤트
        // db에서 유저 삭제 후 메인 으로
        DbConnectionThreadLocal.initialize();
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());

        User user = (User) req.getSession(false).getAttribute("user");

        userService.deleteUser(user.getUserId());
        DbConnectionThreadLocal.reset();

        log.debug("{} 유저 탈퇴함", user.getUserId());

        return "shop/main/index";

    }
}
