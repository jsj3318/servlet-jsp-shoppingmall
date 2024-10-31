package com.nhnacademy.shoppingmall.controller.user.cart;

import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = {"/cartItemDecrease.do"})
public class cartItemDecreaseController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        int product_id = Integer.parseInt(req.getParameter("product_id"));

        Cart cart = (Cart) session.getAttribute("cart");
        cart.decrease(product_id);

        session.setAttribute("cart", cart);

        return "redirect:/cart.do";
    }
}
