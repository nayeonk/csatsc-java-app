<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:instructorpage>
    <jsp:attribute name="styles">
        <style>
            #attendance-students td {
                vertical-align: middle;
            }

            #attendance-students label {
                width: 100%;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="dist/scripts/attendance.js"></script>
    </jsp:attribute>
    <jsp:body>
        <jsp:include page="attendancetemplate.jsp"/>
    </jsp:body>
</t:instructorpage>