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
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
<%--		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">--%>
<%--		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/studentNavbar.css">--%>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/camperstyle.css" />

		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>

		<c:set var="mf" value="${medicalForm}"/>
		<c:set var="genderID" value="${(mf.genderID == null ? student.genderID : mf.genderID)}"/>
		<c:set var="dob" value="${(mf.dateOfBirth == null ? student.dob : mf.dateOfBirth)}"/>
		<c:set var="insurance" value="${mf.insuranceCard}"/>
	</head>
	<body>
		<!-- CS@SC Header -->
		<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

		<div id="page">
			<!-- Static yellow sidebar -->
			<jsp:include page="../includes/studentNavbar.jsp"/>

			<!-- Main page content -->
			<div id="main">
				<form name="medical" method="POST" action="${pageContext.request.contextPath}/medical" encType="multipart/form-data" onsubmit="return validateForm(this, event, ${insurance != null});">

					<!-- Title -->
					<h1 class="full-line">Medical Information</h1>
					<p class="changes-note">Note: Changes you make on this page will not be saved until you click <i>Save & Continue</i>.</p>

					<!-- Error Message -->
					<c:set var="error" value="${errorMessage}" scope="request" />
					<c:choose>
						<c:when test="${error != null && error.length() != 0}">
							<div class="message-popup" style="text-align:center;z-index:999999999;">
								<div class="error-message" style="text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
									<p class="message-text-error" id="errormsgdisplay" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;">${error}</p>
								</div>
								<br />
								<br />
							</div>
						</c:when>
						<c:otherwise>
							<div class="message-popup" style="display:none;text-align:center;z-index:999999999;">
								<div class="error-message" style="text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
									<p class="message-text-error" id="errormsgdisplay" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
								</div>
								<br />
								<br />
							</div>
						</c:otherwise>
					</c:choose>


					<!-- OnCampus -->
					<div class="customRow margin-below">
						<div id="OnCampus-div">
							<div class="customRow center-vertical">
								<c:choose>
									<c:when test="${OnCampus eq true}">
										<br />
										<span> You may now register all available camps. </span>
									</c:when>

									<c:when test="${OnCampus eq false}">
										<br />
										<span> It's mandatory for campers to complete this form before being able to register for on-campus camps. </span>
									</c:when>
								</c:choose>
							</div>
						</div>
					</div>

					<!-- Student Information -->
					<h3 class="full-line">Student Information</h3>

					<!-- Name -->
					<c:choose>
						<c:when test="${mf.studentFName != null}">
							<div class="customRow smargin-below full-line d_flex name">
								<div class="half-line name-inner">
									<label class="required" for="legal-first-name">Legal First Name</label>
									<input type="text" name="legal-first-name" d="legal-first-name" value="${mf.studentFName}" required />
								</div>

								<div class="half-line wrap name-inner">
									<label class="required" for="legal-last-name">Legal Last Name</label>
									<input type="text" name="legal-last-name" id="legal-last-name" value="${mf.studentLName}" required />
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="customRow smargin-below full-line d_flex name">
								<div class="half-line name-inner">
									<label class="required" for="legal-first-name">Legal First Name</label>
									<input type="text" name="legal-first-name" id="legal-first-name" value="${student.firstName}" required />
								</div>

								<div class="half-line wrap name-inner">
									<label class="required" for="legal-last-name">Legal Last Name</label>
									<input type="text" name="legal-last-name" id="legal-last-name" value="${student.lastName}" required />
								</div>
							</div>
						</c:otherwise>
					</c:choose>

					<!-- Gender -->
					<div class="customRow full-line smargin-below">
						<label class="required full-line" for="gender">Gender</label>
						<c:forEach items="${genderArray}" var="gender">
							<c:choose>
								<c:when test="${gender.genderID == genderID}">
									<input type="radio" name="gender" id="gender-${gender.genderID}" value="${gender.genderID}" checked/> ${gender.gender} &nbsp; &nbsp; &nbsp;
								</c:when>
								<c:otherwise>
									<input type="radio" name="gender" id="gender-${gender.genderID}" value="${gender.genderID}"/> ${gender.gender} &nbsp; &nbsp; &nbsp;
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>

					<!-- Date of Birth -->
					<div class="customRow full-line smargin-below">
						<label class="required full-line" for="birthdate">Date of Birth</label>
						<input type="date" name="birthdate" id="birthdate" value="${dob}" required />
					</div>

					<!-- Phone Number -->
					<div class="full-line margin-below">
						<label class="required full-line" for="personal-phone">Phone Number</label>
						<c:choose>
							<c:when test="${mf.parentPhoneCellOrHome != null }">
								<div>
									<input type="text" name="personal-phone" id="personal-phone" value="${mf.parentPhone}" required />
								</div>

								<c:choose>
									<c:when test="${mf.parentPhoneCellOrHome }">
										<input required type="radio" id="personal-phone1" name="phone" value=1 > Office number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="personal-phone2" name="phone" value=2 checked> Cell number &nbsp; &nbsp; &nbsp;
									</c:when>
									<c:otherwise>
										<input required type="radio" id="personal-phone1" name="phone" checked value=1 > Office number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="personal-phone2" name="phone" value=2> Cell number &nbsp; &nbsp; &nbsp;
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<input type="text" name="personal-phone" id="personal-phone" value="${parent.phone}" required />
								<input required type="radio" id="personal-phone1" name="phone" value=1 > Office number &nbsp; &nbsp; &nbsp;
								<input required type="radio" id="personal-phone2" name="phone" value=2> Cell number &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>
					</div>

					<hr class="divider">

					<!-- Legal Guardian -->
					<h3 class="full-line">Legal Guardian</h3>

					<!-- Legal Guardian Name -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="guardian">Full Name</label>
						<div>
							<input type="text" name="guardian" id="guardian" value="${mf.legalGuardianName}" required />
						</div>

						<c:choose>
							<c:when test="${mf.legalGuardianAgree == 'agree'}">
								<input type="checkbox" name="guardian-agree" id="guardian-agree" value="agree" required checked>
								I agree that this is the legal guardian
								</input>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="guardian-agree" id="guardian-agree" value="agree" required>
								I agree that this is the legal guardian
								</input>
							</c:otherwise>
						</c:choose>
					</div>

					<hr class="divider">

					<!-- Emergency Contact -->
					<h3 class="full-line">Emergency Contact</h3>

					<!-- Emergency Contact Name -->
					<c:choose>
						<c:when test="${mf.ecPhoneCellOrHome != null}">
							<div class="full-line smargin-below">
								<label class="required full-line" for="econtact-name">Full Name</label>
								<input type="text" name="econtact-name" id="econtact-name" value="${mf.ecName}" required />
							</div>
							<div class="full-line smargin-below">
								<label class="required full-line" for="econtact-relationship">Relationship to student</label>
								<input type="text" name="econtact-relationship" id="econtact-relationship" value="${mf.ecRelationship}" required />
							</div>

							<div class="full-line smargin-below">
								<div>
									<label class="required full-line" for="econtact-phone">Phone Number</label>
									<input type="text" name="econtact-phone" id="econtact-phone" value="${mf.ecPhone}" required />
								</div>
								<c:choose>
									<c:when test="${mf.ecPhoneCellOrHome}">
										<input required type="radio" id="econtact-phone1" name="ecphone" value=1 > Home number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="econtact-phone2" name="ecphone" checked value=2> Cell number &nbsp; &nbsp; &nbsp;
									</c:when>
									<c:otherwise>
										<input required type="radio" id="econtact-phone1" name="ecphone" checked value=1 > Home number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="econtact-phone2" name="ecphone" value=2> Cell number &nbsp; &nbsp; &nbsp;
									</c:otherwise>
								</c:choose>
							</div>
						</c:when>
						<c:otherwise>
							<div class="full-line smargin-below">
								<label class="required full-line" for="econtact-name">Full Name</label>
								<input type="text" name="econtact-name" id="econtact-name" value="${ec.name}" required />
							</div>

							<div class="full-line smargin-below">
								<label class="required full-line" for="econtact-relationship">Relationship to student</label>
								<input type="text" name="econtact-relationship" id="econtact-relationship" value="" required />
							</div>

							<div class="full-line smargin-below">
								<div>
									<label class="required full-line" for="econtact-phone">Phone Number</label>
									<input type="text" name="econtact-phone" id="econtact-phone" value="${ec.phone}" required />
								</div>
								<c:choose>
									<c:when test="${mf.ecPhoneCellOrHome}">
										<input required type="radio" id="econtact-phone1" name="ecphone" value=1 > Home number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="econtact-phone2" name="ecphone" checked value=2> Cell number &nbsp; &nbsp; &nbsp;
									</c:when>
									<c:otherwise>
										<input required type="radio" id="econtact-phone1" name="ecphone" checked value=1 > Home number &nbsp; &nbsp; &nbsp;
										<input required type="radio" id="econtact-phone2" name="ecphone" value=2> Cell number &nbsp; &nbsp; &nbsp;
									</c:otherwise>
								</c:choose>
							</div>
						</c:otherwise>
					</c:choose>

					<hr class="divider">

					<!-- Insurance Information -->
					<h3 class="full-line">Insurance Information</h3>

					<!-- Insurance Carrier Name -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="insurance-name">Name of Insurance Carrier</label>
						<input type="text" name="insurance-name" id="insurance-name" value="${mf.insuranceCarrier}" required />
					</div>

					<!-- Insurance Card Name -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="insurance-person">Name on Insurance Card</label>
						<input type="text" name="insurance-person" id="insurance-person" value="${mf.nameOfInsured}" required />
					</div>

					<!-- Policy Number -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="policy-number">Policy Number</label>
						<input type="text" name="policy-number" id="policy-number" value="${mf.policyNumber}" required />
					</div>

					<!-- Policy Phone Number -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="policy-phone">Policy Phone Number</label>
						<input type="text" name="policy-phone" id="policy-phone" value="${mf.policyPhone}" required />
					</div>

					<!-- Physician Name -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="physician">Name of Personal Physician</label>
						<input type="text" name="physician" id="physician" value="${mf.physicianName}" required />
					</div>

					<!-- Physician Phone Number -->
					<div class="full-line margin-below">
						<div>
							<label class="full-line" for="physician-phone">Phone Number of Personal Physician</label>
							<input type="text" name="physician-phone" id="physician-phone" value="${mf.physicianPhone}" />
						</div>
						<c:choose>
							<c:when test="${mf.physicianPhoneCellOrWork != null && mf.physicianPhoneCellOrWork }">
								<input required type="radio" id="physician-phone1" name="physphone" value=1 > Office number &nbsp; &nbsp; &nbsp;
								<input required type="radio" id="physician-phone2" name="physphone" checked value=2> Cell number &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test = "${mf.physicianPhoneCellOrWork != null }">
								<input required type="radio" id="physician-phone1" name="physphone" checked value=1 > Office number &nbsp; &nbsp; &nbsp;
								<input required type="radio" id="physician-phone2" name="physphone" value=2> Cell number &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="physician-phone1" name="physphone" checked value=1 > Office number &nbsp; &nbsp; &nbsp;
								<input required type="radio" id="physician-phone2" name="physphone" value=2> Cell number &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>
					</div>

					<!-- Insurance Card -->
					<div class="full-line smargin-below">
						<c:choose>
							<c:when test="${insurance != null && insurance.deleted == false}">
								<div class="insurancecard" id="insurancecard">
									Files uploaded on ${insurance.readableTimestamp}
									<a type="button" onclick="return deleteInsurance('${insurance.insuranceCardID}');" >[Delete]</a><br />
									<a name="oldFront" id="insurancehreffront" href="viewuploadedpicture?kind=insurance&side=front" target="_blank">Front Image of Insurance Card</a><br />
									<a name="oldBack" id="insurancehrefback" href="viewuploadedpicture?kind=insurance&side=back" target="_blank">Back Image of Insurance Card</a>
								</div>
							</c:when>
						</c:choose>
						<div class="margin-below">
							<label class="required full-line" for="front">Upload Front Image of Insurance Card</label>
							<input type="file" name="front" id="front" accept=".jpeg, .jpg, .jpe, .png, .pdf, .gif" />
						</div>

						<div class="margin-below">
							<label class="required full-line" for="back">Upload Back Image of Insurance Card</label>
							<input type="file" name="back" id="back" accept=".jpeg, .jpg, .jpe, .png, .pdf, .gif" />
						</div>
					</div>

					<hr class="divider">

					<!-- Other Medical Information -->
					<c:set var = "dontHaveMedForm" value="${mf.medicalFormID == -1}"/>
					<h3 class="full-line">General Medical Information</h3>

					<!-- Tetanus Shot -->
					<div class="full-line smargin-below">
						<label class="required full-line" for="tetanus">Has the student had a tetanus shot in the last 10 years?</label>
						<c:set var="tetVal" value="${mf.tetanusShot}"/>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="contactChoice1" name="tetanus" value="1"> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="contactChoice2" name="tetanus" value="2"> No &nbsp; &nbsp; &nbsp;
								<input type="radio" id="contactChoice3" name="tetanus" value="3"> Not sure &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${tetVal == null}">
										<input required type="radio" id="contactChoice1" name="tetanus" value=1> Yes &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice2" name="tetanus" value=2> No &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice3" name="tetanus" checked value=3> Not sure &nbsp; &nbsp; &nbsp;
									</c:when>
									<c:when test="${tetVal}">
										<input required type="radio" id="contactChoice1" name="tetanus" checked value=1> Yes &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice2" name="tetanus" value=2> No &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice3" name="tetanus" value=3> Not sure &nbsp; &nbsp; &nbsp;
									</c:when>
									<c:otherwise>
										<input required type="radio" id="contactChoice1" name="tetanus" value=1> Yes &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice2" name="tetanus" checked value=2> No &nbsp; &nbsp; &nbsp;
										<input type="radio" id="contactChoice3" name="tetanus" value=3> Not sure &nbsp; &nbsp; &nbsp;
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>

					<!-- Allergies -->
					<c:set var="allergies" value="${mf.allergies}"/>
					<c:set var="dontHaveAllergies" value="${(allergies == null) || (fn:length(allergies) == 0)}"/>
					<div class="full-line smargin-below">
						<label class="required full-line" for="allergy">Any allergies?</label>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="allergychoice1" name="allergyz" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="allergychoice2" name="allergyz" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test = "${dontHaveAllergies}">
								<input required type="radio" id="allergychoice1" name="allergyz" value=1>Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="allergychoice2" checked name="allergyz" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="allergychoice1" name="allergyz" checked value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="allergychoice2" name="allergyz" value=2> No &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>
						<div id="allergies" style="display: ${ dontHaveAllergies || dontHaveVisits ? 'none' : 'block'};">
							<% int i = 1; %>
							<c:if test="${!dontHaveAllergies}">
								<c:forEach items="${allergies}" var="aller">
									<div class="med-input">
										<label for="allergy<%=i%>"></label>
										<input type="text" placeholder="Allergy" name="allergy<%=i%>" value="${aller.allergy}" />
										<input type="text" placeholder="Reaction" value="${aller.reaction}" name="reaction<%=i%>" />
										<% i++; %>
									</div>
								</c:forEach>
							</c:if>
							<% if (i<5){
								for (int j = i; j<5; j++){ %>
							<div class="med-input">
								<label for="allergy<%=j%>"></label>
								<input type="text" placeholder="Allergy" name="allergy<%=j%>" />
								<input type="text" placeholder="Reaction" name="reaction<%=j%>" />
							</div>
							<% }
							} %>
						</div>
					</div>

					<!-- Prescribed Medications -->
					<c:set var="prescribed" value="${mf.prescribedMeds}"/>
					<c:set var="dontHaveP" value="${(prescribed == null) || (fn:length(prescribed) == 0)}"/>
					<div class="full-line smargin-below">
						<label class="required full-line" for="medication">Any prescribed medications?</label>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="medicationchoice1" name="medicationz" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2" name="medicationz" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test="${dontHaveP}">
								<input required type="radio" id="medicationchoice1" name="medicationz" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2" name="medicationz" checked value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="medicationchoice1" name="medicationz" checked value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2" name="medicationz" value=2> No &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>

						<div id="medications" style="display: ${ dontHaveMedForm || dontHaveP ? 'none' : 'block'};">
							<% i = 1; %>
							<c:if test="${!dontHaveP}">
								<c:forEach items="${prescribed}" var="p">
									<div class="med-input">
										<label for="medication-<%=i%>"></label>
										<input type="text" placeholder="Medication" name="medication<%=i%>" value="${p.med}" />
										<input type="text" placeholder="Dosage" value="${p.dose}" name="dosage<%=i%>" />
										<% i++; %>
									</div>
								</c:forEach>
							</c:if>
							<% if (i<5){
								for (int j = i; j<5; j++){ %>
							<div class="med-input">
								<label for="medication-<%=j%>"></label>
								<input type="text" placeholder="Medication" name="medication<%=j%>" />
								<input type="text" placeholder="Dosage" name="dosage<%=j%>" />
							</div>
							<% }
							} %>
						</div>
					</div>

					<!-- Non-Prescribed Medications -->
					<c:set var="nonp" value="${mf.nonPrescribedMeds}"/>
					<c:set var="dontHaveNp" value="${(nonp == null) || (fn:length(nonp) == 0)}"/>
					<div class="full-line smargin-below">
						<label class="required full-line" for="medicationnp">Any non-prescribed medications?</label>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="medicationchoice1np" name="medicationznp" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2np" name="medicationznp" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test="${dontHaveNp}">
								<input required type="radio" id="medicationchoice1np" name="medicationznp" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2np" name="medicationznp" checked value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="medicationchoice1np" name="medicationznp" checked value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="medicationchoice2np" name="medicationznp" value=2> No &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>

						<div id="medicationsnp" style="display: ${ dontHaveMedForm || dontHaveNp ? 'none' : 'block'};">
							<% i = 1; %>
							<c:if test="${!dontHaveNp}">
								<c:forEach items="${nonp}" var="np">
									<div class="med-input">
										<label for="medicationnp-<%=i%>"></label>
										<input type="text" placeholder="Medication" name="medicationnp<%=i%>" value="${np.med}" />
										<input type="text" placeholder="Dosage" value="${np.dose}" name="dosagenp<%=i%>" />
										<% i++; %>
									</div>
								</c:forEach>
							</c:if>
							<% if (i<5){
								for (int j = i; j<5; j++){ %>
							<div class="med-input">
								<label for="medicationnp-<%=j%>"></label>
								<input type="text" placeholder="Medication" name="medicationnp<%=j%>" />
								<input type="text" placeholder="Dosage" name="dosagenp<%=j%>" />
							</div>
							<% }
							} %>
						</div>
					</div>

					<!-- Injuries or Illnesses -->
					<c:set var="injuries" value="${mf.illnessesInjuries}"/>
					<c:set var="dontHaveInjuries" value="${(injuries == null) || (fn:length(injuries) == 0)}"/>
					<div class="full-line smargin-below">
						<label class="required full-line" for="injury">Any illnesses or injuries we should be aware of?</label>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="injurychoice1" name="injuriez" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="injurychoice2" name="injuriez" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test="${dontHaveInjuries}">
								<input required type="radio" id="injurychoice1" name="injuriez" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="injurychoice2" name="injuriez" checked value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="injurychoice1" name="injuriez" checked value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="injurychoice2" name="injuriez" value=2> No &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>

						<div id="injuries" style="display: ${ dontHaveMedForm || dontHaveInjuries ? 'none' : 'block'};">
							<% i = 1; %>
							<c:if test="${!dontHaveInjuries}">
								<c:forEach items="${injuries}" var="in">
									<div class="med-input">
										<label for="injury-<%=i%>"></label>
										<input type="text" placeholder="Injury" name="injury<%=i%>" value="${in}" />
										<% i++; %>
									</div>
								</c:forEach>
							</c:if>
							<% if (i<5){
								for (int j = i; j<5; j++){ %>
							<div class="med-input">
								<label for="injury-<%=j%>"></label>
								<input type="text"  placeholder="Injury" name="injury<%=j%>" />
							</div>
							<% }
							} %>
						</div>
					</div>

					<!-- Surgeries or Hospitalizations -->
					<c:set var="visits" value="${mf.hospitalVisits}"/>
					<c:set var="dontHaveVisits" value="${(visits == null) || (fn:length(visits) == 0)}"/>
					<div class="full-line smargin-below">
						<label class="required full-line" for="surgery">Any major surgeries or hospitalizations?</label>
						<c:choose>
							<c:when test="${dontHaveMedForm}">
								<input required type="radio" id="surgerychoice1" name="surgeriez" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="surgerychoice2" name="surgeriez" value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:when test="${dontHaveVisits}">
								<input required type="radio" id="surgerychoice1" name="surgeriez" value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="surgerychoice2" name="surgeriez" checked value=2> No &nbsp; &nbsp; &nbsp;
							</c:when>
							<c:otherwise>
								<input required type="radio" id="surgerychoice1" name="surgeriez" checked value=1> Yes &nbsp; &nbsp; &nbsp;
								<input type="radio" id="surgerychoice2" name="surgeriez" value=2> No &nbsp; &nbsp; &nbsp;
							</c:otherwise>
						</c:choose>

						<div id="surgeries" style="display: ${ dontHaveMedForm || dontHaveVisits ? 'none' : 'block'};">
							<% i = 1; %>
							<c:if test="${!dontHaveVisits}">
								<c:forEach items="${visits}" var="visit">
									<div class="med-input">
										<label for="year-<%=i%>"></label>
										<input type="text"  placeholder="Year" name="year<%=i%>" value="${visit.year}" />
										<input type="text" placeholder="Reason" name="reason<%=i%>" value="${visit.reason}" />
										<% i++; %>
									</div>
								</c:forEach>
							</c:if>
							<% if (i<5){
								for (int j = i; j<5; j++){ %>
							<div class="med-input">
								<label for="year-<%=j%>"></label>
								<input type="text"  placeholder="Year" name="year<%=j%>" />
								<input type="text" placeholder="Reason" name="reason<%=j%>" />
							</div>
							<% }
							} %>
						</div>
					</div>

					<!-- Form Submit Buttons -->
					<div class="submit-row">
						<button name="save-continue" id="save-continue" class="btn btn-primary" value="save-continue">Save &amp; Continue</button><br />
					</div>
				</form>

			</div>
		</div>
	</body>

	<script>
		function showError(error) {
			$('#errormsgdisplay').html(error);
			$('.message-popup').css("display","inline-block");
			$(".message-popup").slideDown().delay(7000).slideUp();
		}

		function deleteInsurance(id){
			$.post('${pageContext.request.contextPath}/deleteuploadedfile', {fileID : id, fileKind :'insurance'})
				.done(function(data) {
					// need to reload so that progress is updated
					location.reload();
					if (data !== "") {
						showError(data);
					}
					else {
						document.getElementById('insurancecard').remove();
					}
				})
				.fail(function(data) {
					showError("something went wrong!");
				});
		}

		$("#allergychoice1").change(function () {
			if ($('#allergychoice1').is(':checked')) {
				$('#allergies').slideDown();
			}
		});

		$("#allergychoice2").change(function () {
			if ($('#allergychoice2').is(':checked')) {
				$('#allergies').slideUp();
			}
		});

		$("#medicationchoice1").change(function () {
			if ($('#medicationchoice1').is(':checked')) {
				$('#medications').slideDown();
			}
		});

		$("#medicationchoice2").change(function () {
			if ($('#medicationchoice2').is(':checked')) {
				$('#medications').slideUp();
			}
		});

		$("#medicationchoice1np").change(function () {
			if ($('#medicationchoice1np').is(':checked')) {
				$('#medicationsnp').slideDown();
			}
		});

		$("#medicationchoice2np").change(function () {
			if ($('#medicationchoice2np').is(':checked')) {
				$('#medicationsnp').slideUp();
			}
		});

		$("#injurychoice1").change(function () {
			if ($('#injurychoice1').is(':checked')) {
				$('#injuries').slideDown();
			}
		});

		$("#injurychoice2").change(function () {
			if ($('#injurychoice2').is(':checked')) {
				$('#injuries').slideUp();
			}
		});

		$("#surgerychoice1").change(function () {
			if ($('#surgerychoice1').is(':checked')) {
				$('#surgeries').slideDown();
			}
		});

		$("#surgerychoice2").change(function () {
			if ($('#surgerychoice2').is(':checked')) {
				$('#surgeries').slideUp();
			}
		});

		function displayError(errorMsg) {
			$('#errormsgdisplay').html(errorMsg);
			$('.message-popup').css("display","inline-block");
			$(".message-popup").slideDown().delay(7000).slideUp();
		}

		function validateForm(form, e, haveInsurance) {
			var cancel = form.submitted;
			if((cancel == null) || (cancel !== 'Cancel')) {
				e.preventDefault();
				if(validateFormFields(form, haveInsurance)) {
					form.submit();
					return true;
				}
				return false;
			}
			else {
				form.legalagree.checked=false;
			}
			return true;
		}

		function validateFormFields(form, haveInsurance) {
			// start with the allergies
			var errormessage = "";
			if ($("#allergychoice1").is(':checked')) {
				var x1 = form.allergy1.value;
				var y1 = form.reaction1.value;
				var x2 = form.allergy2.value;
				var y2 = form.reaction2.value;
				var x3 = form.allergy3.value;
				var y3 = form.reaction3.value;
				var x4 = form.allergy4.value;
				var y4 = form.reaction4.value;

				if  ((x1 === "" || y1 === "") && (x2 === "" || y2 === "") &&
						(x3 === "" || y3 === "") && (x4 === "" || y4 === ""))  {
					errormessage += "You checked yes for allergies, however you did not list any allergies of your child. Please make sure to fill out allergies. <br><br>";
				}
			}

			if ($("#medicationchoice1").is(':checked')) {
				var x1 = form.medication1.value;
				var y1 = form.dosage1.value;
				var x2 = form.medication2.value;
				var y2 = form.dosage2.value;
				var x3 = form.medication3.value;
				var y3 = form.dosage3.value;
				var x4 = form.medication4.value;
				var y4 = form.dosage4.value;

				if ((x1 === "" || y1 === "") && (x2 === "" || y2 === "") &&
						(x3 === "" || y3 === "") && (x4 === "" || y4 === "")) {
					errormessage += "You checked yes for prescribed medications, however you did not list any medications of your child. Please make sure to fill out prescribed medications.<br><br>";
				}
			}

			if ($("#medicationchoice1np").is(':checked')) {
				var x1 = form.medicationnp1.value;
				var y1 = form.dosagenp1.value;
				var x2 = form.medicationnp2.value;
				var y2 = form.dosagenp2.value;
				var x3 = form.medicationnp3.value;
				var y3 = form.dosagenp3.value;
				var x4 = form.medicationnp4.value;
				var y4 = form.dosagenp4.value;

				if ((x1 === "" || y1 === "") && (x2 === "" || y2 === "") &&
						(x3 === "" || y3 === "") && (x4 === "" || y4 === "")){
					errormessage += "You checked yes for non-prescribed medications, however you did not list any medications of your child. Please make sure to fill out non-prescribed medications.<br><br>";
				}
			}

			///////////////////FIX THIS FOR INJURIES
			if ($("#injurychoice1").is(':checked')) {
				var x1 = form.injury1.value;
				var x2 = form.injury2.value;
				var x3 = form.injury3.value;
				var x4 = form.injury4.value;
				if ((x1 === "") && (x2 === "") && (x3 === "") && (x4 === "")){
					errormessage += "You checked yes for illnesses and injuries, however you did not list any for your child. Please make sure to fill out any injuries or illnesses. <br><br>";
				}
			}

			if ($("#surgerychoice1").is(':checked')) {
				var x1 = form.year1.value;
				var y1 = form.reason1.value;
				var x2 = form.year2.value;
				var y2 = form.reason2.value;
				var x3 = form.year3.value;
				var y3 = form.reason3.value;
				var x4 = form.year4.value;
				var y4 = form.reason4.value;

				if ((x1 === "" || y1 === "") && (x2 === "" || y2 === "") &&
						(x3 === "" || y3 === "") && (x4 === "" || y4 === "")){
					errormessage += "You checked yes for surgeries or hospital visits, however you did not list any for your child. Please make sure to fill out any surgeries or hospital visits. <br>";
				}
			}
			var insuranceCard = document.getElementById('insurancecard');
			if ((form.front.value === "" || form.back.value === "") && ((!haveInsurance) || (insuranceCard == null ) || (insuranceCard.value === ""))) {
				errormessage += "Please upload images of your insurance card <br>";
			}

			if (errormessage !== ""){
				displayError(errormessage);
				return false;
			}
			return true;
		}
	</script>
</html>