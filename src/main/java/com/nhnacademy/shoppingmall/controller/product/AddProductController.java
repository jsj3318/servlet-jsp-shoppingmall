package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/addProduct.do"})
public class AddProductController implements BaseController {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        //유저 정보 확인하고 받기
        User user = null;
        if(session != null){
            user = (User) session.getAttribute("user");
        }

        if(user == null){
            return "redirect:/login.do";
        }

        if(user.getUserAuth() != User.Auth.ROLE_ADMIN){
            log.debug("{} 회원이 상품 등록 페이지 접근 시도함", user.getUserId());
            return "redirect:/main.do";
        }

        // 카테고리 목록 전달
        req.setAttribute("categoryList", categoryRepository.findAll() );

        return "shop/product/addProduct";
    }


}
