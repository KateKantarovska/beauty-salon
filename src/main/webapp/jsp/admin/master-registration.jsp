<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.master-registration"/>
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
            <div class="mask d-flex align-items-center">
                <div class="container h-150">
                    <div class="row d-flex justify-content-center align-items-center h-100">
                        <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                            <div class="card-body p-4">
                                <h3 class="text-uppercase text-center mb-3">
                                    <fmt:message key="header.create-master-account"/>
                                </h3>
                                <form action="${pageContext.request.contextPath}/app" method="post">
                                    <input type="hidden" name="command" value="registerMaster"/>

                                    <c:if test="${not empty sessionScope.message}">
                                        <jsp:useBean id="message" scope="session" type="java.lang.String"/>
                                        <div class="alert alert-warning" role="alert">
                                            <fmt:message key="${message}"/>
                                        </div>
                                        <c:remove var="message" scope="session"/>
                                    </c:if>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="firstNameUa">
                                            <fmt:message key="label.first-name.ua"/>
                                        </label>
                                        <input type="text" class="form-control" id="firstNameUa" name="firstNameUa"
                                               pattern="[А-яіІєЄґҐїЇ\-']+" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="surnameUa">
                                            <fmt:message key="label.surname.ua"/>
                                        </label>
                                        <input type="text" class="form-control" id="surnameUa" name="surnameUa"
                                               pattern="[А-яіІєЄґҐїЇ\-']+" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="firstNameEn">
                                            <fmt:message key="label.first-name.en"/>
                                        </label>
                                        <input type="text" class="form-control" id="firstNameEn" name="firstNameEn"
                                               pattern="[A-z\-']+" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="surnameEn">
                                            <fmt:message key="label.surname.en"/>
                                        </label>
                                        <input type="text" class="form-control" id="surnameEn" name="surnameEn"
                                               pattern="[A-z\-']+" required/>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="email">
                                            <fmt:message key="label.email"/>
                                        </label>
                                        <input type="email" class="form-control" id="email" name="email"
                                               pattern="[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z]+" required>
                                    </div>

<%--                                    <div class="form-outline mb-4">--%>
<%--                                        <label class="form-label" for="password">--%>
<%--                                            <fmt:message key="label.password"/>--%>
<%--                                        </label>--%>
<%--                                        <input type="password" class="form-control" id="password" name="password"--%>
<%--                                               minlength="8"--%>
<%--                                               title="Must contain at least 8 characters" required>--%>
<%--                                    </div>--%>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="phoneNumber">
                                            <fmt:message key="label.phone-number"/>
                                        </label>
                                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                               pattern="+?\d{10,15}"
                                               title="Can contain only digits and one + symbol">
                                    </div>

                                    <div class="d-flex justify-content-center">
                                        <fmt:message key="button.register-master" var="register"/>
                                        <input type="submit"
                                               class="btn btn-block btn-lg text-body btn-outline-primary me-3"
                                               role="button" value="${register}" aria-pressed="true"/>
                                        <a href="${pageContext.request.contextPath}/app?command=viewMastersList"
                                           class="btn btn-block btn-lg text-body btn-outline-secondary" role="button"
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
</div>
</body>
</html>
