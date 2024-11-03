<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<div class="container mt-5">
    <!-- 주문 정보 -->
    <h2>주문 상세</h2>
    <div class="mt-4">
        <strong>주문 번호:</strong> ${purchase.purchase_id} <br>
        <strong>주문 일시:</strong> ${purchase.purchased_at} <br>
        <strong>배송지:</strong> ${purchase.destination}
    </div>

    <!-- 상품 목록 테이블 -->
    <table class="table mt-4">
        <thead>
        <tr>
            <th>상품 이름</th>
            <th class="text-end">수량</th>
            <th class="text-end">가격</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${productList}" varStatus="status">
            <tr>
                <td>${product.product_name}</td>
                <td class="text-end">${purchaseProductList[status.index].quantity}</td>
                <td class="text-end">${product.price * purchaseProductList[status.index].quantity} 원</td>
            </tr>
        </c:forEach>
        </tbody>
        <!-- 총 금액 -->
        <tfoot>
        <tr>
            <th colspan="2" class="text-end">총 금액</th>
            <th class="text-end">${purchase.total_amount} 원</th>
        </tr>
        </tfoot>
    </table>
</div>