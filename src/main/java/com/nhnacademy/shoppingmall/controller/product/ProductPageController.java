package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = {"/productPage.do"})
public class ProductPageController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int product_id = Integer.parseInt(req.getParameter("product_id"));
        Product product = productRepository.findbyId(product_id).orElse(null);
        if(product == null){
            throw new RuntimeException("존재하지 않는 상품의 페이지!!!!");
        }

        String thumbnail_uri = "/resources/thumbnail/" + product_id + ".png";
        String image_uri = "/resources/image/" + product_id + ".png";

        req.setAttribute("thumbnail_uri", thumbnail_uri);
        req.setAttribute("image_uri", image_uri);
        req.setAttribute("product", product);
        log.debug("{} 상품 조회", product.getProduct_name());
        return "shop/product/productpage";
    }


}
