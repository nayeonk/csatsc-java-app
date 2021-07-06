<%--
  Created by IntelliJ IDEA.
  User: neutonfoo
  Date: 11/3/20
  Time: 7:47 PM
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"/>
    <link rel="stylesheet" href="/SummerCamp/css/general.css">
    <link rel="stylesheet" href="/SummerCamp/css/studentNavbar.css">
    <link rel="stylesheet" type="text/css" href="/SummerCamp/css/mycamps.css">
</head>
<body>
<!-- CS@SC Header -->
<iframe src="/SummerCamp/navbar/header.jsp" class="header"></iframe>

<div id="page">
    <!-- Static yellow sidebar -->
    <jsp:include page="../includes/studentNavbar.jsp"/>

    <!-- Main page content -->
    <div id="main">
        <div class="page">
            <h1><b>Make Payment</b></h1>
            <div class="section">
                <h2>Approved Camps</h2>
                <c:choose>
                    <c:when test="${studentAcceptedUnpaidCamps.isEmpty()}">
                        <p>No unpaid camps.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table id="applied-camps" class="table">
                                <thead>
                                <tr>
                                    <th>Camp Name</th>
                                    <th>Date</th>
                                    <th>Days</th>
                                    <th>Time</th>
                                    <th>Status</th>
                                    <th>Cost</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${studentAcceptedUnpaidCamps}" var="camp">
                                    <tr id="${camp.getStudentCampID()}">
                                        <td class="align-middle">${camp.campTopic} ${camp.campLevel}</td>
                                        <td class="align-middle">${camp.getDateString()}</td>
                                        <td class="align-middle">${camp.getCampDays()}</td>
                                        <td class="align-middle">${camp.getTimeString()}</td>
                                        <td class="align-middle">${camp.getStatus()}</td>
                                        <td class="align-middle">$<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${camp.cost / 100.0}" /></td>
                                        <td class="align-middle">
                                            <form class="payment-form" method="post"
                                                  action="https://api.convergepay.com/VirtualMerchant/process.do">
                                                <input type="hidden"
                                                       name="ssl_merchant_id"
                                                       value="726454"/>
                                                <input type="hidden"
                                                       name="ssl_user_id"
                                                       value="campwebpage"/>
                                                <input type="hidden"
                                                       name="ssl_pin"
                                                       value="EYKKTXPIUU2PN1RXZG83BD3A76HJYHZOP428SW528ASOVS378PIEU3OM524RFP7J"/>
                                                <input type="hidden"
                                                       name="ssl_transaction_type"
                                                       value="ccsale"/>
                                                <input type="hidden"
                                                       name="ssl_show_form"
                                                       value="true"/>
                                                <input type="hidden"
                                                       name="ssl_amount"
                                                       value="${camp.getCost() / 100}"/>

                                                <input type="hidden"
                                                       name="camper_first_name"
                                                       value="${student.firstName}"/>
                                                <input type="hidden"
                                                       name="camper_last_name"
                                                       value="${student.lastName}"/>
                                                <input type="hidden"
                                                       name="camp_topic"
                                                       value="${camp.campTopic}"/>
                                                <input type="hidden"
                                                       name="camp_grades"
                                                       value="${camp.getRecommendedGradeLow()} - ${camp.getRecommendedGradeHigh()}"/>
                                                <input type="hidden"
                                                       name="camp_level"
                                                       value="${camp.campLevel}"/>
                                                <input type="hidden"
                                                       name="camp_dates"
                                                       value="${camp.getDateString()}"/>
                                                <input type="hidden"
                                                       name="camp_campID"
                                                       value="${camp.campOfferedID}"/>
                                                <input type="hidden"
                                                       name="camp_studentID"
                                                       value="${student.studentID}"/>
                                                <input type="hidden"
                                                       name="camp_token"
                                                       value="${tokenStr}"/>
                                                <input type="hidden"
                                                       name="ssl_first_name"
                                                       value="${parent.firstName}"/>
                                                <input type="hidden"
                                                       name="ssl_last_name"
                                                       value="${parent.lastName}"/>
                                                <input type="hidden"
                                                       name="ssl_avs_address"
                                                       value="${parent.address.street}"/>

                                                <input type="hidden"
                                                       name="ssl_city"
                                                       value="${parent.address.city}"/>
                                                <input type="hidden"
                                                       name="ssl_state"
                                                       value="${parent.address.state}"/>
                                                <input type="hidden"
                                                       name="ssl_avs_zip"
                                                       value="${parent.address.zip}"/>
                                                <input type="hidden"
                                                       name="ssl_phone"
                                                       value="${parent.phone}"/>
                                                <input type="hidden"
                                                       name="ssl_email"
                                                       value="${parent.email}"/>

                                                <input type="hidden"
                                                       name="ssl_exp_date"
                                                       value=""/>
                                                <input type="hidden"
                                                       name="ssl_cvv2cvc2"
                                                       value=""/>
                                                <input type="hidden"
                                                       name="ssl_invoice_number"
                                                       value="321"/>
                                                <button type="submit" class="btn btn-warning my-0 p-2">Make Payment
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
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

</html>