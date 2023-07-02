<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>Navigation Bar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg p-0">
    <div class="container-fluid mt-2 mb-3 justify-content-start">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="btn btn-lg nav-link text-uppercase"
                   href="${pageContext.request.contextPath}/app?command=changeLang&lang=uk">
                    UA</a>
            </li>
            <li class="nav-item" style="border-left:thin solid #0000ff">
                <a class="btn btn-lg nav-link text-uppercase"
                   href="${pageContext.request.contextPath}/app?command=changeLang&lang=en">
                    EN</a>
            </li>
        </ul>
    </div>
    <div class="container-fluid mt-2 mb-3 justify-content-end">
        <ul class="navbar-nav">

            <li class="nav-item">
                <a class="btn btn-lg nav-link" href="${pageContext.request.contextPath}/home">
                    <fmt:message key="home"/></a>
            </li>

            <c:if test="${sessionScope.user == null or sessionScope.role.equals('client')}">
                <li class="nav-item">
                    <a class="btn btn-lg nav-link" href="${pageContext.request.contextPath}/app?command=viewServices">
                        <fmt:message key="services"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-lg nav-link" href="${pageContext.request.contextPath}/app?command=viewMasters">
                        <fmt:message key="masters"/>
                    </a>
                </li>
            </c:if>


            <c:choose>
                <c:when test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="btn btn-lg nav-link" href="${pageContext.request.contextPath}/registration">
                            <fmt:message key="sign-up"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-lg nav-link" href="${pageContext.request.contextPath}/sign-in">
                            <fmt:message key="sign-in"/>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <jsp:useBean id="user" scope="session" type="model.User"/>
                    <jsp:useBean id="role" scope="session" type="java.lang.String"/>
                    <li class="nav-item">
                        <div class="dropdown">
                            <a href="#" class="btn btn-outline-primary btn-lg dropdown-toggle nav-link" role="button"
                               data-bs-toggle="dropdown" aria-expanded="true">
                                    ${user.name} ${user.surname}
                            </a>
                            <ul class="dropdown-menu text-center" aria-labelledby="dropdownMenuLink">
                                <li>
                                    <fmt:message key="title.account" var="account"/>
                                    <c:choose>
                                        <c:when test="${role.equals('admin')}">
                                            <a href="${pageContext.request.contextPath}/admin/account"
                                               class="dropdown-item">
                                                    ${account}
                                            </a>
                                        </c:when>
                                        <c:when test="${role.equals('master')}">
                                            <a href="${pageContext.request.contextPath}/master/account"
                                               class="dropdown-item">
                                                    ${account}
                                            </a>
                                        </c:when>
                                        <c:when test="${role.equals('client')}">
                                            <a href="${pageContext.request.contextPath}/client/account"
                                               class="dropdown-item">
                                                    ${account}
                                            </a>
                                        </c:when>
                                    </c:choose>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/app?command=logout"
                                       class="dropdown-item">
                                        <fmt:message key="sign-out"/>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
</body>
</html>
