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
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/userDeleteAction.do"})
public class UserDeleteController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 회원 탈퇴 버튼 클릭 시 이벤트
        // db에서 유저 삭제 후 메인 으로

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        userService.deleteUser(user.getUserId());

        log.debug("{} 유저 탈퇴함", user.getUserId());

        // 세션 제거
        session.invalidate();

        return "redirect:/main.do";

    }
}
