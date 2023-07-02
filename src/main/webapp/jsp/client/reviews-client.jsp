<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="reviews"/>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/star-rating.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
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
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="balance"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointments" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="appointments"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewReviews" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
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
                                <c:if test="${empty user.reviewsList}">
                                    <h4 class="text-center mt-4 mb-4">
                                        <fmt:message key="header.no-reviews.client"/>
                                    </h4>
                                </c:if>
                                <c:if test="${not empty user.reviewsList}">
                                    <div>
                                        <c:forEach var="review" items="${user.reviewsList}">
                                            <h5 class="mb-2">
                                                <fmt:message key="master"/>:
                                                <c:choose>
                                                    <c:when test="${sessionScope.lang.equals('uk')}">
                                                        ${review.master.fullnameUA}
                                                    </c:when>
                                                    <c:when test="${sessionScope.lang.equals('en')}">
                                                        ${review.master.fullnameEN}
                                                    </c:when>
                                                </c:choose>
                                            </h5>
                                            <div class="card card-body border border-1 mt-3 mb-3 pb-1">
                                                <div class="row">
                                                    <div class="col-2">
                                                        <b>
                                                            <fmt:message key="rating"/>:</b>
                                                    </div>
                                                    <div class="col-10">
                                                        <c:forEach var="point" begin="1" end="${review.rating}">
                                                            <span class="fa fa-star checked"></span>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="row mt-3">
                                                    <div class="col-2">
                                                        <b>
                                                            <fmt:message key="review"/>:
                                                        </b>
                                                    </div>
                                                    <div class="col-10">
                                                        <p>
                                                            ${review.text}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
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
