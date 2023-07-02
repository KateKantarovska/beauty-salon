<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="work-schedule"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="container">
    <div class="row gutters-sm">
        <div class="col-md-4 d-none d-md-block">
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
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="services"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewSchedule" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
                            <fmt:message key="work-schedule"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests"
                           data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="appointment-requests"/>
                        </a>
                    </nav>
                </div>
            </div>
        </div>

        <div class="col-md-8">
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

                        <div class="d-flex">
                            <jsp:useBean id="appointmentMap" scope="request" type="java.util.Map"/>
                            <ul>
                                <c:forEach var="appointmentMapEntry" items="${appointmentMap.entrySet()}">
                                    <li>
                                        ${appointmentMapEntry.getKey()}
                                        <ul>
                                            <c:forEach var="timeslot"
                                                       items="${appointmentMapEntry.getValue().entrySet()}">
                                                <li>
                                                    ${timeslot.getKey()}
                                                    <c:choose>
                                                        <c:when test="${empty timeslot.getValue()}">
                                                            - <fmt:message key="status.free"/>
                                                        </c:when>
                                                        <c:otherwise>

                                                            <table class="table table-bordered table-striped table-hover align-middle text-center">
                                                                <tr>
                                                                    <th class="col-4">
                                                                        <fmt:message key="service"/>
                                                                    </th>
                                                                    <th class="col-4">
                                                                        <fmt:message key="master"/>
                                                                    </th>
                                                                    <th class="col-4">
                                                                        <fmt:message key="client"/>
                                                                    </th>
                                                                    <th></th>
                                                                </tr>
                                                                <c:forEach var="appointment"
                                                                           items="${timeslot.getValue()}">
                                                                    <tr>
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
                                                                            ${appointment.getClientFullname()}
                                                                        </td>
                                                                        <td>
                                                                            <c:if test="${appointment.getStatus().equals('booked')}">
                                                                                <a href="${pageContext.request.contextPath}/app?command=cancelAppointment&id=${appointment.getId()}"
                                                                                   class="btn btn-outline-danger">
                                                                                    <fmt:message key="button.cancel"/>
                                                                                </a>
                                                                            </c:if>
                                                                            <c:if test="${appointment.getStatus().equals('pending')}">
                                                                                <fmt:message
                                                                                        key="status.pending"/>
                                                                            </c:if>
                                                                            <c:if test="${appointment.getStatus().equals('completed')}">
                                                                                <fmt:message
                                                                                        key="status.completed"/>
                                                                            </c:if>
                                                                        </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </table>

                                                        </c:otherwise>
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
</div>
</body>
</html>
