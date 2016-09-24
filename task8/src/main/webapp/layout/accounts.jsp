<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Accounts Page</title>
</head>
<body>
    <h2>Accounts Page</h2>
    <button><a href="/account" target="_self"><spring:message code="account.add" /></a></button>

    <c:choose>
        <c:when test="${not empty accounts}">
            <c:forEach items="${accounts}" var="account">
                <a href="/account/${account.id}" target="_self">
                    <p>id: ${account.id} | name: ${account.name} | status: ${account.status} | balance: ${account.balance}</p>
                </a>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p style="color:red;">Accounts list is empty</p>
        </c:otherwise>
    </c:choose>
</body>
</html>