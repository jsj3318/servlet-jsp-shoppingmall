package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.cart.CartImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.pointHistory.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = {"/loginAction.do"})
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryRepository pointHistoryRepository = new PointHistoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        // 이 user는 latest login이 현재 시간으로 적용된게 아닌 유저
        User user = userService.doLogin(req.getParameter("user_id"), req.getParameter("user_password"));

        // latest login 하루 지났을 경우 1만 포인트 적립
        LocalDateTime latestLogin = user.getLatestLoginAt();

//        RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute(PointThreadInitializer.CONTEXT_REQUEST_CHANNEL_NAME);
//        requestChannel.addRequest(new );

        if(LocalDate.now().isAfter(latestLogin.toLocalDate())){
            //하루가 지났다
            user.setUserPoint(user.getUserPoint() + 10000);
            userService.updateUser(user);

            PointHistory pointHistory = new PointHistory(
                    user.getUserId(),
                    10000,
                    "출석 체크",
                    LocalDateTime.now()
            );

            pointHistoryRepository.save(pointHistory);

        }

        // 세션 생성
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("user", user);


        //세션에 장바구니 생성
        session.setAttribute("cart", new CartImpl());

        log.debug("{} 로그인 됨", user.getUserId());

        return "redirect:/main.do";
    }

}
