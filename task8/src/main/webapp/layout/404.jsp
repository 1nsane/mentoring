<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="message" scope="request">
    <spring:message code="error.page_not_found" />
</c:set>

<jsp:include page="error.jsp">
    <jsp:param name="message" value="${message}" />
</jsp:include>