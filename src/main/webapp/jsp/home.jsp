<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <fmt:message key="title.homepage" var="home"/>
    <title>${home}</title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>

<c:if test="${not empty sessionScope.user}">
    <jsp:useBean id="role" scope="session" type="java.lang.String"/>
    <jsp:useBean id="user" scope="session" type="model.User"/>
</c:if>
<div class="container p-5">
    <div class="text-center mt-5 p-5">
        <fmt:message key="hello" var="hello"/>

        <c:choose>

            <c:when test="${empty sessionScope.user}">
                <h1 class="display-2 mt-5 mb-5">
                    <fmt:message key="welcome"/>
                </h1>
                <a href="${pageContext.request.contextPath}/app?command=viewServices"
                   class="display-4 mt-5 text-decoration-none">
                    <fmt:message key="check-services"/>
                </a>
            </c:when>

            <c:when test="${role.equals('client')}">
                <h1 class="display-2 mt-5 mb-5">
                        ${hello} ${user.name} ${user.surname}!</h1>
                <a href="${pageContext.request.contextPath}/app?command=viewServices"
                   class="display-4 mt-5 text-decoration-none">
                    <fmt:message key="check-services"/>
                </a>
            </c:when>

            <c:when test="${role.equals('master')}">
                <h1 class="display-2 mt-5 mb-5">
                    ${hello} ${user.getFullnameUA()}!
                </h1>
                <a href="${pageContext.request.contextPath}/app?command=viewMasterSchedule"
                   class="display-4 mt-5 text-decoration-none">
                    <fmt:message key="check-schedule"/>
                </a>
            </c:when>

            <c:when test="${role.equals('admin')}">
                <h1 class="display-2 mt-5 mb-5">
                        ${hello} ${user.name} ${user.surname}!
                </h1>
                <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests"
                   class="display-4 mt-5 text-decoration-none">
                    <fmt:message key="check-requests"/>
                </a>
            </c:when>

        </c:choose>
    </div>
</div>
</body>
</html>
