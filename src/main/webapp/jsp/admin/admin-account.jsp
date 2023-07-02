<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.account"/>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                           class="nav-item nav-link has-icon nav-link-faded active">
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
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointmentRequests" data-toggle="tab" class="nav-item nav-link has-icon nav-link-faded">
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
                        <div class="d-flex justify-content-center">
                            <div class="card-body p-4">
                                <jsp:useBean id="user" scope="session" type="model.Admin"/>
                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="editProfile"/>

                                    <c:if test="${not empty sessionScope.message}">
                                        <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                        <div class="alert alert-primary justify-content-center" role="alert">
                                            <fmt:message key="${message}"/>
                                        </div>
                                        <c:remove var="message" scope="session"/>
                                    </c:if>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="firstName">
                                            <fmt:message key="label.first-name"/>
                                        </label>
                                        <input type="text" class="form-control" id="firstName" name="firstName"
                                               pattern="[A-zА-яіІєЄґҐїЇ\-']+" value="${user.name}" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="surname">
                                            <fmt:message key="label.surname"/>
                                        </label>
                                        <input type="text" class="form-control" id="surname" name="surname"
                                               pattern="[A-zА-яіІєЄґҐїЇ\-']+"
                                               value="${user.surname}"
                                               required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="email">
                                            <fmt:message key="label.email"/>
                                        </label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               pattern="[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z]+" value="${user.email}"
                                               required>
                                    </div>

                                    <div class="d-flex justify-content-center">
                                        <fmt:message key="button.save" var="save"/>
                                        <input type="submit" class="btn btn-block btn-lg text-body btn-outline-primary"
                                               role="button" value="${save}" aria-pressed="true"/>
                                    </div>

                                </form>

                                <hr>

                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="changePassword"/>

                                    <c:if test="${not empty sessionScope.alert}">
                                        <jsp:useBean id="alert" scope="session" type="java.lang.String"/>
                                        <div class="alert alert-primary" role="alert">
                                            <fmt:message key="${alert}"/>
                                        </div>
                                        <c:remove var="alert" scope="session"/>
                                    </c:if>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="current-password">
                                            <fmt:message key="label.current-password"/>
                                        </label>
                                        <input type="password" class="form-control" id="current-password"
                                               name="current-password" required>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="new-password">
                                            <fmt:message key="label.new-password"/>
                                        </label>
                                        <input type="password" class="form-control" id="new-password"
                                               name="new-password" minlength="8"
                                               title="Must contain at least 8 characters" required>
                                    </div>

                                    <div class="d-flex justify-content-center">
                                        <fmt:message key="button.change-password" var="changePassword"/>
                                        <input type="submit" class="btn btn-block btn-lg text-body btn-outline-primary"
                                               role="button" value="${changePassword}" aria-pressed="true"/>
                                    </div>

                                </form>

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
