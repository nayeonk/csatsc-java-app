var ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

function validateECFormFields(form){
	var errormessage = "";
	var atLeastOne = false;
	
	if(form.e_fname.value.trim() === ""){
		errormessage += "Please enter your first name<br>";
	} else { atLeastOne = true; }
	if(form.e_lname.value.trim() === ""){
		errormessage += "Please enter your last name<br>";
	}else { atLeastOne = true; }
	
	if(form.e_phone.value.trim() === ""){
		errormessage += "Please enter your phone number<br>";
	}else if (isNaN(form.e_phone.value.trim())) {
		errormessage += "Please enter a valid phone number<br>";
	}else { atLeastOne = true; }

	if(form.e_country.value === ""){
		errormessage += "Please enter your country<br>";
	}else { atLeastOne = true; }

	if(form.e_street.value.trim() === ""){
		errormessage += "Please enter your street address<br>";
	}else { atLeastOne = true; }
	
	if(form.e_city.value.trim() === ""){
		errormessage += "Please enter your city<br>";
	}else { atLeastOne = true; }

	if(form.e_zip.value.trim() === ""){
		errormessage += "Please enter your zipcode<br>";
	}else { atLeastOne = true; }

	if (form.e_country.value === "United States") {
		if(form.e_state.value == 0){
			errormessage += "Please enter your state<br>";
		}else { atLeastOne = true; }
	}

	if (form.e_email.value.trim() != "") {
		if (!validEmail(form.e_email.value)){
			errormessage += "Invalid email. <br>";
		}else { atLeastOne = true; }
	}

	if((errormessage !== "") && atLeastOne){
		$('#errormsgdisplayec').html(errormessage);
		$('.message-popupec').css("display","inline-block");	
		// $(".message-popupec").slideDown().delay(7000).slideUp();
		document.querySelector('#errormsgdisplayec').scrollIntoView({
			behavior: 'smooth',
			block: "center"
		});
		return false;
	} else {
		$('.message-popupec').css("display","none");
	}
	
	return true;
}
function validEmail(email){
	var result = email.match(ePattern);
	return (result != null);
}