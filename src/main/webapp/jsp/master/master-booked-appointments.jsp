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
                        <a href="${pageContext.request.contextPath}/master/account" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="profile"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewMasterSchedule" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
                            <fmt:message key="appointments"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewWorkingDays" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="working-days"/>
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
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <a href="${pageContext.request.contextPath}/app?command=viewMasterSchedule" class="nav-link"
                       type="button" role="tab">
                        <fmt:message key="schedule"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewBookedAppointments"
                       class="nav-link active"
                       type="button" role="tab">
                        <fmt:message key="booked-appointments"/>
                    </a>
                </div>
            </nav>
            <div class="card">
                <div class="card-body tab-content">
                    <div class="tab-pane active">
                        <c:if test="${not empty sessionScope.message}">
                            <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                            <div class="alert alert-info" role="alert">
                                <fmt:message key="${message}"/>
                            </div>
                            <c:remove var="message" scope="session"/>
                        </c:if>
                        <jsp:useBean id="bookedAppointments" scope="request" type="java.util.List"/>
                        <c:if test="${empty bookedAppointments}">
                            <h4 class="text-center">
                                <fmt:message key="header.no-booked-appointments"/>
                            </h4>
                        </c:if>
                        <c:if test="${not empty bookedAppointments}">
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
                                        <fmt:message key="client"/>
                                    </th>
                                    <th></th>
                                </tr>
                                <c:forEach var="appointment" items="${bookedAppointments}">
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
                                                    ${appointment.getServiceNameUA()}
                                                </c:when>
                                                <c:when test="${sessionScope.lang.equals('en')}">
                                                    ${appointment.getServiceNameEN()}
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            ${appointment.getClientFullname()}
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/app?command=completeAppointment&id=${appointment.getId()}"
                                               class="btn btn-outline-success">
                                                <fmt:message key="button.completed"/>
                                            </a>
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
</body>
</html>
