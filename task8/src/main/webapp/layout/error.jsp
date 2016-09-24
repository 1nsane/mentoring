<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Error</title>
</head>
<body>
    <h2>Error</h2>

    <c:choose>
        <c:when test="${not empty message}">
            <p>${message}</p>
        </c:when>
        <c:otherwise>
            <p>Something really odd happened here</p>
        </c:otherwise>
    </c:choose>
</body>
</html>