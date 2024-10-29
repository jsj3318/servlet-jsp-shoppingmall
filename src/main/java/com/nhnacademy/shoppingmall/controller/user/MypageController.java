package com.nhnacademy.shoppingmall.controller.user;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.pointHistory.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/mypage.do"})
public class MypageController implements BaseController {

    private final AddressRepository addressRepository = new AddressRepositoryImpl();
    private final PointHistoryRepository pointHistoryRepository = new PointHistoryRepositoryImpl();

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

        // 유저의 포인트 이용 내역 전달하기
        // 일시 desc 되어서 옴
        //페이징 적용
        int page = Integer.parseInt(req.getParameter("pointPage") != null ?
                req.getParameter("pointPage") : "1");
        int pageSize = 10;

        long totalItems = pointHistoryRepository.countById(user.getUserId());
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<PointHistory> pointHistoryPage = pointHistoryRepository.findAllbyId(user.getUserId(), page, pageSize);
        List<PointHistory> pointHistoryList = pointHistoryPage.getContent();

        req.setAttribute("pointHistoryList", pointHistoryList);
        req.setAttribute("pointHistoryCurrentPage", page);
        req.setAttribute("pointHistoryTotalPages", totalPages);

        // 해당 유저의 주소리스트 받아서 전달해야 함
        //페이징 적용
        page = Integer.parseInt(req.getParameter("addressPage") != null ?
                req.getParameter("addressPage") : "1");
        pageSize = 5;

        totalItems = addressRepository.countByUserId(user.getUserId());
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<Address> addressPage = addressRepository.findAllbyId(user.getUserId(), page, pageSize);
        List<Address> addressList = addressPage.getContent();

        req.setAttribute("addressList", addressList);
        req.setAttribute("addressCurrentPage", page);
        req.setAttribute("addressTotalPages", totalPages);


        return "shop/user/mypage";
    }
}
