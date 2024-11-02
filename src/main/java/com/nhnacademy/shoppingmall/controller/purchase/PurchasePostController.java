package com.nhnacademy.shoppingmall.controller.purchase;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.cart.Cart;
import com.nhnacademy.shoppingmall.cart.CartItem;
import com.nhnacademy.shoppingmall.common.initialize.PointThreadInitializer;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory;
import com.nhnacademy.shoppingmall.pointHistory.repository.PointHistoryRepository;
import com.nhnacademy.shoppingmall.pointHistory.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.purchase.repository.PurchaseRepository;
import com.nhnacademy.shoppingmall.purchase.repository.impl.PurchaseRepositoryImpl;
import com.nhnacademy.shoppingmall.purchase_product.repository.PurchaseProductRepository;
import com.nhnacademy.shoppingmall.purchase_product.repository.impl.PurchaseProductRepositoryImpl;
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = {"/purchase.do"})
public class PurchasePostController implements BaseController {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final PointHistoryRepository pointHistoryRepository = new PointHistoryRepositoryImpl();
    private final PurchaseRepository purchaseRepository = new PurchaseRepositoryImpl();
    private final PurchaseProductRepository purchaseProductRepository = new PurchaseProductRepositoryImpl();
    private final AddressRepository addressRepository = new AddressRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

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

        // 주문 실패시 롤백 되어야 하므로 하나의 트랜잭션(이 execute 함수)에서 모든 작업 수행

        // 주문 실패 시 어트리뷰트로 errormsg 전달하고 rollback 되도록 sqlerror 설정 후 purchaseResult 페이지 표시

        // 상품 재고 차감 -> 상품의 재고가 부족한지 검사
        Cart cart = (Cart) session.getAttribute("cart");

        for(CartItem item : cart.getItemList()){
            Product product = productRepository.findbyId(item.getProduct_id()).get();
            if(product.getQuantity() >= item.getQuantity()){
                // 상품의 재고가 주문한 갯수보다 크거나 같다 -> 구매 가능
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.update(product);
                log.debug("[{}] 제품 재고 {}개 감소",
                        product.getProduct_name(), item.getQuantity());
            }
            else{
                // 상품의 재고가 부족하다 -> 구매 실패
                req.setAttribute("errorMsg",
                        "[" + item.getProduct_name()+"]의 재고가 부족합니다.");
                DbConnectionThreadLocal.setSqlError(true);
                log.debug("주문 실패 : {}", req.getAttribute("errorMsg"));
                return "shop/purchase/purchaseResult";
            }
        }


        // 유저 포인트 충분한지 검사
        if(BigInteger.valueOf(user.getUserPoint()).compareTo(cart.getTotal()) < 0){
            // 포인트가 주문 하기 부족하다
            req.setAttribute("errorMsg",
                    "포인트가 부족합니다.");
            DbConnectionThreadLocal.setSqlError(true);
            log.debug("주문 실패 : {}", req.getAttribute("errorMsg"));
            return "shop/purchase/purchaseResult";
        }

        // 유저 포인트 차감
        int totalPrice = cart.getTotal().intValue();
        user.setUserPoint( user.getUserPoint() - totalPrice );

        // 포인트 차감 내역 생성
        pointHistoryRepository.save(
                new PointHistory(
                        user.getUserId(),
                        -totalPrice,
                        "주문 결제",
                        LocalDateTime.now()
                )
        );

        // purchase 추가
        // 넘어온 address_id 활용
        int purchaseId = purchaseRepository.saveAndGetId(
                user.getUserId(),
                addressRepository.getAddressById(Integer.parseInt(req.getParameter("address_id"))),
                cart.getTotal()
        );

        // 유저 포인트 10% 페이백
        // 독립 thread 에서 처리
        int payback = (int)(totalPrice*0.1);
        user.setUserPoint( user.getUserPoint() + payback );
        try {
            RequestChannel requestChannel = (RequestChannel) req.getServletContext().getAttribute(PointThreadInitializer.CONTEXT_REQUEST_CHANNEL_NAME);
            requestChannel.addRequest(new PointChannelRequest(payback, user));
        } catch (InterruptedException e) {
            log.debug("포인트 적립 스레드 에러");
            // 포인트 증가 에러 내역 생성
            pointHistoryRepository.save(
                    new PointHistory(
                            user.getUserId(),
                            0,
                            "포인트 적립 실패",
                            LocalDateTime.now()
                    )
            );
        }

        // purchase_product들 장바구니에서 가져와서 추가
        // -> purchase id 필요함
        // purchase save and get Id 로 바꿔야 함
        for(CartItem item : cart.getItemList()){
            purchaseProductRepository.save(
                    purchaseId,
                    item.getProduct_id(),
                    item.getQuantity()
            );
        }


        // 모든 주문 작업 완료 ------------------
        // 카트 비우기
        cart.clear();

        // 세션에 업데이트 된 정보들 저장하기
        session.setAttribute("user", user);
        session.setAttribute("cart", cart);


        return "shop/purchase/purchaseResult";
    }


}
