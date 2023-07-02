<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="appointments"/>
    </title>
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
                           class="nav-item nav-link has-icon nav-link-faded active">
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
                                <jsp:useBean id="appointmentsList" scope="request" type="java.util.List"/>
                                <c:if test="${empty appointmentsList}">
                                    <h4 class="text-center">
                                        <fmt:message key="header.no-appointments"/>
                                    </h4>
                                </c:if>
                                <c:if test="${not empty appointmentsList}">
                                    <table class="table table-striped table-hover align-middle text-center">
                                        <tr class="bg-light">
                                            <th>
                                                <fmt:message key="date"/>
                                            </th>
                                            <th>
                                                <fmt:message key="time"/>
                                            </th>
                                            <th>
                                                <fmt:message key="service"/>
                                            </th>
                                            <th>
                                                <fmt:message key="master"/>
                                            </th>
                                            <th>
                                                <fmt:message key="status"/>
                                            </th>
                                        </tr>
                                        <c:forEach var="appointment" items="${appointmentsList}">
                                            <tr>
                                                <td>
                                                    ${appointment.getDateString()}
                                                </td>
                                                <td>
                                                    ${appointment.getTimeslotString()}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.lang.equals('uk')}">
                                                            ${appointment.serviceNameUA}
                                                        </c:when>
                                                        <c:when test="${sessionScope.lang.equals('en')}">
                                                            ${appointment.serviceNameEN}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.lang.equals('uk')}">
                                                            ${appointment.getMasterFullnameUA()}
                                                        </c:when>
                                                        <c:when test="${sessionScope.lang.equals('en')}">
                                                            ${appointment.getMasterFullnameEN()}
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${appointment.getStatus().equals('pending')}">
                                                            <fmt:message key="status.pending"/>
                                                        </c:when>
                                                        <c:when test="${appointment.getStatus().equals('booked')}">
                                                            <fmt:message key="status.booked"/>
                                                        </c:when>
                                                        <c:when test="${appointment.getStatus().equals('completed')}">
                                                            <fmt:message key="status.completed"/>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
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
