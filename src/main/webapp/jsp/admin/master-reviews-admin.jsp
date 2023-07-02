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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/star-rating.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<jsp:useBean id="master" scope="request" type="model.Master"/>
<div class="container mt-4">
    <div class="container mx-auto" style="width: 750px">
        <c:if test="${empty master.reviewsList}">
            <h4 class="text-center mt-4 mb-4">
                <fmt:message key="header.no-reviews"/>
            </h4>
        </c:if>

        <c:if test="${not empty master.reviewsList}">
            <div>
                <h3 class="text-center mb-5">
                        <fmt:message key="header.reviews-on-master"/>
                    <c:choose>
                        <c:when test="${sessionScope.lang.equals('uk')}">
                            ${master.fullnameUA}:
                        </c:when>
                        <c:when test="${sessionScope.lang.equals('en')}">
                            ${master.fullnameEN}:
                        </c:when>
                    </c:choose>
                </h3>
                <c:forEach var="review" items="${master.reviewsList}">
                    <div class="card card-body border border-1 mt-3 mb-2 pb-1">
                        <div class="row">
                            <div class="col-2">
                                <b>
                                    <fmt:message key="rating"/>:
                                </b>
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
                                <p>${review.text}</p>
                            </div>
                        </div>
                    </div>
                    <div class="mb-4 text-center">
                        <c:choose>
                            <c:when test="${review.isVisible()}">
                                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/app?command=changeReviewVisibility&id=${review.id}">
                                    <fmt:message key="button.hide"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="btn btn-outline-info" href="${pageContext.request.contextPath}/app?command=changeReviewVisibility&id=${review.id}">
                                    <fmt:message key="button.unhide"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
