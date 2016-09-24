<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="account.delete" /></title>
</head>
<body>
    <h2><spring:message code="account.delete" /></h2>
    <form method="POST" autocomplete="off" action="/account/${id}/remover">
        <button type="submit"><spring:message code="account.delete.confirm" /></button>
    </form>
    <button><a href="/account/${id}" target="_self"><spring:message code="account.delete.go_back" /></a></button>
</body>
</html>