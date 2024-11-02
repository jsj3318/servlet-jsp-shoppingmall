package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.pointHistory.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class PointChannelRequest extends ChannelRequest {

    private final PointHistoryRepository pointHistoryRepository = new PointHistoryRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private int payback;
    private User user;
    public PointChannelRequest(int payback, User user) {
        this.payback = payback;
        this.user = user;
    }

    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.
        log.debug("pointChannel execute");

            // 포인트 증가 내역 생성
            pointHistoryRepository.save(
                    new PointHistory(
                            user.getUserId(),
                            payback,
                            "포인트 적립",
                            LocalDateTime.now()
                    )
            );

            // 유저 포인트 변화 db 적용
            userRepository.update(user);


        DbConnectionThreadLocal.reset();
    }
}
