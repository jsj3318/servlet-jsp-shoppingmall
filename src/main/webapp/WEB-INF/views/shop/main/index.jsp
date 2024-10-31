<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<link rel="stylesheet" href="/style.css" />

<!-- meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>



<title>상품 목록</title>

<div class="container mt-5">
    <div class="row">

        <!-- 좌측 카테고리 리스트 -->
        <div class="col-md-3 category-list-container">
            <h5>카테고리</h5>
            <ul class="list-group category-list">
                <c:forEach var="category" items="${categoryList}">
                    <li class="list-group-item ${category.category_id == currentCategory ? 'active-category' : ''}">
                        <a href="?currentCategory=${category.category_id}" class="category-link">${category.category_name}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- 우측 상품 목록 -->
        <div class="col-md-9">
            <h5>상품 목록</h5>
            <div class="row">
                <c:forEach var="product" items="${productList}" varStatus="status">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <img src="${thumbnailUris[status.index]}" class="card-img-top" alt="${product.product_name}">
                            <div class="card-body">
                                <h5 class="card-title">${product.product_name}</h5>
                                <p class="card-text">${product.price} 원</p>
                                <a href="/productPage.do?product_id=${product.product_id}" class="btn btn-primary">상세보기</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- 페이지네이션 -->
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${productCurrentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?CurrentCategory=${param.CurrentCategory}&productPage=1&query=${param.query}">첫 페이지</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="?CurrentCategory=${param.CurrentCategory}&productPage=${productCurrentPage - 1}&query=${param.query}">이전</a>
                        </li>
                    </c:if>

                    <c:forEach begin="1" end="${productTotalPages}" var="i">
                        <li class="page-item <c:if test='${i == productCurrentPage}'>active</c:if>">
                            <a class="page-link" href="?CurrentCategory=${param.CurrentCategory}&productPage=${i}&query=${param.query}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${productCurrentPage < productTotalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?CurrentCategory=${param.CurrentCategory}&productPage=${productCurrentPage + 1}&query=${param.query}">다음</a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="?CurrentCategory=${param.CurrentCategory}&productPage=${productTotalPages}&query=${param.query}">마지막 페이지</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </div>
</div>