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
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/purchase.do"})
public class PurchaseController implements BaseController {
    private final AddressRepository addressRepository = new AddressRepositoryImpl();

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


        //유저의 주소지 목록
        // 하나 선택하게 할 것임
        // 페이징 안하고 개별 스크롤로 할 예정
        List<Address> addressList = addressRepository.findByUserId(user.getUserId());
        req.setAttribute("addressList", addressList);

        // 장바구니 정보
        Cart cart = (Cart) session.getAttribute("cart");



        return "shop/purchase/purchase";
    }


}
