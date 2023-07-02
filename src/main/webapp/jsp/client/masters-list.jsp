<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="masters"/>
    </title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/star-rating.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="container start-20 end-20">
    <div class="card text-center pt-2 bg-light">
        <form action="${pageContext.request.contextPath}/app" method="get" class="form-inline">
            <input type="hidden" name="command" value="viewMasters">
            <div class="row align-items-center">
                <div class="col">
                    <fmt:message key="sort-by"/>
                </div>
                <div class="col">
                    <ul class="list-unstyled">
                        <li class="m-4">
                            <input type="radio" id="sort1" name="sort" value="name" class="d-inline">
                            <label for="sort1" class="d-inline">
                                <fmt:message key="option.name"/>
                            </label>
                        </li>
                        <li>
                            <input type="radio" id="sort2" name="sort" value="rating" class="d-inline">
                            <label for="sort2" class="d-inline">
                                <fmt:message key="option.rating"/>
                            </label>
                        </li>
                    </ul>
                </div>
                <div class="col">
                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-block text-body btn-outline-primary">
                            <fmt:message key="button.sort"/>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>


    <div class="card-body" style="border-radius: 10px;">
        <table class="table table-striped table-hover align-middle text-center">
            <jsp:useBean id="mastersList" scope="request" type="java.util.List"/>
            <tr class="bg-light">
                <th class="col-md-4">
                    <fmt:message key="name"/>
                </th>
                <th class="col-md-4">
                    <fmt:message key="services"/>
                </th>
                <th class="col-md-4">
                    <fmt:message key="rating"/>
                </th>
            </tr>

            <c:forEach var="master" items="${mastersList}">
                <tr>
                    <td>
                        <a href="${pageContext.request.contextPath}/app?command=viewMasterInfo&id=${master.getId()}" class="text-decoration-none">
                            <c:choose>
                                <c:when test="${sessionScope.lang.equals('uk')}">
                                    ${master.fullnameUA}
                                </c:when>
                                <c:when test="${sessionScope.lang.equals('en')}">
                                    ${master.fullnameEN}
                                </c:when>
                            </c:choose>
                        </a>
                    </td>
                    <td>
                        <ul class="list-unstyled">
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
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${master.rating != 0.0}">
                                ${master.rating}
                                <span class="fa fa-star checked"></span>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="n-a"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
</body>
</html>
