<%@ page language="java"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Make all relative href links relative to SummerCamp since root is used by WordPress --%>
<base href="SummerCamp"/>

<t:adminpage>
	<div id="container" class="container">

		<form id="searchForm" action="search">
			<br class="clear"/>

			<label>
				<span>Student first name:</span><input name="studentFirstName" style="cursor: text;" type="text" class="input-field form-control">
			</label>

			<label>
				<span>Student last name:</span><input name="studentLastName" style="cursor: text;" type="text" class="input-field form-control">
			</label>

			<label>
				<span>Parent first name:</span><input name="parentFirstName" style="cursor: text;" type="text" class="input-field form-control">
			</label>

			<label class="searchLabel">
				<span>Parent last name:</span><input name="parentLastName" style="cursor: text;" type="text" class="input-field form-control">
			</label>

			<label class="searchLabel">
				<span>Parent email:</span><input name="parentEmail" style="cursor: text;" type="text" class="input-field form-control">
			</label>

			<input type="submit" name="searchSubmit" placeholder="Search">

		</form>

		<br class="clear"/>

		<table class="table">
			<thead>
				<tr>
					<th style="text-align:center">Student name </th>
					<th style="text-align:center">Parent name </th>
					<th style="text-align:center">Parent email </th>
					<th style="text-align:center">Medical Form </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${results}" var="res" varStatus ="loop">
					<tr>
							<td align="center"> <c:out value="${res.student.firstName} ${res.student.lastName}"/></td>
							<td align="center"> <a href="/SummerCamp/searchredirectparent?email=${res.parent.email}"><c:out value="${res.parent.firstName} ${res.parent.lastName}"/> </a> </td>
							<td align="center"> <c:out value="${res.parent.email}"/> </td>
							<c:choose>
							<c:when test="${res.student.medForm != null}">
								<td align="center"> <a href="generatepdf?studentID=${res.student.studentID}&parentID=${res.parent.parentID}" target="_blank" >Download PDF</a> </td>
						     </c:when>
						     <c:otherwise>
								<td align="center"> <a href="generatepdf?studentID=${res.student.studentID}&parentID=${res.parent.parentID}" target="_blank" >Download PDF (Incomplete )</a>  </td>
							</c:otherwise>
							</c:choose>
						<!-- </form> -->
					</tr>
				</c:forEach>
            		</tbody>
		</table>

	</div>
</t:adminpage>


