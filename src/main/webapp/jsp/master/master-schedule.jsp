<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="schedule"/>
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
                    <a href="${pageContext.request.contextPath}/app?command=viewMasterSchedule" class="nav-link active"
                       type="button" role="tab">
                        <fmt:message key="schedule"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewBookedAppointments"
                       class="nav-link"
                       type="button" role="tab">
                        <fmt:message key="booked-appointments"/>
                    </a>
                </div>
            </nav>
            <div class="card">
                <div class="card-body tab-content">
                    <div class="tab-pane active">
                        <jsp:useBean id="masterAppointmentsMap" scope="request" type="java.util.Map"/>
                        <ul>
                            <c:forEach var="dateAppointment" items="${masterAppointmentsMap.entrySet()}">
                                <li>
                                    ${dateAppointment.getKey()}
                                    <ul>
                                        <c:forEach var="timeslot" items="${dateAppointment.getValue().entrySet()}">
                                            <li>
                                                ${timeslot.getKey()}

                                                <c:choose>
                                                    <c:when test="${timeslot.getValue().equals('free')}">
                                                        <fmt:message key="status.free"/>
                                                    </c:when>
                                                    <c:when test="${timeslot.getValue().equals('pending')}">
                                                        <fmt:message key="status.pending"/>
                                                    </c:when>
                                                    <c:when test="${timeslot.getValue().equals('booked')}">
                                                        <fmt:message key="status.booked"/>
                                                    </c:when>
                                                    <c:when test="${timeslot.getValue().equals('completed')}">
                                                        <fmt:message key="status.completed"/>
                                                    </c:when>
                                                </c:choose>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
