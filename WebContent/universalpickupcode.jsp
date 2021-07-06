<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:adminpage>
    <jsp:attribute name="scripts">
        <script src="dist/scripts/universalPickupCode.js"></script>
    </jsp:attribute>
    <jsp:attribute name="styles">
        <link rel="stylesheet" type="text/css" href="css/universalpickupcode.css" />
    </jsp:attribute>
    <jsp:body>
        <div id="upc-background">
            <div id="upc-container">
                <div class="header">
                    <h3 id="header-title">UPC List</h3>
                    <div id="input-container">
                        <input id="upc-text-input" class="form-control" type="text" placeholder="New UPC...">
                        <div id="button-container">
                            <button id="button-add-upc" type="button" class="btn">
                                Add
                            </button>
                        </div>
                    </div>
                </div>
                <ul id="upc-list" class="list-group">
                </ul>
            </div>
        </div>
    </jsp:body>
</t:adminpage>
