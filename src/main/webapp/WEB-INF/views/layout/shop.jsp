<%@ page import="com.nhnacademy.shoppingmall.user.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="x" uri="jakarta.tags.xml" %>
<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title>nhn아카데미 shopping mall</title>

</head>
<body>

<%
    HttpSession session = request.getSession(false);
    User user = (session != null) ? (User) session.getAttribute("user") : null;
    request.setAttribute("user", user); // EL에서 사용 가능하도록 설정
%>

    <div class="mainContainer">
        <header class="p-3 bg-dark text-white">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">

                    <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
                    </a>

                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="/index.do" class="nav-link px-2 text-secondary">Home</a></li>
                        <li><a href="/mypage.do" class="nav-link px-2 text-white">마이페이지</a></li>
                        <c:if test="${user != null && user.userAuth == 'ROLE_ADMIN'}">
                            <li><a href="/manage.do" class="nav-link px-2 text-white" >관리</a></li>
                        </c:if>
                    </ul>

                    <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" action="index.do" method="get">
                        <input type="search" class="form-control form-control-dark" placeholder="Search..." aria-label="Search" name="query" value="${param.query}">
                    </form>

                    <div class="text-end d-flex align-items-center" style="height: 100%;">
                        <c:choose>

<%--                            로그인 한 경우 이름, 로그아웃 --%>
                            <c:when test="${not empty user}">
                                <p class="text-white mb-0">${user.userName} 님 환영합니다!</p>
                                <form method="post" action="/logoutAction.do">
                                    <button class="btn btn-warning ms-3" type="submit">로그아웃</button>
                                </form>
                            </c:when>

<%--                            로그인 안한 경우 로그인, 회원가입 버튼 --%>
                            <c:otherwise>
                                <div class="text-end">
                                    <a class="btn btn-outline-light me-2" href="/login.do" >로그인</a>
                                    <a class="btn btn-warning" href="userRegister.do" >회원가입</a>
                                </div>
                            </c:otherwise>

                        </c:choose>
                    </div>


                </div>
            </div>
        </header>

        <main>
            <div class="album py-5 bg-light">
                <div class="container">
                    <jsp:include page="${layout_content_holder}" />
                </div>
            </div>

        </main>

        <footer class="text-muted py-5">
            <div class="container">
                <p class="float-end mb-1">
                    <a href="#">Back to top</a>
                </p>
                <p class="mb-1">shoppingmall by 조승주</p>
            </div>
        </footer>

    </div>

</body>
</html>