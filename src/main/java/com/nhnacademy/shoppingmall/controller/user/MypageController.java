package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/mypage.do"})
public class MypageController implements BaseController {

    private final AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

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

        // 로그인 되어있음
        // 유저 정보 전달해서 shop/user/mypage.jsp 연결
        req.setAttribute("user", user);


        // 해당 유저의 주소리스트 받아서 전달해야 함

        List<Address> addressList = addressService.getAddress(user.getUserId());
        req.setAttribute("addressList", addressList);


        return "shop/user/mypage";
    }
}
