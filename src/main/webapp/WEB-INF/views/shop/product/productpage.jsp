<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<title>상품 정보 페이지</title>

<div class="container mt-5">

  <div class="row">

<%--  속한 카테고리 목록 가로로 --%>
  <div class="category-container mt-3">
    <c:forEach var="category" items="${categoryList}">
      <span class="category-badge">${category.category_name}</span>
    </c:forEach>
  </div>



  <div class="col-md-4">
      <c:choose>
        <c:when test="${not empty thumbnail_uri}">
          <img src="${thumbnail_uri}" alt="${product.product_name} Thumbnail" class="img-fluid mb-3">
        </c:when>
        <c:otherwise>
          <img src="/resources/no-image.png" alt="No Image Available" class="img-fluid mb-3">
        </c:otherwise>
      </c:choose>
    </div>

    <div class="col-md-8">
      <h2><strong>${product.product_name}</strong></h2>
      <p><strong>가격:</strong> ${product.price} 원</p>
      <p><strong>설명:</strong> ${product.description}</p>
      <p><strong>재고 수량:</strong> ${product.quantity}</p>
    </div>

  </div>

  <div class="row mt-4">

    <div class="col-md-12">
      <c:choose>
        <c:when test="${not empty image_uri}">
          <img src="${image_uri}" alt="${product.product_name} Image" class="img-fluid">
        </c:when>
        <c:otherwise>
          <img src="/resources/no-image.png" alt="No Image Available" class="img-fluid mb-3">
        </c:otherwise>
      </c:choose>
    </div>
    
  </div>

</div>
