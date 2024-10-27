package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()){
//            throw new UserNotFoundException(userId + " 유저 없음");
            log.error("{} 유저 없음", userId);
            return null;
        }

        return userOptional.get();
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록

        if(userRepository.countByUserId(user.getUserId()) > 0){
            throw new UserAlreadyExistsException(user.getUserId() + " 이미 존재함");
        }

        userRepository.save(user);
        log.debug("{} 유저 생성됨", user.getUserId());

    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if(userRepository.countByUserId(user.getUserId()) == 0){
            throw new UserNotFoundException(user.getUserId() + " 유저 없음");
        }

        userRepository.update(user);

    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if(userRepository.countByUserId(userId) == 0){
            throw new UserNotFoundException(userId + " 유저 없음");
        }

        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회

        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId, userPassword);

        if(userOptional.isEmpty()){
            throw new UserNotFoundException(userId + " 로그인 실패");
        }

        userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
        return userOptional.get();

    }

}
