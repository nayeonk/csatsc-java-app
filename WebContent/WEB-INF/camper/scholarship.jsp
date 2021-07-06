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
					<h1>Scholarship Application (Optional)</h1>

					<!-- Error Message -->
					<c:set var="error" value="${errorMessage}" scope="request" />
					<c:choose>
						<c:when test="${error != null && error.length() != 0}">
							<div class="message-popup" style="text-align:center;z-index:999999999;">
								<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
									<p class="message-text-error" id="errormsgdisplay" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;">${error}</p>
								</div>
								<br />
								<br />
							</div>
						</c:when>
						<c:otherwise>
							<div class="message-popup" style="display:none;text-align:center;z-index:999999999;">
								<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
									<p class="message-text-error" id="errormsgdisplay" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
								</div>
								<br />
								<br />
							</div>
						</c:otherwise>
					</c:choose>

          			<!-- Skip Scholarship Application -->
					<c:choose>
						<c:when test="${(student.gradeReports == null || student.gradeReports.size() == 0) && (student.reducedMealsVerifications == null || student.reducedMealsVerifications.size() == 0)}">
							<div id="skip-box" class="margin-below">
								<p>Thanks to the generosity of our donors like the Institute for Education, we offer a number of scholarships based on merit and need.</p>
								<p>If you do not wish to apply for scholarship, please skip this step.</p>
								<button type="submit" class="btn btn-dark" id="skip" name="submit" value="skip">Skip this step</button>
							</div>
						</c:when>
						<c:otherwise>

						</c:otherwise>
					</c:choose>

					<div class="alert alert-info" role="alert">
						This step is completely <em>OPTIONAL</em>. If you do not wish to apply for a scholarship, please continue by clicking <button type="submit" class="btn btn-dark" id="skip" name="submit" value="skip">Skip this step</button>
					</div>


					<p class="changes-note">Note: Changes you make on this page will not be saved until you click <i>Apply for Scholarship (Optional)</i>. If you wish to unapply for a scholarship, please contact cscamps@usc.edu.</p>

					<!-- Scholarship Information -->
					<div id="scholarship-application" style="display:block">
						<!-- Grade Report -->
						<div class="row">
							<div class="col">
								<label class="bold" for="gradeReportFile">Grade Report</label>
								<br />
								<% int i = 0; %>
								<c:forEach items="${student.gradeReports}" var="gradeReport">
									<div class="gradereports${student.studentID}" id="gradereport-${gradeReport.gradeReportID}-${student.studentID}">
										<a href="viewuploadedpicture?id=<%=i%>&kind=grade" target="_blank">File uploaded on
												${gradeReport.readableTimestamp}
										</a>
										<a type="button" onclick="return deleteGrade('${gradeReport.gradeReportID}', 'gradereport-${gradeReport.gradeReportID}-${student.studentID}');" target="_blank">[Delete]</a>
										<br />
									</div>
									<% i++; %>
								</c:forEach>
								<input type="file" accept=".jpeg, .jpg, .jpe, .png, .pdf, .gif" id="gradereportsfile${student.studentID}" name="gradeReportFile">
							</div>
						</div>
						<br />

						<!-- Reduced Meals -->
						<div class="row">
							<div class="col">
								<label class="bold" for="reducedMeals">Does your child qualify for reduced or free meals at school?</label>
								<br />
								<c:forEach items="${reducedMealsArray}" var="reducedMeals">
									<c:choose>
										<c:when test="${reducedMeals.reducedMealsID == reducedMealsID}">
											<input type="radio" name="reducedMeals" id="reducedMeals${reducedMeals.reducedMealsID}-${student.studentID}" value="${reducedMeals.reducedMealsID}" checked/>
											<label for="reducedMeals${reducedMeals.reducedMealsID}-${student.studentID}">${reducedMeals.description}</label><br>
										</c:when>
										<c:otherwise>
											<input type="radio" name="reducedMeals" id="reducedMeals${reducedMeals.reducedMealsID}-${student.studentID}" value="${reducedMeals.reducedMealsID}" />
											<label for="reducedMeals${reducedMeals.reducedMealsID}-${student.studentID}">${reducedMeals.description}</label><br>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<br />
							</div>
						</div>
						<br />

						<!-- Reduced Meal Verification -->
						<div class="row">
							<div class="col">
								<label class="bold" for="reducedMealsVerificationFile">Reduced Meal Verification</label>
								<br />
								<% int j = 0; %>
								<c:forEach items="${student.reducedMealsVerifications}" var="reducedMealsVerification">
									<div class = "reducedmeals${student.studentID}" id="reducedmealsverification-${reducedMealsVerification.reducedMealsVerificationID}-${student.studentID}">
										<a href="viewuploadedpicture?id=<%=j%>&kind=reduced" target="_blank">
											File uploaded on ${reducedMealsVerification.readableTimestamp}
										</a>
										<a type="button" onclick="return deleteReduced('${reducedMealsVerification.reducedMealsVerificationID}', 'reducedmealsverification-${reducedMealsVerification.reducedMealsVerificationID}-${student.studentID}');" target="_blank">[Delete]</a>
										<br />
									</div>
									<% j++; %>
								</c:forEach>
								<input type="file" accept=".jpeg, .jpg, .jpe, .png, .pdf, .gif" id="reducedmealsfile${student.studentID}" name="reducedMealsVerificationFile" />
							</div>
						</div>
						<br />

						<!-- Form Submit Buttons -->
						<div class="submit-row">
							<button type="submit" id="save-continue" class="btn btn-primary" name="submit" value="save-continue">Apply for Scholarship (Optional)</button><br />
							<button type="submit" class="btn btn-dark" id="skip" name="submit" value="skip">Skip this step</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</body>

	<script>
		$('#save-continue').on('click', function(event) {
			var errormessage = "";
			var reducedMealsVal = $('input[name="reducedMeals"]').val();

			if(($("div.gradereports" + ${student.studentID}).find( "a" ).length === 0
					&& $('#gradereportsfile' + ${student.studentID}).val() === '')){
				errormessage += "Please upload grade report.<br>";
			}

			if(reducedMealsVal === "") {
				errormessage += "Please select if your child qualifies for reduced meals.<br>";
			}

			if((reducedMealsVal === "1" || reducedMealsVal === "2")
					&&($( "div.reducedmeals" + ${student.studentID}).find( "a" ).length === 0
							&& $('#reducedmealsfile' + ${student.studentID}).val() === '')) {
				errormessage += "Please upload reduced meals verification.<br>";

			}

			if(errormessage !== "") {
				$('#errormsgdisplay').html(errormessage);
				$('.message-popup').css("display","inline-block");
				$(".message-popup").slideDown().delay(7000).slideUp();

				event.preventDefault();
			}
		});

		function deleteFile(id, kind, divID){
			$.post('${pageContext.request.contextPath}/deleteuploadedfile', {fileID : id, fileKind :kind})
				.done(function(data) {
					//need to reload so that progress is updated
					location.reload();
					if (data != null && data != ""){
						showError(data);
					}
					else {
						document.getElementById(divID).remove();
					}
				})
				.fail(function(data) {
					showError("something went wrong!");
				});
		}

		function deleteGrade(id, divID) {
			deleteFile(id, 'grade', divID);
		}

		function deleteReduced(id, divID) {
			deleteFile(id, 'reduced', divID);
		}
	</script>
</html>