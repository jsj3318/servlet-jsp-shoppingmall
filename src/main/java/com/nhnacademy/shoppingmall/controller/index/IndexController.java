package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do", "/main.do"})
public class IndexController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        //페이징을 위한 변수들
        int page = 1;
        int pageSize = 9;
        long totalItems = 0;
        int totalPages = 1;


        int totalCategory = categoryRepository.totalCount();
        int currentCategory = Integer.parseInt(
                req.getParameter("currentCategory") == null?
                        "0" : req.getParameter("currentCategory")
        );

        req.setAttribute("totalCategory", totalCategory);
        req.setAttribute("currentCategory", currentCategory);
        // 카테고리 불러오고 카테고리로 목록 만들기
        List<Category> categoryList = categoryRepository.findAll();
        req.setAttribute("categoryList", categoryList);

        // 상품 리스트 설정
        //커렌트 카테고리에 따라서 불러오기
        //0이면 전체
        page = Integer.parseInt(req.getParameter("productPage") != null ?
                req.getParameter("productPage") : "1");
        pageSize = 9;
        totalItems = currentCategory == 0 ?
                productRepository.countAll() :
                productRepository.countbyCategoryId(currentCategory);
        totalPages = (int) Math.ceil((double) totalItems / pageSize);

        Page<Product> productPage = currentCategory == 0 ?
                productRepository.pageAll(page, pageSize) :
                productRepository.pagebyCategoryId(currentCategory, page, pageSize);
        List<Product> productList = productPage.getContent();

        req.setAttribute("productList", productList);
        req.setAttribute("productCurrentPage", page);
        req.setAttribute("productTotalPages", totalPages);

        String[] thumbnailUris = new String[productList.size()];
        for(int i=0; i<productList.size(); i++){
            int product_id = productList.get(i).getProduct_id();
            String thumbnail_uri = UriUtil.toThumbnailUri(product_id);

            File file = new File(req.getServletContext().getRealPath(thumbnail_uri));
            if(!file.exists()){
                thumbnailUris[i] = UriUtil.NO_IMAGE;
            }else{
                thumbnailUris[i] = thumbnail_uri;
            }

        }
        req.setAttribute("thumbnailUris", thumbnailUris);

        return "shop/main/index";
    }

}