<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="appointment-requests"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="container">
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
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="services"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewSchedule" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="work-schedule"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests"
                           data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded active">
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
                        <c:if test="${not empty sessionScope.message}">
                            <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                            <div class="alert alert-info" role="alert">
                                <fmt:message key="${message}"/>
                            </div>
                            <c:remove var="message" scope="session"/>
                        </c:if>

                        <jsp:useBean id="pendingAppointments" scope="request" type="java.util.List"/>
                        <c:if test="${empty pendingAppointments}">
                            <h4 class="text-center">
                                <fmt:message key="header.no-requests"/>
                            </h4>
                        </c:if>
                        <c:if test="${not empty pendingAppointments}">
                            <table class="table table-striped table-hover align-middle text-center">
                                <tr class="bg-light">
                                    <th>
                                        <fmt:message key="client"/>
                                    </th>
                                    <th>
                                        <fmt:message key="service"/>
                                    </th>
                                    <th>
                                        <fmt:message key="master"/>
                                    </th>
                                    <th>
                                        <fmt:message key="date"/>
                                    </th>
                                    <th>
                                        <fmt:message key="time"/>
                                    </th>
                                    <th class="col-5"></th>
                                </tr>

                                <c:forEach var="appointment" items="${pendingAppointments}">
                                    <tr>
                                        <td>
                                            ${appointment.getClientFullname()}
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
                                            ${appointment.getDateString()}
                                        </td>
                                        <td>
                                            ${appointment.getTimeslotString()}
                                        </td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/app?command=confirmRequest&id=${appointment.getId()}"
                                               class="btn btn-outline-success me-1">
                                                <fmt:message key="button.confirm"/>
                                            </a>
                                            <button type="button" class="btn btn-outline-info me-1"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#edit-timeslot-modal">
                                                <fmt:message key="button.change-timeslot"/>
                                            </button>
                                            <div class="modal fade" id="edit-timeslot-modal" tabindex="-1"
                                                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel">
                                                                <fmt:message key="header.pick-new-datetime"/>
                                                            </h5>
                                                            <button type="button" class="btn-close"
                                                                    data-bs-dismiss="modal"
                                                                    aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="${pageContext.request.contextPath}/app"
                                                                  id="editTimeslot"
                                                                  method="post">
                                                                <input type="hidden" name="command"
                                                                       value="editTimeslot"/>
                                                                <input type="hidden" name="appointmentId"
                                                                       value="${appointment.id}"/>
                                                                <jsp:useBean id="freeTimeslots" scope="request"
                                                                             type="java.util.Map"/>
                                                                <label for="dateTime"></label>
                                                                <select id="dateTime" name="newAppointmentId">
                                                                    <c:forEach var="timeslot"
                                                                               items="${freeTimeslots.get(appointment.getMasterID()).entrySet()}">
                                                                        <option value="${timeslot.getKey()}">
                                                                            ${timeslot.getValue()}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </form>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-bs-dismiss="modal">
                                                                <fmt:message key="button.close"/>
                                                            </button>
                                                            <button type="submit" form="editTimeslot"
                                                                    class="btn btn-primary">
                                                                <fmt:message key="button.save"/>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="${pageContext.request.contextPath}/app?command=declineRequest&id=${appointment.getId()}"
                                               class="btn btn-outline-danger">
                                                <fmt:message key="button.decline"/>
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
