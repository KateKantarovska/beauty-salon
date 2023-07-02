<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.master-profile"/>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/star-rating.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="container mt-4">
    <jsp:useBean id="master" scope="request" type="model.Master"/>
    <h3 class="text-center mb-5">
        <fmt:message key="master"/>:
        <c:choose>
            <c:when test="${sessionScope.lang.equals('uk')}">
                ${master.fullnameUA}
            </c:when>
            <c:when test="${sessionScope.lang.equals('en')}">
                ${master.fullnameEN}
            </c:when>
        </c:choose>
    </h3>
    <div class="container mx-auto" style="width: 750px">
        <div class="row">
            <div class="col-8">
                <h5>
                    <fmt:message key="master-categories"/>
                </h5>
                <ul>
                    <c:forEach var="category" items="${master.categories}">
                        <li>
                            <c:choose>
                                <c:when test="${sessionScope.lang.equals('uk')}">
                                    ${category.nameUA}
                                </c:when>
                                <c:when test="${sessionScope.lang.equals('en')}">
                                    ${category.nameEN}
                                </c:when>
                            </c:choose>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-4">
                <h5>
                    <fmt:message key="rating"/>:
                    <c:choose>
                        <c:when test="${master.rating != 0.0}">
                            ${master.rating}
                            <span class="fa fa-star checked"></span>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="n-a"/>
                        </c:otherwise>
                    </c:choose>
                </h5>
            </div>
        </div>

        <c:if test="${not empty sessionScope.message}">
            <jsp:useBean id="message" scope="session" type="java.lang.String"/>
            <div class="alert alert-success" role="alert">
                <fmt:message key="${message}"/>
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>

        <c:if test="${empty master.reviewsList}">
            <h4 class="text-center mt-4 mb-4">
                <fmt:message key="header.no-reviews"/>
            </h4>
        </c:if>

        <c:if test="${not empty master.reviewsList}">
            <div>
                <h5>
                    <fmt:message key="reviews"/>:
                </h5>
                <c:forEach var="review" items="${master.reviewsList}">
                    <div class="card card-body border border-1 mt-3 mb-3 pb-1">
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
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <div class="text-center">

                <button type="button" class="btn btn-outline-info mb-4" data-bs-toggle="modal"
                        data-bs-target="#add-review-modal">
                    <fmt:message key="button.add-review"/>
                </button>

                <div class="modal fade btn-group-vertical" id="add-review-modal" tabindex="-1"
                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">
                                    <fmt:message key="modal-title.add-review"/>
                                    <c:choose>
                                        <c:when test="${sessionScope.lang.equals('uk')}">
                                            ${master.fullnameUA}
                                        </c:when>
                                        <c:when test="${sessionScope.lang.equals('en')}">
                                            ${master.fullnameEN}
                                        </c:when>
                                    </c:choose>
                                </h5>
                                <button type="button" class="btn-close"
                                        data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form id="add-review" action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="addReview">
                                    <input type="hidden" name="masterId" value="${master.id}">
                                    <div class="row align-items-center">
                                        <div class="col-2 text-start">
                                            <h5 class="mt-2">
                                                <fmt:message key="rating"/>:
                                            </h5>
                                        </div>
                                        <div class="rating col-10">
                                            <input type="radio" name="rating" value="5" id="5">
                                            <label for="5">☆</label>
                                            <input type="radio" name="rating" value="4" id="4">
                                            <label for="4">☆</label>
                                            <input type="radio" name="rating" value="3" id="3">
                                            <label for="3">☆</label>
                                            <input type="radio" name="rating" value="2" id="2">
                                            <label for="2">☆</label>
                                            <input type="radio" name="rating" value="1" id="1">
                                            <label for="1">☆</label>
                                        </div>
                                    </div>
                                    <div class="text-start">
                                        <label for="feedback" class="form-label h5 mt-3">
                                            <fmt:message key="review"/>:
                                        </label>
                                        <textarea class="form-control" id="feedback" name="feedback" maxlength="255"
                                                  rows="3"></textarea>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary"
                                        data-bs-dismiss="modal">
                                    <fmt:message key="button.close"/>
                                </button>
                                <button type="submit" form="add-review"
                                        class="btn btn-primary">
                                    <fmt:message key="button.submit"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
