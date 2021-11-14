<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&display=swap" rel="stylesheet">

<div class="sidebar .col-12">
    <!-- SIDEBAR FOR DESKTOP -->

    <div class="desktop-nav-container d-none d-md-block">
        <div class="desktop-nav">
            <div class="desktop-nav-contents">
                <div class="row back-arrow center-vertical"><a href="/SummerCamp/managecampers" class="center-vertical"><i
                        class="material-icons md-48">keyboard_backspace</i></a></div>
                <div class="row"><h1 id="user">${student.getName()}</h1></div>

                <c:if test="${is_admin_access}">
                    <div class="row adminRow">
                        <div class="alert alert-primary p-1 m-0 mb-2 text-center" role="alert">
                            Accessing as admin<br><strong>${is_admin_access_name}</strong>
                            <hr/>
                            <a class="btn btn-secondary btn-block m-0 p-1" href="/SummerCamp/admincontrolpanel"
                               role="button">Back to Admin Control Panel</a>
                        </div>
                    </div>
                </c:if>

                <div class="${fn:contains(pageContext.request.requestURL, 'mycamps') ? 'selected ' : ''}
                            ${student == null ? 'greyed ' : ''}row">
                    <a href="/SummerCamp/mycamps">My Camps</a>
                </div>

                <div class="${fn:contains(pageContext.request.requestURL, 'Profile') ? 'selected ' : ''}
                            ${student == null ? 'greyed ' : ''}row">
                    <a style="${student == null ? 'color:black;' : ''}" href="/SummerCamp/createstudent">Camper
                        Profile</a>
                </div>

                <div class="${fn:contains(pageContext.request.requestURL, 'scholarship') ? 'selected ' : ''}
                                ${student == null ? 'greyed ' : ''}row">
                    <a href="/SummerCamp/applyforscholarship">Scholarship Application</a>
                </div>
                <div class="${fn:contains(pageContext.request.requestURL, 'medical') ? 'selected ' : ''}
                            ${student == null || student.getProgress() < 1 ? 'greyed ' : ''}row">
                    <a href="/SummerCamp/medical">Medical Information</a>
                </div>
                <div class="${fn:contains(pageContext.request.requestURL, 'Registration') ? 'selected ' : ''}
                            ${student == null || student.getProgress() < 2 ? 'greyed ' : ''}row">
                    <a href="/SummerCamp/campregistration">Apply for Camps</a>
                </div>
                <div class="${fn:contains(pageContext.request.requestURL, 'makePayment.jsp') ? 'selected ' : ''}
                            ${student == null || student.getProgress() < 3 ? 'greyed ' : ''}row"
                     data-toggle="tooltip" data-placement="padding-right" title="Please complete parent profile first">
                    <a href="/SummerCamp/makepayment">Payment</a>
                </div>
            </div>

        </div>
    </div>
    <!-- SIDEBAR FOR MOBILE -->
    <div class="d-md-none">
        <nav class="navbar navbar-light light-blue lighten-4">
            <div class="navbar-header">
                    <div class="mobile-logo">
                        <a target="_blank" href="https://summercamp.usc.edu/"><img src="/SummerCamp/header-logo.png" alt="Logo"></a>
                    </div>

                    <button class="navbar-toggler toggler-example" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent1" aria-controls="navbarSupportedContent1"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="hamburger-icon">
                        <i class="material-icons">menu</i>
                    </span>
                </button>
            </div>

            <!-- Collapsible content -->
            <div class="collapse navbar-collapse" id="navbarSupportedContent1">
                <!-- Links -->
                <ul class="navbar-nav mr-auto">
                    <div class="row back-arrow"><a href="/SummerCamp/managecampers" class="center-vertical"><i
                            class="material-icons md-48">keyboard_backspace</i> Back to Parent Account</a></div>

                    <div class="${fn:contains(pageContext.request.requestURL, 'mycamps') ? 'selected ' : ''}
                                ${student == null ? 'greyed ' : ''}row">
                        <a href="/SummerCamp/mycamps">My Camps</a>
                    </div>

                    <div class="${fn:contains(pageContext.request.requestURL, 'Profile') ? 'selected ' : ''}
                                ${student == null ? 'greyed ' : ''}row">
                        <a href="/SummerCamp/createstudent">Camper Profile</a>
                    </div>

                    <div class="${fn:contains(pageContext.request.requestURL, 'scholarship') ? 'selected ' : ''}
                                ${student == null ? 'greyed ' : ''}row">
                        <a href="/SummerCamp/applyforscholarship">Scholarship Application</a>
                    </div>

<%--                    <div class="${fn:contains(pageContext.request.requestURL, 'medical') ? 'selected ' : ''}--%>
<%--                                ${student == null || student.getProgress() < 1 ? 'greyed ' : ''}row">--%>
<%--                        <a href="/SummerCamp/medical">Medical Information</a>--%>
<%--                    </div>--%>

                    <div class="${fn:contains(pageContext.request.requestURL, 'Registration') ? 'selected ' : ''}
                                ${student == null || student.getProgress() < 2 ? 'greyed ' : ''}row">
                        <a href="/SummerCamp/campregistration">Apply for Camps</a>
                    </div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'makePayment.jsp') ? 'selected ' : ''}
                                ${student == null || student.getProgress() < 3 ? 'greyed ' : ''}row"
                         data-toggle="tooltip" data-placement="padding-right"
                         title="Please complete parent profile first">
                        <a href="/SummerCamp/makepayment">Payment</a>
                    </div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'logout') ? 'selected ' : ''}
                                ${student == null ? 'greyed ' : ''}row">
                        <a href="/SummerCamp/logout">Logout</a>
                    </div>
                </ul>
            </div>
        </nav>
    </div>
</div>

<div class="sidebar-space .col-12 d-none d-md-block">
</div>

<script>
    // disallow clicking into pages when not complete with previous steps
    document.querySelectorAll(".greyed").forEach(item => {
        item.addEventListener('click', event => {
            event.preventDefault();
            window.alert("Please complete previous application steps first.");
        });
    })
</script>