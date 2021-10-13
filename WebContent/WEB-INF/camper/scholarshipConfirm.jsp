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
    <link rel="stylesheet" href="/SummerCamp/css/general.css">
    <link rel="stylesheet" href="/SummerCamp/css/studentNavbar.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/scholarship.css" />

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
                <!-- Form Submit Buttons -->
                <div class="submit-row">
                    <button type="submit" id="save-continue" class="btn btn-warning" name="submit" value="save-continue">Apply for Scholarship</button><br />
                    <button type="submit" class="btn btn-dark" id="skip" name="submit" value="skip">Skip this step</button>
                </div>
        </form>
    </div>
</div>
</body>
</html>