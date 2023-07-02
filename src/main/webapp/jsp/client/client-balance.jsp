<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="balance"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="container">
    <div class="row gutters-sm">
        <div class="col-md-3 d-none d-md-block">
            <div class="card">
                <div class="card-body">
                    <nav class="nav flex-column nav-pills nav-gap-y-1">
                        <a href="${pageContext.request.contextPath}/client/account" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="profile"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/client/balance" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
                            <fmt:message key="balance"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointments" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="appointments"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewReviews" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="reviews"/>
                        </a>
                    </nav>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="card">
                <div class="card-body tab-content">
                    <div class="tab-pane active">
                        <div class="d-flex justify-content-center">

                            <div class="card-body p-4">
                                <jsp:useBean id="user" scope="session" type="model.Client"/>
                                <p class="fs-3">
                                    <fmt:message key="current-balance"/> ${user.balance} <fmt:message
                                        key="currency.uah"/>
                                </p>

                                <hr>

                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="topUpBalance"/>
                                    <div class="form-outline mb-4">
                                        <label class="form-label fs-4" for="sum">
                                            <fmt:message key="label.enter-sum"/>
                                        </label><br/>
                                        <input class="col-md-3 mb-3" type="number" id="sum" name="sum" min="1" required><br/>
                                        <fmt:message key="button.top-up" var="topUp"/>
                                        <input type="submit" class="btn btn-block text-body btn-outline-primary fs-5"
                                               role="button" value="${topUp}" aria-pressed="true"/>
                                    </div>
                                </form>

                                <c:if test="${not empty sessionScope.message}">
                                    <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                    <div class="alert alert-success" role="alert">
                                        <fmt:message key="${message}"/>
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                </c:if>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
