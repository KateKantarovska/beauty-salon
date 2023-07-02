<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="clients-list"/>
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
                       class="nav-item nav-link has-icon nav-link-">
                        <fmt:message key="profile"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewClientsList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded active">
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
                    <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded">
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
                                <th class="col-3">
                                    <fmt:message key="full-name"/>
                                </th>
                                <th class="col-3">
                                    <fmt:message key="label.email"/>
                                </th>
                                <th class="col-3">
                                    <fmt:message key="label.phone-number"/>
                                </th>
                                <th class="col-1">
                                    <fmt:message key="balance"/>, <fmt:message key="currency.uah"/>
                                </th>
                                <th class="col-1">
                                    <fmt:message key="status"/>
                                </th>
                                <th class="col-1"></th>
                            </tr>
                            <jsp:useBean id="clientsList" scope="request" type="java.util.List"/>

                            <c:set var="listSize" value="${clientsList.size()}"/>
                            <c:set var="recordsPerPage" value="5"/>
                            <c:set var="numberOfPages"
                                   value="${Integer.valueOf(Math.ceil(listSize / recordsPerPage))}"/>

                            <c:choose>
                                <c:when test="${numberOfPages == 1}">
                                    <c:set var="sublist" value="${clientsList}"/>
                                </c:when>
                                <c:when test="${listSize - (page - 1) * recordsPerPage < recordsPerPage}">
                                    <c:set var="sublist"
                                           value="${clientsList.subList((page - 1) * recordsPerPage, listSize)}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="sublist"
                                           value="${clientsList.subList((page - 1) * recordsPerPage, page * recordsPerPage)}"/>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="client" items="${sublist}">
                                <tr>
                                    <td>${client.name} ${client.surname}</td>
                                    <td>${client.email}</td>
                                    <td>${client.phoneNumber}</td>
                                    <td>${client.balance}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${client.isActive()}">
                                                <fmt:message key="status.active"/>
                                            </c:when>
                                            <c:otherwise>
                                                <fmt:message key="status.blocked"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${client.isActive()}">
                                                <a class="btn btn-sm btn-outline-danger"
                                                   href="${pageContext.request.contextPath}/app?command=blockClient&id=${client.id}">
                                                    <fmt:message key="button.block"/>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-sm btn-outline-success"
                                                   href="${pageContext.request.contextPath}/app?command=unblockClient&id=${client.id}">
                                                    <fmt:message key="button.unblock"/>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
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
                                               href="${pageContext.request.contextPath}/app?command=viewClientsList&page=${page - 1}"
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
                                                    <a class="page-link"
                                                       href="${pageContext.request.contextPath}/app?command=viewClientsList&page=${pageNumber}">
                                                            ${pageNumber}
                                                    </a>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${page < numberOfPages}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/app?command=viewClientsList&page=${page + 1}"
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
            </div>
        </div>
    </div>
</div>
</body>
</html>
