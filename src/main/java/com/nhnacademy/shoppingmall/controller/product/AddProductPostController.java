package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.UriUtil;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product_category.ProductCategoryRepository;
import com.nhnacademy.shoppingmall.product_category.impl.ProductCategoryRepositoryImpl;
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
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 10,         //10MB
        maxRequestSize = 1024 * 1024 * 30       //30MB
)
@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/addProduct.do"})
public class AddProductPostController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final ProductCategoryRepository productCategoryRepository = new ProductCategoryRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

            String productName = null;
            BigInteger price = null;
            String description = null;
            int quantity = 0;

            int productId = -1;

            // 파일 저장 경로 지정
            String thumbnailPath = req.getServletContext().getRealPath(UriUtil.THUMBNAIL_PREFIX);
            String imagePath = req.getServletContext().getRealPath(UriUtil.IMAGE_PREFIX);
            log.debug("path: {}", imagePath);

            File thumbnailDir = new File(thumbnailPath);
            File imageDir = new File(imagePath);

            if (!thumbnailDir.exists()) thumbnailDir.mkdirs();
            if (!imageDir.exists()) imageDir.mkdirs();

        try {
            for (Part part : req.getParts()) {
                String contentDisposition = part.getHeader("content-disposition");

                if (contentDisposition.contains("filename=")) {

                    if (part.getSize() > 0) {

                        if (part.getName().equals("thumbnail")) {
                            // 파일 이름을 나중에 사용할 수 있도록 저장 (예: 섬네일)
                            part.write(thumbnailPath + "temp.png");
                        } else if (part.getName().equals("image")) {
                            // 파일 이름을 나중에 사용할 수 있도록 저장 (예: 상세 이미지)
                            part.write(imagePath + "temp.png");
                        }

                    }

                } else {
                    // 폼 필드인 경우
                    String formValue = req.getParameter(part.getName());

                    log.debug("{} = {}", part.getName(), formValue);

                    // 각 파라미터를 해당 변수에 저장
                    if (part.getName().equals("product_name")) {
                        productName = formValue;
                    } else if (part.getName().equals("price")) {
                        price = new BigInteger(formValue);
                    } else if (part.getName().equals("description")) {
                        description = formValue;
                    } else if (part.getName().equals("quantity")) {
                        quantity = Integer.parseInt(formValue);
                    }
                }

            }

            productId = productRepository.saveAndGetId(productName, price, description, quantity);

            // 임시로 저장된 파일을 실제 경로에 저장
            new File(thumbnailPath + "temp.png").renameTo(new File(thumbnailPath + productId + ".png"));
            new File(imagePath + "temp.png").renameTo(new File(imagePath + productId + ".png"));


            log.debug("{} 상품 업로드 됨", productName);

        } catch (NumberFormatException e) {
            log.error("가격 또는 수량 형식 오류: {}", e.getMessage());
            req.setAttribute("status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("exception_type", e.getClass().getName());
            req.setAttribute("message", e.getMessage());
            req.setAttribute("exception", e);
            req.setAttribute("request_uri", req.getRequestURI());

            try {
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (Exception e) {
            log.error("상품 등록 중 오류 발생: {}", e.getMessage());
            req.setAttribute("status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("exception_type", e.getClass().getName());
            req.setAttribute("message", e.getMessage());
            req.setAttribute("exception", e);
            req.setAttribute("request_uri", req.getRequestURI());

            try {
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


        if(productId < 0){
            throw new RuntimeException("상품 정보 가져오기 실패");
        }

        // 선택된 카테고리들 등록하기
        // 선택된 카테고리 처리
        String[] selectedCategories = req.getParameterValues("categories"); // 선택된 카테고리 ID 배열

        if (selectedCategories != null) {

            if(selectedCategories.length < 1 || selectedCategories.length > 3){
                throw new RuntimeException("카테고리 갯수 오류 발생");
            }

            for (String categoryId : selectedCategories) {

                productCategoryRepository.save(productId, Integer.parseInt(categoryId));

            }
        }



        return "redirect:/productPage.do?product_id=" + productId;
    }

}

