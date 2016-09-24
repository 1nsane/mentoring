<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Balance form</title>
</head>
<body>
    <h2>Balance form</h2>
    <form:form method="POST" autocomplete="off" action="/account/${account.id}/balance" modelAttribute="account">
        <form:input type="hidden" path="id" />
        <form:input type="hidden" path="name" />
        <form:input type="hidden" path="status" />

        <p style="color: red;"><form:errors path="balance"/></p>
        <label>balance: </label><form:input type="number" path="balance" />
        <br />

        <button type="submit">Submit</button>
    </form:form>
</body>
</html>