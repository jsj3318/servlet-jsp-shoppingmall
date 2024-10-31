package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;


@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/deleteProduct.do"})
public class DeleteProductPostController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        int productId = Integer.parseInt(req.getParameter("product_id"));

        productRepository.deletebyId(productId);

        File file = new File(req.getServletContext().getRealPath(UriUtil.toThumbnailUri(productId)));
        file.delete();

        file = new File(req.getServletContext().getRealPath(UriUtil.toImageUri(productId)));
        file.delete();

        log.debug("{}번 상품과 이미지 삭제", productId);

        return "redirect:/manage.do";
    }

}

