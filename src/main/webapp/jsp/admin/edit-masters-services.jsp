<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.edit-master-categories"/>
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
                    <jsp:useBean id="master" scope="request" type="model.Master"/>
                    <h4 class="text-center mb-2">
                        <fmt:message key="header.pick-categories-for-master"/>
                        <c:choose>
                            <c:when test="${sessionScope.lang.equals('uk')}">
                                ${master.fullnameUA}
                            </c:when>
                            <c:when test="${sessionScope.lang.equals('en')}">
                                ${master.fullnameEN}
                            </c:when>
                        </c:choose>
                    </h4>
                </div>
                <form action="${pageContext.request.contextPath}/app" method="post">
                    <input type="hidden" name="command" value="editMasterCategories">
                    <input type="hidden" name="masterId" value="${master.id}">

                    <jsp:useBean id="categories" scope="request" type="java.util.List<model.Category>"/>
                    <c:forEach var="category" items="${categories}">
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" name="${category.id}"
                                   id="category" ${master.categories.contains(category) ? "checked" : ""}>
                            <label class="form-check-label" for="category">
                                <c:choose>
                                    <c:when test="${sessionScope.lang.equals('uk')}">
                                        ${category.nameUA}
                                    </c:when>
                                    <c:when test="${sessionScope.lang.equals('en')}">
                                        ${category.nameEN}
                                    </c:when>
                                </c:choose>
                            </label>
                        </div>
                    </c:forEach>

                    <div class="d-flex justify-content-center">
                        <button type="submit" class="btn btn-block btn-lg text-body btn-outline-primary">
                            <fmt:message key="button.save"/>
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
