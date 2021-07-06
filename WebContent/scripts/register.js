var ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";



function validateLogin(event, form){
	event.preventDefault();
	var email = form.email.value;
	var pass = form.password.value;
	var errormessage = "";
	
	if ((email === "")){
		errormessage += "No email address was entered <br>";
	}
	if ((pass === "")){
		errormessage += "No password was entered <br>";
	}
	if (errormessage === ""){
		$.post( "uniqueemail", {e : email, password: pass, purpose : "login"})
		  .done(function(data) {
			  if (data !== ""){
				  $('#errormsgdisplaylogin').html(data+"<br>");
				  $('.message-popup-login').css("display","inline-block");
				  $(".message-popup-login").slideDown().delay(7000).slideUp();
				  return false;
			  }
			  else{
				  form.submit();
				  return true;
			  }
		  });

		  //form.submit();
	}
	else{
		$('#errormsgdisplaylogin').html(errormessage);
		$('.message-popup-login').css("display","inline-block");
		$(".message-popup-login").slideDown().delay(7000).slideUp();
		return false;
	}
}



function validEmail(email){
	var result = email.match(ePattern);
	return (result != null);
}

function validateCreateAccount(event, form){
	console.log('hi');
	event.preventDefault();
	
	var email = form.email.value;
	var unique = true;
	if (email !== ""){
		$.post( "uniqueemail", {e : email, purpose : "create"})
		  .done(function(data) {
		    unique = (data == -1);
		    return continueValidation(form, unique);
		  });
	}
	else{
		return continueValidation(form, unique);
	}
	
}
function continueValidation(form, unique){
	var email = form.email.value;
	var email2 = form.email2.value;
	var pass = form.password.value;
	var pass2 = form.password2.value;
	var errormessage = "";
	
	if ((email !== "") && !unique){
		errormessage += "Email address already exists <br>";
	}
	else if((email === "") || (email2 === "") || (pass === "") || (pass2 === "")){
		errormessage += "Please fill out both email and password fields<br>";
	}
	else{
		if (!validEmail(email)){
			errormessage += "Invalid email <br>";
		}
		if (email.toLowerCase() !== email2.toLowerCase()){
			errormessage += "Emails do not match <br>";
		}
		//check length, all number, or all letter
		if (pass.length < 6 || (pass.match(".*\\d+.*") == null) || (/^-?\d+$/.test(pass))){
			errormessage+= "Password must be at least 6 characters and have at least one number and at least one letter <br>";
		}
		if (pass !== pass2){
			errormessage += "Passwords do not match <br>";
		}
		
	}
	
	if(errormessage !== ""){
		$('#errormsgdisplayreg').html(errormessage);
		$('.message-popup-register').css("display","inline-block");	
		// $(".message-popup-register").slideDown().delay(7000).slideUp();
		return false;
	}
	else{
		form.submit();
		return true;
	}
	
}