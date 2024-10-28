<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="/style.css" />

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
        <td>${user.userAuth}</td>
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


<ul>

    <li>
        <a href="/userUpdate.do">회원 정보 수정</a>
    </li>

    <li>
        <form method="post" action="/userDeleteAction.do">
            <button class="btn btn-danger ms-3" type="submit">회원탈퇴</button>
        </form>
    </li>

 </ul>
