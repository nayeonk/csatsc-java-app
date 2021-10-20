package util;

import data.*;
import data.EmergencyContact.EmergencyContactBuilder;
import data.Parent.ParentBuilder;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import org.apache.commons.fileupload.FileItem;
import servlets.CreateStudent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ModifyDataUtil {


    public static String reqParam(HttpServletRequest request, String key) throws ServletException, IOException {
        return request.getParameter(key);
    }

    private static String getGender(Integer genderID) {
        String gender = "";
        if (genderID != null) {
            if (genderID == 1) {
                gender = "Male";
            } else if (genderID == 2) {
                gender = "Female";
            } else {
                gender = "Other";
            }
        }
        return gender;
    }

    public static Student buildStudent(HttpServletRequest request) throws IOException, ServletException {
        // Student Information
        String firstName = reqParam(request, "s_fname");
        // allow empty firstnames/lastnames for erroneous submissions
        firstName = firstName.isEmpty() ? "" : firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        String middleName = reqParam(request, "s_mname"); //
        if (middleName != null && middleName.length() > 0) {
            middleName = middleName.substring(0, 1).toUpperCase() + middleName.substring(1);
        }
        String lastName = reqParam(request, "s_lname");
        lastName = lastName.isEmpty() ? "" : lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        String preferredName = reqParam(request, "s_pname"); //
        if (preferredName != null && preferredName.length() > 0) {
            preferredName = preferredName.substring(0, 1).toUpperCase() + preferredName.substring(1);
        }
        String gender = reqParam(request, "gender");
        String dateOfBirth = reqParam(request, "birthdate");
        String[] ethnicityIDs = request.getParameterValues("ethnicity");
        String emailAddress = reqParam(request, "emailAddress"); //
        String school = reqParam(request, "school");
        String otherSchool = reqParam(request, "otherSchool");
        Integer genderID = (gender == null) ? null : Integer.parseInt(gender); //gender nothing rn

        // Other Stuff
        String allGirls = "no";

        // Transportation
        String transportTo = reqParam(request, "transportTo");
        String transportFrom = reqParam(request, "transportFrom");

        // Additional Information
        String attended = reqParam(request, "attended");
        String OnCampus = reqParam(request, "OnCampus");
        String experience = reqParam(request, "experience");
        String diet = reqParam(request, "diet");
        String otherInfo = reqParam(request, "otherInfo");

        Boolean yesGirls = gender != null && allGirls != null && gender.equals("Female") && allGirls.equals("yes");

        return new Student.StudentBuilder()
                .name(firstName, middleName, lastName, preferredName)
                .gender(new Gender(genderID, getGender(genderID)))
                .dob(dateOfBirth)
                .ethinictyIDs(ethnicityIDs)
                .emailAddress(emailAddress)
                .school(new School((school == null ? null : Integer.parseInt(school))))
                .otherSchool(otherSchool)
                .transportTo(transportTo == null ? null : transportTo.equals("yes"))
                .transportFrom(transportFrom == null ? null : transportFrom.equals("yes"))
                .attended(attended == null ? null : attended.equals("yes"))
                .OnCampus(OnCampus == null? null: OnCampus.equals("yes"))
                .experience(experience)
                .diet(diet)
                .otherInfo(otherInfo)
                .interestedGirlsCamp(yesGirls)
                .studentID(-1)
                .lastUpdatedYear(Calendar.getInstance().get(Calendar.YEAR))
                .build();

    }

    public static void createReducedMeals(HttpServletRequest request, int studentID) throws IOException, ServletException {
        String reducedMeals = reqParam(request, "reducedMeals");
        Integer reducedMealsID = reducedMeals == null ? null : Integer.parseInt(reducedMeals);
        reducedMeals = getReducedMeals(reducedMealsID);
        if (reducedMeals != null) {
            Student student = DatabaseQueries.getStudent(studentID, true);
            if (student != null) {
                student.setReducedMeals(new ReducedMeals(reducedMealsID, reducedMeals));
                DatabaseUpdates.modifyStudent(student);
            } else {
                System.out.println("Error in finding student by studentID: " + studentID);
            }
        }
    }

    public static String uploadScholarshipFiles(HttpServletRequest request, int studentID) throws IOException, ServletException {
        String errorMessage = "";
        FileItem gradeReportFileItem = (FileItem) request.getAttribute("gradeReportFile");
        FileItem reducedMealsFileItem = (FileItem) request.getAttribute("reducedMealsVerificationFile");
        Student student = DatabaseQueries.getStudent(studentID, true);
        Integer parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));

        // upload grade report
        if (gradeReportFileItem != null) {
            try {
                String fileLoc = FileUploadUtil.uploadFile(StorageDirectory.GRADE_REPORTS, parentID, student.getStudentID(), gradeReportFileItem);
                DatabaseInserts.insertGradeReport(student.getStudentID(), fileLoc);
                student.setGradeReports(DatabaseQueries.getGradeReports(student));
            } catch (Exception e1) {
                System.out.println(e1);
                errorMessage += "Grade report file upload unsuccessful.";
                System.out.println(errorMessage);
            }
        }

        // upload reduced meals file
        if (reducedMealsFileItem != null) {
            try {
                String fileLoc = FileUploadUtil.uploadFile(StorageDirectory.REDUCED_MEALS, parentID, student.getStudentID(), reducedMealsFileItem);
                DatabaseInserts.insertReducedMealsVerification(student.getStudentID(), fileLoc);
                student.setReducedMealsVerifications(DatabaseQueries.getReducedMealsVerifications(student));
            } catch (Exception e1) {
                System.out.println(e1);
                errorMessage += "Reduced meals verification file upload unsuccessful.";
                System.out.println(errorMessage);
            }
        }

        if (errorMessage.length() == 0) {
            request.setAttribute("lastUpdatedYear", Calendar.getInstance().get(Calendar.YEAR));
        }

        return errorMessage;
    }

    private static String getReducedMeals(Integer reducedMealsID) {
        String reducedMeals = null;
        if (reducedMealsID != null) {
            switch (reducedMealsID) {
                case 1:
                    reducedMeals = "Free Meals";
                    break;
                case 2:
                    reducedMeals = "Reduced Meals";
                    break;
                case 3:
                    reducedMeals = "None";
                    break;
                default:
                    reducedMeals = "Prefer not to enter";
                    break;
            }
        }
        return reducedMeals;
    }

    public static String createOrUpdateStudent(HttpServletRequest request, int parentID, Student student, Boolean isUpdate)
            throws IOException, ServletException {
        String errorMessage = "";

        Map<Integer, Ethnicity> allEthnicities = DatabaseQueries.getMappedEthnicities();
        List<Ethnicity> studentEthnicities = new ArrayList<Ethnicity>();
        List<StudentEthnicity> ethnicitiesToStore = new ArrayList<StudentEthnicity>();
        String otherEthnicity = ModifyDataUtil.reqParam(request, "otherEthnicity");

        for (String e : student.getEthnicityIDS()) {
            //e is like a string like caucasian need to convert to id
            //tried using switch case but not sure how to use .equals in that
            Integer id = 1;
            if (e.equalsIgnoreCase("caucasian")) {
                id = 1;
            } else if (e.equalsIgnoreCase("african-american")) {
                id = 2;
            } else if (e.equalsIgnoreCase("asian")) {
                id = 3;
            } else if (e.equalsIgnoreCase("hispanic")) {
                id = 4;
            } else if (e.equalsIgnoreCase("american-indian")) {
                id = 5;
            } else if (e.equalsIgnoreCase("other")) {
                id = 6;
            } else {
                id = 6;
            }

            studentEthnicities.add(allEthnicities.get(id));

            StudentEthnicity se = new StudentEthnicity();
            se.setEthnicityID(id);
            // CC: Check to see if other was selected
            if (id == 6) {
                se.setOtherEthnicity((otherEthnicity == null) ? "Other" : otherEthnicity);
            }

            ethnicitiesToStore.add(se);
        }

        //reduced meals is missing in the form
        //Boolean reducedMeals = student.getReducedMeals().getReducedMealsID() == 1 || student.getReducedMeals().getReducedMealsID() == 2;
        Boolean reducedMeals = false;
        student.setEthnicities(studentEthnicities);

        //Creating student
        if (!isUpdate) {
            student.setStudentID(DatabaseInserts.insertStudent(student));
            request.getSession().setAttribute("studentID", student.getStudentID());

            if (!student.getOtherInfo().equals("")) {
                DatabaseInserts.insertOtherInfo(student.getStudentID(), student.getOtherInfo());
            }

            StudentParent studentParent = new StudentParent(student.getStudentID(), parentID);
            studentParent.setStudentParentID(DatabaseInserts.insertStudentParent(studentParent));
            request.getSession().setAttribute("studentCreated", "" + student.getStudentID());

            if (reducedMeals) {
                CreateStudent.reducedMealsStack.add(student);
            }
            //after you create now u want to update not create new ones
            request.getSession().setAttribute("isUpdate", "yes");
        }
        //Update student
        else {
            DatabaseUpdates.modifyStudent(student);
            // JC: Update OtherInfo table
            DatabaseUpdates.modifyOtherInfo(student.getStudentID(), student.getOtherInfo());
            // CC: Now, update our student ethnicities
            DatabaseUpdates.invalidateStudentEthnicities(student.getStudentID());

        }

        // CC: Now, insert our student ethnicities
        DatabaseInserts.insertStudentEthnicity(student.getStudentID(), ethnicitiesToStore);

        if (errorMessage.length() == 0) {
            request.setAttribute("lastUpdatedYear", Calendar.getInstance().get(Calendar.YEAR));
        }

        return errorMessage;
    }

    public static String createOrUpdateEC(EmergencyContact ec, Boolean isUpdate) {
        String errorMessage = "";

        Boolean atLeastOneNotFilled = (ec.getFirstName() == null) || (ec.getFirstName().length() == 0) ||
                (ec.getLastName() == null) || (ec.getLastName().length() == 0) ||
                (ec.getPhone() == null) || (ec.getPhone().length() == 0) || (ec.getAddress().getCountry() == null) ||
                (ec.getAddress().getStreet() == null) || (ec.getAddress().getStreet().length() == 0) ||
                (ec.getAddress().getCity() == null) || (ec.getAddress().getCity().length() == 0) ||
                (ec.getAddress().getZip() == null) || (ec.getAddress().getZip().length() == 0) ||
                (ec.getAddress().getCountry() == "United States" && (ec.getAddress().getStateID() == 0));

        Boolean atLeastOneFilled = ((ec.getFirstName() != null) && (ec.getFirstName().length() > 0)) ||
                ((ec.getLastName() != null) && (ec.getLastName().length() > 0)) ||
                ((ec.getPhone() != null) && (ec.getPhone().length() > 0)) ||
                ((ec.getAddress().getCountry() != null) && (ec.getAddress().getCountry().length() > 0)) ||
                ((ec.getAddress().getStreet() != null) && (ec.getAddress().getStreet().length() > 0)) ||
                ((ec.getAddress().getCity() != null) && (ec.getAddress().getCity().length() > 0)) ||
                ((ec.getAddress().getZip() != null) && (ec.getAddress().getZip().length() > 0)) ||
                (ec.getAddress().getCountry() == "United States" && (ec.getAddress().getStateID() != 0));

        if (atLeastOneNotFilled && atLeastOneFilled) {
            errorMessage = "All fields for Parent 2 must be filled if you choose to include a second parent.";
        } else if (atLeastOneFilled) {
            EmergencyContact ecOld = null;
            if (isUpdate) {
                ecOld = DatabaseQueries.getEmergencyContact(ec.getParentID());
                isUpdate = isUpdate && (ecOld.getEmergencyContactID() != 0);
            }

            int ecEmailID = (ecOld == null) ? 0 : ecOld.getEmailID();

            if (ec.getEmail() != null && ec.getEmail().length() > 0) {
                ecEmailID = DatabaseQueries.doesEmailExist(ec.getEmail());
                if (!ValidateEmail.isValidEmailAddress(ec.getEmail())) {
                    //If email address is invalid
                    errorMessage = "Email address is invalid.";
                } else {

                    if (ecEmailID == -1) {
                        ecEmailID = DatabaseInserts.insertEmail(ec.getEmail());
                    }
                }
            }

            ec.setEmailID(ecEmailID);


            if (isUpdate) {
                int addressID = ecOld.getAddressID();
                ec.setAddressID(addressID);
                DatabaseUpdates.modifyAddress(ec.getAddress(), addressID);
                DatabaseUpdates.modifyEmergencyContact(ec);
                System.out.println("AddressID " + addressID);
            } else {
                int ecID = DatabaseInserts.insertEmergencyContact(ec);
                ec.setEmergencyContactID(ecID);
            }
        }

        return errorMessage;
    }


    public static Parent buildParent(HttpServletRequest request, int parentID) {
        String state = request.getParameter("state");
        String income = request.getParameter("income");
        Integer inc = (income == null || income.equals("")) ? 0 : Integer.parseInt(income);

        int stateID = (state == null || state.isEmpty()) ? 0 : Integer.parseInt(state);

        Address address = new Address(request.getParameter("street"), request.getParameter("country"), request.getParameter("city"), stateID, request.getParameter("zip"));

        Parent.ParentBuilder builder = new ParentBuilder()
            .name(request.getParameter("fname"), request.getParameter("lname"))
            .phone(request.getParameter("phone"))
            .parentID(parentID)
            .address(address)
            .incomeID(inc);

        return builder.build();
    }

    public static EmergencyContact buildEmergencyContact(HttpServletRequest request, int parentID) {
        String emergencyState = request.getParameter("e_state");
        //String emergencyEmail = ;
        EmergencyContactBuilder ecBuilder = new EmergencyContactBuilder();
        int emergencyStateID = (emergencyState == null || emergencyState.isEmpty()) ? 0 : Integer.parseInt(emergencyState);

        Address emergencyAddress = new Address(request.getParameter("e_street"), request.getParameter("e_country"), request.getParameter("e_city"), emergencyStateID, request.getParameter("e_zip"));
        ecBuilder.name(request.getParameter("e_fname"), request.getParameter("e_lname"));
        ecBuilder.phone(request.getParameter("e_phone"));
        ecBuilder.parentID(parentID);
        ecBuilder.address(emergencyAddress);
        ecBuilder.email(request.getParameter("e_email"));
        return ecBuilder.build();
    }


    public static String modifyParent(Parent p, boolean insertIncome) {
        String errorMessage = "";

        if (p.getFirstName() == null || p.getLastName() == null || p.getPhone() == null || p.getAddress().getStreet() == null ||
                p.getAddress().getCity() == null || p.getAddress().getZip() == null ||
                p.getIncomeID() == 0 || p.getFirstName().length() == 0 || p.getLastName().length() == 0 ||
                p.getPhone().length() == 0 || p.getAddress().getStreet().length() == 0 || p.getAddress().getCity().length() == 0 ||
                p.getAddress().getZip().length() == 0 || p.getAddress().getCountry() == null) {
            errorMessage = "All fields for the first parent must be filled.";
        } else if (p.getAddress().getCountry() == "United States" && p.getAddress().getStateID() == 0) {
            errorMessage = "Please input a state.";
        } else {
            Parent oldParent = DatabaseQueries.getParentByPID(p.getParentID());

            DatabaseUpdates.modifyAddress(p.getAddress(), oldParent.getAddressID());
            p.setEmailID(oldParent.getEmailID());
            p.setAddressID(oldParent.getAddressID());
            if (p.getEmail() == null || p.getEmail().isEmpty()) {
                p.setEmail(oldParent.getEmail());
            }

            if (p.getParentID() == -1) {
                errorMessage = "Failed to recover parent from the database.";
            } else {
                DatabaseUpdates.modifyParent(p);

                if (insertIncome) {
                    DatabaseInserts.insertParentIncome(p.getParentID(), p.getIncomeID());
                } else {
                    DatabaseUpdates.modifyParentIncome(p.getParentID(), p.getIncomeID());
                }
            }
        }

        return errorMessage;
    }
}