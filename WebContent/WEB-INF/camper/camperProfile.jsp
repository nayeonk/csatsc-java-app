<%@ page language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CS@SC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Stylesheets -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/general.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/studentNavbar.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/camperProfile.css"/>

    <!-- JavaScript files -->
    <script src="/SummerCamp/scripts/camperProfile.js"></script>
    <script src="/SummerCamp/scripts/camperform.js"></script>
</head>
<body>

<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <!-- Static yellow sidebar -->
    <jsp:include page="../includes/studentNavbar.jsp"/>


    <!-- Main page content -->
    <div id="main">
        <form onSubmit="return validateForm(event, this);" name="camperprofile" method="POST"
              action="${pageContext.request.contextPath}/createstudent">

            <!-- Title -->
            <h1>Camper Profile</h1>
            <h4>${student.firstName} ${student.lastName}</h4>
            <p class="changes-note">Note: Changes you make on this page will be reflected across all submitted camp
                applications.
                <br>Your changes will not be saved until you click <i>Save & Continue</i>.
            </p>
            <c:choose>
                <c:when test="${errorMessage == null ||  errorMessage.isBlank()}">
                    <div class="message-popup" style="display:none;text-align:center;z-index:999999999;">
                </c:when>
                <c:otherwise>
                    <div class="message-popup" style="text-align:center;z-index:999999999;">
                    </c:otherwise>
            </c:choose>
                <div class="error-message"
                     style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px;">
                    <p id="errormsgdisplay" class="message-text-error"
                       style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;">
                        <c:out value="${errorMessage}" escapeXml="false"/>
                    </p>
                </div>
                <br/><br/>
            </div>

            <!-- Student Information -->
            <h3>Basic Information</h3>
            <div class="customRow name-row margin-below">
                <div class="first-name">
                    <label for="s_fname" class="required">First Name</label>
                    <input type="text" id="s_fname" name="s_fname" value="${student.firstName}"/>
                </div>

                <div class="middle-name">
                    <label for="s_mname">Middle Name</label>
                    <input type="text" id="s_mname" name="s_mname" value="${student.middleName}"/>
                </div>

                <div class="last-name">
                    <label for="s_lname" class="required">Last Name</label>
                    <input type="text" id="s_lname" name="s_lname" value="${student.lastName}"/>
                </div>
            </div>

            <div class="margin-below preferred-name">
                <label for="s_pname" class="full-line">Preferred Name</label>
                <input type="text" id="s_pname" name="s_pname" value="${student.preferredName}"/>
            </div>


            <label for="gender" class="required">Gender</label>
            <div class="customRow margin-below">
                <c:forEach items="${genderArray}" var="gender">
                    <div class="center-vertical">
                        <c:choose>
                            <c:when test="${gender.genderID == student.genderID}">
                                <input type="radio" name="gender" id="gender-${gender.genderID}-${student.studentID}"
                                       value="${gender.genderID}" checked/> ${gender.gender} &nbsp; &nbsp; &nbsp;
                            </c:when>
                            <c:otherwise>
                                <input type="radio" name="gender" id="gender-${gender.genderID}-${student.studentID}"
                                       value="${gender.genderID}"/> ${gender.gender} &nbsp; &nbsp; &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>

            <div class="margin-below">
                <div class="customRow">
                    <label for="birthdate" class="required">Date of Birth</label>
                </div>

                <div class="customRow">
                    <input type="date" id="birthdate" name="birthdate" value="${student.dob}"/>
                </div>
            </div>

            <div class="margin-below">
                <div class="customRow">
                    <label for="ethnicity" class="required">Ethnicity</label>
                </div>

                <div id="ethnicity customRow margin-below">
                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isAfricanAmerican == true}">
                                <input type="checkbox" id="african-american" name="ethnicity" value="african-american"
                                       onclick="toggleOther(0)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="african-american" name="ethnicity" value="african-american"
                                       onclick="toggleOther(0)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="african-american">African-American</label>
                    </div>

                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isAmericanIndian}">
                                <input type="checkbox" id="american-indian" name="ethnicity" value="american-indian"
                                       onclick="toggleOther(0)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="american-indian" name="ethnicity" value="american-indian"
                                       onclick="toggleOther(0)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="american-indian">American-Indian</label>
                    </div>

                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isAsian}">
                                <input type="checkbox" id="asian" name="ethnicity" value="asian"
                                       onclick="toggleOther(0)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="asian" name="ethnicity" value="asian"
                                       onclick="toggleOther(0)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="asian">Asian</label>
                    </div>


                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isCaucasian}">
                                <input type="checkbox" id="caucasian" name="ethnicity" value="caucasian"
                                       onclick="toggleOther(0)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="caucasian" name="ethnicity" value="caucasian"
                                       onclick="toggleOther(0)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="caucasian">Caucasian</label>
                    </div>

                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isHispanic}">
                                <input type="checkbox" id="hispanic" name="ethnicity" value="hispanic"
                                       onclick="toggleOther(0)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="hispanic" name="ethnicity" value="hispanic"
                                       onclick="toggleOther(0)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="hispanic">Hispanic</label>
                    </div>

                    <div class="ethnicity-entry">
                        <c:choose>
                            <c:when test="${student.isOther}">
                                <input type="checkbox" id="other-ethnicity" name="ethnicity" value="other"
                                       onclick="toggleOther(1)" checked/>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" id="other-ethnicity" name="ethnicity" value="other"
                                       onclick="toggleOther(1)"/>
                            </c:otherwise>
                        </c:choose>
                        <label for="other-ethnicity">Other:</label>
                        <c:forEach items="${studentEthnicities}" var="studentEthnicity">
                            <c:if test="${studentEthnicity.valid}">
                                <c:if test="${studentEthnicity.ethnicityID == 6}">
                                    <c:if test="${studentEthnicity.studentID == student.studentID}">
                                        <c:set var="otherEthnicity" value="${studentEthnicity.otherEthnicity}"/>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        <input type="text" class="input-field" id="otherEthnicity" name="otherEthnicity"
                               value="${otherEthnicity}"/></label>
                    </div>
                </div>
            </div>

            <div class="customRow margin-below">
                <label for="emailAddress" class="full-line">Email Address</label>
                <small class="full-line">If entered, the student will receive CS@SC emails.</small>
                <input type="text" id="emailAddress" name="emailAddress" value="${student.emailAddress}"/>
            </div>


            <div class="customRow margin-below">
                <label for="school-dropdown${student.studentID}" class="required">School in Fall</label>

                <select name="school" class="form-control " id="school-dropdown${student.studentID}">
                    <option value="" selected disabled>- Select School -</option>
                    <c:forEach items="${schoolArray}" var="school">
                        <c:choose>
                            <c:when test="${school.schoolID == student.schoolID}">
                                <option value="${school.schoolID}" selected>${school.school}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${school.schoolID}">${school.school}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

