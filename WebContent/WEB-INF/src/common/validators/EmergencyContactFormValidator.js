export function validateEmergencyContactForm(e, form) {
    e.preventDefault();
    if (emergencyContactFormIsValid(form)) {
        form.submit();
    }
}

function emergencyContactFormIsValid(form) {
    let errormessage = "";
    let atLeastOne = false;

    if (form.e_fname.value === "") {
        errormessage += "Please enter your first name<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_lname.value === "") {
        errormessage += "Please enter your last name<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_phone.value === "") {
        errormessage += "Please enter your phone number<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_street.value === "") {
        errormessage += "Please enter your street address<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_city.value === "") {
        errormessage += "Please enter your city<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_zip.value === "") {
        errormessage += "Please enter your zipcode<br>";
    } else {
        atLeastOne = true;
    }

    if (form.e_state.value == 0) {
        errormessage += "Please enter your state<br>";
    } else {
        atLeastOne = true;
    }

    if ((errormessage !== "") && atLeastOne) {
        $('#errormsgdisplayec').html(errormessage);
        $('.message-popupec').css("display", "inline-block");
        $(".message-popupec").slideDown().delay(7000).slideUp();
        return false;
    }

    return true;
}