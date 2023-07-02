<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="services"/>
    </title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="container start-20 end-20">
    <div class="card text-center pt-2 bg-light">
        <form action="${pageContext.request.contextPath}/app" method="get" class="form-inline">
            <input type="hidden" name="command" value="viewServices">
            <div class="row align-items-center">
                <div class="col">
                    <fmt:message key="filter-by"/>
                </div>
                <div class="col">
                    <ul class="list-unstyled">
                        <li class="m-4">
                            <jsp:useBean id="mastersList" scope="request" type="java.util.List"/>
                            <label>
                                <input type="radio" name="filter" value="master" class="d-inline">
                            </label>
                            <label for="master" class="d-inline">
                                <fmt:message key="master"/>
                            </label>
                            <select id="master" name="master" class="d-inline">
                                <c:forEach var="master" items="${mastersList}">
                                    <option value="${master.id}">
                                        <c:choose>
                                            <c:when test="${sessionScope.lang.equals('uk')}">
                                                ${master.fullnameUA}
                                            </c:when>
                                            <c:when test="${sessionScope.lang.equals('en')}">
                                                ${master.fullnameEN}
                                            </c:when>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                        </li>
                        <li>
                            <jsp:useBean id="categoriesList" scope="request" type="java.util.List"/>
                            <label>
                                <input type="radio" name="filter" value="category" class="d-inline">
                            </label>
                            <label for="category" class="d-inline">
                                <fmt:message key="category"/>
                            </label>
                            <select id="category" name="category">
                                <c:forEach var="category" items="${categoriesList}">
                                    <option value="${category.id}">
                                        <c:choose>
                                            <c:when test="${sessionScope.lang.equals('uk')}">
                                                ${category.nameUA}
                                            </c:when>
                                            <c:when test="${sessionScope.lang.equals('en')}">
                                                ${category.nameEN}
                                            </c:when>
                                        </c:choose>
                                    </option>
                                </c:forEach>
                            </select>
                        </li>
                    </ul>
                </div>
                <div class="col">
                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-block text-body btn-outline-primary">
                            <fmt:message key="button.filter"/>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div class="card-body" style="border-radius: 10px;">
        <table class="table table-striped table-hover align-middle text-center">
            <jsp:useBean id="servicesList" scope="request" type="java.util.List"/>
            <tr class="bg-light">
                <th class="col-md-4">
                    <fmt:message key="service"/>
                </th>
                <th class="col-md-4">
                    <fmt:message key="masters"/>
                </th>
                <th class="col-md-4">
                    <fmt:message key="price"/>
                </th>
            </tr>

            <c:set var="listSize" value="${servicesList.size()}"/>
            <c:set var="recordsPerPage" value="3"/>
            <c:set var="numberOfPages"
                   value="${Integer.valueOf(Math.ceil(listSize / recordsPerPage))}"/>

            <c:choose>
                <c:when test="${numberOfPages == 1}">
                    <c:set var="sublist" value="${servicesList}"/>
                </c:when>
                <c:when test="${listSize - (page - 1) * recordsPerPage < recordsPerPage}">
                    <c:set var="sublist"
                           value="${servicesList.subList((page - 1) * recordsPerPage, listSize)}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="sublist"
                           value="${servicesList.subList((page - 1) * recordsPerPage, page * recordsPerPage)}"/>
                </c:otherwise>
            </c:choose>

            <c:forEach var="service" items="${sublist}">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${sessionScope.lang.equals('uk')}">
                                ${service.nameUA}
                            </c:when>
                            <c:when test="${sessionScope.lang.equals('en')}">
                                ${service.nameEN}
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <ul class="list-unstyled mt-2">
                            <c:forEach var="master" items="${service.mastersList}">
                                <li class="mb-2">
                                    <a href="${pageContext.request.contextPath}/app?command=viewMasterInfo&id=${master.id}" class="text-decoration-none">
                                        <c:choose>
                                            <c:when test="${sessionScope.lang.equals('uk')}">
                                                ${master.fullnameUA}
                                            </c:when>
                                            <c:when test="${sessionScope.lang.equals('en')}">
                                                ${master.fullnameEN}
                                            </c:when>
                                        </c:choose>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/app?command=bookAppointmentForm&serviceId=${service.id}&masterId=${master.id}"
                                       class="btn btn-sm btn-outline-primary">
                                        <fmt:message key="button.book"/>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td>
                            ${service.priceUAH} <fmt:message key="currency.uah"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${numberOfPages != 1}">
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${page > 1}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app?command=viewServices&page=${page - 1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach var="pageNumber" begin="1" end="${numberOfPages}">
                        <c:choose>
                            <c:when test="${pageNumber == page}">
                                <li class="page-item active" aria-current="page">
                                    <span class="page-link">${pageNumber}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="page-item">
                                    <c:set var="query_string"
                                           value="${requestScope['javax.servlet.forward.query_string']}"/>
                                    <c:if test="${query_string.contains('page=')}">
                                        <c:set var="query_string"
                                               value="${query_string.substring(0, query_string.length() - 7)}"/>
                                    </c:if>
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/app?${query_string}&page=${pageNumber}">
                                            ${pageNumber}
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${page < numberOfPages}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/app?command=viewServices&page=${page + 1}"
                               aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>

    </div>
</div>
</body>
</html>
