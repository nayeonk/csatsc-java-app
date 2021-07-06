package util;

import data.InsuranceCard;
import data.Student;
import database.DatabaseQueries;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class ValidateFormUtil {

    public static boolean validateScholarship(HttpServletRequest request) throws ServletException, IOException {
        String errorMessage = "";

        // Grade Report file
        Student student = (Student) request.getSession().getAttribute("student");
        FileItem gradeReportFileItem = (FileItem) request.getAttribute("gradeReportFile");
        if (gradeReportFileItem == null && student.getGradeReports().size() == 0) {
            errorMessage += "Please upload a picture of your child's grade report. <br><br>";
        }

        // Reduced Meal option
        String reducedMeals = request.getParameter("reducedMeals");
        if (reducedMeals == null) {
            errorMessage += "Please select the type of reduced meal to apply for. <br><br>";
        }

        // Reduced Meal Verification file
        FileItem reducedMealsFileItem = (FileItem) request.getAttribute("reducedMealsVerificationFile");
        if (reducedMealsFileItem == null && student.getReducedMealsVerifications().size() == 0) {
            errorMessage += "Please upload a picture of your child's reduced meal verification. <br><br>";
        }

        if (errorMessage.length() > 0) {
            request.setAttribute("errorMessage", errorMessage);
            return false;
        }
        return true;
    }

    public static boolean validateMedical(HttpServletRequest request) throws ServletException, IOException {
        String errorMessage = "";

        // Student Information
        String studentFName = request.getParameter("legal-first-name");
        if (studentFName.isEmpty()) {
            errorMessage += "Please enter your child's first name. <br><br>";
        }
        String studentLName = request.getParameter("legal-last-name");
        if (studentLName.isEmpty()) {
            errorMessage += "Please enter your child's last name. <br><br>";
        }
        String gender = request.getParameter("gender");
        if (gender.isEmpty()) {
            errorMessage += "Please enter your child's gender. <br><br>";
        }
        String birthdate = request.getParameter("birthdate");
        if (birthdate.isEmpty()) {
            errorMessage += "Please enter your child's date of birth. <br><br>";
        }
        String personalPhone = request.getParameter("personal-phone");
        if (personalPhone.isEmpty()) {
            errorMessage += "Please enter your child's phone number. <br><br>";
        }

        // Legal Guardian Information
        String guardianName = request.getParameter("guardian");
        if (guardianName.isEmpty()) {
            errorMessage += "Please enter the legal guardian's name. <br><br>";
        }
        String guardianAgree = request.getParameter("guardian-agree");
        if (guardianAgree.isEmpty()) {
            errorMessage += "Please confirm that this is the legal guardian. <br><br>";
        }

        // Emergency Contact Information
        String ecName = request.getParameter("econtact-name");
        if (ecName.isEmpty()) {
            errorMessage += "Please enter the emergency contact's name. <br><br>";
        }
        String ecRelationship = request.getParameter("econtact-relationship");
        if (ecRelationship.isEmpty()) {
            errorMessage += "Please enter the emergency contact's relationship to the child. <br><br>";
        }
        String ecPhone = request.getParameter("econtact-phone");
        if (ecPhone.isEmpty()) {
            errorMessage += "Please enter the emergency contact's phone number. <br><br>";
        }

        // Insurance Information
        String insuranceCarrier = request.getParameter("insurance-name");
        if (insuranceCarrier.isEmpty()) {
            errorMessage += "Please enter the insurance carrier's name. <br><br>";
        }
        String nameOfInsured = request.getParameter("insurance-person");
        if (nameOfInsured.isEmpty()) {
            errorMessage += "Please enter the name on the insurance card. <br><br>";
        }
        String policyNumber = request.getParameter("policy-number");
        if (policyNumber.isEmpty()) {
            errorMessage += "Please enter the policy number. <br><br>";
        }
        String policyPhone = request.getParameter("policy-phone");
        if (policyPhone.isEmpty()) {
            errorMessage += "Please enter the policy phone number. <br><br>";
        }
        String physicianName = request.getParameter("physician");
        if (physicianName.isEmpty()) {
            errorMessage += "Please enter the name of the child's physician. <br><br>";
        }
        String physicianPhone = request.getParameter("physician-phone");
        if (physicianPhone.isEmpty()) {
            errorMessage += "Please enter the phone number of the child's physician. <br><br>";
        }

        // Insurance card
        // Check if insurance cards are stored in database - cause for some reason it's extremely hacky to try and grab text from the jsp
        int medicalFormID = -1;
        List<InsuranceCard> insuranceCards = null;
        if (request.getSession().getAttribute("medicalFormID") != null) {
            medicalFormID = (int) request.getSession().getAttribute("medicalFormID");
            insuranceCards = DatabaseQueries.getInsuranceCards(medicalFormID, false);
        }
        // Now check if a file has been uploaded
        FileItem frontInsurance = (FileItem) request.getAttribute("front");
        if (frontInsurance == null && (insuranceCards == null || insuranceCards.size() == 0)) {
            errorMessage += "Please upload a picture of the front side of your insurance card. <br><br>";
        }
        FileItem backInsurance = (FileItem) request.getAttribute("back");
        if (backInsurance == null && (insuranceCards == null || insuranceCards.size() == 0)) {
            errorMessage += "Please upload a picture of the back side of your insurance card. <br><br>";
        }

        String tetanus = request.getParameter("tetanus");
        if (tetanus.isEmpty()) {
            errorMessage += "Please choose an option for the child's tetanus shot. <br><br>";
        }

        boolean haveAllergies = request.getParameter("allergyz").equals("1");
        if (haveAllergies) {
            String allergy1 = request.getParameter("allergy1");
            String reaction1 = request.getParameter("reaction1");
            String allergy2 = request.getParameter("allergy2");
            String reaction2 = request.getParameter("reaction2");
            String allergy3 = request.getParameter("allergy3");
            String reaction3 = request.getParameter("reaction3");
            String allergy4 = request.getParameter("allergy4");
            String reaction4 = request.getParameter("reaction4");

            if ((allergy1.isEmpty() || reaction1.isEmpty()) &&
                    (allergy2.isEmpty() || reaction2.isEmpty()) &&
                    (allergy3.isEmpty() || reaction3.isEmpty()) &&
                    (allergy4.isEmpty() || reaction4.isEmpty())) {
                errorMessage += "You checked yes for allergies, however you did not list any allergies of your child. Please make sure to fill out allergies. <br><br>";
            }
        }

        boolean havePMeds = request.getParameter("medicationz").equals("1");
        if (havePMeds) {
            String prescribedMed1 = request.getParameter("medication1");
            String dosageMed1 = request.getParameter("dosage1");
            String prescribedMed2 = request.getParameter("medication2");
            String dosageMed2 = request.getParameter("dosage2");
            String prescribedMed3 = request.getParameter("medication3");
            String dosageMed3 = request.getParameter("dosage3");
            String prescribedMed4 = request.getParameter("medication4");
            String dosageMed4 = request.getParameter("dosage4");

            if ((prescribedMed1.isEmpty() || dosageMed1.isEmpty()) &&
                    (prescribedMed2.isEmpty() || dosageMed2.isEmpty()) &&
                    (prescribedMed3.isEmpty() || dosageMed3.isEmpty()) &&
                    (prescribedMed4.isEmpty() || dosageMed4.isEmpty())) {
                errorMessage += "You checked yes for prescribed medications, however you did not list any medications of your child. Please make sure to fill out prescribed medications.<br><br>";
            }
        }

        boolean haveNonPMeds = request.getParameter("medicationznp").equals("1");
        if (haveNonPMeds) {
            String nonprescribedMed1 = request.getParameter("medicationnp1");
            String nondosageMed1 = request.getParameter("dosagenp1");
            String nonprescribedMed2 = request.getParameter("medicationnp2");
            String nondosageMed2 = request.getParameter("dosagenp2");
            String nonprescribedMed3 = request.getParameter("medicationnp3");
            String nondosageMed3 = request.getParameter("dosagenp3");
            String nonprescribedMed4 = request.getParameter("medicationnp4");
            String nondosageMed4 = request.getParameter("dosagenp4");

            if ((nonprescribedMed1.isEmpty() || nondosageMed1.isEmpty()) &&
                    (nonprescribedMed2.isEmpty() || nondosageMed2.isEmpty()) &&
                    (nonprescribedMed3.isEmpty() || nondosageMed3.isEmpty()) &&
                    (nonprescribedMed4.isEmpty() || nondosageMed4.isEmpty())) {
                errorMessage += "You checked yes for non-prescribed medications, however you did not list any medications of your child. Please make sure to fill out non-prescribed medications.<br><br>";
            }
        }

        boolean haveInjuries = request.getParameter("injuriez").equals("1");
        if (haveInjuries) {
            String injury1 = request.getParameter("injury1");
            String injury2 = request.getParameter("injury2");
            String injury3 = request.getParameter("injury3");
            String injury4 = request.getParameter("injury4");

            if (injury1.isEmpty() && injury2.isEmpty() && injury3.isEmpty() && injury4.isEmpty()){
                errorMessage += "You checked yes for illnesses and injuries, however you did not list any for your child. Please make sure to fill out any injuries or illnesses. <br><br>";
            }
        }

        boolean haveSurgeries = request.getParameter("surgeriez").equals("1");
        if (haveSurgeries) {
            String year1 = request.getParameter("year1");
            String reason1 = request.getParameter("reason1");
            String year2 = request.getParameter("year2");
            String reason2 = request.getParameter("reason2");
            String year3 = request.getParameter("year3");
            String reason3 = request.getParameter("reason3");
            String year4 = request.getParameter("year4");
            String reason4 = request.getParameter("reason4");

            if ((year1.isEmpty() || reason1.isEmpty()) && (year2.isEmpty() || reason2.isEmpty()) &&
                    (year3.isEmpty() || reason3.isEmpty()) && (year4.isEmpty() || reason4.isEmpty())) {
                errorMessage += "You checked yes for surgeries or hospital visits, however you did not list any for your child. Please make sure to fill out any surgeries or hospital visits. <br>";
            }
        }

        if (errorMessage.length() > 0) {
            request.setAttribute("errorMessage", errorMessage);
            return false;
        }
        return true;
    }
}
