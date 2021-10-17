package servlets;

import data.Ethnicity;
import data.Parent;
import data.StringConstants;
import data.Student;
import database.DatabaseQueries;
import util.ModifyDataUtil;
import util.MultiPartFormUtil;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Stack;

@MultipartConfig
public class CreateStudent extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static Stack<data.Student> reducedMealsStack = new Stack<>();

    public static Student getStudent() {
        return reducedMealsStack.pop();
    }

    // used for populating form fields in Apply For Scholarship page if possible
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentLogin(request, response)) {
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

        // get student and set as session attribute for navbar
        Student student = DatabaseQueries.getStudent(studentID, false);

        //setting the ethnicity
        if (student.getEthnicities() != null) {
            List<Ethnicity> ethnicities = student.getEthnicities();
            int elength = ethnicities.size();
            for (Ethnicity e : ethnicities) {
                if (e.getEthnicity().equalsIgnoreCase("caucasian")) {
                    student.setCaucasian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("african-american")) {
                    student.setAfricanAmerican(true);
                } else if (e.getEthnicity().equalsIgnoreCase("asian")) {
                    student.setAsian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("hispanic")) {
                    student.setHispanic(true);
                } else if (e.getEthnicity().equalsIgnoreCase("american-indian")) {
                    student.setAmericanIndian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("other")) {
                    student.setOtherEthnicity(true);
                } else {
                    student.setOtherEthnicity(true);
                }
            }
        }

        //setting the ethnicity
        if (student.getEthnicities() != null) {
            List<Ethnicity> ethnicities = student.getEthnicities();
            int elength = ethnicities.size();
            for (Ethnicity e : ethnicities) {
                if (e.getEthnicity().equalsIgnoreCase("caucasian")) {
                    student.setCaucasian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("african-american")) {
                    student.setAfricanAmerican(true);
                } else if (e.getEthnicity().equalsIgnoreCase("asian")) {
                    student.setAsian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("hispanic")) {
                    student.setHispanic(true);
                } else if (e.getEthnicity().equalsIgnoreCase("american-indian")) {
                    student.setAmericanIndian(true);
                } else if (e.getEthnicity().equalsIgnoreCase("other")) {
                    student.setOtherEthnicity(true);
                }
            }
        }

        /// Set parent as request variable
        Parent parent = DatabaseQueries.getParentByPID(parentID);
        request.setAttribute(StringConstants.PARENT, parent);

        // Set student as request variable
        request.setAttribute(StringConstants.STUDENT, student);

        // Set multi-choice fields
        request = SetPageAttributeUtil.setMultiChoiceFields(request);

        request.getRequestDispatcher("/WEB-INF/camper/camperProfile.jsp").forward(request, response);
    }

    // used when trying to save scholarship application
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request = MultiPartFormUtil.parseRequest(request);
        String email = (String) request.getSession().getAttribute("email");
        String errorMessage = "";

        // legacy code, not sure if we can delete this safely:
        request.getSession().setAttribute("successMessage", "");
        request.getSession().setAttribute("errorMessage", "");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage = "Please login first.<br>";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            errorMessage = validateForm(request);

            // if erroneous submission, redirect to camperprofile
            if (errorMessage.length() > 0) {
                repopulateAttributes(request);
                SetPageAttributeUtil.setMultiChoiceFields(request);
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/WEB-INF/camper/camperProfile.jsp").forward(request, response);
            } else {
                errorMessage = createStudent(request);

                // if error creating student
                if (errorMessage.length() > 0) {
                    repopulateAttributes(request);
                    SetPageAttributeUtil.setMultiChoiceFields(request);
                    request.setAttribute("errorMessage", errorMessage);
                    processParent(request);
                    request.getRequestDispatcher("/WEB-INF/camper/camperProfile.jsp").forward(request, response);
                }
                // if successful creating student, move to next application step
                else {
                    response.sendRedirect("/SummerCamp/applyforscholarship");
                }
            }
        }
    }

    private String createStudent(HttpServletRequest request) throws IOException, ServletException {
        Student student = ModifyDataUtil.buildStudent(request);

        if (request.getSession().getAttribute("studentID") != null) {
            int studentID = (int) request.getSession().getAttribute("studentID");
            student.setStudentID(studentID);
        }

        student.setLegalAgree(false);
        student.setPickUpCode(Student.generatePickupCode());
        //is update based on studentid
        request.getSession().setAttribute(StringConstants.STUDENT, student);
        Boolean isUpdate = request.getSession().getAttribute("isUpdate").equals("yes");
        return ModifyDataUtil.createOrUpdateStudent(request, Integer.parseInt((String) request.getSession().getAttribute("parentID")), student, isUpdate);
    }

    private void repopulateAttributes(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("otherEthnicity", ModifyDataUtil.reqParam(request, "otherEthnicity"));
        request.setAttribute("student", ModifyDataUtil.buildStudent(request));
    }

    private void processParent(HttpServletRequest request) throws IOException, ServletException {
        Integer parentID = Integer.parseInt(request.getParameter("parentID"));
        request.setAttribute("student", ModifyDataUtil.buildStudent(request));
        Parent parent = DatabaseQueries.getParentByPID(parentID);
        request.setAttribute("parent", parent);
        SetPageAttributeUtil.setMultiChoiceFields(request);
    }

    private String validateForm(HttpServletRequest request) {
        String error = "";

        String firstName = request.getParameter("s_fname");
        if (firstName.trim().isEmpty()) {
            error += "Please enter your first name<br>";
        }

        String lastName = request.getParameter("s_lname");
        if (lastName.trim().isEmpty()) {
            error += "Please enter your last name<br>";
        }

        String gender = request.getParameter("gender");
        if (gender.isEmpty()) {
            error += "Please select a gender<br>";
        }

        String dateOfBirth = request.getParameter("birthdate");;
        if (dateOfBirth.isEmpty()) {
            error += "Please enter Date of Birth.<br>";
        }

        String[] ethnicityIDs = request.getParameterValues("ethnicity");
        if (ethnicityIDs == null || ethnicityIDs.length == 0) {
            error += "Please check ethnicity.<br>";
        }

        // TODO: check for valid email

        String school = request.getParameter("school");
        String otherSchool = request.getParameter("otherSchool");
        if (school == "-1" && otherSchool.trim().isEmpty()) {
            error += "Please enter the name of your school.<br>";
        }

        String transportTo = request.getParameter("transportTo");
        String transportFrom = request.getParameter("transportFrom");
        if (transportTo.isEmpty() || transportFrom.isEmpty()) {
            error += "Please tell us if your child has transportation.<br>";
        }

        String attended = request.getParameter("attended");
        if (attended.isEmpty()) {
            error += "Please tell us if your child has attended CS@SC before.<br>";
        }

        String experience = request.getParameter("experience");
        if (experience.isEmpty()) {
            error += "Please tell us your child's experience with computers or computer programming.<br>";
        }

        String diet = request.getParameter("diet");
        if (diet.isEmpty()) {
            error += "Please tell us if your child has dietary restrictions or medical issues.<br>";
        }

        String OnCampus = request.getParameter("OnCampus");
        if (OnCampus.isEmpty()) {
            error += "Please tell us if your child want to attend camps on campus.<br>";
        }
        else if(OnCampus == "yes") {
            if(request.getParameter("Medical") == "no") {
                error += "Please fill in the medical form if your child want to attend on campus.";
            }
        }
        else {
            error += OnCampus;
        }

        return error;
    }
}
