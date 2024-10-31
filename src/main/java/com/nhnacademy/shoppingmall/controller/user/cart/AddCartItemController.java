package com.nhnacademy.shoppingmall.controller.user.cart;

import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.cart.CartItem;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/addCartItem.do"})
public class AddCartItemController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();

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
        Product product = productRepository.findbyId(product_id).get();

        CartItem item = new CartItem(
                product_id, product.getProduct_name(), product.getPrice(), 1, UriUtil.toThumbnailUri(product_id));
        Cart cart = (Cart) session.getAttribute("cart");
        cart.add(item);

        session.setAttribute("cart", cart);

        return "redirect:/cart.do";
    }
}
