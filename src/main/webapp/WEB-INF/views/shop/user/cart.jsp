<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.address.domain.Address" %>
<%@ page import="com.nhnacademy.shoppingmall.pointHistory.domain.PointHistory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<title>장바구니</title>

<div class="container mt-5">
    <h2>장바구니</h2>
    <c:if test="${not empty cart.itemList}">
        <ul class="list-group">
            <c:forEach var="item" items="${cart.itemList}">
                <li class="list-group-item cart-item d-flex align-items-center">

                    <a href="productPage.do?product_id=${item.product_id}">
                        <img src="${item.thumbnailUri}" alt="${item.product_name} Thumbnail" class="thumbnail me-3">
                    </a>

                    <div class="item-details">
                        <h3><a href="productPage.do?product_id=${item.product_id}" style="color: inherit; text-decoration: none;">
                            <strong>${item.product_name}</strong>
                        </a></h3>
                        <strong>가격:</strong> ${item.price * item.quantity} 원 <br>
                        <strong>수량:</strong> ${item.quantity}

                    <form action="/cartItemIncrease.do" method="post" style="display:inline;">
                            <input type="hidden" name="product_id" value="${item.product_id}">
                            <button type="submit" class="btn btn-sm quantity-button">+</button>
                        </form>

                        <form action="/cartItemDecrease.do" method="post" style="display:inline;">
                            <input type="hidden" name="product_id" value="${item.product_id}">
                            <button type="submit" class="btn btn-sm quantity-button">-</button>
                        </form>

                    </div>

                    <form action="/deleteCartItem.do" method="post" style="display:inline;">
                        <input type="hidden" name="product_id" value="${item.product_id}">
                        <button type="submit" class="btn btn-sm delete-button">삭제</button>
                    </form>

                </li>
            </c:forEach>
        </ul>

    </c:if>
    <c:if test="${empty cart.itemList}">
        <p>장바구니가 비어있습니다.</p>
    </c:if>

    <div class="d-flex justify-content-between align-items-center mt-3">
        <a href="index.do" class="btn btn-success">쇼핑 계속하기</a>

        <!-- 장바구니에 상품이 있을 때만 총액과 주문하기 버튼 표시 -->
        <c:if test="${not empty cart.itemList}">
            <!-- 장바구니 총액 표시 -->
            <div>
                <strong>총액:</strong> ${cart.getTotal()} 원
            </div>

            <!-- 주문하기 버튼 -->
            <a href="purchase.do" class="btn btn-danger">주문하기</a>
        </c:if>

    </div>

</div>

