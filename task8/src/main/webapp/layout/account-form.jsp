<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Account form</title>
</head>
<body>
    <h2>Account form</h2>
    <form:form method="POST" autocomplete="off" action="/account" modelAttribute="account">
        <c:choose>
            <c:when test="${account.id != 0}">
                <form:input type="hidden" path="id" />
            </c:when>
        </c:choose>

        <p style="color: red;"><form:errors path="name"/></p>
        <label>name: </label><form:input type="text" path="name" />
        <br />

        <p style="color: red;"><form:errors path="status"/></p>
        <label>status: </label><form:input type="text" path="status" />
        <br />

        <p style="color: red;"><form:errors path="balance"/></p>
        <label>balance: </label><form:input type="number" path="balance" />
        <br />

        <button type="submit">Submit</button>
    </form:form>
</body>
</html>