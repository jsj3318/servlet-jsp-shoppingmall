package com.nhnacademy.shoppingmall.controller.user.address;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;

@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/updateAddress.do"})
public class UpdateAddressPostController implements BaseController {
    private final AddressRepository addressRepository = new AddressRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        Address original = new Address(0, req.getParameter("user_id"), req.getParameter("originalAddress"));
        String newAddress = req.getParameter("newAddress");

        addressRepository.update( original, newAddress );

        return "redirect:/mypage.do";
    }
}
