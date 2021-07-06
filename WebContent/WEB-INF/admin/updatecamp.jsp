<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<jsp:include page="../includes/header.jsp"/>

		<div id="container" class="container">
			<div class="back-button-container">
				<a href="admincontrolpanel"> Back </a>
				<a class="logout-link" href="logout"> Logout </a>
			</div>
			<div class="admin-control-panel">
				<div class="admin-control-panel-header">
					<h4 style="text-align:center;">Update Camp<br/></h4>
					<br/><p style="text-align:center;">${errorMessage}</p>
				</div>
				<div class="form-style-2">
					<div style="width:300px;">
						<form id="camp-form" name="camp-form" method="POST" action="updatecamp?campOfferedID=${campOffered.campOfferedID}" encType="multipart/form-data">
							<label for="camp-topic"><span>Camp Topic <span class="required">*</span></span><select name="camp-topic" id="camp-topic" onChange="addTextBoxForOther()"><c:forEach items="${campTopicList}" var="campTopic"><option value="${campTopic.topic}" ${campOffered.campTopic == campTopic.topic ? "selected" : '' }><c:out value="${campTopic.topic}"/></option></c:forEach><option value="other"> Enter new topic </option> </select></label> <input type="text" name="other-camp-topic" class="input-field" id="other-camp-topic" style="display: none;">
							<label for="camp-level"><span>Camp Level <span class="required">*</span></span><select name="camp-level"><c:forEach items="${campLevelList}" var="campLevel"><option value="${campLevel.campLevelDescription}" ${campOffered.campLevel == campLevel.campLevelDescription ? "selected" : ''}><c:out value="${campLevel.campLevelDescription}"/></option></c:forEach></select></label>
							<label for="camp-start-date"><span>Start Date <span class="required">*</span></span><input
									style="cursor: text;" type="date" class="input-field form-control"
									name="camp-start-date" value="${startDate}" id="camp-start-date"></label>
							<label for="camp-end-date"><span>End Date <span class="required">*</span></span><input
									style="cursor: text;" type="date" class="input-field form-control"
									name="camp-end-date" value="${endDate}" id="camp-end-date"></label>
							<label><span style="margin-bottom:6px;">Days of the Week</span></label>
							<div class="daysOfWeek">
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayM">M</label>
									<input type="checkbox" name="dayM" id="dayM" ${dayM ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayT">T</label>
									<input type="checkbox" name="dayT" id="dayT" ${dayT ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayW">W</label>
									<input type="checkbox" name="dayW" id="dayW" ${dayW ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayH">H</label>
									<input type="checkbox" name="dayH" id="dayH" ${dayH ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayF">F</label>
									<input type="checkbox" name="dayF" id="dayF" ${dayF ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:12px;">
									<label style="text-align:center;margin-bottom:10px;" for="daySa">Sa</label>
									<input type="checkbox" name="daySa" id="daySa" ${daySa ? "checked" : ""}>
								</div>
								<div style="float:left;padding-right:12px;margin-bottom: 20px;">
									<label style="text-align:center;margin-bottom:10px;" for="daySu">Su</label>
									<input type="checkbox" name="daySu" id="daySu" ${daySu ? "checked" : ""}>
								</div>
							</div>
							<label for="camp-start-time"><span>Start Time <span class="required">*</span></span><input
									style="cursor: text;" type="time" class="input-field form-control"
									name="camp-start-time" value="${startTime}" id="camp-start-time"></label>
							<label for="camp-end-time"><span>End Time <span class="required">*</span></span><input
									style="cursor: text;" type="time" class="input-field form-control"
									name="camp-end-time" value="${endTime}" id="camp-end-time"></label>
							<label for="camp-description"><span>Description <span class="required">*</span></span><textarea name="camp-description" class="textarea-field" id="description-field" form="camp-form" placeholder="${campOffered.description}" required>${campOffered.description}</textarea></label>
								<script>
								$(document).ready(function() {
									var description = $('#description-field').attr('placeholder');
									$('#description-field').html(description);
								});
								</script>
							<label for="camp-recommended-grade"><span>Grade <span class="required">*</span></span>
								<select name="camp-recommended-grade-start">
									<c:forEach items="${gradeList}" var="grade">
										<option value="${grade.gradeID}" ${recommendedGradeStart == grade.grade ? "selected" : ''}>${grade.grade}</option>
									</c:forEach>
								</select>

								to

								<select name="camp-recommended-grade-end">
									<c:forEach items="${gradeList}" var="grade">
										<option value="${grade.gradeID}" ${recommendedGradeEnd == grade.grade ? "selected" : ''}>${grade.grade}</option>
									</c:forEach>
								</select>

								<br />
							</label>
							<label for="file"><span style="float:none;">Change Photo?</span><input type="checkbox" name="file" id="yesNewPhoto" value="Yes">Yes</label>
							
							<div id="newFileDiv" style="display:none;">
								<label for="file-input"><span style="float:none;">Upload New Photo <span class="required">*</span></span>
								<input type="file" name="file-input"></label><br/>
							</div>
							
							<label for="camp-capacity"><span>Capacity <span class="required">*</span></span><input type="number" class="input-field" name="camp-capacity" value="${campOffered.capacity}" min="0" required></label>
							<label for="camp-cost"><span>Cost<span class="required">*</span></span><input type="number" class="input-field" name="camp-cost" value="${cost}" min="0" step="0.01" onchange= "setTwoNumberDecimal(this)" required></label>
							<label for="camp-remote"><span>Remote</span><input type="checkbox" name="camp-remote" ${remote ? "checked" : ""}></label>

							<%--							text input for TA--%>
							<label for="camp-ta"><span>Camp TA </span></label>
							<input type="text" id="camp-ta" class="form-control" name="camp-ta" placeholder="${campOffered.assignedTA}">
							<%--							add break to seperate from submit button--%>
							<br>

							<input type="Submit" name="Submit" value="Submit" />
						
							<script>
								$(document).ready (function () {									
									if ($("#yesNewPhoto").attr('checked')) {
										$('#newFileDiv').show();
									}

									else {
										$('#newFileDiv').hide();
									}
								});

								$("#yesNewPhoto").change(function () {									
									if ($("#yesNewPhoto").attr('checked')) {
										$('#newFileDiv').slideDown();
									}

									else {
										$('#newFileDiv').slideUp();
									}
								});
							</script>
						
						</form>
					</div>
				</div>
			</div>

			<br class="clear"/>
			<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>
