<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="masters-list"/>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/star-rating.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                       class="nav-item nav-link has-icon nav-link-faded active">
                        <fmt:message key="masters-list"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="services"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewSchedule" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="work-schedule"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="appointment-requests"/>
                    </a>
                </nav>
            </div>
        </div>
    </div>
    <div class="col-md-9">
        <div class="card">
            <div class="card-body tab-content">
                <div class="tab-pane active">
                    <div class="justify-content-center">
                        <table class="table table-bordered table-striped table-hover text-center align-middle">
                            <tr>
                                <th>
                                    <fmt:message key="full-name.ua"/>
                                </th>
                                <th>
                                    <fmt:message key="full-name.en"/>
                                </th>
                                <th>
                                    <fmt:message key="label.email"/>
                                </th>
                                <th>
                                    <fmt:message key="label.phone-number"/>
                                </th>
                                <th>
                                    <fmt:message key="rating"/>
                                </th>
                                <th>
                                    <fmt:message key="services-categories"/>
                                </th>
                                <th>
                                    <fmt:message key="status"/>
                                </th>
                                <th colspan="2"></th>
                            </tr>
                            <jsp:useBean id="mastersList" scope="request" type="java.util.List"/>
                            <c:forEach var="master" items="${mastersList}">
                            <tr>
                                <td>${master.getFullnameUA()}</td>
                                <td>${master.getFullnameEN()}</td>
                                <td>${master.email}</td>
                                <td>${master.phoneNumber}</td>
                                <td>
                                    <a class="text-decoration-none"
                                       href="${pageContext.request.contextPath}/app?command=viewReviews&id=${master.id}">
                                        <c:choose>
                                            <c:when test="${master.rating != 0.0}">
                                                ${master.rating}
                                                <span class="fa fa-star checked"></span>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="n-a"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                                <td class="align-middle">
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
                                        <c:when test="${master.isActive()}">
                                            <fmt:message key="status.active"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="status.blocked"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <c:choose>
                                <c:when test="${master.isActive()}">
                                <td>
                                    <a class="btn btn-outline-danger btn-sm"
                                       href="${pageContext.request.contextPath}/app?command=blockMaster&id=${master.id}">
                                        <fmt:message key="button.block"/>
                                    </a>
                                </td>
                                <td class="text-center align-middle">
                                    <a class="btn btn-outline-primary btn-sm"
                                       href="${pageContext.request.contextPath}/app?command=editMasterCategoriesForm&id=${master.id}">
                                        <fmt:message key="button.edit-categories"/>
                                    </a>
                                </td>
                                </c:when>
                                <c:otherwise>
                                <td class="text-center align-middle">
                                    <a class="btn btn-outline-success btn-sm"
                                       href="${pageContext.request.contextPath}/app?command=unblockMaster&id=${master.id}">
                                        <fmt:message key="button.unblock"/>
                                    </a>
                                </td>
                                </c:otherwise>
                                </c:choose>
                                </c:forEach>
                        </table>
                        <div class="d-flex justify-content-center">
                            <a class="btn btn-outline-info"
                               href="${pageContext.request.contextPath}/admin/master-registration">
                                <fmt:message key="button.register-new-master"/>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
