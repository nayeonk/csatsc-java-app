<%@ page language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>

<jsp:include page="../includes/header.jsp"/>

<div id="container" class="container" style="width:100%;">
    <div class="back-button-container">
        <a href="admincontrolpanel"> &lt; Back </a>
        <a class="logout-link" href="logout"> Logout </a>
    </div>
    <div class="admin-control-panel">
        <div class="admin-control-panel-header">
            <h4 style="text-align:center;"><strong><c:out value="${campOffered.campLevel}"/> <c:out
                    value="${campOffered.campTopic}"/></strong><br><br>
                <c:out value="${campPrice}"/> &nbsp;|&nbsp;
                <fmt:formatDate type="date" dateStyle="short" value="${campOffered.startDate}"/> - <fmt:formatDate
                        type="date" dateStyle="short" value="${campOffered.endDate}"/> &nbsp;|&nbsp;
                Grades <c:out value="${campOffered.recommendedGradeLow}-${campOffered.recommendedGradeHigh}"/></h4>
        </div>
        <br>
        <button data-toggle="collapse" data-target="#filter-container">All Emails</button>
        <div class="collapse" id="filter-container">
            <div class="select-filter">
                <c:if test="${confirmEmailsLength != 0}">
                    <h4>Confirmed</h4>
                </c:if>
                <p>
                    <c:forEach items="${confirmEmails}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
                <c:if test="${acceptEmailsLength != 0}">
                    <h4>Accepted</h4>
                </c:if>
                <p>
                    <c:forEach items="${acceptEmails}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
                <c:if test="${waitlistEmailsLength != 0}">
                    <h4>Waitlisted</h4>
                </c:if>
                <p>
                    <c:forEach items="${waitlistEmails}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
                <c:if test="${rejectEmailsLength != 0}">
                    <h4>Rejected</h4>
                </c:if>
                <p>
                    <c:forEach items="${rejectEmails}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
                <c:if test="${withdrawnEmailsLength != 0}">
                    <h4>Withdrawn</h4>
                </c:if>
                <p>
                    <c:forEach items="${withdrawnEmails}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
                <h4>All</h4>
                <p>
                    <c:forEach items="${allEmailList}" var="email">
                        <c:if test="${email != 'null'}">
                            <c:out value="${email};"/>
                        </c:if>
                    </c:forEach>
                </p>
            </div>
        </div>

        <div class="admin-control-panel-body">
            <div class="camp-student-list-tabs">

                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#applied-student-list"> Applied (<c:out
                            value="${campOffered.applied}"/>)</a></li>
                    <li><a data-toggle="tab" href="#confirmed-student-list"> Confirmed (<c:out
                            value="${campOffered.confirmed}"/> of <c:out value="${campOffered.capacity}"/>)</a></li>
                    <li><a data-toggle="tab" href="#accepted-student-list"> Accepted (<c:out
                            value="${campOffered.accepted}"/>)</a></li>
                    <li><a data-toggle="tab" href="#waitlisted-student-list"> Waitlisted (<c:out
                            value="${campOffered.waitlisted}"/>)</a></li>
                    <li><a data-toggle="tab" href="#rejected-student-list"> Rejected (<c:out
                            value="${campOffered.rejected}"/>)</a></li>
                    <li><a data-toggle="tab" href="#withdrawn-student-list"> Withdrawn (<c:out
                            value="${campOffered.withdrawn}"/>)</a></li>
                </ul>

                <div class="tab-content">
                    <c:forEach items="${campStatuses}" var="campStatus">
                        <c:choose>
                            <c:when test="${campStatus == 'applied'}">
                                <c:set var="activepane" value="in active"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="activepane" value=""/>
                            </c:otherwise>
                        </c:choose>
                        <div id="${campStatus}-student-list" class="tab-pane fade ${activepane}">
                            <form class="student-status-form" method="POST" action="campstudents">
                                <div class="table-responsive" style="overflow:scroll;">
                                    <table class="table sortable" style="font-size:14px;">
                                        <thead>
                                        <tr class="camp-student">
                                            <c:choose>
                                                <c:when test="${campStatus == 'applied'}">
                                                    <th><a href="javascript:sorttable();">Name</a></th>
                                                    <th><a href="javascript:sorttable();">Applied Date </a></th>
                                                    <th><a href="javascript:sorttable();"> Gender</a></th>
                                                    <th><a href="javascript:sorttable();">Ethnicity</a></th>
                                                    <th><a href="javascript:sorttable();">School</a></th>
                                                    <th><a href="javascript:sorttable();">Grade</a></th>
                                                    <th><a href="javascript:sorttable();">Date of Birth</a></th>
                                                    <th><a href="javascript:sorttable();">Reduced Meals</a></th>
                                                    <th><a href="javascript:sorttable();">Verification</a></th>
                                                    <th><a href="javascript:sorttable();">Grade Reports</a></th>
                                                    <th><a href="javascript:sorttable();">Income</a></th>
                                                    <th><a href="javascript:sorttable();">Transport To</a></th>
                                                    <th><a href="javascript:sorttable();">Transport From</a></th>
                                                    <th><a href="javascript:sorttable();">Experience</a></th>
                                                    <th><a href="javascript:sorttable();">USC Parent?</a></th>
                                                    <th>Status</th>
                                                </c:when>
                                                <c:otherwise>
                                                    <th>Name</th>
                                                    <th>Timestamp</th>
                                                    <c:if test="${campStatus == 'withdrawn'}">
                                                        <th>Accepted</th>
                                                    </c:if>
                                                    <th>Gender</th>
                                                    <th>Ethnicity</th>
                                                    <th>School</th>
                                                    <th>Grade</th>
                                                    <th>Date of Birth</th>
                                                    <th>Reduced Meals</th>
                                                    <th>Verification</th>
                                                    <th>Grade Reports</th>
                                                    <th>Income</th>
                                                    <th>Transport To</th>
                                                    <th>Transport From</th>
                                                    <th>Experience</th>
                                                    <th>USC Parent?</th>
                                                    <c:if test="${campStatus != 'withdrawn'}">
                                                        <th>Status</th>
                                                    </c:if>
                                                    <c:if test="${campStatus == 'confirmed'}">
                                                        <th>Student Price</th>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:set var="campApplications" value="${campStatus}StudentCampList"/>
                                        <c:set var="studentParentMap" value="${campStatus}ParentMap"/>

                                        <c:forEach items="${sessionScope[campApplications]}" var="campApplication">

                                            <c:set var="student" value="${campApplication.student}"/>
                                            <c:set var="studentID" value="${student.studentID}"/>
                                            <c:set var="parentMap" value="${sessionScope[studentParentMap]}"/>
                                            <c:set var="parent" value="${parentMap[campApplication]}"/>

                                            <%-- Student and Parent Info Pop-up --%>
                                            <div id="${studentID}"
                                                 style="display:none; width:500px; height:650px; margin: 0 15px; z-index:999999999;">
                                                <h3>${student.name}</h3>
                                                <hr/>
                                                <p><strong>Applied At:</strong> &nbsp;<fmt:formatDate type="both"
                                                                                                      timeStyle="short"
                                                                                                      dateStyle="short"
                                                                                                      value="${campApplication.requestedTimestamp}"/>
                                                </p>
                                                <p>
                                                    <strong>Parent Name:</strong> &nbsp;<c:out
                                                        value="${parent.name}"/><br>
                                                    <strong>Parent Email:</strong> &nbsp;<c:out
                                                        value="${parent.email}"/><br>
                                                    <strong>Parent Phone:</strong> &nbsp;<c:out
                                                        value="${parent.phone}"/><br>
                                                    <strong>Parent Income:</strong> &nbsp;<c:out
                                                        value="${campApplication.studentsParentsIncome}"/>
                                                </p>
                                                <p><strong>All Current Camp Applications:</strong><br>
                                                    <c:forEach items="${studentCampArray}" var="studentCamp">
                                                        <c:if test="${studentID == studentCamp.studentID}">
                                                            <c:forEach items="${campArray}" var="camp">
                                                                <c:if test="${camp.campOfferedID == studentCamp.campOfferedID}">
                                                                    ${camp.campName} (<c:choose><c:when
                                                                        test="${camp.paid == 1}">${camp.price},</c:when><c:otherwise>Free,</c:otherwise></c:choose>
                                                                    <fmt:formatDate pattern="M/d"
                                                                                    value="${camp.campStartDate}"/>-<fmt:formatDate
                                                                        pattern="M/d"
                                                                        value="${camp.campEndDate}"/>, ${camp.campDays} ${camp.campTime}):
                                                                    <fmt:formatNumber value="${studentCamp.cost}"
                                                                                      minFractionDigits="2"
                                                                                      var="formattedPrice"/>
                                                                    <c:choose>
                                                                        <c:when test="${studentCamp.withdrawn == 1}">withdrawn</c:when>
                                                                        <c:when test="${studentCamp.confirmed == 1}">confirmed</c:when>
                                                                        <c:when test="${studentCamp.accepted == 1}">accepted for $${formattedPrice}</c:when>
                                                                        <c:when test="${studentCamp.rejected == 1}">rejected</c:when>
                                                                        <c:when test="${studentCamp.waitlisted == 1}">waitlisted</c:when>
                                                                        <c:otherwise>applied</c:otherwise>
                                                                    </c:choose><br>
                                                                </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                    </c:forEach>
                                                </p>
                                                <p>
                                                    <strong>Gender:</strong> &nbsp;${student.gender.gender}<br>
                                                    <strong>Ethnicity:</strong>&nbsp;
                                                    <c:forEach items="${student.ethnicities}" var="ethnicity"
                                                               varStatus="loop">
                                                        ${ethnicity.ethnicity}
                                                    </c:forEach>
                                                </p>
                                                <p>
                                                    <strong>School:</strong> &nbsp;${student.school.school}<br>
                                                    <strong>Grade:</strong> &nbsp;${student.grade.grade}
                                                </p>
                                                <c:set var="address" value="${parent.address}"/>
                                                <p><strong>Address:</strong>
                                                    &nbsp;${address.street}<br>${address.city}, ${address.state} ${address.zip}
                                                </p>
                                                <p>
                                                    <strong>Transportation To:</strong>&nbsp;
                                                    <c:choose>
                                                        <c:when test="${student.transportTo}">yes</c:when>
                                                        <c:otherwise>no</c:otherwise>
                                                    </c:choose>
                                                    <br>
                                                    <strong>Transportation From:</strong>&nbsp;
                                                    <c:choose>
                                                        <c:when test="${student.transportFrom}">yes</c:when>
                                                        <c:otherwise>no</c:otherwise>
                                                    </c:choose>
                                                </p>
                                                <p><strong>Reduced Meals:</strong>
                                                    &nbsp;${student.reducedMeals.description}</p>
                                                <p><strong>Reduced Meals Verifications:</strong><br/>
                                                    <c:forEach items="${student.reducedMealsVerifications}" var="rmv"
                                                               varStatus="loop">
                                                        <a href="viewuploadedpicture?id=${rmv.reducedMealsVerificationID}&kind=reduced"
                                                           target="_blank">${rmv.readableTimestamp} <c:choose><c:when
                                                                test='${rmv.deleted}'>(Deleted)</c:when></c:choose></a>
                                                        <br/>
                                                    </c:forEach></p>
                                                <p><strong>Grade Reports:</strong><br/>
                                                    <c:forEach items="${student.gradeReports}" var="gr"
                                                               varStatus="loop">
                                                        <a href="viewuploadedpicture?id=${gr.gradeReportID}&kind=grade"
                                                           target="_blank">${gr.readableTimestamp} <c:choose><c:when
                                                                test='${gr.deleted}'>(Deleted)</c:when></c:choose></a><br/>
                                                    </c:forEach></p>
                                                <p><strong>Diet/Medical:</strong> &nbsp;${student.diet}</p>
                                                <p><strong>Experience:</strong> &nbsp;${student.experience}</p>
                                                <p><strong>Other Info:</strong> &nbsp;${student.otherInfo}</p>
                                            </div>

                                            <!-- MAKE SURE TO LOOK AT THIS -->
                                            <tr class="camp-student">
                                                <td>
                                                    <a data-fancybox data-src="#${studentID}" data-options='{"touch": false}' href="javascript:"
                                                       class="fancybox">${student.name}</a>
                                                </td>
                                                <td><fmt:formatDate type="both" timeStyle="short" dateStyle="short"
                                                                    value="${campApplication.requestedTimestamp}"/></td>
                                                <c:if test="${campStatus == 'withdrawn'}">
                                                    <td><c:choose><c:when
                                                            test="${campApplication.acceptedTimestamp != null}">yes</c:when><c:otherwise>no</c:otherwise></c:choose></td>
                                                </c:if>
                                                <td>${student.gender.gender}</td>
                                                <td>
                                                    <c:forEach items="${student.ethnicities}" var="ethnicity"
                                                               varStatus="loop">
                                                        ${ethnicity.ethnicity}
                                                    </c:forEach>
                                                </td>
                                                <td>${student.school.school}</td>

                                                <td>${student.grade.grade}</td>
                                                <td>${student.dob}</td>
                                                <!--<td>${student.reducedMeals.description}<button class = "button" onClick="window.open('');"><span class = "icon"></span>Uploaded Picture</button></td>-->
                                                <td>${student.reducedMeals.description}</td>
                                                <td>
                                                    <c:forEach items="${student.reducedMealsVerifications}" var="rmv"
                                                               varStatus="loop">
                                                        <a href="viewuploadedpicture?id=${rmv.reducedMealsVerificationID}&kind=reduced"
                                                           target="_blank">${rmv.readableTimestamp} <c:choose><c:when
                                                                test='${rmv.deleted}'>(Deleted)</c:when></c:choose></a>
                                                        <br/>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <c:forEach items="${student.gradeReports}" var="gr"
                                                               varStatus="loop">
                                                        <a href="viewuploadedpicture?id=${gr.gradeReportID}&kind=grade"
                                                           target="_blank">${gr.readableTimestamp} <c:choose><c:when
                                                                test='${gr.deleted}'>(Deleted)</c:when></c:choose></a><br/>
                                                    </c:forEach>
                                                </td>

                                                <td>${campApplication.studentsParentsIncome}</td>
                                                <td><c:choose><c:when
                                                        test="${student.transportTo}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                                                <td><c:choose><c:when
                                                        test="${student.transportFrom}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose></td>
                                                <td>${student.experience}</td>
                                                <td class="wide-col">
                                                    <c:if test="${fn:contains(parent.email, 'usc.edu')}">Yes</c:if>
                                                </td>
                                                    <%--
                                                        STATUS CHANGE OPTIONS
                                                        Applied: accept, waitlist, reject
                                                        Confirmed: waitlist, reject
                                                        Accepted: paid (if paid camp), waitlist, reject
                                                        Waitlisted: accept, reject
                                                        Rejected: accept, waitlist
                                                    --%>
                                                <c:if test="${campStatus != 'withdrawn'}">
                                                    <td style="white-space: nowrap;">
                                                        <c:if test="${campStatus != 'confirmed' && campStatus != 'accepted'}">
                                                            <label for="status1-${studentID}"><input type="radio"
                                                                                                     id="status1-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="accept"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Accept</label>
                                                        </c:if>
                                                        <c:if test="${campStatus == 'accepted' && campOffered.paid == 1}">
                                                            <label for="status5-${studentID}"><input type="radio"
                                                                                                     id="status5-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="paid"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Paid</label>
                                                        </c:if>
                                                        <c:if test="${campStatus != 'waitlisted'}">
                                                            <label for="status2-${studentID}"><input type="radio"
                                                                                                     id="status2-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="waitlist"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Waitlist</label>
                                                        </c:if>
                                                        <c:if test="${campStatus != 'rejected'}">
                                                            <label for="status3-${studentID}"><input type="radio"
                                                                                                     id="status3-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="reject"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Reject
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${campStatus == 'accepted' && campOffered.paid == 1}">
                                                            <label for="status6-${studentID}"><input type="radio"
                                                                                                     id="status6-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="accept"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Change
                                                                Price</label>
                                                        </c:if>
                                                        <label for="status4-${studentID}"><input type="radio"
                                                                                                 id="status4-${studentID}"
                                                                                                 name="status-${studentID}"
                                                                                                 value="noaction"
                                                                                                 checked="checked"
                                                                                                 onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">No
                                                            Action</label>
                                                        <c:if test="${campStatus == 'accepted'}">
                                                            <label for="status5-${studentID}"><input type="radio"
                                                                                                     id="status5-${studentID}"
                                                                                                     name="status-${studentID}"
                                                                                                     value="withdraw"
                                                                                                     onclick="change_visibility('${campStatus}-payment-${studentID}', this, '${campStatus}');">Withdraw
                                                            </label>
                                                        </c:if>
                                                        <c:forEach items="${studentCampArray}" var="studentCamp">
                                                            <c:if test="${studentID == studentCamp.studentID && campOfferedID == studentCamp.campOfferedID && campStatus == 'accepted'}">
                                                                <div id="${campStatus}-payment-${studentID}"
                                                                     style="display:none;">
                                                                    <fmt:formatNumber value="${studentCamp.cost}"
                                                                                      minFractionDigits="2"
                                                                                      var="formattedPrice"/>
                                                                    <script> console.log("student accepted!!")</script>
                                                                    <br/>
                                                                    Payment Amount: <input type="number" step="0.01"
                                                                                           name="payment-field-${studentID}"
                                                                                           value="${formattedPrice}"/>
                                                                </div>
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:if test="${campStatus != 'accepted'}">
                                                            <div id="${campStatus}-payment-${studentID}"
                                                                 style="display:none;">
                                                                <fmt:formatNumber value="${campPriceAsInt}"
                                                                                  minFractionDigits="2"
                                                                                  var="formattedPrice"/>
                                                                <script> console.log("student not accepted")</script>
                                                                <br/>
                                                                Payment Amount: <input type="number" step="0.01"
                                                                                       name="payment-field-${studentID}"
                                                                                       value="${formattedPrice}"/>
                                                            </div>
                                                        </c:if>
                                                            <%-- <c:if test="${campStatus == 'rejected'}">
                                                                <div id="${campStatus}-payment-${studentID}" style="display:none;">
                                                                    <br />
                                                                    Payment Amount: <input type="number" step="0.01" name="payment-field-${studentID}" value="${campPriceAsInt}" />
                                                                </div>
                                                            </c:if> --%>
                                                    </td>
                                                </c:if>
                                                <c:if test="${campStatus == 'confirmed'}">
                                                    <td>${campApplication.cost}</td>
                                                </c:if>


                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <input type="hidden" name="submit" value="${campStatus}">
                                <input type="submit" value="Submit">
                            </form>
                        </div>
                    </c:forEach>

                    <script type="text/javascript">
                        $(document).ready(function () {
                            // Javascript to enable link to tab
                            var hash = document.location.hash;
                            var prefix = "tab_";
                            if (hash) {
                                $('.nav-tabs a[href=' + hash.replace(prefix, "") + ']').tab('show');
                            }

                            // Change hash for page-reload
                            $('.nav-tabs a').on('shown', function (e) {
                                window.location.hash = e.target.hash.replace("#", "#" + prefix);
                            });
                        });
                    </script>

                    <script type="text/javascript">
                        function change_visibility(elementID, radioClicked, campStatus) {
                            var e = document.getElementById(elementID);
                            console.log("camp status : " + campStatus);
                            if ((radioClicked.checked && radioClicked.value == "accept")) {
                                e.style.display = 'block';
                                console.log("display block for visibility");
                            } else {
                                e.style.display = 'none';
                            }
                        }
                    </script>
                    <script type="text/javascript">

                        /* ******************************************************************
                        //////////////////// SCRIPTS FOR SORTING COLUMNS ////////////////////
                        ****************************************************************** */

                        var stIsIE = /*@cc_on!@*/false;

                        sorttable = {
                            init: function () {
                                // quit if this function has already been called
                                if (arguments.callee.done) return;
                                // flag this function so we don't do the same thing twice
                                arguments.callee.done = true;
                                // kill the timer
                                if (_timer) clearInterval(_timer);

                                if (!document.createElement || !document.getElementsByTagName) return;

                                sorttable.DATE_RE = /^(\d\d?)[\/\.-](\d\d?)[\/\.-]((\d\d)?\d\d)$/;

                                forEach(document.getElementsByTagName('table'), function (table) {
                                    if (table.className.search(/\bsortable\b/) != -1) {
                                        console.log("calling sort");

                                        sorttable.makeSortable(table);
                                    }
                                });

                            },

                            makeSortable: function (table) {
                                if (table.getElementsByTagName('thead').length == 0) {
                                    // table doesn't have a tHead. Since it should have, create one and
                                    // put the first table row in it.
                                    the = document.createElement('thead');
                                    the.appendChild(table.rows[0]);
                                    table.insertBefore(the, table.firstChild);
                                }
                                // Safari doesn't support table.tHead, sigh
                                if (table.tHead == null) table.tHead = table.getElementsByTagName('thead')[0];

                                if (table.tHead.rows.length != 1) return; // can't cope with two header rows

                                // Sorttable v1 put rows with a class of "sortbottom" at the bottom (as
                                // "total" rows, for example). This is B&R, since what you're supposed
                                // to do is put them in a tfoot. So, if there are sortbottom rows,
                                // for backwards compatibility, move them to tfoot (creating it if needed).
                                sortbottomrows = [];
                                for (var i = 0; i < table.rows.length; i++) {
                                    if (table.rows[i].className.search(/\bsortbottom\b/) != -1) {
                                        sortbottomrows[sortbottomrows.length] = table.rows[i];
                                    }
                                }
                                if (sortbottomrows) {
                                    if (table.tFoot == null) {
                                        // table doesn't have a tfoot. Create one.
                                        tfo = document.createElement('tfoot');
                                        table.appendChild(tfo);
                                    }
                                    for (var i = 0; i < sortbottomrows.length; i++) {
                                        tfo.appendChild(sortbottomrows[i]);
                                    }
                                    delete sortbottomrows;
                                }

                                // work through each column and calculate its type
                                headrow = table.tHead.rows[0].cells;
                                for (var i = 0; i < headrow.length; i++) {
                                    // manually override the type with a sorttable_type attribute
                                    if (!headrow[i].className.match(/\bsorttable_nosort\b/)) { // skip this col
                                        mtch = headrow[i].className.match(/\bsorttable_([a-z0-9]+)\b/);
                                        if (mtch) {
                                            override = mtch[1];
                                        }
                                        if (mtch && typeof sorttable["sort_" + override] == 'function') {
                                            headrow[i].sorttable_sortfunction = sorttable["sort_" + override];
                                        } else {
                                            headrow[i].sorttable_sortfunction = sorttable.guessType(table, i);
                                        }
                                        // make it clickable to sort
                                        headrow[i].sorttable_columnindex = i;
                                        headrow[i].sorttable_tbody = table.tBodies[0];
                                        dean_addEvent(headrow[i], "click", sorttable.innerSortFunction = function (e) {

                                            if (this.className.search(/\bsorttable_sorted\b/) != -1) {
                                                // if we're already sorted by this column, just
                                                // reverse the table, which is quicker
                                                sorttable.reverse(this.sorttable_tbody);
                                                this.className = this.className.replace('sorttable_sorted',
                                                    'sorttable_sorted_reverse');
                                                this.removeChild(document.getElementById('sorttable_sortfwdind'));
                                                sortrevind = document.createElement('span');
                                                sortrevind.id = "sorttable_sortrevind";
                                                sortrevind.innerHTML = stIsIE ? '&nbsp<font face="webdings">5</font>' : '&nbsp;&#x25B4;';
                                                this.appendChild(sortrevind);
                                                return;
                                            }
                                            if (this.className.search(/\bsorttable_sorted_reverse\b/) != -1) {
                                                // if we're already sorted by this column in reverse, just
                                                // re-reverse the table, which is quicker
                                                sorttable.reverse(this.sorttable_tbody);
                                                this.className = this.className.replace('sorttable_sorted_reverse',
                                                    'sorttable_sorted');
                                                this.removeChild(document.getElementById('sorttable_sortrevind'));
                                                sortfwdind = document.createElement('span');
                                                sortfwdind.id = "sorttable_sortfwdind";
                                                sortfwdind.innerHTML = stIsIE ? '&nbsp<font face="webdings">6</font>' : '&nbsp;&#x25BE;';
                                                this.appendChild(sortfwdind);
                                                return;
                                            }

                                            // remove sorttable_sorted classes
                                            theadrow = this.parentNode;
                                            forEach(theadrow.childNodes, function (cell) {
                                                if (cell.nodeType == 1) { // an element
                                                    cell.className = cell.className.replace('sorttable_sorted_reverse', '');
                                                    cell.className = cell.className.replace('sorttable_sorted', '');
                                                }
                                            });
                                            sortfwdind = document.getElementById('sorttable_sortfwdind');
                                            if (sortfwdind) {
                                                sortfwdind.parentNode.removeChild(sortfwdind);
                                            }
                                            sortrevind = document.getElementById('sorttable_sortrevind');
                                            if (sortrevind) {
                                                sortrevind.parentNode.removeChild(sortrevind);
                                            }

                                            this.className += ' sorttable_sorted';
                                            sortfwdind = document.createElement('span');
                                            sortfwdind.id = "sorttable_sortfwdind";
                                            sortfwdind.innerHTML = stIsIE ? '&nbsp<font face="webdings">6</font>' : '&nbsp;&#x25BE;';
                                            this.appendChild(sortfwdind);

                                            // build an array to sort. This is a Schwartzian transform thing,
                                            // i.e., we "decorate" each row with the actual sort key,
                                            // sort based on the sort keys, and then put the rows back in order
                                            // which is a lot faster because you only do getInnerText once per row
                                            row_array = [];
                                            col = this.sorttable_columnindex;
                                            rows = this.sorttable_tbody.rows;
                                            for (var j = 0; j < rows.length; j++) {
                                                row_array[row_array.length] = [sorttable.getInnerText(rows[j].cells[col]), rows[j]];
                                            }
                                            /* If you want a stable sort, uncomment the following line */
                                            //sorttable.shaker_sort(row_array, this.sorttable_sortfunction);
                                            /* and comment out this one */
                                            row_array.sort(this.sorttable_sortfunction);

                                            tb = this.sorttable_tbody;
                                            for (var j = 0; j < row_array.length; j++) {
                                                tb.appendChild(row_array[j][1]);
                                            }
                                            delete row_array;
                                        });
                                    }
                                }
                            },

                            guessType: function (table, column) {
                                // guess the type of a column based on its first non-blank row
                                sortfn = sorttable.sort_alpha;
                                for (var i = 0; i < table.tBodies[0].rows.length; i++) {
                                    text = sorttable.getInnerText(table.tBodies[0].rows[i].cells[column]);
                                    if (text != '') {
                                        if (text.match(/^-?[£$§]?[\d,.]+%?$/)) {
                                            return sorttable.sort_numeric;
                                        }
                                        // check for a date: dd/mm/yyyy or dd/mm/yy
                                        // can have / or . or - as separator
                                        // can be mm/dd as well
                                        possdate = text.match(sorttable.DATE_RE)
                                        if (possdate) {
                                            // looks like a date
                                            first = parseInt(possdate[1]);
                                            second = parseInt(possdate[2]);
                                            if (first > 12) {
                                                // definitely dd/mm
                                                return sorttable.sort_ddmm;
                                            } else if (second > 12) {
                                                return sorttable.sort_mmdd;
                                            } else {
                                                // looks like a date, but we can't tell which, so assume
                                                // that it's dd/mm (English imperialism!) and keep looking
                                                sortfn = sorttable.sort_ddmm;
                                            }
                                        }
                                    }
                                }
                                return sortfn;
                            },

                            getInnerText: function (node) {
                                // gets the text we want to use for sorting for a cell.
                                // strips leading and trailing whitespace.
                                // this is *not* a generic getInnerText function; it's special to sorttable.
                                // for example, you can override the cell text with a customkey attribute.
                                // it also gets .value for <input> fields.

                                if (!node) return "";

                                hasInputs = (typeof node.getElementsByTagName == 'function') &&
                                    node.getElementsByTagName('input').length;

                                if (node.getAttribute("sorttable_customkey") != null) {
                                    return node.getAttribute("sorttable_customkey");
                                } else if (typeof node.textContent != 'undefined' && !hasInputs) {
                                    return node.textContent.replace(/^\s+|\s+$/g, '');
                                } else if (typeof node.innerText != 'undefined' && !hasInputs) {
                                    return node.innerText.replace(/^\s+|\s+$/g, '');
                                } else if (typeof node.text != 'undefined' && !hasInputs) {
                                    return node.text.replace(/^\s+|\s+$/g, '');
                                } else {
                                    switch (node.nodeType) {
                                        case 3:
                                            if (node.nodeName.toLowerCase() == 'input') {
                                                return node.value.replace(/^\s+|\s+$/g, '');
                                            }
                                        case 4:
                                            return node.nodeValue.replace(/^\s+|\s+$/g, '');
                                            break;
                                        case 1:
                                        case 11:
                                            var innerText = '';
                                            for (var i = 0; i < node.childNodes.length; i++) {
                                                innerText += sorttable.getInnerText(node.childNodes[i]);
                                            }
                                            return innerText.replace(/^\s+|\s+$/g, '');
                                            break;
                                        default:
                                            return '';
                                    }
                                }
                            },

                            reverse: function (tbody) {
                                // reverse the rows in a tbody
                                newrows = [];
                                for (var i = 0; i < tbody.rows.length; i++) {
                                    newrows[newrows.length] = tbody.rows[i];
                                }
                                for (var i = newrows.length - 1; i >= 0; i--) {
                                    tbody.appendChild(newrows[i]);
                                }
                                delete newrows;
                            },

                            /* sort functions
                               each sort function takes two parameters, a and b
                               you are comparing a[0] and b[0] */
                            sort_numeric: function (a, b) {
                                aa = parseFloat(a[0].replace(/[^0-9.-]/g, ''));
                                if (isNaN(aa)) aa = 0;
                                bb = parseFloat(b[0].replace(/[^0-9.-]/g, ''));
                                if (isNaN(bb)) bb = 0;
                                return aa - bb;
                            },
                            sort_alpha: function (a, b) {
                                if (a[0] == b[0]) return 0;
                                if (a[0] < b[0]) return -1;
                                return 1;
                            },
                            sort_ddmm: function (a, b) {
                                mtch = a[0].match(sorttable.DATE_RE);
                                y = mtch[3];
                                m = mtch[2];
                                d = mtch[1];
                                if (m.length == 1) m = '0' + m;
                                if (d.length == 1) d = '0' + d;
                                dt1 = y + m + d;
                                mtch = b[0].match(sorttable.DATE_RE);
                                y = mtch[3];
                                m = mtch[2];
                                d = mtch[1];
                                if (m.length == 1) m = '0' + m;
                                if (d.length == 1) d = '0' + d;
                                dt2 = y + m + d;
                                if (dt1 == dt2) return 0;
                                if (dt1 < dt2) return -1;
                                return 1;
                            },
                            sort_mmdd: function (a, b) {
                                mtch = a[0].match(sorttable.DATE_RE);
                                y = mtch[3];
                                d = mtch[2];
                                m = mtch[1];
                                if (m.length == 1) m = '0' + m;
                                if (d.length == 1) d = '0' + d;
                                dt1 = y + m + d;
                                mtch = b[0].match(sorttable.DATE_RE);
                                y = mtch[3];
                                d = mtch[2];
                                m = mtch[1];
                                if (m.length == 1) m = '0' + m;
                                if (d.length == 1) d = '0' + d;
                                dt2 = y + m + d;
                                if (dt1 == dt2) return 0;
                                if (dt1 < dt2) return -1;
                                return 1;
                            },

                            shaker_sort: function (list, comp_func) {
                                // A stable sort function to allow multi-level sorting of data
                                // see: http://en.wikipedia.org/wiki/Cocktail_sort
                                // thanks to Joseph Nahmias
                                var b = 0;
                                var t = list.length - 1;
                                var swap = true;

                                while (swap) {
                                    swap = false;
                                    for (var i = b; i < t; ++i) {
                                        if (comp_func(list[i], list[i + 1]) > 0) {
                                            var q = list[i];
                                            list[i] = list[i + 1];
                                            list[i + 1] = q;
                                            swap = true;
                                        }
                                    } // for
                                    t--;

                                    if (!swap) break;

                                    for (var i = t; i > b; --i) {
                                        if (comp_func(list[i], list[i - 1]) < 0) {
                                            var q = list[i];
                                            list[i] = list[i - 1];
                                            list[i - 1] = q;
                                            swap = true;
                                        }
                                    } // for
                                    b++;

                                } // while(swap)
                            }
                        }

                        /* ******************************************************************
                           Supporting functions: bundled here to avoid depending on a library
                           ****************************************************************** */

                        // Dean Edwards/Matthias Miller/John Resig

                        /* for Mozilla/Opera9 */
                        if (document.addEventListener) {
                            document.addEventListener("DOMContentLoaded", sorttable.init, false);
                        }

                        /* for Internet Explorer */
                        /*@cc_on @*/
                        /*@if (@_win32)
                            document.write("<script id=__ie_onload defer src=javascript:void(0)><\/script>");
                            var script = document.getElementById("__ie_onload");
                            script.onreadystatechange = function() {
                                if (this.readyState == "complete") {
                                    sorttable.init(); // call the onload handler
                                }
                            };
                        /*@end @*/

                        /* for Safari */
                        if (/WebKit/i.test(navigator.userAgent)) { // sniff
                            var _timer = setInterval(function () {
                                if (/loaded|complete/.test(document.readyState)) {
                                    sorttable.init(); // call the onload handler
                                }
                            }, 10);
                        }

                        /* for other browsers */
                        window.onload = sorttable.init;

                        // written by Dean Edwards, 2005
                        // with input from Tino Zijdel, Matthias Miller, Diego Perini

                        // http://dean.edwards.name/weblog/2005/10/add-event/

                        function dean_addEvent(element, type, handler) {
                            if (element.addEventListener) {
                                element.addEventListener(type, handler, false);
                            } else {
                                // assign each event handler a unique ID
                                if (!handler.$$guid) handler.$$guid = dean_addEvent.guid++;
                                // create a hash table of event types for the element
                                if (!element.events) element.events = {};
                                // create a hash table of event handlers for each element/event pair
                                var handlers = element.events[type];
                                if (!handlers) {
                                    handlers = element.events[type] = {};
                                    // store the existing event handler (if there is one)
                                    if (element["on" + type]) {
                                        handlers[0] = element["on" + type];
                                    }
                                }
                                // store the event handler in the hash table
                                handlers[handler.$$guid] = handler;
                                // assign a global event handler to do all the work
                                element["on" + type] = handleEvent;
                            }
                        }

                        // a counter used to create unique IDs
                        dean_addEvent.guid = 1;

                        function removeEvent(element, type, handler) {
                            if (element.removeEventListener) {
                                element.removeEventListener(type, handler, false);
                            } else {
                                // delete the event handler from the hash table
                                if (element.events && element.events[type]) {
                                    delete element.events[type][handler.$$guid];
                                }
                            }
                        }

                        function handleEvent(event) {
                            var returnValue = true;
                            // grab the event object (IE uses a global event object)
                            event = event || fixEvent(((this.ownerDocument || this.document || this).parentWindow || window).event);
                            // get a reference to the hash table of event handlers
                            var handlers = this.events[event.type];
                            // execute each event handler
                            for (var i in handlers) {
                                this.$$handleEvent = handlers[i];
                                if (this.$$handleEvent(event) === false) {
                                    returnValue = false;
                                }
                            }
                            return returnValue;
                        }

                        function fixEvent(event) {
                            // add W3C standard event methods
                            event.preventDefault = fixEvent.preventDefault;
                            event.stopPropagation = fixEvent.stopPropagation;
                            return event;
                        }

                        fixEvent.preventDefault = function () {
                            this.returnValue = false;
                        };
                        fixEvent.stopPropagation = function () {
                            this.cancelBubble = true;
                        }

                        // Dean's forEach: http://dean.edwards.name/base/forEach.js
                        /*
                            forEach, version 1.0
                            Copyright 2006, Dean Edwards
                            License: http://www.opensource.org/licenses/mit-license.php
                        */

                        // array-like enumeration
                        if (!Array.forEach) { // mozilla already supports this
                            Array.forEach = function (array, block, context) {
                                for (var i = 0; i < array.length; i++) {
                                    block.call(context, array[i], i, array);
                                }
                            };
                        }

                        // generic enumeration
                        Function.prototype.forEach = function (object, block, context) {
                            for (var key in object) {
                                if (typeof this.prototype[key] == "undefined") {
                                    block.call(context, object[key], key, object);
                                }
                            }
                        };

                        // character enumeration
                        String.forEach = function (string, block, context) {
                            Array.forEach(string.split(""), function (chr, index) {
                                block.call(context, chr, index, string);
                            });
                        };

                        // globally resolve forEach enumeration
                        var forEach = function (object, block, context) {
                            if (object) {
                                var resolve = Object; // default
                                if (object instanceof Function) {
                                    // functions have a "length" property
                                    resolve = Function;
                                } else if (object.forEach instanceof Function) {
                                    // the object implements a custom forEach method so use that
                                    object.forEach(block, context);
                                    return;
                                } else if (typeof object == "string") {
                                    // the object is a string
                                    resolve = String;
                                } else if (typeof object.length == "number") {
                                    // the object is array-like
                                    resolve = Array;
                                }
                                resolve.forEach(object, block, context);
                            }
                        };
                    </script>


                </div>
            </div>
        </div>
    </div>

    <br class="clear"/>
    <br class="clear"/>

</div>
<!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>

