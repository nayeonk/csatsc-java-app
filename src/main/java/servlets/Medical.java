package servlets;

import data.*;
import data.MedicalForm.MedicalFormBuilder;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import org.apache.commons.fileupload.FileItem;
import util.FileUploadUtil;
import util.MultiPartFormUtil;
import util.SetPageAttributeUtil;
import util.ValidateFormUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@MultipartConfig
@WebServlet(name = "Medical")
public class Medical extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check authorization
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        // Check parent has authorization
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");
        if (!SecurityChecker.parentHasAccessToStudent(parentID, studentID)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Puts a wrapper on request so can convert input[type=file] to FileItem to store in database
        request = MultiPartFormUtil.parseRequest(request);

        // If GET request or it fails form validation
        if ("GET".equalsIgnoreCase(request.getMethod()) || !ValidateFormUtil.validateMedical(request)) {
            this.populatePage(request, response);
        }
        else {
            this.submitForm(request, response);
        }
    }

    // used for populating form fields in Upload Health Documents page if possible
    protected void populatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");

        // Enters this if condition if navigating to the page, not from an erroneous submission
        if (request.getAttribute("errorMessage") == null) {
            // Set medical form as request variable
            MedicalForm medicalForm = DatabaseQueries.getMedicalForm(studentID);
            if (medicalForm == null) {
                // Medical Form not found, so try to prefill insurance information from another camper
                medicalForm = new MedicalForm();
                List<Student> students = DatabaseQueries.getStudents(parentID, true);
                for (int i = 0; i < students.size(); i++) {
                    MedicalForm oldMedicalForm = students.get(i).getMedForm();
                    if (oldMedicalForm != null) {
                        medicalForm = new MedicalFormBuilder()
                                .insuranceCarrier(oldMedicalForm.getInsuranceCarrier())
                                .nameOfInsured(oldMedicalForm.getNameOfInsured())
                                .policyNumber(oldMedicalForm.getPolicyNumber())
                                .policyPhone(oldMedicalForm.getPolicyPhone())
                                .physicianName(oldMedicalForm.getPhysicianName())
                                .physicianPhone(oldMedicalForm.getPhysicianPhone())
                                .physicianPhoneCellOrWork(oldMedicalForm.getPhysicianPhoneCellOrWork())
                                .build();
                        break;
                    }
                }
            }
            request.setAttribute("medicalForm", medicalForm);
            request.getSession().setAttribute("medicalFormID", medicalForm.getMedicalFormID());

            // If insurance card exists, save ID so can be viewed by parent
            if (medicalForm.getInsuranceCard() != null) {
                request.getSession().setAttribute("insuranceCardID", medicalForm.getInsuranceCard().getInsuranceCardID());
            }

            // Set parent as request variable
            Parent parent = DatabaseQueries.getParentByPID(parentID);
            request.setAttribute(StringConstants.PARENT, parent);

            // Set emergency contact as request variable
            request.setAttribute("ec", DatabaseQueries.getEmergencyContact(parentID));
        }
        else { // Erroneous submission
            DatabaseUpdates.updateStudentProgress(studentID, 1);
            request.setAttribute("medicalForm", saveMedical(request, true));
        }

        // get student and set as session attribute for navbar (and form)
        Student student = DatabaseQueries.getStudent(studentID, true);
        request.getSession().setAttribute("student", student);

        request = SetPageAttributeUtil.setControlPanelAttributes(request);
        request.getRequestDispatcher("/WEB-INF/camper/medical.jsp").forward(request, response);
    }

    protected MedicalForm saveMedical(HttpServletRequest request, boolean isError) {
        // Student Information
        String studentFName = request.getParameter("legal-first-name");
        String studentLName = request.getParameter("legal-last-name");
        Integer gender = Integer.parseInt(request.getParameter("gender"));
        String birthdate = request.getParameter("birthdate");
        String personalPhone = request.getParameter("personal-phone");

        // Legal Guardian Information
        String guardianName = request.getParameter("guardian");
        String guardianAgree = request.getParameter("guardian-agree");

        // Emergency Contact Information
        String ecName = request.getParameter("econtact-name");
        String ecRelationship = request.getParameter("econtact-relationship");
        String ecPhone = request.getParameter("econtact-phone");

        // Insurance Information
        String insuranceCarrier = request.getParameter("insurance-name");
        String nameOfInsured = request.getParameter("insurance-person");
        String policyNumber = request.getParameter("policy-number");
        String policyPhone = request.getParameter("policy-phone");
        String physicianName = request.getParameter("physician");
        String physicianPhone = request.getParameter("physician-phone");

        boolean haveNonPMeds = request.getParameter("medicationznp").equals("1");
        boolean havePMeds = request.getParameter("medicationz").equals("1");
        boolean haveInjuries = request.getParameter("injuriez").equals("1");
        boolean haveSurgeries = request.getParameter("surgeriez").equals("1");
        boolean haveAllergies = request.getParameter("allergyz").equals("1");

        int tetanus = Integer.parseInt(request.getParameter("tetanus"));
        boolean tetanusShot = tetanus == 1; // options are 1, 2, 3

        List<Allergy> allergies = new LinkedList<Allergy>();
        if (haveAllergies) {
            String allergy1 = request.getParameter("allergy1");
            String reaction1 = request.getParameter("reaction1");
            String allergy2 = request.getParameter("allergy2");
            String reaction2 = request.getParameter("reaction2");
            String allergy3 = request.getParameter("allergy3");
            String reaction3 = request.getParameter("reaction3");
            String allergy4 = request.getParameter("allergy4");
            String reaction4 = request.getParameter("reaction4");

            if (!allergy1.isEmpty() || !reaction1.isEmpty())
                allergies.add(new Allergy(allergy1, reaction1));
            if (!allergy2.isEmpty() || !reaction2.isEmpty())
                allergies.add(new Allergy(allergy2, reaction2));
            if (!allergy3.isEmpty() || !reaction3.isEmpty())
                allergies.add(new Allergy(allergy3, reaction3));
            if (!allergy4.isEmpty() || !reaction4.isEmpty())
                allergies.add(new Allergy(allergy4, reaction4));
        }

        List<Medication> prescribedMeds = new LinkedList<Medication>();
        if (havePMeds) {
            String prescribedMed1 = request.getParameter("medication1");
            String dosageMed1 = request.getParameter("dosage1");
            String prescribedMed2 = request.getParameter("medication2");
            String dosageMed2 = request.getParameter("dosage2");
            String prescribedMed3 = request.getParameter("medication3");
            String dosageMed3 = request.getParameter("dosage3");
            String prescribedMed4 = request.getParameter("medication4");
            String dosageMed4 = request.getParameter("dosage4");

            if (!prescribedMed1.isEmpty() || !dosageMed1.isEmpty())
                prescribedMeds.add(new Medication(prescribedMed1, dosageMed1));
            if (!prescribedMed2.isEmpty() || !dosageMed2.isEmpty())
                prescribedMeds.add(new Medication(prescribedMed2, dosageMed2));
            if (!prescribedMed3.isEmpty() || !dosageMed3.isEmpty())
                prescribedMeds.add(new Medication(prescribedMed3, dosageMed3));
            if (!prescribedMed4.isEmpty() || !dosageMed4.isEmpty())
                prescribedMeds.add(new Medication(prescribedMed4, dosageMed4));
        }

        List<Medication> nonPrescribedMeds = new LinkedList<Medication>();
        if (haveNonPMeds) {
            String nonprescribedMed1 = request.getParameter("medicationnp1");
            String nondosageMed1 = request.getParameter("dosagenp1");
            String nonprescribedMed2 = request.getParameter("medicationnp2");
            String nondosageMed2 = request.getParameter("dosagenp2");
            String nonprescribedMed3 = request.getParameter("medicationnp3");
            String nondosageMed3 = request.getParameter("dosagenp3");
            String nonprescribedMed4 = request.getParameter("medicationnp4");
            String nondosageMed4 = request.getParameter("dosagenp4");

            if (!nonprescribedMed1.isEmpty() || !nondosageMed1.isEmpty())
                nonPrescribedMeds.add(new Medication(nonprescribedMed1, nondosageMed1));
            if (!nonprescribedMed2.isEmpty() || !nondosageMed2.isEmpty())
                nonPrescribedMeds.add(new Medication(nonprescribedMed2, nondosageMed2));
            if (!nonprescribedMed3.isEmpty() || !nondosageMed3.isEmpty())
                nonPrescribedMeds.add(new Medication(nonprescribedMed3, nondosageMed3));
            if (!nonprescribedMed4.isEmpty() || !nondosageMed4.isEmpty())
                nonPrescribedMeds.add(new Medication(nonprescribedMed4, nondosageMed4));
        }

        List<String> illnessesInjuries = new LinkedList<String>();
        if (haveInjuries) {
            String injury1 = request.getParameter("injury1");
            String injury2 = request.getParameter("injury2");
            String injury3 = request.getParameter("injury3");
            String injury4 = request.getParameter("injury4");
            if (!injury1.isEmpty())
                illnessesInjuries.add(injury1);
            if (!injury2.isEmpty())
                illnessesInjuries.add(injury2);
            if (!injury3.isEmpty())
                illnessesInjuries.add(injury3);
            if (!injury4.isEmpty())
                illnessesInjuries.add(injury4);
        }

        List<HospitalVisit> hospitalVisits = new LinkedList<HospitalVisit>();

        if (haveSurgeries) {
            String year1 = request.getParameter("year1");
            String reason1 = request.getParameter("reason1");
            String year2 = request.getParameter("year2");
            String reason2 = request.getParameter("reason2");
            String year3 = request.getParameter("year3");
            String reason3 = request.getParameter("reason3");
            String year4 = request.getParameter("year4");
            String reason4 = request.getParameter("reason4");
            if (!year1.isEmpty() || !reason1.isEmpty()) {
                hospitalVisits.add(new HospitalVisit(year1, reason1));
            }
            if (!year2.isEmpty() || !reason2.isEmpty())
                hospitalVisits.add(new HospitalVisit(year2, reason2));
            if (!year3.isEmpty() || !reason3.isEmpty())
                hospitalVisits.add(new HospitalVisit(year3, reason3));
            if (!year4.isEmpty() || !reason4.isEmpty())
                hospitalVisits.add(new HospitalVisit(year4, reason4));
        }

        MedicalForm medicalForm;
        if (isError) {
            medicalForm = new MedicalFormBuilder()
                    .studentFName(studentFName)
                    .studentLName(studentLName)
                    .genderID(gender)
                    .dateOfBirth(birthdate)
                    .parentPhone(personalPhone)
                    .parentPhoneCellOrHome(request.getParameter("phone").equals("2"))
                    .legalGuardianName(guardianName)
                    .legalGuardianAgree(guardianAgree)
                    .ecName(ecName)
                    .ecRelationship(ecRelationship)
                    .ecPhone(ecPhone)
                    .ecPhoneCellOrHome(request.getParameter("ecphone").equals("2"))
                    .insuranceCarrier(insuranceCarrier)
                    .nameOfInsured(nameOfInsured)
                    .policyNumber(policyNumber)
                    .policyPhone(policyPhone)
                    .physicianName(physicianName)
                    .physicianPhone(physicianPhone)
                    .physicianPhoneCellOrWork(request.getParameter("physphone").equals("2"))
                    .tetanusShot(tetanusShot)
                    .allergies(allergies)
                    .prescribedMeds(prescribedMeds)
                    .nonPrescribedMeds(nonPrescribedMeds)
                    .illnessesInjuries(illnessesInjuries)
                    .hospitalVisits(hospitalVisits)
                    .build();

            if (request.getSession().getAttribute("medicalFormID") != null) {
                medicalForm.setMedicalFormID((int) request.getSession().getAttribute("medicalFormID"));
            }

            if (request.getSession().getAttribute("insuranceCardID") != null) {
                int insuranceCardID = (int) request.getSession().getAttribute("insuranceCardID");
                InsuranceCard insuranceCard = DatabaseQueries.getInsuranceCardByInsuranceCardID(insuranceCardID);
                medicalForm.setInsuranceCard(insuranceCard);
            }
        }
        else {
            // Save address from parent information
            int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
            Parent parent = DatabaseQueries.getParentByPID(parentID);
            String street = parent.getAddress().getStreet();
            String country = parent.getAddress().getCountry();
            String city = parent.getAddress().getCity();
            int stateID = parent.getAddress().getStateID();
            String zip = parent.getAddress().getZip();
            Address address = new Address(street, country, city, stateID, zip);

            int addressID = DatabaseQueries.getParentByPID(parentID).getAddressID();
            address.setAddressID(addressID);

            medicalForm = new MedicalFormBuilder()
                    .studentID((int) request.getSession().getAttribute("studentID"))
                    .studentFName(studentFName)
                    .studentLName(studentLName)
                    .genderID(gender)
                    .dateOfBirth(birthdate)
                    .address(address)
                    .parentPhone(personalPhone)
                    .parentPhoneCellOrHome(request.getParameter("phone").equals("2"))
                    .legalGuardianName(guardianName)
                    .legalGuardianAgree(guardianAgree)
                    .ecName(ecName)
                    .ecRelationship(ecRelationship)
                    .ecPhone(ecPhone)
                    .ecPhoneCellOrHome(request.getParameter("ecphone").equals("2"))
                    .insuranceCarrier(insuranceCarrier)
                    .nameOfInsured(nameOfInsured)
                    .policyNumber(policyNumber)
                    .policyPhone(policyPhone)
                    .physicianName(physicianName)
                    .physicianPhone(physicianPhone)
                    .physicianPhoneCellOrWork(request.getParameter("physphone").equals("2"))
                    .tetanusShot(tetanusShot)
                    .allergies(allergies)
                    .prescribedMeds(prescribedMeds)
                    .nonPrescribedMeds(nonPrescribedMeds)
                    .illnessesInjuries(illnessesInjuries)
                    .hospitalVisits(hospitalVisits)
                    .build();
        }
        return medicalForm;
    }

    // used when trying to save medical form
    protected void submitForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");

        // All fields have been validated, so create a new medical form
        int medicalFormID = (int) request.getSession().getAttribute("medicalFormID");
        MedicalForm medicalForm = saveMedical(request, false);
        if (medicalFormID == -1) { // New medical form, so insert new entry in database
            medicalFormID = DatabaseInserts.insertMedicalForm(medicalForm);
        } else { // Updating medical form, so update old entry in database
            medicalForm.setMedicalFormID(medicalFormID);
            DatabaseUpdates.updateMedicalForm(medicalForm);
        }

        // upload insurance card
        FileItem frontInsurance = (FileItem) request.getAttribute("front");
        FileItem backInsurance = (FileItem) request.getAttribute("back");
        if ((frontInsurance != null) && (backInsurance != null)) {
            try {
                FileUploadUtil.uploadInsuranceCard(medicalFormID, parentID, studentID, frontInsurance, backInsurance);
            } catch (Exception e1) {
                System.out.println("insurance card file upload unsuccessful: " + e1);
            }
        }

        request = SetPageAttributeUtil.setControlPanelAttributes(request);

        // update student's progress
        if (((Student) request.getSession().getAttribute("student")).getProgress() == 1) {
            DatabaseUpdates.updateStudentProgress(studentID, 2);
        }

        request.getSession().removeAttribute("medicalFormID");
        request.getSession().setAttribute("OnCampus", true);
        response.sendRedirect("/SummerCamp/campregistration");
    }
}