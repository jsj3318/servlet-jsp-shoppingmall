package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/manage.do"})
public class ManageController implements BaseController {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
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
            return "redirect:/login.do";
        }

        if(user.getUserAuth() != User.Auth.ROLE_ADMIN){
            log.debug("{} 회원이 관리자 페이지 접근 시도함", user.getUserId());
            return "redirect:/main.do";
        }

        //페이징을 위한 변수들
        int page = 1;
        int pageSize = 10;
        long totalItems = 0;
        int totalPages = 1;

        // 카테고리 리스트
        page = Integer.parseInt(req.getParameter("categoryPage") != null ?
                req.getParameter("categoryPage") : "1");
        pageSize = 10;
        totalItems = categoryRepository.totalCount();
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<Category> categoryPage = categoryRepository.findPage(page, pageSize);
        List<Category> categoryList = categoryPage.getContent();

        req.setAttribute("categoryList", categoryList);
        req.setAttribute("categoryCurrentPage", page);
        req.setAttribute("categoryTotalPages", totalPages);


        // 상품목록
        page = Integer.parseInt(req.getParameter("productPage") != null ?
                req.getParameter("productPage") : "1");
        pageSize = 10;
        totalItems = productRepository.countAll();
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<Product> productPage = productRepository.pageAll(page, pageSize);
        List<Product> productList = productPage.getContent();


        req.setAttribute("productList", productList);
        req.setAttribute("productCurrentPage", page);
        req.setAttribute("productTotalPages", totalPages);



        // 회원 목록을 위한 리스트 보내기
        // memberList
        // memberPage
        page = Integer.parseInt(req.getParameter("memberPage") != null ?
                req.getParameter("memberPage") : "1");
        pageSize = 10;
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
