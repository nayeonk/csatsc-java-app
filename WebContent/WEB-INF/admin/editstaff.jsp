<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>
<%--this style was added for the + thing to work for description--%>

<t:adminpage>

    <div id="container" class="container">

		<div class="admin-control-panel">
			<div class="admin-control-panel-header">
				<br/><p style="text-align:center;">${errorMessage}</p>
			</div>
			<div class="admin-control-panel-body">
				<div class="add-container">
					<a class="add-link" href="addstaff"> > Add Staff </a>
				</div>
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th> Name </th>
								<th> Email Address </th>
								<th> Title </th>
								<th> Company </th>
								<th> Instructor </th>
								<th> Admin </th>
								<th> Description </th>
								<th> Action </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${staffList}" var="staff">
								<tr>
									<td><c:out value="${staff.firstName} "/><c:out value="${staff.lastName}"/></td>
									<td><c:out value="${staff.email.email}"/></td>
									<td><c:out value="${staff.title}"/></td>
									<td><c:out value="${staff.company}"/></td>
									<td>
										<c:choose>
											<c:when test="${staff.instructor}">
												&#10004;
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose> </td>
									<td>
										<c:choose>
											<c:when test="${staff.admin}">
												&#10004;
											</c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
									</td>
									<td>
<%--										description jsp has not been added should we just delete this anchor tag?--%>
										<a id="inline" onclick="descriptionPopup(${staff.staffID})"> + </a>
										<div class="popUpText" id="description-${staff.staffID}"> <c:out value="${staff.description}"/></div>
									</td>
									<td>
										<button type="submit" name="delete" value="delete" onClick="deleteConfirmation('editstaff?staffID=${staff.staffID}&delete=delete', 'editstaff')"> Delete </button>
										<button type="submit" name="update" value="update" onClick="location.href = 'updatestaff?staffID=${staff.staffID}';">Update</button>
									</td>
								</tr>
							</c:forEach>
							<script>
								$(document).ready(function() {
									$('a#inline').fancybox();	
								});
								function descriptionPopup(staffID){
									console.log("i am being pressed");
									let staffDescription = "description-" + staffID;
									console.log("staff desc: " + staffDescription);
									let popup = document.getElementById(staffDescription);
									console.log("popup: " + popup);
									popup.classList.toggle("show");
								}
							</script>
							<style>
								.popUpText{
									display:none;
									width:500px;
								}
								.show{
									display: block;
									width:500px;
								}
							</style>
						</tbody>
					</table>
					
				</div>
			</div>
		</div>

		<br class="clear"/>
		<br class="clear"/>
	</div><!-- Ends: #CONTAINER -->
</t:adminpage>


