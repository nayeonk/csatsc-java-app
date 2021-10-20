<%@ page language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<jsp:include page="WEB-INF/includes/header.jsp"/>
<script src="scripts/register.js"></script>
<div id="container" class="container">
    <div class="inner col-xs-12">
        <div class="">
            <div class="">
                <div id="page-title">
                    <h1 style="text-align: center;">My Account</h1>
                </div><!-- Ends: #PAGE-TITLE -->
                <div class="contentDetail">
                    <div class="main row">
                        <div class="contentDetail">

                            <br/>
                            <!-- <script>
                                            //$(".message-popup").delay(6000).fadeOut(4000, function() {});
                                $(".message-popup-login").slideDown().delay(7000).slideUp();
                                $(".message-popup-register").slideDown().delay(7000).slideUp();
                            </script> -->
                            <div id="loginForm" class="col-md-6 col-sm-12">
                                <div class="message-popup-login"
                                     style="display:none; text-align:center;z-index:999999999;">
                                    <div class="error-message"
                                         style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
                                        <p class="message-text-error" id="errormsgdisplaylogin"
                                           style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
                                    </div>

                                    <br/>
                                    <br/>
                                </div>
                                <div class="form-style-2">
                                    <div class="form-style-2-heading">Log in to an existing account</div>
                                    <form action="/SummerCamp/login" method="POST" name="emailform"
                                          onSubmit="return validateLogin(event, this);">
                                        <label for="email"><span>Email <span class="required">*</span></span>
                                            <input type="text" class="input-field" name="email"></label>
                                        <label for="password"><span>Password <span class="required">*</span></span>
                                            <input type="password" class="input-field" name="password"></label>
                                        <label><span>&nbsp;</span><input class="form-control" type="submit"
                                                                         value="Log In"/></label>
                                    </form>

                                    <a href="${pageContext.request.contextPath}/forgotpassword">Forgot Password?</a>
                                </div>
                            </div>

                            <div id="registerForm" class="col-md-6 col-sm-12">
                                <div class="message-popup-register"
                                     style="display:none; text-align:center;z-index:999999999;">
                                    <div class="error-message"
                                         style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
                                        <p class="message-text-error" id="errormsgdisplayreg"
                                           style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;"></p>
                                    </div>

                                    <br/>
                                    <br/>
                                </div>
                                <div class="form-style-2">
                                    <div class="form-style-2-heading">Create a new account</div>
                                    <!-- CC: Had to change redirect here. -->
                                    <form action="/SummerCamp/createaccount" method="POST" name="emailform"
                                          onSubmit="return validateCreateAccount(event, this);">
                                        <label for="email"><span>Email <span class="required">*</span></span>
                                            <input type="text" class="input-field" name="email"></label>

                                        <label for="email2"><span>Reenter Email <span class="required">*</span></span>
                                            <input type="text" class="input-field" name="email2"></label>


                                        <label for="password"><span>Password <span class="required">*</span></span>
                                            <input type="password" class="input-field" name="password"></label>

                                        <label for="password2"><span>Reenter Password <span
                                                class="required">*</span></span>
                                            <input type="password" class="input-field" name="password2"></label>

                                        <label for="uscemployee"><span>
                                            <input type="checkbox" name="uscemployee" value="USC Employee"
                                                   onclick="showIDEntry(this)"> I am an employee of USC. </span></label>

                                        <label for="uscid"><span id="uscidspan" style="display: none;">USC Student/Employee ID <span
                                                class="required">*</span></span>
                                            <input type="text" class="input-field" name="uscid" id="uscid"
                                                   style="display: none;"></label>

                                        <label><span>&nbsp;</span><input class="form-control" type="submit"
                                                                         value="Sign Up"/></label>
                                        <div><i>Special consideration given for our USC family members so if you are an
                                            employee of USC, please register with your given USC email.</i></div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- Ends: .LAYOUT-1COL-WIDE -->
                <br class="clear"/>
            </div> <!-- Ends: .COL COL1 -->
        </div><!-- Ends: .LAYOUT-1COL CLEARFIX -->
    </div> <!-- Ends: .INNER -->
</div>
<!-- Ends: #CONTAINER -->


<jsp:include page="WEB-INF/includes/footer.jsp"/>

