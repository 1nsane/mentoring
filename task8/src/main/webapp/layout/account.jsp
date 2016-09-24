<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Account Page</title>
</head>
<body>
    <h2>Account Page</h2>

    <c:choose>
        <c:when test="${not empty account}">
            <p>
            id: ${account.id} <br />
            name: ${account.name} <br />
            status: ${account.status}<br />
            balance: ${account.balance}
            </p>
        </c:when>
        <c:otherwise>
            <p style="color:red;">Account not found</p>
        </c:otherwise>
    </c:choose>

    <br />
    <button><a href="/account/${account.id}/editor" target="_self"><spring:message code="account.edit" /></a></button>
    <br /><br />
    <button><a href="/account/${account.id}/balance" target="_self"><spring:message code="account.edit_balance" /></a></button>
    <br /><br />
    <button><a href="/account/${account.id}/remover" target="_self"><spring:message code="account.delete" /></a></button>
    <br /><br />
    <button><a href="/account/list" target="_self"><spring:message code="account.list" /></a></button>

</body>
</html>