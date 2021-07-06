<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<jsp:include page="../includes/header.jsp"/>
<script src="scripts/password.js"></script>
<div id="container" class="container">
	<div class="inner" style="display:block; margin-left:auto; margin-right:auto;">
		<div class="layout-1col clearfix" style="width:100%;">
			<div class="col col1">
				<div id="page-title">
					<h1 style="text-align: center;">Reset Password</h1>
				</div><!-- Ends: #PAGE-TITLE -->

				<div class="inner-content-be" style="width:880px; display:block; margin-left:auto; margin-right:auto;">
					<div class="contentDetail layout-1col-wide clearfix">
						<div class="col main">
							<div class="contentDetail">
							
								<div style="margin-top:30px;"></div>
								<%-- <c:set var="error" value="${errorMessage}" />
								<c:if test="${fn:length(error) != 0}"> --%>
									<div class="message-popup" style="display:none; text-align:center;">
										<div class="error-message" style="width:400px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:15px 5px 5px; margin-top:20px; display:inline-block;">
											<p style="text-align:center; color:#ff0000; font-weight:bold; font-size:11pt;">											
												Password must be at least 6 characters and have at least one number. Please reenter a new password. <br/>
											</p>
										</div><br /><br />
									</div>
									<%-- <script>
										$(".message-popup").slideDown().delay(7000).slideUp();
									</script>
								</c:if> --%>
								<div class="form-style-2" style="margin-bottom:150px;">
									<div style="width:400px;">
										<div class="form-style-2-heading">Password Reset</div>
										<form id="new-pass-form" onSubmit = "return validateNewPass(event, this);" name="passwordform">
											<label for="password">New Password<input type="password" class="input-field" name="password" value="" /></label>
											<label for="password2">Confirm Password<input type="password" class="input-field" name="password2" value="" /></label>
											<label><span>&nbsp;</span><input class="form-control" type="submit" value="Save New Password" /></label>
										</form>	
										<form id = "success-form" style="display: none;"  action="/SummerCamp/logout" >
											<p>Your new password has been saved.</p><br>
											<label><span>&nbsp;</span><input class="form-control" type="submit" value="Log In" /></label>
										</form>						
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- Ends: .LAYOUT-1COL-WIDE -->
				<br class="clear"/>
			</div> <!-- Ends: .COL COL1 -->
		</div><!-- Ends: .LAYOUT-1COL CLEARFIX -->
	</div> <!-- Ends: .INNER -->
</div><!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>
