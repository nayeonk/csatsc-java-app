function displayErrorForgotPass(error){
	$('#errormsgdisplay').html(error);
	$('.message-popup').css("display","inline-block");	
	 $('#success').css("display","none");	
	  $('#forgot-form').css("display","inline-block");
	  $(".message-popup").slideDown().delay(7000).slideUp();
}

function displayErrorNewPass(error){
	$('#errormsgdisplay').html(error);
	$('.message-popup').css("display","inline-block");	
	 $('#success-form').css("display","none");	
	  $('#new-pass-form').css("display","inline-block");
	  $(".message-popup").slideDown().delay(7000).slideUp();
}

function validateNewPass(event, form){
	event.preventDefault();
	var pass1 = form.password.value;
	var pass2 = form.password2.value;
	if ((pass1 === "") || (pass2 === "")){
		displayErrorNewPass("Please fill out both password fields <br>");
		return false;
	}
	else if (pass1 !== pass2){
		displayErrorNewPass("Passwords do not match <br>");
		return false;
	}
	else if (pass1.length < 6 || (pass1.match(".*\\d+.*") == null)){
		displayErrorNewPass("Password must be at least 6 characters and have at least one number <br>");
	}
	else{
		$.post( "resetpassword", {p1 : pass1, p2 : pass2})
		  .done(function(data) {
			 
			  if (data !== ""){
				  displayErrorNewPass(data+" <br>")
				  return false;
			  }
			  else{
				  $('#success-form').css("display","inline-block");	
				  $('#new-pass-form').css("display","none");
				  $('.message-popup').css("display","none");	
				  return true;
			  }
		  });
	}
	
	return true;
}

function validateForgotPass(event, form){
	event.preventDefault();
	var email = form.email.value;
	if ((email === "")){
		displayErrorForgotPass("No email address was entered <br>");
		return false;
	}
	
	else{
		$.post( "forgotpassword", {e : email})
		  .done(function(data) {
			 
			  if (data !== ""){
				  displayErrorForgotPass(data+" <br>")
				  return false;
			  }
			  else{
				  $('#success').css("display","inline-block");	
				  $('#forgot-form').css("display","none");
				  $('.message-popup').css("display","none");	
				  return true;
			  }
		  });
	}
	
	return true;
}