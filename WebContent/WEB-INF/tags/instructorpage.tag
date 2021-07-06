<%@tag description="Template for instructor page" %>
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
            <a class="add-link" href="instructorattendance.jsp"> Attendance </a>
            <a class="add-link" href="updateloginadmin"> Change Login </a>
            <a class="logout-link" href="logout"> Logout </a>
        </div>
        <br>
        <jsp:doBody/>
    </jsp:body>
</t:genericpage>