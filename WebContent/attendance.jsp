<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:adminpage>
    <jsp:attribute name="styles">
        <style>

            #attendance-students tr th.first-name {
                width: 15%;
            }

            #attendance-students tr th.last-name {
                width: 15%;
            }

            #attendance-students tr th.camp {
                width: 20%;
            }

            #attendance-students tr th.check-in {
                width: 25%;
            }

            #attendance-students tr th.check-out {
                width: 25%;
            }

            #attendance-students td {
                vertical-align: middle;
            }

            #attendance-students label {
                /*width: 100%;*/
                margin-left: 2px;
            }

            /*#attendance-students .checkout {*/
            /*    display: flex;*/
            /*    align-items: center;*/
            /*    justify-content: space-between;*/
            /*}*/
        </style>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="dist/scripts/attendance.js"></script>
    </jsp:attribute>
    <jsp:body>
        <jsp:include page="attendancetemplate.jsp"/>
    </jsp:body>
</t:adminpage>

