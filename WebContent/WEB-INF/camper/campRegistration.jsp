<%@ page language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Camp Registration</title>

    <!-- Stylesheets -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/SummerCamp/css/general.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/camperstyle.css">
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/SummerCamp/css/campRegistration.css">--%>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans&display=swap" rel="stylesheet">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <jsp:include page="../includes/studentNavbar.jsp"/>

    <div id="main">

        <!-- FILTER -->
        <div class="d-none d-sm-block">
            <h1 class="d-none d-sm-block">Apply for Camps</h1>
            <h5 class="username d-none d-sm-block">${student.firstName} ${student.lastName}</h5>
            <h5>Notes:</h5>
            <p class="changes-note">Selections you make on this page will not be saved until you click
                <i>Confirm</i>.</p>
            <p class="changes-note">Classes are subject to cancellation if minimum enrollment is not met.</p>

            <h5>Filter By...</h5>


            <div class="filter margin-below">
                <div class="topic-filter"  style="display: flex">
                    <c:forEach items="${campTopics}" var="campTopic">
                        <a class="dropdown-item" href="#">
                            <div class="checkbox">
                                <label for="${campTopic.getTopic()}">
                                    <input type="checkbox" id="${campTopic.getTopic()}" checked> <span data-topic="${campTopic.getTopic()}" class="dropdown-check-text">${campTopic.getTopic()} </span>
                                </label>
                            </div>
                        </a>
                    </c:forEach>
                </div>

                <div class="level-filter"  style="display: flex">
                    <c:forEach items="${campLevels}" var="campLevel">
                        <a class="dropdown-item" href="#">
                            <div class="checkbox">
                                <label for="${campLevel.getCampLevelDescription()}">
                                    <input type="checkbox" id="${campLevel.getCampLevelDescription()}" checked> <span data-level="${campLevel.getCampLevelDescription()}" class="dropdown-check-text"> ${campLevel.getCampLevelDescription()} </span>
                                </label>
                            </div>
                        </a>
                    </c:forEach>
                </div>

            </div>

            <button type="button" class="btn btn-secondary" onclick="handleApplyAllFilters()"> Apply All Filters </button>
            <button type="button" class="btn btn-secondary" onclick="clearFilters()"> Clear Filters </button>
            <button type="button" class="btn btn-warning" onclick="handleApplyFilters()"> Apply Filters </button>
        </div>

        <!-- FOR MOBILE -->
        <button type="button" id="filter-mobile" class="btn d-block d-sm-none" data-toggle="modal" data-target="#filter-modal">
            <h3>Filter</h3>
        </button>



        <!-- FILTER MODAL -->
        <div class="modal fade" id="filter-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="exampleModalLabel2">Filters</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="filter-segment">
                            <div class="filter-segment-header">
                                <div class="filter-checkbox active-checkbox"></div>
                                <h5>Topic</h5>
                            </div>
                            <div class="filter-segment-content">
                                <c:forEach items="${campTopics}" var="campTopic">
                                    <div class="filter-segment-entry" data-topic="${campTopic.getTopic()}">
                                        <div class="filter-entry-checkbox active-checkbox"></div>
                                        ${campTopic.getTopic()}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                        <div class="filter-segment">
                            <div class="filter-segment-header">
                                <div class="filter-checkbox active-checkbox"></div>
                                <h5>Level</h5>
                            </div>
                            <div class="filter-segment-content">
                                <c:forEach items="${campLevels}" var="campLevel">
                                    <div class="filter-segment-entry" data-level="${campLevel.getCampLevelDescription()}">
                                        <div class="filter-entry-checkbox active-checkbox"></div>
                                        ${campLevel.getCampLevelDescription()}
                                    </div>
                                </c:forEach>

                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="close-filter" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" onclick="handleMobileApplyFilters()" class="btn btn-warning">Apply Filters</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- ERROR MODAL WHEN CLICKING ON FULL CLASS -->
        <div class="modal fade" id="full-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="exampleModalLabel3">Full Class</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <h6> This class is currently full. Please select another camp or email cscamps@usc.edu to be put on a waitlist. </h6>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- SELECTED BOX -->
        <div class="topic selected-box">
            <div class="topic-name">
                <h5>Your Selected Camps</h5>
            </div>

            <!-- FOR DESKTOP -->
            <div class="topic-contents d-none d-sm-block">
                <div class="class-header">
                    <span class="checkbox-space"></span>
                    <h7 class="camp-name">Camp Level</h7>
                    <h7 class="recommended-grade">Recommended Grade</h7>
                    <h7 class="online">Location</h7>
                    <h7 class="date">Date</h7>
                    <h7 class="days">Days</h7>
                    <h7 class="time">Time</h7>
                    <h7 class="capacity">Capacity</h7>
                    <h7 class="cost">Price</h7>
                </div>

                <div id="selected-contents">

                </div>
            </div>

            <!-- FOR MOBILE -->
            <div class="topic-contents d-block d-sm-none">
                <div id="mobile-selected-contents">

                </div>
            </div>
        </div>

        <!-- TOPICS -->
        <form action="/SummerCamp/campregistration" method="POST">
            <c:forEach items="${allEligibleCamps}" var="eligibleCamp">
                <c:if test="${fn:length(eligibleCamp) != 0}">
                    <div class="topic" data-topic="${eligibleCamp.get(0).campTopic}">
                        <div class="topic-name">
                            <h5>${eligibleCamp.get(0).campTopic}</h5>
                            <i class="material-icons md-24 d-none d-sm-block">expand_more</i>
                        </div>

                        <!-- FOR DESKTOP -->
                        <div class="topic-contents d-none d-sm-block">
                            <div class="class-header">
                                <span class="checkbox-space"></span>
                                <h7 class="camp-name">Camp Level</h7>
                                <h7 class="recommended-grade">Recommended Grade</h7>
                                <h7 class="online">Location</h7>
                                <h7 class="date">Date</h7>
                                <h7 class="days">Days</h7>
                                <h7 class="time">Time</h7>
                                <h7 class="capacity">Capacity</h7>
                                <h7 class="cost">Price</h7>
                            </div>

