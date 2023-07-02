<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="working-days"/>
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
                        <a href="${pageContext.request.contextPath}/master/account" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="profile"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewMasterSchedule" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="appointments"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewWorkingDays" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
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
            <div class="card ">
                <div class="card-body tab-content ">
                    <div class="col-md-6 ">
                        <c:if test="${not empty sessionScope.message}">
                            <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                            <div class="alert alert-success" role="alert">
                                <fmt:message key="${message}"/>
                            </div>
                            <c:remove var="message" scope="session"/>
                        </c:if>
                        <table class="table table-bordered table-striped table-hover ">
                            <jsp:useBean id="workingDaysMap" scope="request" type="java.util.Map"/>
                            <c:forEach var="entry" items="${workingDaysMap.entrySet()}">
                                <tr>
                                    <td>${entry.getKey()}</td>
                                    <td>
                                        <c:if test="${entry.getValue().equals('true')}">
                                            <fmt:message key="working"/>
                                        </c:if>
                                        <c:if test="${entry.getValue().equals('false')}">
                                            <fmt:message key="not-working"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <div class="d-flex justify-content-center">
                            <a class="btn btn-lg btn-outline-primary"
                               href="${pageContext.request.contextPath}/app?command=editWorkingDaysForm">
                                <fmt:message key="button.edit"/>
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
