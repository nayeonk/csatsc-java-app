export function validateStudentForm(e, id, form) {
    e.preventDefault();
    if (studentFormIsValid(id, form)) {
        form.submit();
    }
}

function studentFormIsValid(id, form) {
    let errormessage = "";

    if (!$(":checkbox[name='ethnicity']", form).is(":checked")) {
        errormessage += "Please check ethnicity.<br>";
    }

    if ((form.school.value === "-1") && (form.otherSchool.value === "")) {
        errormessage += "Please enter the name of your school.<br>";
    }

    if (form.gender.value === "") {
        errormessage += "Please select a gender.<br>";
    }

    if ((form.transportTo.value === "") || (form.transportFrom.value === "")) {
        errormessage += "Please tell us if your child has transportation.<br>";
    }

    const reducedMealsVal = form.reducedMeals.value;

    if(($( "div.gradereports"+ id).find( "a" ).length === 0
        && $('#gradereportsfile'+id).val() === '')){
        errormessage += "Please upload grade report.<br>";

    }

   if (reducedMealsVal === "") {
        errormessage += "Please select if your child qualifies for reduced meals.<br>";
    }
    if ((reducedMealsVal === "1" || reducedMealsVal === "2")
        && ($("div.reducedmeals" + id).find("a").length === 0
            && $('#reducedmealsfile' + id).val() === '')) {
        errormessage += "Please upload reduced meals verification.<br>";
    }

    if (errormessage !== "") {
        $('#errormsgdisplay' + id).html(errormessage);
        $('.message-popup').css("display", "inline-block");
        $(".message-popup").slideDown().delay(7000).slideUp();
        return false;
    }

    return true;
}