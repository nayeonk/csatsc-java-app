<%@ page import="java.util.Collections" %>
<%@ page import="data.CampTopic" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="database.DatabaseQueries" %>
<%@ page language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<jsp:include page="../includes/header.jsp"/>

<div id="container" class="container">
    <div id="page-title">
        <!-- May be changed to user ID -->
        <h1 style="text-align: center;">Welcome ${name}</h1>
    </div>

    <br class="clear"/>

    <div class="admin-control-panel">
        <jsp:include page="adminheader.jsp"/>
        <br>
        <p>Only topics with open camps are shown.</p>
        <div class="admin-control-panel-body">
            <div class="camp-student-list-tabs">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#current-camps-list"> Current Camps </a></li>
                    <li><a data-toggle="tab" href="#all-camps-list"> All Camps </a></li>
                </ul>

                <div class="tab-content">
                    <c:set var="panes" value="${fn:split('current,all', ',')}"/>
                    <c:forEach items="${panes}" var="pane">
                        <c:choose>
                            <c:when test="${pane == 'current'}">
                                <c:set var="activepane" value="in active"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="activepane" value=""/>
                            </c:otherwise>
                        </c:choose>

                        <div id="${pane}-camps-list" class="tab-pane fade ${activepane}">
                            <div class="panel-group" id="${pane}-accordion">
                                <c:forEach items="${campTopicList}" var="campTopic">
                                    <%-- For each topic, select the right data --%>
                                    <c:choose>
                                        <c:when test="${pane == 'current'}">
                                            <c:set var="campOfferedList"
                                                   value="${campTopic.campOfferedList}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="campOfferedList"
                                                   value="${campTopic.allCampOfferedList}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${pane == 'current' && campOfferedList.isEmpty()}">
                                            <%-- If in current and it is empty, don't show anything --%>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title"><a data-toggle="collapse"
                                                                               data-parent="#accordion"
                                                                               href="#${pane}-collapse-${campTopic.campTopicID}"
                                                                               style="display: block;"><c:out
                                                            value="${campTopic.topic}"/></a></h4>
                                                </div>
                                                <div id="${pane}-collapse-${campTopic.campTopicID}"
                                                     class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <div class="table responsive">
                                                            <table class="table">
                                                                <thead>
                                                                <tr>
                                                                    <th style="min-width: 50px; text-align:center">
                                                                        Start Date
                                                                    </th>
                                                                    <th style="min-width: 50px; text-align:center"> End
                                                                        Date
                                                                    </th>
                                                                    <th style="min-width: 50px; text-align:center">
                                                                        Days
                                                                    </th>
                                                                    <th style="min-width: 50px; text-align:center">
                                                                        Start Time
                                                                    </th>
                                                                    <th style="min-width: 50px; text-align:center"> End
                                                                        Time
                                                                    </th>
                                                                    <th style="text-align:center"> Level</th>
                                                                    <th style="text-align:center"> Grade</th>
                                                                    <th style="text-align:center"> Cost</th>
                                                                    <th style="text-align:center"> Description</th>
                                                                    <th style="text-align:center"> Applied</th>
                                                                    <th style="text-align:center"> Accepted</th>
                                                                    <th style="text-align:center"> Confirmed</th>
                                                                    <th style="text-align: center">Remote</th>
                                                                    <th style="text-align:center"> Action</th>
                                                                        <%--																Oct 13th 2020 John Kang--%>
                                                                    <th style="text-align:center"> Assigned TA</th>
                                                                </tr>
                                                                </thead>
                                                                <tbody>
                                                                <c:forEach items="${campOfferedList}" var="campOffered">
                                                                    <tr class="offered-camp">
                                                                        <td align="center"><fmt:formatDate
                                                                                pattern="MM-dd-YY"
                                                                                value="${campOffered.startDate}"/></td>
                                                                        <td align="center"><fmt:formatDate
                                                                                pattern="MM-dd-YY"
                                                                                value="${campOffered.endDate}"/></td>
                                                                        <td align="center"><c:out
                                                                                value="${campOffered.days}"/></td>
                                                                        <td align="center"><fmt:formatDate
                                                                                pattern="hh:mm a"
                                                                                value="${campOffered.startTime}"/></td>
                                                                        <td align="center"><fmt:formatDate
                                                                                pattern="hh:mm a"
                                                                                value="${campOffered.endTime}"/></td>
                                                                        <td align="center"><c:out
                                                                                value="${campOffered.campLevel}"/></td>
                                                                        <td align="center"><c:out
                                                                                value="${campOffered.recommendedGradeLow}-${campOffered.recommendedGradeHigh}"/></td>
                                                                        <td align="center">
                                                                            <c:choose>
                                                                                <c:when test="${campOffered.price == 0}">
                                                                                    Free
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <fmt:setLocale value="en_US"/>
                                                                                    <fmt:formatNumber
                                                                                            value="${campOffered.price/100.00}"
                                                                                            type="currency"/>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </td>
                                                                        <td>
                                                                                <%--																		weird bug where 'test' topic camps offered can only show the first description and not the
                                                                                                                                                            others but for other topics each camp offered's description works--%>
                                                                            <a id="inline"
                                                                               onclick="descriptionPopup(${campOffered.campOfferedID})">
                                                                                + </a>
                                                                            <div class="popUpText"
                                                                                 id="description-${campOffered.campOfferedID}">
                                                                                <c:out value="${campOffered.description}"/></div>
                                                                        </td>

                                                                        <td align="center"><a
                                                                                href="campstudents?campOfferedID=${campOffered.campOfferedID}"><c:out
                                                                                value="${campOffered.applied}"/></a>
                                                                        </td>
                                                                        <td align="center"><a
                                                                                href="campstudents?campOfferedID=${campOffered.campOfferedID}#tab_accepted-student-list"><c:out
                                                                                value="${campOffered.accepted}"/></a>
                                                                        </td>
                                                                        <td align="center"><a
                                                                                href="campstudents?campOfferedID=${campOffered.campOfferedID}#tab_confirmed-student-list"><c:out
                                                                                value="${campOffered.confirmed}"/> of
                                                                            <c:out
                                                                                    value="${campOffered.capacity}"/></a>
                                                                        </td>
                                                                        <td align="center"><p><c:out
                                                                                value="${campOffered.remote}"/></p></td>
                                                                        <td align="center">
                                                                            <c:choose>
                                                                                <c:when test="${!campOffered.closed}">
                                                                                    <button type="submit" name="close"
                                                                                            value="close"
                                                                                            onClick="closeConfirmation('admincontrolpanel?campOfferedID=${campOffered.campOfferedID}&close=close', 'admincontrolpanel')">
                                                                                        Close
                                                                                    </button>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <button type="submit" name="open"
                                                                                            value="open"
                                                                                            onClick="openConfirmation('admincontrolpanel?campOfferedID=${campOffered.campOfferedID}&open=open', 'admincontrolpanel')">
                                                                                        Open
                                                                                    </button>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                            <button type="submit" name="delete"
                                                                                    value="delete"
                                                                                    onClick="deleteConfirmation('admincontrolpanel?campOfferedID=${campOffered.campOfferedID}&delete=delete', 'admincontrolpanel')">
                                                                                Delete
                                                                            </button>
                                                                            <button type="submit" name="update"
                                                                                    value="update"
                                                                                    onClick="location.href = 'updatecamp?campOfferedID=${campOffered.campOfferedID}';">
                                                                                Update
                                                                            </button>
                                                                        </td>
                                                                        <td align="center"><c:out
                                                                                value="${campOffered.assignedTA}"/></td>
                                                                    </tr>
                                                                </c:forEach>
                                                                <script>
                                                                    $(document).ready(function () {
                                                                        $('a#inline').fancybox();
                                                                    });
                                                                </script>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <br class="clear"/>
    <br class="clear"/>

</div>
<!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript">

    function formSubmit() {
        $("#contentValue1").val($("#paraContent").text());
        var form = document.getElementById("form1");
        var str = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.action = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.submit();
    }

    function descriptionPopup(campID) {
        console.log("i am being pressed");
        let campDescription = "description-" + campID;
        console.log("camp desc: " + campDescription);
        let popup = document.getElementById(campDescription);
        console.log("popup: " + popup);
        popup.classList.toggle("show");
    }
</script>
<%--this style was added for the + thing to work for description--%>
<style>
    .popUpText {
        display: none;
        width: 500px;
    }

    .show {
        display: block;
        width: 500px;
    }
</style>
