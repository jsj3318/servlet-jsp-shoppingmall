package com.nhnacademy.shoppingmall.controller.user.cart;

import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = {"/deleteCartItem.do"})
public class DeleteCartItemController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        //유저 정보 확인하고 받기
        User user = null;
        if(session != null){
            user = (User) session.getAttribute("user");
        }

        if(user == null){
            // 로그인 하지 않은 세션
            // 로그인으로 보내기
            return "redirect:/login.do";
        }

        int product_id = Integer.parseInt(req.getParameter("product_id"));

        Cart cart = (Cart) session.getAttribute("cart");
        cart.remove(product_id);

        session.setAttribute("cart", cart);

        return "redirect:/cart.do";
    }
}
