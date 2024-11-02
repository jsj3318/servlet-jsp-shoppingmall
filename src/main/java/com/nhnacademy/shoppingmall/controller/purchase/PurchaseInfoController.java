package com.nhnacademy.shoppingmall.controller.purchase;

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
import com.nhnacademy.shoppingmall.purchase.domain.Purchase;
import com.nhnacademy.shoppingmall.purchase.repository.PurchaseRepository;
import com.nhnacademy.shoppingmall.purchase.repository.impl.PurchaseRepositoryImpl;
import com.nhnacademy.shoppingmall.purchase_product.domain.PurchaseProduct;
import com.nhnacademy.shoppingmall.purchase_product.repository.PurchaseProductRepository;
import com.nhnacademy.shoppingmall.purchase_product.repository.impl.PurchaseProductRepositoryImpl;
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
@RequestMapping(method = RequestMapping.Method.GET, value = {"/purchaseInfo.do"})
public class PurchaseInfoController implements BaseController {
    private final PurchaseRepository purchaseRepository = new PurchaseRepositoryImpl();
    private final PurchaseProductRepository purchaseProductRepository = new PurchaseProductRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int purchase_id = Integer.parseInt(req.getParameter("purchase_id"));
        Purchase purchase = purchaseRepository.findById(purchase_id);

        // 유저 정보 가져와서 주문한 유저가 아니면 내쫒기
        HttpSession session = req.getSession(false);
        User user = null;
        if(session != null){
            user = (User) session.getAttribute("user");
        }

        if(user == null){
            return "redirect:/login.do";
        }

        if(!purchase.getUser_id().equals(user.getUserId())){
            return "redirect:/index.do";
        }

        List<PurchaseProduct> purchaseProductList = purchaseProductRepository.findByPurchaseId(purchase_id);
        List<Product> productList = productRepository.findByPurchaseId(purchase_id);

        req.setAttribute("purchase", purchase);
        req.setAttribute("purchaseProductList", purchaseProductList);
        req.setAttribute("productList", productList);

        return "shop/purchase/purchaseInfo";
    }


}
