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

<h1>관리자 페이지</h1>

<%-- 상품 카테고리 관리  --%>
<div class="container mt-4">

    <h2>카테고리 목록</h2>

    <%--    카테고리 추가 input button --%>
    <form action="addCategory.do" method="post" class="mb-3">
        <div class="input-group">
            <input type="text" name="newCategory" class="form-control" placeholder="새 카테고리명 입력" required>
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form>


    <%--    카테고리 수정 창 --%>
    <div class="modal fade" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editCategoryLabel">카테고리 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="updateCategory.do" method="post">
                    <div class="modal-body">
                        <!-- 카테고리 id 전달하기 위한 hidden 필드 -->
                        <input type="hidden" name="category_id" id="category_id">
                        <!-- 카테고리명 입력 필드 -->
                        <div class="mb-3">
                            <label for="newCategory" class="form-label">새 카테고리명</label>
                            <input type="text" class="form-control" id="newCategory" name="newCategory" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <%--    카테고리 목록 출력 --%>
    <table class="table table-striped">

        <thead>
        <tr>
            <th>카테고리</th>
            <th style="width: 120px;" class="text-end">관리</th>
        </tr>
        </thead>

        <tbody>
        <c:choose>

            <c:when test="${empty categoryList}">
                <tr>
                    <td colspan="2" class="text-center">등록된 카테고리가 없습니다. 새 카테고리를 등록 해 주세요.</td>
                </tr>
            </c:when>

            <c:otherwise>

                <c:forEach var="category" items="${categoryList}">
                    <tr>
                        <td>${category.category_name}</td>
                        <td class="text-end">
                            <button
                                    type="button"
                                    class="btn btn-sm btn-warning"
                                    data-bs-toggle="modal"
                                    data-bs-target="#editCategoryModal"
                                    onclick="setEditCategoryModalValues('${category.category_id}', '${category.category_name}')">
                                수정
                            </button>
                            <form action="deleteCategory.do" method="post" style="display:inline;" onsubmit="return confirmDeleteCategory()">
                                <input type="hidden" name="category_id" value="${category.category_id}">
                                <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                            </form>

                            <script>
                                function confirmDeleteCategory(){
                                    return confirm("${category.category_name} 카테고리를 진짜루 삭제 하시겠습니까?");
                                }
                            </script>

                        </td>
                    </tr>
                </c:forEach>

            </c:otherwise>
        </c:choose>
        </tbody>

    </table>

    <!-- 페이징 UI -->
    <div class="pagination">
        <c:forEach var="i" begin="1" end="${categoryTotalPages}">
            <a href="?categoryPage=${i}&productPage=${productCurrentPage}&memberPage=${memberCurrentPage}" class="${i == categoryCurrentPage ? 'active' : ''} page-link">${i}</a>
        </c:forEach>
    </div>

    <script>
        // 모달에 기존 주소 값을 설정하는 함수
        function setEditCategoryModalValues(category_id, category_name) {
            document.getElementById("category_id").value = category_id;
            document.getElementById("newCategory").value = category_name;
        }
    </script>


</div>



<%-- 상품 관리 --%>
<%-- 상품 카테고리 관리  --%>
<div class="container mt-4">

    <h2>상품 목록</h2>

    <%--    상품 추가 페이지 button --%>
    <a href="/addProduct.do" class="btn btn-primary">상품 등록</a>

    <%--    상품 목록 출력 --%>
    <table class="table table-striped">

        <thead>
        <tr>
            <th>상품명</th>
            <th>가격</th>
            <th>재고</th>
            <th class="text-end">관리</th>
        </tr>
        </thead>

        <tbody>
        <c:choose>

            <c:when test="${empty productList}">
                <tr>
                    <td colspan="4" class="text-center">등록된 상품이 없습니다. 상품을 등록 해 주세요.</td>
                </tr>
            </c:when>

            <c:otherwise>

                <c:forEach var="product" items="${productList}">
                    <tr>

                        <td>${product.product_name}</td>
                        <td>${product.price}</td>
                        <td>${product.quantity}</td>

                        <td class="text-end">

                            <a href="/productPage.do?product_id=${product.product_id}" class="btn btn-sm btn-primary" style="display:inline-block; margin-right: 5px;">상품 페이지</a>

                            <a href="/updateProduct.do?product_id=${product.product_id}" class="btn btn-sm btn-warning" style="display:inline-block; margin-right: 5px;">수정</a>

                            <form action="deleteProduct.do" method="post" style="display:inline-block;" onsubmit="return confirmDeleteProduct()">
                                <input type="hidden" name="product_id" value="${product.product_id}">
                                <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                            </form>

                            <script>
                                function confirmDeleteProduct(){
                                    return confirm("${product.product_name} 상품을 진짜루 삭제 하시겠습니까?");
                                }
                            </script>

                        </td>

                    </tr>
                </c:forEach>

            </c:otherwise>
        </c:choose>
        </tbody>

    </table>

    <!-- 페이징 UI -->
    <div class="pagination">
        <c:forEach var="i" begin="1" end="${productTotalPages}">
            <a href="?categoryPage=${categoryCurrentPage}&product=${i}&memberPage=${memberCurrentPage}" class="${i == productCurrentPage ? 'active' : ''} page-link">${i}</a>
        </c:forEach>
    </div>


