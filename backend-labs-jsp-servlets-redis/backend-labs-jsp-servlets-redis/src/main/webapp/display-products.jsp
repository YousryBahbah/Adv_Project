<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <h1 align="center">Product list</h1>
</head>
<body>
<br>
<br>

<c:forEach var="item" items="${data}">

    <div align="center">${item.id}</div>
    <br>

    <div align="center">${item.name}</div>
    <br>

    <div align="center">${item.price}</div>
    <br>


</c:forEach>
</body>
</html>