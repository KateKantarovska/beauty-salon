<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="content"/>
    <title>
        <fmt:message key="title.edit-working-days"/>
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
                    <div class="col-md-3 m-sm-3">
                        <form action="${pageContext.request.contextPath}/app" method="post">
                            <input type="hidden" name="command" value="editWorkingDays">
                            <jsp:useBean id="workingDaysMap" scope="request" type="java.util.Map"/>
                            <c:forEach var="entry" items="${workingDaysMap.entrySet()}">
                                <div class="form-check form-switch">
                                    <label class="form-check-label" for="${entry.getKey()}">${entry.getKey()}</label>
                                    <input class="form-check-input" type="checkbox" id="${entry.getKey()}" name="${entry.getKey()}" ${entry.getValue()}>
                                </div>
                            </c:forEach>
                            <div class="d-flex mt-1 p-3">
                                <button type="submit" class="btn btn-block text-body btn-outline-primary">
                                    <fmt:message key="button.save"/>
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
