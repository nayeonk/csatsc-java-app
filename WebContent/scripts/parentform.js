function validatePFormFields(form){
	var errormessage = "";
	
	if(form.fname.value.trim() === ""){
		errormessage += "Please enter your first name<br>";
	} 
	if(form.lname.value.trim() === ""){
		errormessage += "Please enter your last name<br>";
	}

	var phoneFormat = new RegExp(/^[\+1]*[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4}$/im);
	if(form.phone.value.trim() === ""){
		errormessage += "Please enter your phone number<br>";
	} else if (isNaN(form.phone.value.trim())) {
		errormessage += "Please enter a valid phone number<br>";
	} else if (!phoneFormat.test(form.phone.value.trim())) {
		errormessage += "Please enter a valid phone number<br>";
	}

	if(form.country.value === ""){
		errormessage += "Please enter your country<br>";
	}

	if(form.street.value.trim() === ""){
		errormessage += "Please enter your street address<br>";
	}
	
	if(form.city.value.trim() === ""){
		errormessage += "Please enter your city<br>";
	}
	if(form.zip.value.trim() === ""){
		errormessage += "Please enter your zipcode<br>";
	}

	if (form.country.value === "United States") {
		if(form.state.value === ""){
			errormessage += "Please enter your state<br>";
		}
	}
	
	if(form.income.value === ""){
		errormessage += "Please enter your income<br>";
	}
	
	if((errormessage !== "")){
		$('#errormsgdisplayparent').html(errormessage);
		$('.message-popupparent').css("display","inline-block");
		document.querySelector('#errormsgdisplayparent').scrollIntoView({
			behavior: 'smooth',
			block: "center"
		});
		return false;
	} else {
		$('.message-popupparent').css("display","none");
	}
	
	return true;
}