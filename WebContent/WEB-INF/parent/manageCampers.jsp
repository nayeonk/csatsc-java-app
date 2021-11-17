<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Campers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/parentstyle.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <jsp:include page="../includes/parentNavbar.jsp"/>
    <div id="main">

        <div id="header-row">
            <h1> Manage Campers </h1>

            <form action="${pageContext.request.contextPath}/managecampers" method="POST">
                <button class="btn btn-warning">
                    + Add Camper
                </button>
            </form>
        </div>

        <div id="campers-content" class="row">
            <c:forEach items="${studentArray}" var="student">
                <div class="col-12 col-md-6 col-lg-4 camper-container">
                    <div class="card">
                        <form method="POST" action="${pageContext.request.contextPath}/managecampers">
                            <h5 class="card-header text-white bg-dark">${student.name}</h5>
                            <div class="card-body">
                                <p class="card-text">
                                    <c:choose>
                                        <c:when test="${student.getProgress() == 0}">
                                            Please complete <strong>Scholarship Application</strong>.
                                        </c:when>
                                        <c:when test="${student.getProgress() == 1}">
                                            Please complete <strong>Medical Information</strong>.
                                        </c:when>
                                        <c:otherwise>
                                            Start applying for camps!
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <input type="hidden" name="studentID" value="${student.studentID}">
                                <button type="submit" name="submit_param" value="submit_value"
                                        class="campers-entry btn btn-warning link-button">
                                    Manage
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</div>

</body>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
<script src="../../scripts/manageCampers.js"></script>
</html>