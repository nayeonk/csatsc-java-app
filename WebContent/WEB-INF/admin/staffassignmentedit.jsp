<%@ page language="java"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<jsp:include page="../includes/header.jsp"/>

    <div id="container" class="container">
		<br class="clear"/>
		<div class="admin-control-panel">
			<div class="admin-control-panel-header">
				<h4 style="text-align:center;">Edit Staff Assignment<br/></h4>
				<br/><p style="text-align:center;">${errorMessage}</p>
			</div>
			<div class="link-container">
			    <a href="staffassignment"> < Back </a>
				<a class="logout-link" href="logout"> Logout </a>
			</div>
			<br>
				<div id="success-container"class="message-popup hidden" style="text-align:center;z-index:999999999;">
					<div class="success-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(0, 255, 0, 0.25); padding:10px; display:inline-block;">
						<p id="success" class="message-text-success" style="line-height:16pt; text-align:center; color:#00cc00; font-weight:bold; font-size:11pt; padding-top:6px;">TA Deletion Successful</p>
					</div>

					<br />
					<br />
				</div>
				<script>
					$(".message-popup").slideDown().delay(7000).slideUp();
				</script>
					<div id="error-container" class="message-popup hidden" style="text-align:center;z-index:999999999;">
						<div class="error-message" style="width:300px; text-align:center; border-radius:5px; background-color:rgba(255, 0, 0, 0.25); padding:5px; display:inline-block;">
							<p id = "error" class="message-text-error" style="line-height:16pt; text-align:center; color:#ff0000; font-weight:bold; font-size:11pt; padding-top:6px;">Error: TA not deleted</p>
						</div>

						<br />
						<br />
					</div>
					<script>
						$(".message-popup").slideDown().delay(7000).slideUp();
					</script>
			<br>
			
			<div class="admin-control-panel-body">
				<form action="/SummerCamp/staffassignmentedit" method="POST" name="checkoutform">
				  	<label for="name"><span>Full Name:</span>
						<% String nameEntered = "";
						if(request.getAttribute("name") != null){
							nameEntered = (String)request.getAttribute("name");
						}%>
					<input type="text" class="input-field" list="taList" name="name" value=<%= nameEntered%>></label>
					<datalist id="taList">
						<c:forEach items="${allTAList}" var="taList" varStatus ="TAloop">
							<option value="${taList.firstName} ${taList.lastName}"></option>
						</c:forEach>
					</datalist>
					<input type="hidden" name="campid" value="${campid}">
					<label><span>&nbsp;</span><input class="form-control" type="submit" value="Add TA" /></label>
				</form>

				<table class="table">
					<thead>
						<tr>
							<th style="text-align:center"> First Name </th>
							<th style="text-align:center"> Last Name </th>
							<th style="text-align:center"> Delete </th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${taList}" var="ta" varStatus ="loop">
							<tr class="offered-camp">
								<td id="taID${loop.index}" align="center" style="display:none"> <c:out value="${ta.staffID}"/> </td> 
								<td align="center"> <c:out value="${ta.firstName}"/> </td>
								<td align="center"> <c:out value="${ta.lastName}"/> </td>
								<td align="center"> <button id="button${loop.index}" onclick="deleteTA(${loop.index}, ${campid})">Delete</button> </td>
							</tr>
						</c:forEach>
              		</tbody>
				</table>
			</div>
		</div>

		<br class="clear"/>
		<br class="clear"/>

	</div><!-- Ends: #CONTAINER -->

<jsp:include page="../includes/footer.jsp"/>
<script type="text/javascript">
    function deleteTA(index, campid){
		var taID = document.getElementById("taID"+index).innerText;
    		var xhttp = new XMLHttpRequest();
    		xhttp.onreadystatechange = function() {
	        if (this.readyState == 4 && this.status == 200) {
	        		if(this.responseText === "success"){
	        			document.getElementById("error-container").classList.add("hidden");
	        			document.getElementById("success-container").classList.remove("hidden");
	        			document.getElementById("button"+index).classList.add("hidden");
	        		} else {
	        			document.getElementById("error-container").classList.remove("hidden");
	        			document.getElementById("success-container").classList.add("hidden");
	        		}    
 	        }
    		};
    		xhttp.open("POST", "staffassignmentedit?taid=" + taID + "&campid=" + campid);
    		xhttp.send();
    }
</script>
