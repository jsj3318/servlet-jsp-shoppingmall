<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Error Page</title>
  <link rel="stylesheet" href="/style.css" />
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</head>
<body>

<table class="table">
  <tbody>
  <tr>
    <th>status_code</th>
    <td>${status_code}</td>
  </tr>
  <tr>
    <th>exception_type</th>
    <td>${exception_type}</td>
  </tr>
  <tr>
    <th>message</th>
    <td>${message}</td>
  </tr>
  <tr>
    <th>exception</th>
    <td>${exception}</td>
  </tr>
  <tr>
    <th>request_uri</th>
    <td>${request_uri}</td>
  </tr>
  </tbody>

</table>
</body>
</html>