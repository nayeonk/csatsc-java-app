<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
		<div id="container" class="container">
			<div class="admin-control-panel">
				<div class="admin-control-panel-header">
					<br/><p style="text-align:center;">${errorMessage}</p>
				</div>
				<div class="form-style-2">
					<div style="width:300px;">
						<form id="camp-form" name="camp-form" method="POST" action="addcamp" encType="multipart/form-data">
							<label for="camp-topic"><span>Camp Topic <span class="required">*</span></span><select name="camp-topic" id="camp-topic" class="form-control" onChange="addTextBoxForOther()"><c:forEach items="${campTopicList}" var="campTopic"><option value="${campTopic.topic}" ${campTopic.topic == pastCamp.getCampTopic() ? 'selected="selected"' : ''}><c:out value="${campTopic.topic}"/></option></c:forEach><option value="other"> Enter new topic </option> </select></label> <input type="text" name="other-camp-topic" class="input-field" id="other-camp-topic" style="display: ${fn:length(campTopicList) lt 1 ? 'block' : 'none'}">
							<label for="camp-level"><span>Camp Level <span class="required">*</span></span><select name="camp-level" id="camp-level" class="form-control" onChange="addTextBoxForOtherLevel()"><c:forEach items="${campLevelList}" var="campLevel"><option value="${campLevel.campLevelDescription}" ${campLevel.campLevelDescription == pastCamp.getCampLevel() ? 'selected="selected"' : ''}><c:out value="${campLevel.campLevelDescription}"/></option></c:forEach><option value="other"> Enter new level </option> </select></label> <input type="text" name="other-camp-level" class="input-field" id="other-camp-level" style="display: ${fn:length(campLevelList) lt 1 ? 'block' : 'none'}">
                            <label for="camp-start-date"><span>Start Date <span class="required">*</span></span><input
                                    style="cursor: text;" type="date" class="input-field form-control"
                                    name="camp-start-date" value="" id="camp-start-date"
                                    required></label>
                            <label for="camp-end-date"><span>End Date <span class="required">*</span></span><input
                                    style="cursor: text;" type="date" class="input-field form-control"
                                    name="camp-end-date" value="" id="camp-end-date" required></label>
							<label><span style="margin-bottom:6px;">Days of the Week <span class="required">*</span></span></label>
							<div class="daysOfWeek">
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayM">M</label>
									<input type="checkbox" name="dayM" id="dayM">
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayT">T</label>
									<input type="checkbox" name="dayT" id="dayT">
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayW">W</label>
									<input type="checkbox" name="dayW" id="dayW">
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayH">H</label>
									<input type="checkbox" name="dayH" id="dayH">
								</div>
								<div style="float:left;padding-right:15px;">
									<label style="text-align:center;margin-bottom:10px;" for="dayF">F</label>
									<input type="checkbox" name="dayF" id="dayF">
								</div>
								<div style="float:left;padding-right:12px;">
									<label style="text-align:center;margin-bottom:10px;" for="daySa">Sa</label>
									<input type="checkbox" name="daySa" id="daySa">
								</div>
								<div style="float:left;padding-right:12px;margin-bottom: 20px;">
									<label style="text-align:center;margin-bottom:10px;" for="daySu">Su</label>
									<input type="checkbox" name="daySu" id="daySu">
								</div>
							</div>
							<label for="camp-start-time"><span>Start Time <span class="required">*</span></span><input
									style="cursor: text;" type="time" class="input-field form-control"
									name="camp-start-time" value="" id="camp-start-time" required></label>
							<label for="camp-end-time"><span>End Time <span class="required">*</span></span><input
									style="cursor: text;" type="time" class="input-field form-control"
									name="camp-end-time" value="" id="camp-end-time" required></label>
							<label for="camp-description"><span>Description <span class="required">*</span></span><textarea name="camp-description" class="textarea-field" form="camp-form" required>${pastCamp.getDescription()}</textarea></label>
							<label for="camp-recommended-grade"><span>Grade Levels <span class="required">*</span></span>
								<select name="camp-recommended-grade-start" class="form-control">
									<c:forEach items="${gradeList}" var="grade">
										<option value="${grade.gradeID}" ${grade.grade == pastCamp.getRecommendedGradeLow() ? 'selected="selected"' : ''}>${grade.grade} </option>
									</c:forEach>
								</select>

								to

								<select name="camp-recommended-grade-end" class="form-control">
									<c:forEach items="${gradeList}" var="grade">
										<option value="${grade.gradeID}" ${grade.grade == pastCamp.getRecommendedGradeHigh() ? 'selected="selected"' : ''}>${grade.grade}</option>
									</c:forEach>
								</select>

								<br />
							</label>
							<label for="file"><span style="float:none;">Photo</span><input  accept=".jpeg, .jpg, .jpe, .png, .pdf, .gif" type="file" name="file"></label>
							<label for="camp-capacity"><span>Capacity <span class="required">*</span></span><input type="number" class="input-field" name="camp-capacity" value="${pastCamp.getCapacity()}" min="0" required></label>
							<fmt:formatNumber value="${pastCamp.getPrice()/100}" minFractionDigits="2" var="formattedPrice"/>
							<label for="camp-cost"><span>Cost<span class="required">*</span></span><input type="number" class="input-field" name="camp-cost" value='${formattedPrice}' min="0" step="0.01" onchange="setTwoNumberDecimal(this)" required></label>
							<label for="camp-remote"><span>Remote</span><input type="checkbox" name="camp-remote"></label>
<%--							text input for TA--%>
							<label for="camp-ta"><span>Camp TA </span></label>
							<input type="text" id="camp-ta" class="form-control" name="camp-ta">
<%--							add break to seperate from submit button--%>
							<br>

							<input type="Submit" name="Submit" value="Submit" />
						</form>
					</form>
				</div>
			</div>

			<br class="clear"/>
			<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->
</t:adminpage>