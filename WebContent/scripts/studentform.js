function validateSForm(e, id, form){
	var cancel = form.submitted;
	if((cancel == null) || (cancel !== 'Cancel')){
	
		e.preventDefault();
	    if(!validateSFormFields(id, form)) {
	        return false;
	    }
	    else{
	    	form.submit();
	    	return true;
	    }
	}
	
	return true;
}

function validateSFormFields(id, form){
	var errormessage = "";
	var lastname_input = $('#lastname'+id).val();
	var firstname_input = $('#firstname'+id).val();
	var gend = form.gender.value;
	if(firstname_input === ""){
		errormessage += "Please enter First Name<br>";
	}
	if(lastname_input === ""){
		errormessage += "Please enter Last Name<br>";
	}
	if (!$(":checkbox[name='ethnicity']", form).is(":checked")){
	
		errormessage += "Please check ethnicity.<br>";
	}
//	if($('#school-dropdown'+id).val() === ""){
//		errormessage += "Please select a school<br>";
//	}
	if ((form.school.value === "-1") && (form.otherSchool.value === "")){
		errormessage += "Please enter the name of your school.<br>";
	}
	if (form.gender.value === ""){

		errormessage += "Please select a gender.<br>";
	}
	if ((form.transportTo.value === "") || (form.transportFrom.value === "")){
	
		errormessage += "Please tell us if your child has transportation.<br>";
	}
	var reducedMealsVal = form.reducedMeals.value;

	if(($( "div.gradereports"+ id).find( "a" ).length === 0
		&& $('#gradereportsfile'+id).val() === '')){
		errormessage += "Please upload grade report.<br>";
	}

	if(reducedMealsVal === ""){
		errormessage += "Please select if your child qualifies for reduced meals.<br>";
	}

	if((reducedMealsVal === "1" || reducedMealsVal === "2")
			&&($( "div.reducedmeals"+ id).find( "a" ).length === 0
					&& $('#reducedmealsfile'+id).val() === '')){
		errormessage += "Please upload reduced meals verification.<br>";

	}

	if(errormessage !== ""){
		$('#errormsgdisplay'+id).html(errormessage);
		$('.message-popup').css("display","inline-block");
		$(".message-popup").slideDown().delay(7000).slideUp();
		return false;
	}
	
	return true;
}