</div>




<%--    회원 상세 정보 창 --%>
<div class="modal fade" id="memberInfoModal" tabindex="-1" aria-labelledby="memberInfoLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="memberInfoLabel">회원 정보</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
                <div class="modal-body">

                    <div class="mb-3">
                        <label for="userId" class="form-label modal-label">아이디</label>
                        <p id="userId" class="form-control-plaintext">${member.userId}</p>
                    </div>
                    <div class="mb-3">
                        <label for="userName" class="form-label modal-label">이름</label>
                        <p id="userName" class="form-control-plaintext">${member.userName}</p>
                    </div>
                    <div class="mb-3">
                        <label for="userBirth" class="form-label modal-label">생년월일</label>
                        <p id="userBirth" class="form-control-plaintext">${member.userBirth}</p>
                    </div>
                    <div class="mb-3">
                        <label for="userAuth" class="form-label modal-label">권한</label>
                        <p id="userAuth" class="form-control-plaintext">
                            <c:choose>
                                <c:when test="${member.userAuth == 'ROLE_ADMIN'}">관리자</c:when>
                                <c:otherwise>회원</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                    <div class="mb-3">
                        <label for="userPoint" class="form-label modal-label">포인트</label>
                        <p id="userPoint" class="form-control-plaintext">${member.userPoint}</p>
                    </div>
                    <div class="mb-3">
                        <label for="createdAt" class="form-label modal-label">가입일시</label>
                        <p id="createdAt" class="form-control-plaintext">${member.createdAt}</p>
                    </div>
                    <div class="mb-3">
                        <label for="latestLoginAt" class="form-label modal-label">마지막 로그인 일시</label>
                        <p id="latestLoginAt" class="form-control-plaintext">${member.latestLoginAt}</p>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                </div>
        </div>
    </div>
</div>

<div class="container mt-4">
    <h2>회원 목록</h2>
<%--    회원 목록  --%>
<table class="table table-striped">

    <thead>
    <tr>
        <th>ID</th>
        <th>이름</th>
        <th>권한</th>
        <th style="width: 120px;" class="text-end"></th>
    </tr>
    </thead>

    <tbody>

            <c:forEach var="member" items="${memberList}">
                <tr>
                    <td>${member.userId}</td>
                    <td>${member.userName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${member.userAuth == 'ROLE_ADMIN'}">관리자</c:when>
                            <c:otherwise>회원</c:otherwise>
                        </c:choose>
                    </td>

                    <td class="text-end">
                        <button
                                type="button"
                                class="btn btn-sm btn-warning"
                                data-bs-toggle="modal"
                                data-bs-target="#memberInfoModal"
                                onclick="setMemberInfoModal('${member.userId}', '${member.userName}', '${member.userBirth}', '${member.userAuth}', '${member.userPoint}', '${member.createdAt}', '${member.latestLoginAt}'  )">
                            상세정보
                        </button>
                    </td>

                </tr>
            </c:forEach>

    </tbody>

</table>

<!-- 페이징 UI -->
<div class="pagination">
    <c:forEach var="i" begin="1" end="${memberTotalPages}">
        <a href="?categoryPage=${categoryCurrentPage}&productPage=${productCurrentPage}&memberPage=${i}" class="${i == memberCurrentPage ? 'active' : ''} page-link">${i}</a>
    </c:forEach>
</div>

<script>
    // 상세정보 모달에 기존 주소 값을 설정하는 함수
    function setMemberInfoModal(userId, userName, userBirth, userAuth, userPoint, createdAt, latestLoginAt) {
        document.getElementById("userId").textContent = userId;
        document.getElementById("userName").textContent = userName;
        document.getElementById("userBirth").textContent = userBirth;
        document.getElementById("userAuth").textContent = userAuth === 'ROLE_ADMIN' ? '관리자' : '회원';
        document.getElementById("userPoint").textContent = userPoint;
        document.getElementById("createdAt").textContent = createdAt;
        document.getElementById("latestLoginAt").textContent = latestLoginAt;
    }

</script>

</div>