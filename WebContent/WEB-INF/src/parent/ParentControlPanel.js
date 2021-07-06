import * as StudentFormValidator from "../common/validators/StudentFormValidator";
import * as ParentFormValidator from "../common/validators/ParentFormValidator";
import * as EmergencyContactFormValidator from "../common/validators/EmergencyContactFormValidator";

export function initializeFormEventHandlers() {
    for (const form of document.forms) {
        if (form.id.startsWith("updatestudentform")) {
            form.addEventListener("submit", e => {
                StudentFormValidator.validateStudentForm(e, form.dataset.studentId, form);
            });
        } else if (form.id.startsWith("updateparentform")) {
            form.addEventListener("submit", e => {
                ParentFormValidator.validateParentForm(e, form);
            });
        } else if (form.name === "updateemergencycontactform") {
            form.addEventListener("submit", e => {
                EmergencyContactFormValidator.validateEmergencyContactForm(e, form);
            });
        } else if (form.id.startsWith("confirmcamp")) {
            form.addEventListener("submit", e => {
                checkMedical(form.dataset.hasMedical !== undefined, e, form).catch(e => console.error(e));
            });
        }
    }
}

export function initializeGlobalEventHandlers() {
    // Set success or error message to disappear after 10 seconds
    $(".message-popup").slideDown().delay(10000).slideUp();

    // Initialize fancybox (whatever that means)
    $(".fancybox").fancybox();

    // Add event handler for "Update Parent Information" drawer toggle
    $('#dropdown1').on('click', function () {
        if ($('#plusminus').text() == "+") {
            $('#plusminus').html("&ndash;");
        } else {
            $('#plusminus').text("+");
        }
        $('#forms').slideToggle();
    });
}

async function checkMedical(hasMedical, e, form) {
    e.preventDefault();

    const formAction = document.createElement("input");
    formAction.type = "hidden";
    formAction.name = "addThis";

    const action = form.submitted;
    if (action === "Withdraw") {
        if (confirm("Are you sure?")) {
            formAction.value = "Withdraw";
            form.appendChild(formAction);
            form.submit();
        }
    } else if (hasMedical) {
        if (action === "Pay Now!") {
            try {
                const response = await fetch("/SummerCamp/api/validate-payment-session", {
                    method: "POST",
                    body: JSON.stringify({
                        campOfferedId: form.campOfferedID.value,
                        studentId: form.studentID.value,
                        token: form.camp_token.value
                    })
                });

                if (response.ok) {
                    form.action = "https://api.convergepay.com/VirtualMerchant/process.do";
                    form.submit();
                } else {
                    // Errors from backend
                    const errorMessage = await response.json();
                    alert(errorMessage.message);
                }
            } catch (e) {
                // Errors if was not able to reach backend
                console.error(e);
            }
        } else {
            formAction.value = action;
            form.appendChild(formAction);
            form.submit();
        }
    } else {
        alert("Please fill out medical form first before paying or confirming!");
    }
}