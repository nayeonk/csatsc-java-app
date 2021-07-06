<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

		<div class="instructor-control-panel">
			<div class="instructor-control-panel-header">
				<h4 style="text-align:center;">Instructor Control Panel<br/></h4>
				<br/><p style="text-align:center;">${errorMessage}</p>
			</div>
			<div class="link-container">
			    <a class="add-link" href="instructorattendance.jsp"> Attendance </a>
				<a class="add-link" href="updateloginadmin"> Change Login </a>
				<a class="logout-link" href="logout"> Logout </a>
			</div>
			<br>

			<br>
			<div class="admin-control-panel-body">

			</div>
		</div>

		<br class="clear"/>
		<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript">

    function formSubmit() {
        $("#contentValue1").val($("#paraContent").text());
        var form = document.getElementById("form1");
        var str = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.action = "/sendEmail.email.send.html?contentValue=" + $(paraContent);
        form.submit();
    }
</script>
