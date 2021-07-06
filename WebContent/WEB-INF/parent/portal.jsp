<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CS@SC</title>

	<!-- Stylesheets -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
	<link rel="stylesheet" href="../../css/general.css">
	<link rel="stylesheet" href="../../css/parentNavbar.css">
	<link rel="stylesheet" href="../../css/portal.css">


	<link href="https://fonts.googleapis.com/css2?family=DM+Sans&display=swap" rel="stylesheet">
	<link href = "https://fonts.googleapis.com/icon?family=Material+Icons" rel = "stylesheet">

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- JavaScript files -->
	<script src="scripts/camper-profile.js"></script>
</head>
<body>
	<div id="page">
		<jsp:include page="../includes/parentNavbar.jsp"/>
		<div id="main">
			<!-- Logout Button -->
			<button class="btn btn-danger d-none d-md-block" id="logout">Logout</button><br/>

			<!-- Title -->
			<h1>Account Registration</h1>

			<!-- Content -->
			<p class="step">
				<span class="circle"></span>
				<a href="parent/parentRegistration.jsp">Complete Parent Profile</a>
			</p>
			<p class="step">
				<span class="circle"></span>
				Register Camper
			</p>
			<p class="step">
				<span class="circle"></span>
				Make Payment
			</p>

			<div class="card">
				<div class="card-body">
					<h3 class="font-weight-bold">Need Help?</h3>
					Use the navigation on the left panel to complete your application.
					If you have any questions or concerns, please reach out to cscamps@usc.edu.
				</div>
			</div>

		</div>
	</div>
</body>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</html>