package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.impl.ProductCategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.recentViewService.RecentViewService;
import com.nhnacademy.shoppingmall.recentViewService.impl.RecentViewServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/productPage.do"})
public class ProductPageController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final ProductCategoryRepository productCategoryRepository = new ProductCategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int product_id = Integer.parseInt(req.getParameter("product_id"));
        Product product = productRepository.findbyId(product_id).orElse(null);
        if(product == null){
            throw new RuntimeException("존재하지 않는 상품의 페이지!!!!");
        }

        // 장바구니 여부 전달
        HttpSession session = req.getSession(false);
        Cart cart = null;
        if(session != null){
            cart = (Cart)session.getAttribute("cart");
        }

        if(cart != null){
            req.setAttribute("inCart", cart.hasProduct(product_id));
        }


        // 이미지 uri 가져오기
        String thumbnail_uri = UriUtil.toThumbnailUri(product_id);
        String image_uri = UriUtil.toImageUri(product_id);

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

        // 상품의 카테고리 목록 주기
        List<Category> categoryList = productCategoryRepository.findCategoryByProductId(product_id);
        req.setAttribute("categoryList", categoryList);

        req.setAttribute("product", product);
        log.debug("{} 상품 조회", product.getProduct_name());


        // 최근 본 상품 쿠키에 해당 상품 추가하기
        RecentViewService recentViewService = new RecentViewServiceImpl();
        recentViewService.add(product_id, req, resp);


        return "shop/product/productpage";
    }


}
