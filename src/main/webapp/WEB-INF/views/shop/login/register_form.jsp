<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<c:choose>

  <c:when test="${empty user}">
    <c:set var="action" value="/userRegisterAction.do"/>
  </c:when>

  <c:otherwise>
    <c:set var="action" value="/userUpdateAction.do"/>
  </c:otherwise>

</c:choose>

<div style="margin: auto; width: 400px;">
  <div class="p-2">
    <form method="post" action="${action}">

      <h1 class="h3 mb-3 fw-normal">
        <c:choose>
          <c:when test="${empty user}">User Register</c:when>
          <c:otherwise>User Update</c:otherwise>
        </c:choose>
      </h1>

      <div class="form-floating">
        <input type="text" name="user_id" class="form-control" id="user_id" placeholder="회원 아이디"
               value="${user.userId}" <c:if test="${not empty user}">readonly</c:if> required>
        <label for="user_id">회원 아이디</label>
      </div>

      <div class="form-floating">
        <input type="password" name="user_password" class="form-control" id="user_password" placeholder="비밀번호"
               value="${user.userPassword}" required>
        <label for="user_password">비밀번호</label>
      </div>

      <div class="form-floating">
        <input type="text" name="user_name" class="form-control" id="user_name" placeholder="성명"
               value="${user.userName}" required>
        <label for="user_password">성명</label>
      </div>

      <div class="form-floating">
        <input type="number" name="user_birth" class="form-control" id="user_birth" placeholder="YYYYMMDD"
               value="${user.userBirth}" maxlength="8" required>
        <label for="user_birth">생년월일</label>
      </div>

      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">
        <c:choose>
          <c:when test="${empty user}">Register</c:when>
          <c:otherwise>Update</c:otherwise>
        </c:choose>
      </button>

      <p class="mt-5 mb-3 text-muted">© 2024-2024</p>

    </form>
  </div>
</div>