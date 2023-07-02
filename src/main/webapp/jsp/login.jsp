<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.login"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
    <form action="${pageContext.request.contextPath}/app" method="post">
        <input type="hidden" name="command" value="login" />
        <div class="mask d-flex align-items-center h-75 mt-5">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 10px;">
                            <div class="card-body p-4">
                                <h3 class="text-uppercase text-center mb-2">
                                    <fmt:message key="header.welcome-back"/>
                                </h3>

                                <c:if test="${not empty sessionScope.message}">
                                    <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                    <div class="alert alert-warning" role="alert">
                                        <fmt:message key="${message}"/>
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                </c:if>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="email">
                                        <fmt:message key="label.email"/>
                                    </label>
                                    <input type="email" class="form-control" id="email" name="email" required />
                                </div>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="password">
                                        <fmt:message key="label.password"/>
                                    </label>
                                    <input type="password" class="form-control" id="password" name="password" required />
                                </div>
                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-block btn-lg text-body btn-outline-primary">
                                        <fmt:message key="sign-in"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</body>
</html>
