<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Filter --%>
<div class="row">
    <div class="form-group col-md-4">
        <label for="date">Date</label>
        <input class="form-control" type="date" id="date"/>
    </div>
    <div class="form-group col-md-4">
        <label for="topic">Topic</label>
        <select class="form-control" id="topic">
            <option value="-1">No results</option>
        </select>
    </div>
    <div class="form-group col-md-4">
        <label for="level">Level</label>
        <select class="form-control" id="level">
            <option value="-1">No results</option>
        </select>
    </div>
</div>
