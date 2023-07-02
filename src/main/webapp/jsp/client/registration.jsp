<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.registration"/>
    </title>
</head>
<body>
<jsp:include page="/jsp/navbar.jsp"/>
<div class="mask d-flex align-items-center h-75">
    <div class="container h-150">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                <div class="card" style="border-radius: 10px;">
                    <div class="card-body p-4">
                        <h3 class="text-uppercase text-center mb-2">
                            <fmt:message key="header.create-account"/>
                        </h3>
                        <form action="${pageContext.request.contextPath}/app" method="post">
                            <input type="hidden" name="command" value="registerClient"/>
                            <c:if test="${not empty sessionScope.error}">
                                <div class="alert alert-warning justify-content-center" role="alert">
                                        ${sessionScope.error}
                                </div>
                                ${sessionScope.remove("error")}
                            </c:if>
                            <div class="form-outline mb-4">
                                <label class="form-label" for="firstName">
                                    <fmt:message key="label.first-name"/>
                                </label>
                                <input type="text" class="form-control" id="firstName" name="firstName"
                                       pattern="[A-zА-яіІєЄґҐїЇ\-']+" required/>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="surname">
                                    <fmt:message key="label.surname"/>
                                </label>
                                <input type="text" class="form-control" id="surname" name="surname"
                                       pattern="[A-zА-яіІєЄґҐїЇ\-']+" required/>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="email">
                                    <fmt:message key="label.email"/>
                                </label>
                                <input type="email" class="form-control" id="email" name="email"
                                       pattern="[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\.[a-zA-Z]+" required>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="password">
                                    <fmt:message key="label.password"/>
                                </label>
                                <input type="password" class="form-control" id="password" name="password" minlength="8"
                                       title="Must contain at least 8 characters" required>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="phoneNumber">
                                    <fmt:message key="label.phone-number"/>
                                </label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                                       pattern="+?\d{10,15}" title="Can contain only digits and one + symbol" required>
                            </div>

                                <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-block btn-lg text-body btn-outline-primary">
                                    <fmt:message key="sign-up"/>
                                </button>
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
