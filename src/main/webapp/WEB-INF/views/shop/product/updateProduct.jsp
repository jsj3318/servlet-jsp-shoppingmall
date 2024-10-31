<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>



<title>상품 수정 페이지</title>

<div class="container mt-5">
  <h1>상품 수정</h1>

  <form action="/updateProduct.do" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="product_name">상품 이름</label>
      <input type="text" class="form-control" id="product_name" name="product_name"
             value="${product.product_name}" required>
    </div>

    <div class="form-group">
      <label for="price">상품 가격</label>
      <input type="number" class="form-control" id="price" name="price"
             value="${product.price}" required>
    </div>

    <div class="form-group">
      <label for="description">상품 설명</label>
      <textarea class="form-control" id="description" name="description" rows="4" required>${product.description}</textarea>
    </div>

    <div class="form-group">
      <label for="quantity">상품 재고 수량</label>
      <input type="number" class="form-control" id="quantity" name="quantity"
             value="${product.quantity}" required>
    </div>

    <!-- 섬네일과 상세 이미지 파일 업로드 -->
    <div class="form-group">
      <label for="thumbnail">섬네일 이미지</label>
      <input type="file" class="form-control-file" id="thumbnail" name="thumbnail">
      <img src="${thumbnail_uri}" alt="섬네일 이미지" style="max-width: 100px;">
    </div>

    <div class="form-group">
      <label for="image">상세 이미지</label>
      <input type="file" class="form-control-file" id="image" name="image">
      <img src="${image_uri}" alt="상세 이미지" style="max-width: 100px;">
    </div>

    <%-- 카테고리 선택 부분 --%>
    <div class="form-group mt-4">
      <label>카테고리 선택 (최대 3개)</label>
      <div style="border: 1px solid #ccc; padding: 10px; margin-top: 10px; max-height: 150px; overflow-y: auto;">

        <c:forEach var="category" items="${categoryList}">
          <input type="checkbox" name="categories" value="${category.category_id}"
                 <c:if test="${selectedCategory.contains(category.category_id)}">checked</c:if>
                 onchange="limitCheckboxes(this)"> ${category.category_name}<br>
        </c:forEach>

      </div>
    </div>

    <button type="submit" class="btn btn-primary mt-3">수정</button>
  </form>
</div>


<script>

  function limitCheckboxes(checkbox) {
    const checkboxes = document.querySelectorAll('input[name="categories"]');
    const checkedCount = Array.from(checkboxes).filter(i => i.checked).length;

    if (checkedCount > 3) {
      alert("최대 3개까지만 선택할 수 있습니다.");
      checkbox.checked = false; // 마지막으로 체크한 박스를 해제
    }
  }

  function validateForm() {
    const checkboxes = document.querySelectorAll('input[name="categories"]');
    const checkedCount = Array.from(checkboxes).filter(i => i.checked).length;

    if (checkedCount < 1) {
      alert("적어도 하나의 카테고리를 선택해 주세요.");
      return false; // 폼 제출 막기
    }
    return true; // 폼 제출 허용
  }

</script>