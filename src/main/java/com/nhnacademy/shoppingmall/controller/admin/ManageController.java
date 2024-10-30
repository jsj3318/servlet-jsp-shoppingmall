package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/manage.do"})
public class ManageController implements BaseController {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("manage contorller 실행");

        //페이징을 위한 변수들
        int page = 1;
        int pageSize = 10;
        long totalItems = 0;
        int totalPages = 1;

        // 카테고리 리스트
        page = Integer.parseInt(req.getParameter("categoryPage") != null ?
                req.getParameter("categoryPage") : "1");
        pageSize = 3;
        totalItems = categoryRepository.totalCount();
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<Category> categoryPage = categoryRepository.findPage(page, pageSize);
        List<Category> categoryList = categoryPage.getContent();

        req.setAttribute("categoryList", categoryList);
        req.setAttribute("categoryCurrentPage", page);
        req.setAttribute("categoryTotalPages", totalPages);


        // 회원 목록을 위한 리스트 보내기
        // memberList
        // memberPage
        page = Integer.parseInt(req.getParameter("memberPage") != null ?
                req.getParameter("memberPage") : "1");
        pageSize = 3;
        totalItems = userRepository.totalCount();
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<User> memberPage = userRepository.findAll(page, pageSize);
        List<User> memberList = memberPage.getContent();

        req.setAttribute("memberList", memberList);
        req.setAttribute("memberCurrentPage", page);
        req.setAttribute("memberTotalPages", totalPages);

        return "shop/admin/manage";
    }

}
