<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href = "https://fonts.googleapis.com/icon?family=Material+Icons" rel = "stylesheet">
<link href="https://fonts.googleapis.com/css2?family=DM+Sans&display=swap" rel="stylesheet">

<div class="sidebar .col-12">
    <!-- SIDEBAR FOR DESKTOP -->
    <div class="desktop-nav-container d-none d-md-block">
        <div class="desktop-nav">
            <div class="desktop-nav-contents">
                <div class="row"><h1 id="user">CS@SC Application Portal</h1></div>
            </div>
        </div>
    </div>

    <!-- SIDEBAR FOR MOBILE -->
    <div class="d-md-none">
        <nav class="navbar navbar-dark light-blue lighten-4">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">CS@SC</a>
                <button class="navbar-toggler toggler-example" type="button" data-toggle="collapse" data-target="#navbarSupportedContent1" aria-controls="navbarSupportedContent1" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="hamburger-icon">
                        <i class="material-icons">menu</i>
                    </span>
                </button>
            </div>

            <!-- Collapsible content -->
            <div class="collapse navbar-collapse" id="navbarSupportedContent1">
                <!-- Links -->
                <ul class="navbar-nav mr-auto">
                    <div class="row"><a href="/SummerCamp/logout">Logout</a></div>
                </ul>
            </div>
        </nav>
    </div>
</div>

<div class="sidebar-space .col-12 d-none d-md-block">
</div>
