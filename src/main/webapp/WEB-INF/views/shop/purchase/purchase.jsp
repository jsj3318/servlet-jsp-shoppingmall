
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<div class="container mt-5">
    <h2>주문 확인</h2>

    <!-- 상품명 표시 -->
    <div class="mt-4">
        <strong>상품:</strong>
        <span style="font-size: 1.2em;">
            <c:choose>
                <c:when test="${cart.size() == 1}">
                    ${cart.itemList[0].product_name}
                </c:when>
                <c:otherwise>
                    ${cart.itemList[0].product_name} 외 ${cart.size() - 1}개
                </c:otherwise>
            </c:choose>
        </span>
    </div>

    <!-- 총액 표시 -->
    <div class="mt-2">
        <strong>총액:</strong> ${cart.getTotal()} 원
    </div>

    <!-- 주소 선택 -->
    <div class="mt-3">
        <strong>배송지 선택:</strong>
        <div style="max-height: 150px; overflow-y: auto;">

            <form action="purchase.do" method="post">

                <c:forEach var="address" items="${addressList}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="address_id" id="address${address.address_id}"
                               value="${address.address_id}" <c:if test="${addressList[0] == address}">checked</c:if> required>
                        <label class="form-check-label" for="address${address.address_id}">
                                ${address.address}
                        </label>
                    </div>
                </c:forEach>


            </form>

        </div>
    </div>
                <!-- 주문 결정 버튼 -->
                <button type="submit" class="btn btn-danger mt-3">주문 결정</button>
</div>