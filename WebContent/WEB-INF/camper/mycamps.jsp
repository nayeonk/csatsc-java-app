<%--
  Created by IntelliJ IDEA.
  User: nicoleng
  Date: 10/7/20
  Time: 2:06 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/general.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/studentNavbar.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/mycamps.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&display=swap" rel="stylesheet">
    <title>My Camps</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <jsp:include page="../includes/studentNavbar.jsp"/>
    <div id="main">
        <div class="page">
            <h1>My Camps</h1>
            <div class="section">
                <h3>Application Summary</h3>
                <br/>
                Please complete these steps before you apply for camps:
                <br/>
                <br/>
                <div id="checklist">
                    <div class="${student == null ? 'unclickable ' : ''}checklist-item">
                        ${student == null ? '<i class="material-icons" style="font-size: 14px;position: absolute;bottom: 5px;">panorama_fish_eye</i>' : '<i class="material-icons" style="font-size: 17px;color: green;position: absolute;bottom: 5px;">done</i>'}
                        <a href="/SummerCamp/createstudent">Complete Camper Profile</a>
                    </div>
                    <div class="${student == null ? 'unclickable ' : ''}checklist-item">
                        ${student == null || student.getProgress() < 1 ? '<i class="material-icons" style="font-size: 14px;position: absolute;bottom: 5px;">panorama_fish_eye</i>' : '<i class="material-icons" style="font-size: 17px;color: green;position: absolute;bottom: 5px;">done</i>'}
                        <a href="/SummerCamp/applyforscholarship">Apply for Scholarship (Optional)</a>
                    </div>
                    <div class="${student == null || student.getProgress() < 1 ? 'unclickable ' : ''}checklist-item">
                        ${student == null || student.getProgress() < 2 ? '<i class="material-icons" style="font-size: 14px;position: absolute;bottom: 5px;">panorama_fish_eye</i>' : '<i class="material-icons" style="font-size: 17px;color: green;position: absolute;bottom: 5px;">done</i>'}
                        <a href="/SummerCamp/medical">Upload Medical Documents</a>
                    </div>
                    <!-- "Apply for Camp" and "Make Payment" checkboxes, removed for clarity/simplicity
                    <div class="${student == null || student.getProgress() < 2 ? 'unclickable ' : ''}checklist-item">
                        ${student == null || student.getProgress() < 3 ? '<i class="material-icons" style="font-size: 14px;position: absolute;bottom: 5px;">panorama_fish_eye</i>' : '<i class="material-icons" style="font-size: 17px;color: green;position: absolute;bottom: 5px;">done</i>'}
                        <a href="/SummerCamp/campregistration">Apply for Camp</a>
                    </div>
                    <div class="${student == null || student.getProgress() < 3 ? 'unclickable ' : ''}checklist-item">
                        ${student == null || student.getProgress() < 4 ? '<i class="material-icons" style="font-size: 14px;position: absolute;bottom: 5px;">panorama_fish_eye</i>' : '<i class="material-icons" style="font-size: 17px;color: green;position: absolute;bottom: 5px;">done</i>'}
                        <a href="/SummerCamp/makepayment">Make Payment</a>
                    </div>
                    -->
                </div>
                <br/>
                Once accepted to a camp, you may make your payment on the Payment page.
                <br/>
                If you received a full scholarship, you may confirm directly on this page below.
            </div>
            <div class="section">
                <h3>Applied Camps</h3>
                <p>You may check the status of your submitted camp applications here. Once you have been accepted into a
                    camp, you may make your payment on the <i>Make Payments</i> page.</p>
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
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${campsCurrentApplied}" var="camp">
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
                                            $<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${camp.cost / 100.0}" />
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
                                <td class="align-middle">
                                    <c:if test="${camp.withdrawn == false && camp.rejected == false}">
                                        <button type="button" class="btn btn-dark withdraw-btn" data-toggle="modal"
                                                data-target="#withdraw-modal" data-studentcampid="${camp.studentCampID}" onclick="handleWithdraw(event)">
                                            Withdraw
                                        </button>
                                    </c:if>
                                    <c:if test="${!camp.confirmed && camp.accepted && camp.cost == 0}">
                                        <button type="button" class="btn btn-success confirm-btn" data-toggle="modal"
                                                data-target="#confirm-modal" data-studentcampid="${camp.studentCampID}" onclick="handleConfirm(event)">
                                            Confirm
                                        </button>
                                    </c:if>

                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="section">
                <h3>Past Camps Attended</h3>
                <table id="past-camps" class="table">
                    <thead>
                    <tr>
                        <th>Camp Name</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${campsAttended}" var="camp">
                        <tr>
                            <td>${camp.campTopic} ${camp.campLevel}</td>
                            <td>${camp.getDateString()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="withdraw-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content" id="popup">
            <div class="modal-header">
                <h1 class="modal-title">Confirm Your Withdrawal</h1>
            </div>
            <div class="modal-body">
                <table class="withdraw-table">
                    <tr>
                        <th>Camper:</th>
                        <td id="modal-camper-name">${student.getName()}</td>
                    </tr>
                    <tr>
                        <th>Camp:</th>
                        <td id="modal-camp-name"></td>
                    </tr>
                    <tr>
                        <th>Date:</th>
                        <td id="modal-camp-date"></td>
                    </tr>
                    <tr>
                        <th>Time:</th>
                        <td id="modal-camp-time"></td>
                    </tr>
                    <tr>
                        <th>Online:</th>
                        <td id="modal-camp-online"></td>
                    </tr>
                    <tr>
                        <th>Cost:</th>
                        <td id="modal-camp-cost"></td>
                    </tr>
                    <tr>
                        <th>Status:</th>
                        <td id="modal-camp-status"></td>
                    </tr>
                </table>
                <br/>
                <p>This action cannot be undone. Please allow 2-5 business days for refund to appear on your
                    account.</p>
            </div>
            <div class="modal-footer">
                <form action="${pageContext.request.contextPath}/mycamps" method="POST">
                    <input id="withdraw-camp-id" type="hidden" name="withdrawCampID" value="">
                    <input id="withdraw-studentcamp-id" type="hidden" name="withdrawStudentCampID" value="">
                    <button type="submit" class="btn btn-warning">Confirm</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </form>
            </div>
        </div>
    </div>

</div>

<div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content popup">
            <div class="modal-header">
                <h1 class="modal-title">Confirm Camp</h1>
            </div>
            <div class="modal-body">
                <table class="withdraw-table">
                    <tr>
                        <th>Camper:</th>
                        <td id="confirm-modal-camper-name">${student.getName()}</td>
                    </tr>
                    <tr>
                        <th>Camp:</th>
                        <td id="confirm-modal-camp-name"></td>
                    </tr>
                    <tr>
                        <th>Date:</th>
                        <td id="confirm-modal-camp-date"></td>
                    </tr>
                    <tr>
                        <th>Time:</th>
                        <td id="confirm-modal-camp-time"></td>
                    </tr>
                    <tr>
                        <th>Online:</th>
                        <td id="confirm-modal-camp-online"></td>
                    </tr>
                    <tr>
                        <th>Cost:</th>
                        <td id="confirm-modal-camp-cost"></td>
                    </tr>
                    <tr>
                        <th>Status:</th>
                        <td id="confirm-modal-camp-status"></td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <form action="${pageContext.request.contextPath}/mycamps" method="POST">
                    <input id="confirm-camp-id" type="hidden" name="confirmCampID" value="">
                    <input id="confirm-studentcamp-id" type="hidden" name="confirmStudentCampID" value="">
                    <button type="submit" class="btn btn-warning">Confirm</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                </form>
            </div>
        </div>
    </div>

</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script>
    function handleWithdraw(event) {
        const children = event.target.parentNode.parentNode.children;

        const campName = children[0].innerHTML;
        const campDate = children[1].innerHTML;
        const campTime = children[2].innerHTML;
        const isOnline = children[3].innerHTML;
        const campCost = children[4].innerHTML;
        const campStatus = children[5].innerHTML;

        document.querySelector("#modal-camp-name").innerHTML = campName;
        document.querySelector("#modal-camp-date").innerHTML = campDate;
        document.querySelector("#modal-camp-time").innerHTML = campTime;
        document.querySelector("#modal-camp-online").innerHTML = isOnline;
        document.querySelector("#modal-camp-cost").innerHTML = campCost;
        document.querySelector("#modal-camp-status").innerHTML = campStatus;
        document.querySelector("#withdraw-camp-id").value = event.target.parentNode.parentNode.id;
        document.querySelector("#withdraw-studentcamp-id").value = event.target.dataset.studentcampid;

    }

    function handleConfirm(event) {
        const children = event.target.parentNode.parentNode.children;

        const campName = children[0].innerHTML;
        const campDate = children[1].innerHTML;
        const campTime = children[2].innerHTML;
        const isOnline = children[3].innerHTML;
        const campCost = children[4].innerHTML;
        const campStatus = children[5].innerHTML;

        document.querySelector("#confirm-modal-camp-name").innerHTML = campName;
        document.querySelector("#confirm-modal-camp-date").innerHTML = campDate;
        document.querySelector("#confirm-modal-camp-time").innerHTML = campTime;
        document.querySelector("#confirm-modal-camp-online").innerHTML = isOnline;
        document.querySelector("#confirm-modal-camp-cost").innerHTML = campCost;
        document.querySelector("#confirm-modal-camp-status").innerHTML = campStatus;
        document.querySelector("#confirm-camp-id").value = event.target.parentNode.parentNode.id;
        document.querySelector("#confirm-studentcamp-id").value = event.target.dataset.studentcampid;

    }
</script>
</body>
</html>