<%--                            <c:choose>--%>
<%--                                <c:when test="${OnCampus eq true}">--%>
<%--                                    <span>You are cleared to register all available camps.</span>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <span>You can online register for online camps. Please fill in the camper's medical information.</span>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>

                            <c:choose>
                                <c:when test="${OnCampus eq true}">
                                    <c:forEach items="${eligibleCamp}" var="camp">
                                        <div class="class-entry ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-campID ="${camp.campOfferedID}" data-topic="${camp.campTopic}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}>
										<span class="checkbox-space">
											<input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}">
											<div class="class-checkbox"></div>
										</span>
                                            <h7 class="camp-name">${camp.campLevel}</h7>
                                            <h7 class="recommended-grade">${camp.campGradeFormatted}</h7>
                                            <h7 class="online">${camp.remoteString}</h7>
                                            <h7 class="date">${camp.campStartWeek}-${camp.campEndWeek}</h7>
                                            <h7 class="days">${camp.getCampDays()}</h7>
                                            <h7 class="time">${camp.campTime}</h7>
                                            <h7 class="capacity ${camp.getFull() ? "text-danger" : ""}"> ${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</h7>
                                            <h7 class="cost">${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</h7>
                                        </div>
                                    </c:forEach>
                                </c:when>

                                <c:when test="${OnCampus eq false}">
                                    <span>Reminder:</span><br>
                                    <span style="text-decoration-line: underline">1. You are not allowed to select on-campus classes without submitting medical records</span><br>
                                    <span style="text-decoration-line: underline">2. Please complete the medical form if your kid(s) wants to attend on-campus.</span>
                                    <c:forEach items="${eligibleCamp}" var="camp">
                                        <c:choose>
                                            <c:when test="${camp.getRemote() eq true}">
                                                <div class="class-entry ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-campID ="${camp.campOfferedID}" data-topic="${camp.campTopic}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}>
                                                    <span class="checkbox-space">
                                                        <input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}">
                                                        <div class="class-checkbox"></div>
                                                    </span>
                                                    <h7 class="camp-name">${camp.campLevel}</h7>
                                                    <h7 class="recommended-grade">${camp.campGradeFormatted}</h7>
                                                    <h7 class="online">${camp.remoteString}</h7>
                                                    <h7 class="date">${camp.campStartWeek}-${camp.campEndWeek}</h7>
                                                    <h7 class="days">${camp.getCampDays()}</h7>
                                                    <h7 class="time">${camp.campTime}</h7>
                                                    <h7 class="capacity ${camp.getFull() ? "text-danger" : ""}"> ${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</h7>
                                                    <h7 class="cost">${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</h7>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="class-entry ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-campID ="${camp.campOfferedID}" data-topic="${camp.campTopic}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}
                                                     style="pointer-events: none; text-decoration: line-through; color: grey">
                                                    <span class="checkbox-space" >
                                                        <input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}" >
                                                        <div class="class-checkbox" ></div>
                                                    </span>
                                                    <h7 class="camp-name">${camp.campLevel}</h7>
                                                    <h7 class="recommended-grade">${camp.campGradeFormatted}</h7>
                                                    <h7 class="online">${camp.remoteString}</h7>
                                                    <h7 class="date">${camp.campStartWeek}-${camp.campEndWeek}</h7>
                                                    <h7 class="days">${camp.getCampDays()}</h7>
                                                    <h7 class="time">${camp.campTime}</h7>
                                                    <h7 class="capacity ${camp.getFull() ? "text-danger" : ""}"> ${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</h7>
                                                    <h7 class="cost">${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</h7>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>


                                    </c:forEach>
                                </c:when>
                            </c:choose>

                        </div>

                        <!-- FOR MOBILE -->
                        <div class="topic-contents d-block d-sm-none">
                            <c:choose>
                                <c:when test="${OnCampus eq true}">
                                    <span>You are cleared to register all available camps.</span>
                                </c:when>
                                <c:otherwise>
                                    <span>Reminder:</span><br>
                                    <span style="text-decoration-line: underline">1. You are not allowed to select on-campus classes without submitting medical records</span><br>
                                    <span style="text-decoration-line: underline">2. Please complete the medical form if your kid(s) wants to attend on-campus.</span>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach items="${eligibleCamp}" var="camp">

                                <c:choose>
                                    <c:when test="${OnCampus eq true}">
                                        <div class="class-entry mobile ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-topic="${camp.campTopic}" data-campID ="${camp.campOfferedID}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}>
										<span class="checkbox-space">
											<input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}">
											<div class="class-checkbox"></div>
										</span>

                                            <div class="class-entry-description">
                                                <h7 class="small-header ">Camp Level</h7>
                                                <span>${camp.campLevel}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Recommended Grade</h7>
                                                <span>${camp.campGradeFormatted}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Location</h7>
                                                <span>${camp.remoteString}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Date</h7>
                                                <span>${camp.campStartWeek}-${camp.campEndWeek}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Days</h7>
                                                <span>${camp.getCampDays()}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Time</h7>
                                                <span>${camp.campTime}</span>
                                            </div>

                                            <div class="class-entry-description ${camp.getFull() ? "text-danger" : ""}">
                                                <h7 class="small-header">Capacity</h7>
                                                <span>${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</span>
                                            </div>

                                            <div class="class-entry-description">
                                                <h7 class="small-header">Price</h7>
                                                <span>${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</span>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>

                                        <c:choose>
                                            <c:when test="${camp.getRemote() eq true}">

                                                <div class="class-entry mobile ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-topic="${camp.campTopic}" data-campID ="${camp.campOfferedID}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}
                                                     style="pointer-events: none; text-decoration: line-through; color: grey">
                                                <span class="checkbox-space">
                                                    <input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}">
                                                    <div class="class-checkbox"></div>
                                                </span>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header ">Camp Level</h7>
                                                        <span>${camp.campLevel}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Recommended Grade</h7>
                                                        <span>${camp.campGradeFormatted}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Location</h7>
                                                        <span>${camp.remoteString}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Date</h7>
                                                        <span>${camp.campStartWeek}-${camp.campEndWeek}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Days</h7>
                                                        <span>${camp.getCampDays()}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Time</h7>
                                                        <span>${camp.campTime}</span>
                                                    </div>

                                                    <div class="class-entry-description ${camp.getFull() ? "text-danger" : ""}">
                                                        <h7 class="small-header">Capacity</h7>
                                                        <span>${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Price</h7>
                                                        <span>${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</span>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="class-entry mobile ${(camp.isApplied() || camp.getFull()) ? 'applied' : ''}" data-level="${camp.campLevel}" data-topic="${camp.campTopic}" data-campID ="${camp.campOfferedID}" ${camp.getFull() ? "data-toggle='modal' data-target='#full-modal'" : ""}
                                                     style="pointer-events: none; text-decoration: line-through; color: grey">
                                                <span class="checkbox-space">
                                                    <input type="hidden" name="checkbox" value="0 ${camp.campOfferedID}">
                                                    <div class="class-checkbox"></div>
                                                </span>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header ">Camp Level</h7>
                                                        <span>${camp.campLevel}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Recommended Grade</h7>
                                                        <span>${camp.campGradeFormatted}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Location</h7>
                                                        <span>${camp.remoteString}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Date</h7>
                                                        <span>${camp.campStartWeek}-${camp.campEndWeek}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Days</h7>
                                                        <span>${camp.getCampDays()}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Time</h7>
                                                        <span>${camp.campTime}</span>
                                                    </div>

                                                    <div class="class-entry-description ${camp.getFull() ? "text-danger" : ""}">
                                                        <h7 class="small-header">Capacity</h7>
                                                        <span>${camp.getCampConfirmed()} of ${camp.getCampCapacity()}</span>
                                                    </div>

                                                    <div class="class-entry-description">
                                                        <h7 class="small-header">Price</h7>
                                                        <span>${parent.getUSCEmployee() ? camp.employeePrice : camp.price}</span>
                                                    </div>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </div>
                    </div>
                </c:if>

            </c:forEach>

            <div class="submit-row">
                <div class="btn btn-warning" onclick="handleConfirmationModal()" data-toggle="modal" data-target="#confirmation-modal">Submit</div>
            </div>

            <!-- CONFIRMATION MODAL -->
            <div class="modal fade" id="confirmation-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div id="confirmation-modal-dialog" class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Confirming Your Registration</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p> You are registering for the following courses... </p>
                            <div id="confirmation-modal-body">

                            </div>



                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-warning">
                        </div>
                    </div>
                </div>
            </div>

        </form>
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
<script src="/SummerCamp/scripts/campRegistration.js"></script>
</html>