<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="parent" value="${parent}"/>
<c:set var="ec" value="${emergency_contact}"/>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<!DOCTYPE html>
<html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CS@SC</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<!-- Stylesheets -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
	<link rel="stylesheet" href="/SummerCamp/css/general.css">
	<link rel="stylesheet" href="/SummerCamp/css/parentNavbar.css">
	<link rel="stylesheet" href="/SummerCamp/css/parentRegistration.css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.21/js/jquery.dataTables.min.js" integrity="sha512-BkpSL20WETFylMrcirBahHfSnY++H2O1W+UnEEO4yNIl+jI2+zowyoGJpbtk6bx97fBXf++WJHSSK2MV4ghPcg==" crossorigin="anonymous"></script>
	<script src="/SummerCamp/scripts/ecform.js"></script>
	<script src="/SummerCamp/scripts/parentform.js"></script>
</head>
<%--<body onload="checkError(${errorMessage})">--%>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
	<!-- Static red sidebar -->

	<c:choose>
		<c:when test="${parentProfileComplete == true}">
			<jsp:include page="../includes/parentNavbar.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:include page="../includes/parentRegistrationNavbar.jsp"/>
		</c:otherwise>
	</c:choose>

	<!-- Main page content -->
	<div id="main">
		<form onSubmit = "return validateForm(event, this);" name="parentform" method="POST" action="/SummerCamp/createparent">
			<!-- Title -->
			<h1>Parent Account Registration</h1>
			<p>Complete filling out all required information on this page in order to register students for camp.
				<br>Note: Changes you make on this page will not be saved until you click <i>Submit</i>.
			</p>

			<c:if test="${!empty errorMessage}">
				<div class="message-popup" id="message-popup" style="text-align:left;z-index:999999999;">
					<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
						<p class="message-text-error" id="message-text-error" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;">${errorMessage}</p>
					</div>
				</div>
			</c:if>

			<!-- Parent 1 Information -->
			<h3>Parent or Legal Guardian Information</h3>

			<div class="message-popupparent" style="display:none; text-align:center;z-index:999999999;">
				<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
					<p id="errormsgdisplayparent" class="message-text-error" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
				</div><br /><br />
			</div>

			<div class="customRow name-row margin-below">
				<div class="first-name">
					<label for="fname" class="required">First Name</label>
					<input type="text" class="input-field" id="fname" name="fname" value="${parent.firstName}" />
				</div>
				<div class="last-name">
					<label for="lname" class="required">Last Name</label>
					<input type="text" class="input-field" id="lname" name="lname" value="${parent.lastName}" />
				</div>
			</div>

			<div class="margin-below phone">
				<label for="phone" class="required full-line">Phone</label>
				<input type="text" class="input-field" name="phone" id="phone" value="${parent.phone}" />
			</div>

			<div class="margin-below country">
				<label for="country" class="required full-line">Country</label>
				<select name="country" id="country" class="form-control" list="countrylist" onchange="checkcountry()">
					<option value="" selected>- Select Country -</option>
					<c:forEach items="${countryArray}" var="country">
						<c:choose>
							<c:when test="${country == parent.address.country}">
								<option value="${country}" selected>${country}</option>
							</c:when>
							<c:otherwise>
								<option value="${country}">${country}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>

			<div class="customRow margin-below street">
				<label for="street" class="required full-line">Street Address</label>
				<input type="text" class="input-field full-line" name="street" id="street" value="${parent.address.street}" />
			</div>

			<div class="customRow name-row margin-below">
				<div class="city">
					<label for="city" class="required">City</label>
					<input type="text" class="input-field" name="city" id="city" value="${parent.address.city}" />
				</div>

				<c:choose>
					<c:when test="${parent.address.country == 'United States'}">
						<div class="state" id="pstate">
							<label for="state" class="required">State</label>
							<select name="state" id="state" class="form-control" list="statelist">
								<option value="" selected>- Select State -</option>
								<c:forEach items="${stateArray}" var="state">
									<c:choose>
										<c:when test="${state.stateID == parent.address.stateID}">
											<option value="${state.stateID}" selected>${state.state}</option>
										</c:when>
										<c:otherwise>
											<option value="${state.stateID}">${state.state}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</c:when>
					<c:otherwise>
						<div class="state" id="pstate" style="display:none;">
							<label for="state" class="required">State</label>
							<select name="state" id="state" class="form-control" list="statelist" disabled>
								<option value="" selected>- Select State -</option>
								<c:forEach items="${stateArray}" var="state">
									<c:choose>
										<c:when test="${state.stateID == parent.address.stateID}">
											<option value="${state.stateID}" selected>${state.state}</option>
										</c:when>
										<c:otherwise>
											<option value="${state.stateID}">${state.state}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
					</c:otherwise>
				</c:choose>

				<div class="zip">
					<label for="zip" class="required">Zip</label>
					<input type="text" class="input-field" name="zip" id="zip" value="${parent.address.zip}" />
				</div>
			</div>

			<button type="button" class="btn btn-secondary" id="addparent" onclick="addParent()">
				<c:choose>
					<c:when test="${ec == null}">
						+ Add Parent
					</c:when>
					<c:otherwise>
						- Remove Parent
					</c:otherwise>
				</c:choose>
			</button>
			<br />
			<br />


			<!-- Parent 2 Information -->
			<div id="ecparent" style="${ec == null ? 'display: none' : 'display: inline-block'}">
				<h3>Parent or Legal Guardian 2 Information</h3>

				<div class="message-popupec" style="display:none; text-align:center;z-index:999999999;">
					<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
						<p id="errormsgdisplayec" class="message-text-error" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
					</div><br /><br />
				</div>
				<div id="useP1addr" name="useP1addr">
					<label for="useP1">
						<input type="checkbox" name="useP1" id="useP1" value="Yes" onclick="copyParent1()"/> Use the same information as the first parent.
					</label>
				</div>

				<div class="customRow name-row margin-below">
					<div class="first-name">
						<label for="e_fname" class="required">First Name</label>
						<input type="text" class="input-field" id="e_fname" name="e_fname" value="${ec.firstName}" />
					</div>
					<div class="last-name">
						<label for="e_lname" class="required">Last Name</label>
						<input type="text" class="input-field" id="e_lname" name="e_lname" value="${ec.lastName}" />
					</div>
				</div>

				<div class="margin-below phone">
					<label for="e_phone" class="required full-line">Phone</label>
					<input type="text" class="input-field" name="e_phone" id="e_phone" value="${ec.phone}" />
				</div>

				<div class="margin-below country">
					<label for="e_country" class="required full-line">Country</label>
					<select name="e_country" id="e_country" class="form-control" list="countrylist" onchange="check_e_country()">
						<option value="" selected>- Select Country -</option>
						<c:forEach items="${countryArray}" var="country">
							<c:choose>
								<c:when test="${country == ec.address.country}">
									<option value="${country}" selected>${country}</option>
								</c:when>
								<c:otherwise>
									<option value="${country}">${country}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>

				<div class="customRow margin-below street">
					<label for="e_street" class="required full-line">Street Address</label>
					<input type="text" class="input-field full-line" name="e_street" id="e_street" value="${ec.address.street}" />
				</div>

				<div class="customRow name-row margin-below">
					<div class="city">
						<label for="e_city" class="required">City</label>
						<input type="text" class="input-field" name="e_city" id="e_city" value="${ec.address.city}" />
					</div>

					<c:choose>
						<c:when test="${ec.address.country == 'United States'}">
							<div class="state" id="estate">
								<label for="e_state" class="required">State</label>
								<select name="e_state" id="e_state" class="form-control" list="statelist">
									<option value="" selected>- Select State -</option>
									<c:forEach items="${stateArray}" var="state">
										<c:choose>
											<c:when test="${state.stateID == ec.address.stateID}">
												<option value="${state.stateID}" selected>${state.state}</option>
											</c:when>
											<c:otherwise>
												<option value="${state.stateID}">${state.state}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</c:when>
						<c:otherwise>
							<div class="state" id="estate" style="display:none;">
								<label for="e_state" class="required">State</label>
								<select name="e_state" id="e_state" class="form-control" list="statelist" disabled>
									<option value="" selected>- Select State -</option>
									<c:forEach items="${stateArray}" var="state">
										<c:choose>
											<c:when test="${state.stateID == ec.address.stateID}">
												<option value="${state.stateID}" selected>${state.state}</option>
											</c:when>
											<c:otherwise>
												<option value="${state.stateID}">${state.state}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</c:otherwise>
					</c:choose>

					<div class="zip">
						<label for="e_zip" class="required">Zip</label>
						<input type="text" class="input-field" name="e_zip" id="e_zip" value="${ec.address.zip}" />
					</div>
				</div>
				<div class="margin-below email">
					<label for="e_email" class="full-line">Email</label>
					<input type="text" class="input-field" name="e_email" id="e_email" value="${ec.email}"/>
				</div>
			</div>

			<hr />

			<!-- Financial Information -->
			<h3>Financial Information</h3>
			<small>We request this information for reporting purposes. This information will not be shared.</small>
			<div>
				<label for="income" class="required">Estimated Annual Household Income</label>
				<br />
				<select name="income" id="income" class="form-control" list="incomelist">
					<option value="" selected>- Select Income -</option>
					<c:forEach items="${incomeArray}" var="income">
						<c:choose>
							<c:when test="${income.incomeID == parent.incomeID}">
								<option value="${income.incomeID}" selected>${income.income}<br></option>
							</c:when>
							<c:otherwise>
								<option value="${income.incomeID}">${income.income}<br> </option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>

			<c:if test="${parentProfileComplete == true}">
				<hr />

				<h3>Update Parent Password</h3>
				<label for="pemailID" style="display:none;">
					<span>Parent Email ID <span class="required">*</span></span>
					<input type="text" class="input-field" name="pemailID" id="pemailID" value="${parent.emailID}" readonly/>
				</label>
				<div class="margin-below password">
					<label for="password" class="full-line">Enter New Password</label>
					<input type="password" class="input-field" name="password" id="password" value=""/>
				</div>
				<div class="margin-below password">
					<label for="password2" class="full-line">Reenter New Password</label>
					<input type="password" class="input-field" name="password2" id="password2" value=""/>
				</div>
			</c:if>

			<br />

			<!-- Form Submit Buttons -->
			<c:choose>
				<c:when test="${parentProfileComplete == true}">
					<input class="btn btn-primary" type="Submit" name="Submit" value="Save Changes" />
				</c:when>
				<c:otherwise>
					<input class="btn btn-primary" type="Submit" name="Submit" value="Submit" />
				</c:otherwise>
			</c:choose>

			<script>
				function checkcountry() {
					var x = document.getElementById("country").value;
					if (x == "United States") {
						document.getElementById("state").disabled = false;
						document.getElementById("pstate").style.display = "inline-block";
					} else {
						document.getElementById("state").disabled = true;
						document.getElementById("pstate").style.display = "none";
					}
				}
				function check_e_country() {
					var x = document.getElementById("e_country").value;
					if (x == "United States") {
						document.getElementById("e_state").disabled = false;
						document.getElementById("estate").style.display = "inline-block";
					} else {
						document.getElementById("e_state").disabled = true;
						document.getElementById("estate").style.display = "none";
					}
				}
				function copyParent1() {
					var useP1 = document.getElementById("useP1").checked;
					if (useP1) {
						document.getElementById("e_phone").value = document.getElementById("phone").value;
						document.getElementById("e_country").value = document.getElementById("country").value;
						document.getElementById("e_street").value = document.getElementById("street").value;
						document.getElementById("e_city").value = document.getElementById("city").value;
						document.getElementById("e_state").value = document.getElementById("state").value;
						document.getElementById("e_zip").value = document.getElementById("zip").value;
					} else {
						document.getElementById("e_phone").value = "";
						document.getElementById("e_country").value = document.getElementById("city").value;
						document.getElementById("e_street").value = "";
						document.getElementById("e_city").value = "";
						document.getElementById("e_state").value = "";
						document.getElementById("e_zip").value = "";
					}
				}
				function addParent() {
					var x = document.getElementById("ecparent");
					var y = document.getElementById("addparent");
					if (x.style.display === "none") {
						x.style.display = "block";
						y.innerHTML = " - Remove Parent";
					} else {
						// Clear parent 2 data
						var inputs = x.getElementsByTagName("input");
						for (var i = 0; i < inputs.length; i++) {
							if (inputs[i].type === "text") {
								inputs[i].value = "";
							}
							else if (inputs[i].type === "checkbox") {
								inputs[i].checked = false;
							}
						}
						x.style.display = "none";
						y.innerHTML = "+ Add Parent";
					}
				}
				function validateForm(e, form){
					e.preventDefault();
					// only validate ec if it's visible
					ecForm = document.getElementById("ecparent");
					let ecVal = ecForm.style.display === "none" ? true : validateECFormFields(form);
					let pVal = validatePFormFields(form);
					if(!ecVal || !pVal) {
						return false;
					}
					else {
						form.submit();
						return true;
					}
				}
			</script>
		</form>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
</html>