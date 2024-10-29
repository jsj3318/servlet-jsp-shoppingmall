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
            <button type="submit" class="btn btn-primary">등록</button>
        </div>
    </form>

<%--    주소 목록 출력 --%>

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
