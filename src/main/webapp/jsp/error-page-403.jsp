<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.error"/>
    </title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div class="container mt-5 p-5">
    <div class="text-center mt-5 p-5">
        <div class="justify-content-center alert alert-secondary" role="alert">
            <h3 class="alert-heading">
                <fmt:message key="header.error403"/>
            </h3>
            <p>
                <fmt:message key="info.error403"/>
            </p>
            <hr>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-dark">
                <fmt:message key="home"/>
            </a>
        </div>
    </div>
</div>
</body>
</html>
