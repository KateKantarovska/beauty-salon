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
<jsp:include page="/jsp/navbar.jsp"/>
<div class="row gutters-sm">
    <div class="col-md-3 d-none d-md-block">
        <div class="card">
            <div class="card-body">
                <nav class="nav flex-column nav-pills nav-gap-y-1">
                    <a href="${pageContext.request.contextPath}/admin/account" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="profile"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewClientsList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="clients-list"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewMastersList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="masters-list"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded active">
                        <fmt:message key="services"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewSchedule" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="work-schedule"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="appointment-requests"/>
                    </a>
                </nav>
            </div>
        </div>
    </div>
    <div class="col-md-9">
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList" class="nav-link"
                   type="button" role="tab">
                    <fmt:message key="services-categories"/>
                </a>
                <a href="${pageContext.request.contextPath}/app?command=viewServicesList" class="nav-link active"
                   type="button" role="tab">
                    <fmt:message key="services"/>
                </a>
            </div>
        </nav>
        <div class="card">
            <div class="card-body tab-content">
                <div class="tab-pane active">
                    <div>
                        <h3 class="text-uppercase text-center mb-2">
                            <fmt:message key="header.services-list"/>
                        </h3>
                    </div>
                    <div class="justify-content-center">
                        <ul>
                            <jsp:useBean id="categoriesList" scope="request" type="java.util.List"/>
                            <c:forEach var="category" items="${categoriesList}">
                                <li>
                                    <div class="mb-2">
                                        <c:choose>
                                            <c:when test="${sessionScope.lang.equals('uk')}">
                                                ${category.nameUA}
                                            </c:when>
                                            <c:when test="${sessionScope.lang.equals('en')}">
                                                ${category.nameEN}
                                            </c:when>
                                        </c:choose>
                                        <a href="${pageContext.request.contextPath}/app?command=addNewServiceForm&id=${category.id}"
                                           class="btn btn-outline-info ms-2">
                                            <fmt:message key="button.add-new-service"/>
                                        </a>
                                    </div>
                                    <table class="table table-bordered table-striped table-hover text-center align-middle">
                                        <tr>
                                            <th>
                                                <fmt:message key="name.ua"/>
                                            </th>
                                            <th>
                                                <fmt:message key="name.en"/>
                                            </th>
                                            <th>
                                                <fmt:message key="price"/>, <fmt:message key="currency.uah"/>
                                            </th>
                                            <th>
                                                <fmt:message key="price"/>, <fmt:message key="currency.usd"/>
                                            </th>
                                            <th colspan="2"></th>
                                        </tr>
                                        <jsp:useBean id="servicesMap" scope="request" type="java.util.Map"/>
                                        <c:forEach var="service" items="${servicesMap.get(category.id)}">
                                            <tr>
                                                <td>${service.nameUA}</td>
                                                <td>${service.nameEN}</td>
                                                <td>${service.priceUAH}</td>
                                                <td>${service.priceUSD}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/app?command=editServiceForm&id=${service.id}"
                                                       class="btn btn-sm btn-outline-info">
                                                        <fmt:message key="button.edit"/>
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/app?command=deleteService&id=${service.id}"
                                                       class="btn btn-sm btn-outline-danger">
                                                        <fmt:message key="button.delete"/>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </li>
                            </c:forEach>
                        </ul>
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

</body>
</html>
