export function validateParentForm(e, form) {
    e.preventDefault();
    if (parentFormIsValid(form)) {
        form.submit();
    }
}

function parentFormIsValid(form) {
    let errormessage = "";

    if (form.phone.value === "") {
        errormessage += "Please enter your phone number.<br>";
    }

    if (form.street.value === "") {
        errormessage += "Please enter your street address.<br>";
    }

    if (form.city.value === "") {
        errormessage += "Please enter your city.<br>";
    }

    if (form.zip.value === "") {
        errormessage += "Please enter your zipcode.<br>";
    }

    if (form.state.value === "") {
        errormessage += "Please enter your state.<br>";
    }

    if (form.income.value === "") {
        errormessage += "Please enter your income.<br>";
    }

    if ((errormessage !== "")) {
        $('#errormsgdisplayparent').html(errormessage);
        $('.message-popupparent').css("display", "inline-block");
        $(".message-popupparent").slideDown().delay(7000).slideUp();
        return false;
    }

    return true;
}