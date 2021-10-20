var ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

function validateSFormFields(form){
	var errormessage = "";

	if(form.s_fname.value.trim() === ""){
		errormessage += "Please enter your first name<br>";
	} 
	if(form.s_lname.value.trim() === ""){
		errormessage += "Please enter your last name<br>";
	}
	
	if(form.gender.value === ""){
		errormessage += "Please select a gender.<br>";
	}

	if(form.OnCampus.value === ""){
		errormessage += "Please select if you want onCampus classes.<br>";
	}

	if (form.birthdate.value === ""){
		errormessage += "Please enter Date of Birth.<br>";
	}

	if (!$(":checkbox[name='ethnicity']", form).is(":checked")){
		errormessage += "Please check ethnicity.<br>";
	}

	if ($(":checkbox[id='other-ethnicity']", form).is(":checked") && (form.otherEthnicity.value.trim() === "")){
		errormessage += "Please enter ethnicity.<br>";
	}
  
	if (form.emailAddress.value.trim() != "") {
		if (!validEmail(form.emailAddress.value)){
			errormessage += "Invalid email. <br>";
		}
	}

	if ((form.school.value === "-1") && (form.otherSchool.value.trim() === "")){
		errormessage += "Please enter the name of your school.<br>";
	}

	if ((form.transportTo.value === "") || (form.transportFrom.value === "")){
		errormessage += "Please tell us if your child has transportation.<br>";
	}

	if (form.attended.value === ""){
		errormessage += "Please tell us if your child has attended CS@SC before.<br>";
	}

	if (form.experience.value.trim() === ""){
		errormessage += "Please tell us your child's experience with computers or computer programming.<br>";
	}

	if (form.diet.value.trim() === ""){
		errormessage += "Please tell us if your child has dietary restrictions or medical issues.<br>";
	}
	
	if((errormessage !== "")){
		$('#errormsgdisplay').html(errormessage);
		$('.message-popup').css("display","inline-block");
		// $(".message-popupparent").slideDown().delay(7000).slideUp();
		document.querySelector('#errormsgdisplay').scrollIntoView({
			behavior: 'smooth',
			block: "center"
		});
		return false;
	}
	
	return true;
}

function validEmail(email){
	var result = email.match(ePattern);
	return (result != null);
}