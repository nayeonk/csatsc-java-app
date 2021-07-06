<%--
  Created by IntelliJ IDEA.
  User: neutonfoo
  Date: 11/8/20
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CS@SC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
    <link rel="stylesheet" href="/SummerCamp/css/general.css">
    <link rel="stylesheet" href="/SummerCamp/css/parentNavbar.css">
</head>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <!-- Static yellow sidebar -->
    <jsp:include page="../includes/parentNavbar.jsp"/>

    <!-- Main page content -->
    <div id="main">
        <div class="page">
            <h1>View Applications</h1>
            <p>View your campers' recent camp applications. To make changes, please visit the <i>Manage Campers</i> page.</p>
            <div class="section">
                <c:forEach items="${students}" var="student">
                    <h2>${student.getName()}'s <small>Applications</small></h2>
                    <c:choose>
                        <c:when test="${studentsAppliedCamps.get(student.getStudentID()).isEmpty()}">
                            <p>No pending applications.</p>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table id="applied-camps" class="table">
                                    <thead>
                                    <tr>
                                        <th>Camp Name</th>
                                        <th>Date</th>
                                        <th>Days</th>
                                        <th>Time</th>
                                        <th>Online</th>
                                        <th>Cost</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${studentsAppliedCamps.get(student.getStudentID())}" var="camp">
                                        <tr id="${camp.campOfferedID}">
                                            <td class="align-middle">${camp.campTopic} ${camp.campLevel}</td>
                                            <td class="align-middle">${camp.getDateString()}</td>
                                            <td class="align-middle">${camp.getCampDays()}</td>
                                            <td class="align-middle">${camp.getTimeString()}</td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${camp.isRemote() == true}">
                                                        No
                                                    </c:when>
                                                    <c:otherwise>
                                                        Yes
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${camp.cost == 0}">
                                                        Free
                                                    </c:when>
                                                    <c:otherwise>
                                                        $${camp.cost * 1.00 / 100}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="align-middle">
                                                <c:choose>
                                                    <c:when test="${camp.rejected}">
                                                        <div class="alert alert-danger my-0 p-2" role="alert">
                                                            Rejected
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${camp.withdrawn}">
                                                        <div class="alert alert-danger my-0 p-2" role="alert">
                                                            Withdrawn
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${camp.confirmed}">
                                                        <div class="alert alert-success my-0 p-2" role="alert">
                                                            Confirmed
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${camp.accepted && !camp.confirmed}">
                                                        <div class="alert alert-info my-0 p-2" role="alert">
                                                            Accepted
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${!camp.accepted}">
                                                        <div class="alert alert-warning my-0 p-2" role="alert">
                                                            Applied
                                                        </div>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <hr class="divider">
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

</html>