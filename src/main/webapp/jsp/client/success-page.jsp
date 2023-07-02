<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.success"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="container mt-5 p-5">
    <div class="text-center mt-5 p-5">
        <div class="justify-content-center alert alert-success" role="alert">
            <h4 class="alert-heading">
                <fmt:message key="success.alert-heading"/>
            </h4>
            <p>
                <fmt:message key="success.info"/>
            </p>
            <hr>
            <a href="${pageContext.request.contextPath}/app?command=viewServices">
                <fmt:message key="button.check-other-services"/>
            </a>
        </div>
    </div>
</div>
</body>
</html>