<%--                    <c:choose>--%>
<%--                        <c:when test="${student.schoolID == -1}">--%>
<%--                            <option value="-1" selected>Other</option>--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            <option value="-1">Other</option>--%>
<%--                        </c:otherwise>--%>
<%--                    </c:choose>--%>
                </select>
            </div>

            <!-- Other School -->
            <div class="customRow margin-below">
                <div class="customRow">
                    <label for="otherSchool">If Other, please specify:</label>
                </div>
                <input type="text" id="otherSchool" name="otherSchool" value="${student.otherSchool}"/>
            </div>


            <hr class="divider">

            <!-- Transportation -->
            <div class="customRow margin-below">
                <h3 class="full-line">Transportation</h3>
                <small>Information for this section will only apply to in-person camps. If you are only applying to
                    online camps, you may answer with "no" to questions in this section.</small>
            </div>

            <!-- Transportation To -->
            <div class="margin-below">
                <label for="to-camp" class="required">Does your child have transportation to get to camp by 8AM
                    everyday?</label>
                <div id="to-camp margin-below">
                    <div class="customRow center-vertical">
                        <c:choose>
                            <c:when test="${student.transportTo}">
                                <input type="radio" class="center-vertical" name="transportTo" value="yes" checked/>
                                <span>Yes</span>
                                &nbsp;
                                <input type="radio" class="center-vertical" name="transportTo" value="no"/>
                                <span>No</span>
                            </c:when>
                            <c:when test="${student.transportTo != null && !student.transportTo}">
                                <input type="radio" class="center-vertical" name="transportTo" value="yes"/>
                                <span>Yes</span>
                                &nbsp;
                                <input type="radio" class="center-vertical" name="transportTo" value="no" checked/>
                                <span>No</span>
                            </c:when>
                            <c:when test="${student == null}">
                                <input type="radio" class="center-vertical" name="transportTo" value="yes"/>
                                <span>Yes</span>
                                &nbsp;
                                <input type="radio" class="center-vertical" name="transportTo" value="no"/>
                                <span>No</span>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>

            <label for="from-camp" class="required full-line">Does your child have transportation to get picked up from
                camp by 8PM everyday?</label>
            <div id="from-camp margin-below">
                <div class="customRow center-vertical">
                    <c:choose>
                        <c:when test="${student.transportFrom}">
                            <input type="radio" class="center-vertical" name="transportFrom" value="yes" checked/>
                            <span>Yes</span>
                            &nbsp;
                            <input type="radio" class="center-vertical" name="transportFrom" value="no"/>
                            <span>No</span>
                        </c:when>
                        <c:when test="${student.transportFrom != null && !student.transportFrom}">
                            <input type="radio" class="center-vertical" name="transportFrom" value="yes"/>
                            <span>Yes</span>
                            &nbsp;
                            <input type="radio" class="center-vertical" name="transportFrom" value="no" checked/> <span>No</span>
                        </c:when>
                        <c:when test="${student == null}">
                            <input type="radio" class="center-vertical" name="transportFrom" value="yes"/>
                            <span>Yes</span>
                            &nbsp;
                            <input type="radio" class="center-vertical" name="transportFrom" value="no"/>
                            <span>No</span>
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <hr class="divider">

            <!-- Additional Information -->
            <h3>Additional Information</h3>

            <!-- Attended -->
            <div class="customRow margin-below">
                <div class="required full-line margin-below">Has this child attended CS@SC camps before?</div>
                <div id="attended">
                    <div class="customRow center-vertical">
                        <c:choose>
                            <c:when test="${student.attended}">
                                <input type="radio" id="attended-yes" class="center-vertical" name="attended"
                                       value="yes" checked/>
                                <span for="attended-yes">Yes</span>
                                &nbsp;
                                <input type="radio" id="attended-no" class="center-vertical" name="attended"
                                       value="no"/>
                                <span for="attended-no">No</span>
                            </c:when>

                            <c:when test="${student.attended != null && !student.attended}">
                                <input type="radio" id="attended-yes" class="center-vertical" name="attended"
                                       value="yes"/>
                                <span for="attended-yes">Yes</span>
                                &nbsp;
                                <input type="radio" id="attended-no" class="center-vertical" name="attended"
                                       value="no" checked/>
                                <span for="attended-no">No</span>
                            </c:when>

                            <c:when test="${student == null}">
                                <input type="radio" class="center-vertical" name="attended" value="yes"/>
                                <span for="attended-yes">Yes</span>
                                &nbsp;
                                <input type="radio" class="center-vertical" name="attended" value="no"/>
                                <span for="attended-no">No</span>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>

            <!-- Experience -->
            <div class="customRow margin-below">
                <label for="experience" class="required full-line">Experience with computers or computer
                    programming?</label>
                <textarea id="experience" name="experience">${student.experience} </textarea>
            </div>

            <!-- Medical Issues -->
            <div class="customRow margin-below">
                <label for="diet" class="required full-line">Does your child have dietary restrictions or medical issues
                    we should know about?</label>
                <textarea id="diet" name="diet">${student.diet}</textarea>
            </div>

            <!-- Other Info -->
            <div class="customRow margin-below">
                <label for="otherInfo" class="full-line">Is there any other information that you think is important for
                    the camp staff to know about your child?</label>
                <textarea id="otherInfo" name="otherInfo">${student.otherInfo}</textarea>
            </div>

            <!-- Form Submit Buttons -->
            <div class="submitRow">
                <div class="align-self-end">
                    <input class="btn btn-primary" type="Submit" name="Submit" value="Save & Continue"
                           id="save-continue"/>
                </div>
            </div>
        </form>
    </div>
</div>
</body>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
        integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
        crossorigin="anonymous"></script>
<script>
    function validateForm(e, form) {
        e.preventDefault();
        if (!validateSFormFields(form)) {
            return false;
        } else {
            form.submit();
            return true;
        }
    }
</script>
</html>