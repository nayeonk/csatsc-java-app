<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
    <div id="container" class="container">
		<div class="back-button-container">
			<a href="editstaff"> < Back </a>
		</div>

		<div class="admin_control_panel">
			<div class="admin_control_panel_header">
				<h4 style="text-align:center;">Add Staff Member<br/></h4>
				<br/><p style="text-align:center;">${errorMessage}</p>
			</div>
			<div class="form-style-2">
				<div style="width:300px;">
					<form name="staff-form" method="POST" id="staff-form" action="addstaff" enctype="multipart/form-data">
				        <label for="first-name"><span>First Name <span class="required">*</span></span><input type="text" class="input-field" name="first-name" required></label>
				        <label for="last-name"><span>Last Name <span class="required">*</span></span><input type="text" class="input-field" name="last-name" required></label>
				        <label for="email-address"><span>Email Address<span class="required">*</span></span><input type="text" class="input-field" name="email-address" required></label>
				        <label for="title"><span>Title <span class="required">*</span></span><input type="text" class="input-field" name="title" required></label>
				        <label for="company"><span>Company <span class="required">*</span></span><input type="text" class="input-field" name="company" required></label>
				   		<label for="description"><span>Description <span class="required">*</span></span><textarea name="description" class="textarea-field" form="staff-form" required></textarea></label>
				   		<label for="file"><span  style="float:none;">Photo <span class="required">*</span></span><input type="file" name="file"></label>
				   		<label for="type"><span>Type <span class="required">*</span></span><input type="checkbox" name="instructor" value="instructor"> Instructor <input type="checkbox" name="admin" value="admin" id="admin" > Admin </label>
				   		<div id="password-container">
				   			<label for="password"><span>Password <span class="required">*</span></span><input type="password" class="input-field" name="password" required></label>
				   			<label for="confirm-password"><span>Confirm Password <span class="required">*</span></span><input type="password" class="input-field" name="confirm-password" required></label>
				   		</div>
				   		<input type="Submit" name="Submit" value="Submit" />
			   		</form>
		   		</div>
			</div>
		</div>

		<br class="clear"/>
		<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->
</t:adminpage>
