<%@tag description="Template for admin page" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="styles" fragment="true" description="See genericpage.tag" %>
<%@attribute name="scripts" fragment="true" description="See genericpage.tag" %>
<%@attribute name="errorMessage" description="Optional error message to be displayed at the top of the page" %>

<t:genericpage>
    <jsp:attribute name="styles">
        <jsp:invoke fragment="styles"/>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <jsp:invoke fragment="scripts"/>
    </jsp:attribute>
    <jsp:body>
        <div class="admin-control-panel-header">
            <h4 style="text-align:center;">Admin Control Panel<br/></h4>
            <br/>
            <p style="text-align:center;">${errorMessage}</p>
        </div>
        <div class="link-container">
            <a class="add-link" href="admincontrolpanel">Home</a>
            <a class="add-link" href="addcamp">Add Camp</a>
            <a class="add-link" href="editstaff">Edit Staff</a>
            <a class="add-link" href="updateloginadmin">Change Login</a>
            <a class="add-link" href="staffassignment">Staff Assignment</a>
            <a class="add-link" href="attendance.jsp">Attendance</a>
            <a class="add-link" href="attendancereport.jsp">Attendance Report</a>
            <a class="add-link" href="statistics">Statistics</a>
            <a class="add-link" href="search">Search</a>
            <a class="add-link" href="universalpickupcode.jsp">UPC</a>
            <a class="add-link" href="emails">Emails</a>
            <a class="logout-link" href="logout">Logout</a>
        </div>
        <br>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>