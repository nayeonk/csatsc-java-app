package util;

import data.*;
import database.DatabaseQueries;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetPageAttributeUtil {


    public static HttpServletRequest setParentControlPanelAttributes(HttpServletRequest request, String email) {
        int emailID = DatabaseQueries.doesEmailExist(email);
        return setParentControlPanelAttributes(request, emailID);
    }

    public static HttpServletRequest setMultiChoiceFields(HttpServletRequest request) {
        // CC: Get all student ethnicities.
        List<StudentEthnicity> studentEthnicities = DatabaseQueries.getStudentEthnicities();
        request.setAttribute("studentEthnicities", studentEthnicities);

        // Populate the page with the data needed for the form.
        List<Gender> genderArray = DatabaseQueries.getGenders();
        request.getSession().setAttribute("genderArray", genderArray);
        List<Ethnicity> ethnicityArray = DatabaseQueries.getEthnicities();
        request.setAttribute("ethnicityArray", ethnicityArray);
        List<School> schoolArray = DatabaseQueries.getSchools();
        request.getSession().setAttribute("schoolArray", schoolArray);
        List<Grade> gradeArray = DatabaseQueries.getGrades();
        request.setAttribute("gradeArray", gradeArray);
        List<ReducedMeals> reducedMealsArray = DatabaseQueries.getReducedMeals();
        request.setAttribute("reducedMealsArray", reducedMealsArray);
        List<State> stateArray = DatabaseQueries.getStates();
        request.setAttribute("stateArray", stateArray);
        List<Income> incomeArray = DatabaseQueries.getIncomes();
        request.setAttribute("incomeArray", incomeArray);
        ArrayList<String> countryArray = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryArray.add(obj.getDisplayCountry());
        }
        request.setAttribute("countryArray", countryArray);
        return request;
    }

    public static HttpServletRequest setControlPanelAttributes(HttpServletRequest request) {

        // CC: Populate the page with camp information
        // TODO: ONCE CAMP INFORMATION IS PROPERLY ADDED TO THE DATABASE, FIX THIS
        request = setMultiChoiceFields(request);
        List<Camp> campList = new ArrayList<Camp>();
        List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();

        for (CampTopic campTopic : campTopicList) {
            List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, false);
            campTopic.setCampOfferedList(campOfferedList);

            for (CampOffered c : campTopic.getCampOfferedList()) {

                if (!campList.contains(c)) {
                    campList.add(new Camp(c));
                }
            }
        }

        // TODO: remove this stuff ?
        // CC: Now, we need to get the camps a student has already applied to.
        List<StudentCampApplication> studentCampArray = DatabaseQueries.getStudentCamps();
        request.setAttribute("studentCampArray", studentCampArray);
//        SecureRandom random = new SecureRandom();
//        String token = new BigInteger(130, random).toString(32);
//        request.getSession().setAttribute("tokenStr", token);
        request.setAttribute("campArray", campList);
        return request;
    }

    public static HttpServletRequest setManageCampersAttributes(HttpServletRequest request, Integer emailID) {
        // Set parent session attributes
        Parent parent = DatabaseQueries.getParentByEID(emailID);
        request.getSession().setAttribute("parentID", "" + parent.getParentID());

        // Set student session attributes
        List<Student> studentArray = DatabaseQueries.getStudents(parent.getParentID(), false);
        request.getSession().setAttribute("studentArray", studentArray);

        return request;
    }

    public static HttpServletRequest setParentControlPanelAttributes(HttpServletRequest request, Integer emailID) {

        Parent parent = DatabaseQueries.getParentByEID(emailID);
        request.getSession().setAttribute("parentID", "" + parent.getParentID());
        request.setAttribute(StringConstants.PARENT, parent);

        EmergencyContact contact = DatabaseQueries.getEmergencyContact(parent.getParentID());
        if (contact.getEmergencyContactID() != 0) {
            request.setAttribute("emergency_contact", contact);
        }

        // TODO: only need student IDs and names, don't need the whole student
        List<Student> studentArray = DatabaseQueries.getStudents(parent.getParentID(), true);
        request.setAttribute("studentArray", studentArray);

        for (Student s : studentArray) {
            if (s.getDob() == null) s.setDob("");
        }

        return setControlPanelAttributes(request);
    }

    public static HttpServletRequest setCamperAttributes(HttpServletRequest request, Integer studentID) {
        Student student = DatabaseQueries.getStudent(studentID, true);
        if(student.getEthnicities() != null){
            List<Ethnicity> ethnicities = student.getEthnicities();
            for(Ethnicity e : ethnicities){
                if(e.getEthnicity().equalsIgnoreCase("caucasian")){
                    student.setCaucasian(true);
                }
                if (e.getEthnicity().equalsIgnoreCase("african-american")) {
                    student.setAfricanAmerican(true);
                }
                if (e.getEthnicity().equalsIgnoreCase("asian")) {
                    student.setAsian(true);
                }
                if (e.getEthnicity().equalsIgnoreCase("hispanic")) {
                    student.setHispanic(true);
                }
                if (e.getEthnicity().equalsIgnoreCase("american-indian")) {
                    student.setAmericanIndian(true);
                }
                if (e.getEthnicity().equalsIgnoreCase("other")) {
                    student.setOtherEthnicity(true);
                }
            }
        }

        request.getSession().setAttribute("studentID", studentID);
        request.setAttribute(StringConstants.STUDENT, student);

        return request;
    }
}
