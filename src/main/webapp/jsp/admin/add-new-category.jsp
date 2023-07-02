<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="services"/>
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
                       class="nav-item nav-link has-icon nav-link-faded">
                        <fmt:message key="masters-list"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList" data-toggle="tab"
                       class="nav-item nav-link has-icon nav-link-faded active">
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
        <nav>
            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList" class="nav-link active"
                   type="button" role="tab">
                    <fmt:message key="services-categories"/>
                </a>
                <a href="${pageContext.request.contextPath}/app?command=viewServicesList" class="nav-link"
                   type="button" role="tab">
                    <fmt:message key="services"/>
                </a>
            </div>
        </nav>
        <div class="card">
            <div class="card-body tab-content">
                <div class="tab-pane active">
                    <div class="d-flex justify-content-center">
                        <div class="card-body p-4">
                            <h3 class="text-uppercase text-center mb-2">
                                <fmt:message key="header.create-new-category"/>
                            </h3>
                            <form action="${pageContext.request.contextPath}/app" method="post">
                                <input type="hidden" name="command" value="addNewCategory"/>

                                <c:if test="${not empty sessionScope.message}">
                                    <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                    <div class="alert alert-warning" role="alert">
                                            <fmt:message key="${message}"/>
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                </c:if>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="nameUA">
                                        <fmt:message key="name.ua"/>
                                    </label>
                                    <input type="text" class="form-control" id="nameUA" name="nameUA"
                                           pattern="[А-яіІєЄґҐїЇ\-' ]+" required/>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="nameEN">
                                        <fmt:message key="name.en"/>
                                    </label>
                                    <input type="text" class="form-control" id="nameEN" name="nameEN"
                                           pattern="[A-z\-' ]+" required/>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <fmt:message key="button.save" var="save"/>
                                    <input type="submit" class="btn btn-block btn-lg text-body btn-outline-primary me-3"
                                           role="button" value="${save}" aria-pressed="true"/>
                                    <a href="${pageContext.request.contextPath}/app?command=viewCategoriesList"
                                       class="btn btn-block btn-lg text-body btn-outline-secondary " role="button"
                                       aria-pressed="true">
                                        <fmt:message key="button.cancel"/>
                                    </a>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
