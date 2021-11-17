<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CS@SC</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Stylesheets -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/camperstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

</head>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <!-- Static yellow sidebar -->
    <jsp:include page="../includes/studentNavbar.jsp"/>

    <!-- Main page content -->
    <div id="main">
        <form name="scholarship" method="POST" action="${pageContext.request.contextPath}/applyforscholarship" enctype="multipart/form-data">
            <!-- Title -->
            <h1>Continue to Scholarship Application?</h1>
            <!-- Skip Scholarship Application -->
            <c:choose>
                <c:when test="${(student.gradeReports == null || student.gradeReports.size() == 0) && (student.reducedMealsVerifications == null || student.reducedMealsVerifications.size() == 0)}">
                    <div id="skip-box" class="margin-below">
                        <p>Thanks to the generosity of our donors like the Institute for Education, we offer a number of scholarships based on merit and need.</p>
                        <p>If you do not wish to apply for scholarship, please skip this step.</p>
                        <button type="submit" class="btn btn-dark skip" name="submit" value="skip">Skip this step</button>
                    </div>
                </c:when>
                <c:otherwise>

                </c:otherwise>
            </c:choose>

            <div class="alert alert-info" role="alert">
                This step is completely <em>OPTIONAL</em>. If you do not wish to apply for a scholarship, please continue by clicking <button type="submit" class="btn btn-dark skip" name="submit" value="skip">Skip this step</button>
            </div>
                <!-- Form Submit Buttons -->
                <div class="submit-row">
                    <button type="submit" class="btn btn-warning apply" name="submit" value="save-continue">Apply for Scholarship</button><br />
                    <button type="submit" class="btn btn-dark skip" name="submit" value="skip">Skip this step</button>
                </div>
        </form>
    </div>
</div>
</body>
</html>