<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=DM+Sans&display=swap" rel="stylesheet">

<div class="sidebar .col-12">
    <!-- SIDEBAR FOR DESKTOP -->
    <div class="desktop-nav-container d-none d-md-block">
        <div class="desktop-nav">
            <div class="desktop-nav-contents">
                <div class="row"><h1 id="user">Application Portal</h1></div>
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
                <div class="${fn:contains(pageContext.request.requestURL, 'manageCampers') ? 'selected ' : ''}row "><a
                        href="/SummerCamp/managecampers">Manage Campers</a></div>
                <div class="${fn:contains(pageContext.request.requestURL, 'Applications') ? 'selected ' : ''}row">
                    <a href="/SummerCamp/viewapplications">View Applications</a></div>
                <div class="${fn:contains(pageContext.request.requestURL, 'parentRegistration') ? 'selected ' : ''}row">
                    <a href="/SummerCamp/parentregistration">My Account</a></div>
                <div class="${fn:contains(pageContext.request.requestURL, 'makePaymentParent') ? 'selected ' : ''}row">
                    <a href="/SummerCamp/makepaymentparent">Make Payment</a></div>
            </div>

        </div>


    </div>

    <!-- SIDEBAR FOR MOBILE -->
    <div class="d-md-none">
        <nav class="navbar navbar-dark light-blue lighten-4">
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
                    <div class="${fn:contains(pageContext.request.requestURL, 'manageCampers') ? 'selected ' : ''}row">
                        <a href="/SummerCamp/managecampers">Manage Campers</a></div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'viewapplications') ? 'selected ' : ''}row">
                        <a href="/SummerCamp/viewapplications">View Applications</a></div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'parentRegistration') ? 'selected ' : ''}row">
                        <a href="/SummerCamp/parentregistration">My Account</a></div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'makePaymentParent') ? 'selected ' : ''}row">
                        <a href="/SummerCamp/makepaymentparent">Make Payment</a></div>
                    <div class="${fn:contains(pageContext.request.requestURL, 'logout') ? 'selected ' : ''}row">
                        <a href="/SummerCamp/logout">Logout</a></div>
                </ul>
            </div>
        </nav>
    </div>
</div>

<div class="sidebar-space .col-12 d-none d-md-block">
</div>
