<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
    <div id="container" class="container">
		<br class="clear"/>
		<div class="admin-control-panel">
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
                						<div class="panel panel-default">
                							<div class="panel-heading">
                								<h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#${pane}-collapse-${campTopic.campTopicID}" style="display: block;"><c:out value="${campTopic.topic}"/></a></h4>
                							</div>
                							<div id="${pane}-collapse-${campTopic.campTopicID}" class="panel-collapse collapse">
                								<div class="panel-body">
                									<div class="table responsive">
                										<table class="table">
                											<thead>
                												<tr>
                													<th style="min-width: 50px; text-align:center"> Start Date </th>
                													<th style="min-width: 50px; text-align:center"> End Date </th>
																	<th style="min-width: 50px; text-align:center"> Days </th>
																	<th style="min-width: 50px; text-align:center"> Start Time </th>
																	<th style="min-width: 50px; text-align:center"> End Time </th>
                													<th style="text-align:center"> Level </th>
                													<th style="text-align:center"> Grade </th>
                													<th style="text-align:center"> Assigned TAs </th>
                													<th style="text-align:center"> Action </th>
                												</tr>
                											</thead>
                											<tbody>
                                    							<c:choose>
                                    								<c:when test="${pane == 'current'}">
                                                                        <c:set var="campOfferedList" value="${campTopic.campOfferedList}"/>
                                    								</c:when>
                                    								<c:otherwise>
                                    									<c:set var="campOfferedList" value="${campTopic.allCampOfferedList}"/>
                                    								</c:otherwise>
                                    							</c:choose>
                												<c:forEach items="${campOfferedList}" var="campOffered">
                													<tr class="offered-camp">
                														<td align="center"> <fmt:formatDate pattern="MM-dd-YY" value="${campOffered.startDate}"/> </td>
                														<td align="center"> <fmt:formatDate pattern="MM-dd-YY" value="${campOffered.endDate}"/> </td>
																		<td align="center"> <c:out value="${campOffered.days}"/> </td>
																		<td align="center"> <fmt:formatDate pattern="hh:mm a" value="${campOffered.startTime}"/> </td>
																		<td align="center"> <fmt:formatDate pattern="hh:mm a" value="${campOffered.endTime}"/> </td>
                														<td align="center"> <c:out value="${campOffered.campLevel}"/> </td>
                														<td align="center"> <c:out value="${campOffered.recommendedGradeLow}-${campOffered.recommendedGradeHigh}"/> </td>
                													
                														<td align="center">
                															<c:forEach items="${campOffered.campTAs}" var="ta">${ta.firstName} ${ta.lastName}<br /></c:forEach>
                														</td>
                														<td align="center">
                															<a href="/SummerCamp/staffassignmentedit?campid=${campOffered.campOfferedID}">Edit</a>
                														</td>
                													</tr>
                												</c:forEach>
                												<script>
                													$(document).ready(function() {
                														$('a#inline').fancybox();
                													});
                												</script>
                											</tbody>
                										</table>
                									</div>
                								</div>
                							</div>
                						</div>
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

	</div><!-- Ends: #CONTAINER -->
</t:adminpage>

<script type="text/javascript">

    function formSubmit() {
        $("#contentValue1").val($("#paraContent").text());
        var form = document.getElementById("form1");
        var str = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.action = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.submit();
    }
</script>
