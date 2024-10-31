package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/updateProduct.do"})
public class UpdateProductController implements BaseController {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final ProductCategoryRepository productCategoryRepository = new ProductCategoryRepositoryImpl();

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
            log.debug("{} 회원이 상품 등록 페이지 접근 시도함", user.getUserId());
            return "redirect:/main.do";
        }

        // 카테고리 목록 전달
        req.setAttribute("categoryList", categoryRepository.findAll() );

        // 상품 객체 전달
        int productId = Integer.parseInt(req.getParameter("product_id"));
        Product product = productRepository.findbyId(productId).orElse(null);
        if(product == null){
            throw new RuntimeException("상품 정보 불러오기 실패!");
        }
        req.setAttribute("product", product);
        log.debug("상품 설명: {}", product.getDescription());

        // 이미지들 uri 전달
        String thumbnail_uri = UriUtil.toThumbnailUri(productId);
        String image_uri = UriUtil.toImageUri(productId);

        File file = new File(req.getServletContext().getRealPath(thumbnail_uri));
        log.debug("file uri: {}", file.getPath());
        if(!file.exists()){
            thumbnail_uri = UriUtil.NO_IMAGE;
        }

        file = new File(req.getServletContext().getRealPath(image_uri));
        if(!file.exists()){
            image_uri = UriUtil.NO_IMAGE;
        }

        req.setAttribute("thumbnail_uri", thumbnail_uri);
        req.setAttribute("image_uri", image_uri);


        // 선택된 카테고리 리스트 전달
        List<Integer> selectedCategory = productCategoryRepository.findByProductId(productId);
        req.setAttribute("selectedCategory", selectedCategory);


        return "shop/product/updateProduct";
    }


}
