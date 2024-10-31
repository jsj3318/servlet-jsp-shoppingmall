<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<title>상품 등록 페이지</title>

<div class="container mt-5">
  <h1>상품 등록</h1>

  <form action="/addProduct.do" method="post" enctype="multipart/form-data">

    <div class="form-group">
      <label for="product_name">상품 이름</label>
      <input type="text" class="form-control" id="product_name" name="product_name" required>
    </div>

    <div class="form-group">
      <label for="price">상품 가격</label>
      <input type="number" class="form-control" id="price" name="price" step="1" required>
    </div>

    <div class="form-group">
      <label for="description">상품 설명</label>
      <textarea class="form-control" id="description" name="description" rows="4" required></textarea>
    </div>

    <div class="form-group">
      <label for="quantity">상품 재고 수량</label>
      <input type="number" class="form-control" id="quantity" name="quantity" required>
    </div>


<%--    섬네일, 상세 이미지 파일 업로드 부분 --%>
    <div class="form-group">
      <label for="thumbnail">섬네일 이미지</label>
      <input type="file" class="form-control-file" id="thumbnail" name="thumbnail">
    </div>

    <div class="form-group">
      <label for="image">상세 이미지</label>
      <input type="file" class="form-control-file" id="image" name="image">
    </div>



    <button type="submit" class="btn btn-primary mt-3">등록</button>
  </form>





</div>
