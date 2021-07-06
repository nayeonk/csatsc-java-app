<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminpage>
    <jsp:attribute name="styles">
        <link rel="stylesheet" type="text/css" href="css/emails.css" />
        <link rel="stylesheet" href="https://cdn.quilljs.com/1.0.0/quill.snow.css" />
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="dist/scripts/emailControlPanel.js"></script>
    </jsp:attribute>
    <jsp:body>
        <div class="email-page-container">

            <%--  Email editing --%>
            <form id="email-form" class="student-status-form" method="POST" action="emails">
                <div id="emails-container">

                    <ul class="nav nav-tabs email-types-list" >
                        <li class="active"><a data-toggle="tab" href="#acceptpaid-email"> Accept(Paid) Email </a></li>
                        <li><a data-toggle="tab" href="#acceptfree-email"> Accept(Free) Email </a></li>
                        <li><a data-toggle="tab" href="#rejected-email"> Rejected Email </a></li>
                        <li><a data-toggle="tab" href="#waitlist-email"> Waitlist Email </a></li>
                        <li><a data-toggle="tab" href="#registered-email"> Registered Email </a></li>
                        <li><a data-toggle="tab" href="#enrolledpaid-email"> Confirmed(Paid) Email </a></li>
                        <li><a data-toggle="tab" href="#enrolledfree-email"> Confirmed(Free) Email </a></li>
                        <li><a data-toggle="tab" href="#withdraw-email"> Withdraw Email </a></li>
                    </ul>
                    <div class="tab-content">
                        <c:set var="emailpanes" value="${fn:split('acceptpaid,rejected,acceptfree,waitlist,registered,enrolledpaid,enrolledfree,withdraw', ',')}"/>
                        <c:forEach items="${emailpanes}" var="emailpane">
                            <c:choose>
                                <c:when test="${emailpane == 'acceptpaid'}">
                                    <c:set var="activepane" value="in active"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="activepane" value=""/>
                                </c:otherwise>
                            </c:choose>

                            <div id="${emailpane}-email" class="tab-pane fade ${activepane}">
                                <div>
                                    <c:choose>
                                        <c:when test="${emailpane == 'acceptpaid'}">
                                            <c:set var="emails" value="${acceptPaidEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'rejected'}">
                                            <c:set var="emails" value="${rejectedEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'acceptfree'}">
                                            <c:set var="emails" value="${acceptFreeEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'waitlist'}">
                                            <c:set var="emails" value="${waitlistEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'registered'}">
                                            <c:set var="emails" value="${registeredEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'enrolledpaid'}">
                                            <c:set var="emails" value="${enrolledPaidEmails}"/>
                                        </c:when>
                                        <c:when test="${emailpane == 'enrolledfree' }">
                                            <c:set var="emails" value="${enrolledFreeEmails}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="emails" value="${withdrawEmails}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <p><strong>Content Variables: </strong><c:out value="${emails[0]}"/></p>
                                    <p><strong>Subject Variables: </strong><c:out value="${emails[1]}"/></p>


                                    <h4>Subject:</h4>
                                    <input class="subject-text-area" type="text" name="subject-${emailpane}" value="${emails[2]}" >

                                    <h4>Content:</h4>
                                    <input id="hidden-input-${emailpane}" name="content-${emailpane}" type="hidden">
                                    <div id="${emailpane}-editor" class="content-text-area" name="content-${emailpane}">
                                            ${emails[3]}
                                    </div>

                                </div>
                                <button type="submit" class="save-email-button" name="submit-${emailpane}" value="save">
                                    Save
                                </button>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </form>

            <%-- Button to display all emails  --%>
            <br>
            <div class="buttons show-all-emails-button">
                <button data-toggle="collapse" data-target="#filter-container">Show All Emails</button>
            </div>

            <%-- Displays all emails  --%>
            <div class="collapse" id="filter-container">
                <div class="select-filter">
                    <h4>All Emails</h4>
                    <p>
                        <c:forEach items="${allEmails}" var="email">
                            <c:if test="${email != 'null'}">
                                <c:out value="${email};"/>
                            </c:if>
                        </c:forEach>
                    </p>
                </div>
            </div>
        </div>
    </jsp:body>
</t:adminpage>

