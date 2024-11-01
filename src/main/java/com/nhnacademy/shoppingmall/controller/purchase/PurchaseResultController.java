package com.nhnacademy.shoppingmall.controller.purchase;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = {"/purchaseResult.do"})
public class PurchaseResultController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {



        return "shop/purchase/purchaseResult";
    }


}
