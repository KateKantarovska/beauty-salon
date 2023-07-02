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
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="container">
    <div class="row gutters-sm">
        <div class="col-md-3 d-none d-md-block">
            <div class="card">
                <div class="card-body">
                    <nav class="nav flex-column nav-pills nav-gap-y-1">
                        <a href="${pageContext.request.contextPath}/client/account" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded active">
                            <fmt:message key="profile"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/client/balance" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="balance"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/app?command=viewAppointments" data-toggle="tab"
                           class="nav-item nav-link has-icon nav-link-faded">
                            <fmt:message key="appointments"/>
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
            <div class="card">
                <div class="card-body tab-content">
                    <div class="tab-pane active">
                        <div class="d-flex justify-content-center">
                            <div class="card-body p-4">
                                <jsp:useBean id="user" scope="session" type="model.Client"/>
                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="editProfile"/>

                                    <c:if test="${not empty sessionScope.message}">
                                        <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                        <div class="alert alert-info" role="alert">
                                            <fmt:message key="${message}"/>
                                        </div>
                                        <c:remove var="message" scope="session"/>
                                    </c:if>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="first-name">
                                            <fmt:message key="label.first-name"/>
                                        </label>
                                        <input type="text" class="form-control" id="first-name" name="firstName"
                                               value="${user.name}" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="surname">
                                            <fmt:message key="label.surname"/>
                                        </label>
                                        <input type="text" class="form-control" id="surname" name="surname"
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

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="phoneNumber">
                                            <fmt:message key="label.phone-number"/>
                                        </label>
                                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                               pattern="+?\d{10,15}" title="Can contain only digits and one + symbol"
                                               value="${user.phoneNumber}">
                                    </div>

                                    <fmt:message key="button.save" var="save"/>
                                    <div class="d-flex justify-content-center">
                                        <input type="submit" class="btn btn-block btn-lg text-body btn-outline-primary mb-2"
                                               role="button" value="${save}" aria-pressed="true"/>
                                    </div>

                                </form>

                                <hr>

                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="changePassword"/>

                                    <c:if test="${not empty sessionScope.alert}">
                                        <jsp:useBean id="alert" scope="session" type="java.lang.String"/>
                                        <div class="alert alert-info" role="alert">
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

                                    <fmt:message key="button.change-password" var="changePassword"/>
                                    <div class="d-flex justify-content-center">
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
