<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>studentRegister</title>
  <link rel="stylesheet" href="/style.css" />
</head>

<body>


<c:choose>

  <c:when test="${empty student}">
  <c:set var="action" value="/student/register.do"/>
  </c:when>

  <c:otherwise>
    <c:set var="action" value="/student/update.do"/>
  </c:otherwise>

</c:choose>




<form method="post" action="${action}">
<table>

  <tr>
    <th>ID</th>
    <td><input type="text" name="id" value="${student.id}"
               <c:if test="${not empty student}">readonly</c:if>
    /></td>
  </tr>

  <tr>
    <th>이름</th>
    <td><input type="text" name="name" value="${student.name}" /></td>
  </tr>

  <tr>
    <th>성별</th>
    <td>
      
      <c:choose>
        <c:when test="${student.gender == 'M'}">
          <input type="radio" name="gender" value="남" checked>남
          <input type="radio" name="gender" value="여">여
        </c:when>
        <c:when test="${student.gender == 'F'}">
          <input type="radio" name="gender" value="남">남
          <input type="radio" name="gender" value="여" checked>여
        </c:when>
        <c:otherwise>
          <input type="radio" name="gender" value="남">남
          <input type="radio" name="gender" value="여">여
        </c:otherwise>
      </c:choose>
      
      
    </td>
  </tr>

  <tr>
    <th>나이</th>
    <td><input type="number" name="age" value="${student.age}" /></td>
  </tr>

</table>

  <p>
  <button type="submit">
    <c:choose>
      <c:when test="${ action.contains('register') }">등록</c:when>
      <c:otherwise>수정</c:otherwise>
    </c:choose>
  </button>
  </p>

</form>

</body>
</html>
