<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.book-appointment"/>
    </title>
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<div class="mask d-flex align-items-center mt-5">
    <div class="container h-150">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-5 ">
                <div class="card" style="border-radius: 10px;">
                    <div class="card-body p-4 justify-content-center">
                        <h3 class="text-uppercase text-center mb-3">
                            <fmt:message key="header.appointment-details"/>
                        </h3>
                        <form action="${pageContext.request.contextPath}/app" method="post"
                              class="justify-content-center">
                            <input type="hidden" name="command" value="bookAppointment"/>
                            <input type="hidden" name="queryString" value="${pageContext.request.queryString}">
                            <jsp:useBean id="service" scope="request" type="model.Service"/>
                            <jsp:useBean id="master" scope="request" type="model.Master"/>
                            <jsp:useBean id="freeTimeslots" scope="request" type="java.util.Map"/>
                            <div class="container text-center">
                                <div class="form-outline mb-2">

                                    <c:choose>
                                        <c:when test="${sessionScope.lang.equals('uk')}">
                                            <c:set var="masterName" value="${master.fullnameUA}"/>
                                            <c:set var="serviceName" value="${service.nameUA}"/>
                                        </c:when>
                                        <c:when test="${sessionScope.lang.equals('en')}">
                                            <c:set var="masterName" value="${master.fullnameEN}"/>
                                            <c:set var="serviceName" value="${service.nameEN}"/>
                                        </c:when>
                                    </c:choose>

                                    <label for="service" class="form-label">
                                        <fmt:message key="service"/>
                                        <input id="service" type="text" value="${serviceName}" class="form-control mt-3"
                                               readonly>
                                    </label>
                                    <input type="hidden" name="serviceId" value="${service.id}">
                                </div>
                                <div class="form-outline mb-2">
                                    <label for="master" class="form-label">
                                        <fmt:message key="master"/>
                                        <input id="master" type="text" value="${masterName}"
                                               class="form-control mt-3" readonly>
                                    </label>
                                    <input type="hidden" name="masterId" value="${master.id}">
                                </div>
                                <div class="form-outline mb-2">
                                    <label for="price" class="form-label">
                                        <fmt:message key="price"/>
                                        <fmt:message key="currency.uah" var="uah"/>
                                        <input id="price" type="text" value="${service.priceUAH} ${uah}"
                                               class="form-control mt-3"
                                               readonly>
                                    </label>
                                </div>
                                <c:if test="${!freeTimeslots.isEmpty()}">
                                    <div class="form-outline mb-4">
                                        <label for="dateTime" class="form-label fs-5">
                                            <fmt:message key="label.pick-datetime"/>
                                        </label>
                                        <select id="dateTime" name="appointmentId">
                                            <c:forEach var="timeslot" items="${freeTimeslots.entrySet()}">
                                                <option value="${timeslot.getKey()}">
                                                        ${timeslot.getValue()}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </c:if>

                                <c:if test="${freeTimeslots.isEmpty()}">
                                    <h5 class="mt-3">
                                        <fmt:message key="header.no-available-timeslots"/>
                                    </h5>
                                </c:if>

                                <c:if test="${not empty sessionScope.message}">
                                    <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                    <div class="alert alert-warning justify-content-center" role="alert">
                                        <fmt:message key="${message}"/>
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                </c:if>

                                <c:if test="${!freeTimeslots.isEmpty()}">
                                    <div class="d-flex justify-content-center">
                                        <fmt:message key="button.book" var="book"/>
                                        <input type="submit"
                                               class="btn btn-block btn-lg text-body btn-outline-primary me-4"
                                               role="button" value="${book}"/>
                                        <a href="${pageContext.request.contextPath}/app?command=viewServices"
                                           class="btn btn-block btn-lg text-body btn-outline-secondary" role="button"
                                           aria-pressed="true">
                                            <fmt:message key="button.cancel"/>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
