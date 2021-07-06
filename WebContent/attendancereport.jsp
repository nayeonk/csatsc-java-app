<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminpage>
    <jsp:attribute name="scripts">
        <script src="dist/scripts/attendanceReport.js"></script>
    </jsp:attribute>
    <jsp:attribute name="styles">
        <link rel="stylesheet" type="text/css" href="css/attendance-report.css" />
    </jsp:attribute>
    <jsp:body>
        <%-- header filters --%>
        <div class="filter-header-container">
            <div class="form-group col-md-2 filter-header-column">
                <label for="start-date">Start Date</label>
                <input class="form-control" type="date" id="start-date"/>
            </div>
            <div class="form-group col-md-2 filter-header-column">
                <label for="end-date">End Date</label>
                <input class="form-control" type="date" id="end-date"/>
            </div>
            <div class="form-group col-md-3">
                <label for="topic">Topic</label>
                <select class="form-control" id="topic" disabled>
                    <option value="-1">Search logs before filtering</option>
                </select>
            </div>
            <div class="form-group col-md-3">
                <label for="level">Level</label>
                <select class="form-control" id="level" disabled>
                    <option value="-1">Search logs before filtering</option>
                </select>
            </div>
            <div class="col-md-2 filter-button-container">
                <button id="button-search-range" type="button" class="btn filter-button">
                    Search Range
                </button>
            </div>
        </div>

        <%-- table body --%>
        <table class="table table-striped table-bordered" id="attendance-table">
            <thead>
                <tr>
                    <th colspan="3"></th>
                    <th colspan="3">Check In</th>
                    <th colspan="3">Check Out</th>
                    <th></th>
                </tr>
                <tr>
                    <th>Attendance Date</th>
                    <th>Student Name</th>
                    <th>U Code Used</th>
                    <th>Signer Name</th>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Signer Name</th>
                    <th>Date</th>
                    <th>Time</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </jsp:body>
</t:adminpage>
