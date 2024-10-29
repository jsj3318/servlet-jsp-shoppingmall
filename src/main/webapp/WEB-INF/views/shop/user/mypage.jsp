<%@ page import="java.util.List" %>
<%@ page import="com.nhnacademy.shoppingmall.address.domain.Address" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<link rel="stylesheet" href="/style.css" />

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>


<%--회원 정보 표시 / 정보 수정 버튼--%>
<table>
    <tbody>

    <tr>
        <th>아이디</th>
        <td>${user.userId}</td>
    </tr>

    <tr>
        <th>이름</th>
        <td>${user.userName}</td>
    </tr>

    <tr>
        <th>생년월일</th>
        <td>${user.userBirth}</td>
    </tr>

    <tr>
        <th>권한</th>
        <td>
            <c:choose>
                <c:when test="${user.userAuth == 'ROLE_ADMIN'}">관리자</c:when>
                <c:otherwise>유저</c:otherwise>
            </c:choose>
        </td>
    </tr>

    <tr>
        <th>잔여 포인트</th>
        <td>${user.userPoint}</td>
    </tr>

    <tr>
        <th>가입일</th>
        <td>${user.createdAt}</td>
    </tr>


    </tbody>
</table>
<a href="/userUpdate.do" class="btn btn-primary">회원 정보 수정</a>


<%--유저의 주소 목록 / 추가/ 수정/ 삭제 버튼--%>
<%
    List<Address> addressList = (List<Address>) request.getAttribute("addressList");
%>
<div class="container mt-4">

    <h2>주소 목록</h2>

<%--    주소 추가 inpput button --%>
    <form action="addAddress.do" method="post" class="mb-3">
        <div class="input-group">
            <input type="text" name="newAddress" class="form-control" placeholder="새 주소 입력" required>
            <input type="hidden" name="user_id" value="${user.userId}">
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form>


<%--    주소 수정 창 --%>
    <div class="modal fade" id="editAddressModal" tabindex="-1" aria-labelledby="editAddressLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editAddressLabel">주소 수정</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form action="updateAddress.do" method="post">
                    <div class="modal-body">
                        <!-- 유저 아이디 전달용 hidden -->
                        <input type="hidden" name="user_id" id="user_id" value="${user.userId}">
                        <!-- 기존 주소 값을 전달하기 위한 hidden 필드 -->
                        <input type="hidden" name="originalAddress" id="originalAddress">
                        <!-- 수정할 주소 입력 필드 -->
                        <div class="mb-3">
                            <label for="newAddress" class="form-label">새 주소</label>
                            <input type="text" class="form-control" id="newAddress" name="newAddress" required>
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


<%--    주소 목록 출력 --%>
    <table class="table table-striped">

        <thead>
        <tr>
            <th>주소</th>
            <th style="width: 120px;" class="text-end">관리</th>
        </tr>
        </thead>

        <tbody>
        <c:choose>
            <c:when test="${empty addressList}">
                <tr>
                    <td colspan="2" class="text-center">등록된 주소가 없습니다. 주소를 등록 해 주세요.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="address" items="${addressList}">
                    <tr>
                        <td>${address.address}</td>
                        <td class="text-end">
                            <button
                                    type="button"
                                    class="btn btn-sm btn-warning"
                                    data-bs-toggle="modal"
                                    data-bs-target="#editAddressModal"
                                    onclick="setEditModalValues('${address.address}')">
                                수정
                            </button>
                            <form action="deleteAddress.do" method="post" style="display:inline;">
                                <input type="hidden" name="address" value="${address.address}">
                                <input type="hidden" name="user_id" value="${user.userId}">
                                <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>

    </table>

    <script>
        // 모달에 기존 주소 값을 설정하는 함수
        function setEditModalValues(address) {
            document.getElementById("originalAddress").value = address;
            document.getElementById("newAddress").value = address;
        }
    </script>


</div>




<%--탈퇴 버튼--%>
<form method="post" action="/userDeleteAction.do" onsubmit="return confirmDelete()">
    <button class="btn btn-danger ms-3" type="submit">회원탈퇴</button>
</form>

<script>
    function confirmDelete(){
        return confirm("진짜 탈퇴 하시게요?");
    }
</script>
