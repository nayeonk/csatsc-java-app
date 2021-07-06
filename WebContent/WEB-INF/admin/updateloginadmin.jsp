<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
    <div id="container" class="container">

		<div class="admin_control_panel">
			<div class="admin_control_panel_header">
				<br/><p style="text-align:center;">${errorMessage}</p>
			</div>
			<div class="form-style-2">
				<div style="width:300px;">
					<form name="password-form" method="POST" id="password-form" action="updateloginadmin">
				   		<label for="password"><span>New Password <span class="required">*</span></span><input type="password" class="input-field" name="password" required></label>
				   		<label for="password2"><span>Confirm New Password <span class="required">*</span></span><input type="password" class="input-field" name="password2" required></label>
				   		<input type="Submit" name="Submit" value="Submit" />
			   		</form>
		   		</div>
			</div>
		</div>

		<br class="clear"/>
		<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->
</t:adminpage>
