package servlets;

import data.Student;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.ModifyDataUtil;
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

@MultipartConfig
@WebServlet(name = "ApplyForScholarship")
public class UploadScholarshipDetails extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Reinforce parent access authorization
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");
        if (!SecurityChecker.parentHasAccessToStudent(parentID, studentID)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Puts a wrapper on request so can convert input[type=file] to FileItem to store in database
        request = MultiPartFormUtil.parseRequest(request);

        // Check to see if a submission from the scholarship application page
        String submit = request.getParameter("submit");
        if (submit == null) { // Not a submission, so navigating to this page from a link
            this.populatePage(request, response);
        }
        else {
            // Skipping scholarship application, so no form validation or database changes
            if (submit.equals("skip")) {
                this.skipForm(request, response);
            }
            else { // Clicked "Save & Continue" on scholarship application
                // Fails form validation, so return to same page and preserve changes the user made
                if (!ValidateFormUtil.validateScholarship(request)) {
                    this.populatePage(request, response);
                }
                else { // Good submission, so save to database
                    this.submitForm(request, response);
                }
            }
        }
    }

    // used for populating form fields in Apply For Scholarship page if possible
    protected void populatePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentID = (int) request.getSession().getAttribute("studentID");

        // get student and set as session attribute for navbar
        Student student = DatabaseQueries.getStudent(studentID, true);
        request.getSession().setAttribute("student", student);

        if (request.getAttribute("errorMessage") == null) {
            // Set student and reducedMealsID as attributes if scholarship already filled
            request.setAttribute("student", student);
            request.setAttribute("reducedMealsID", student.getReducedMealsID());

            if (student.getGradeReports() != null) {
                request.getSession().setAttribute("gradeReports", student.getGradeReports());
            }
            if (student.getReducedMealsVerifications() != null) {
                request.getSession().setAttribute("reducedMealsVerifications", student.getReducedMealsVerifications());
            }
        }
        else {
            String reducedMeals = request.getParameter("reducedMeals");
            if (reducedMeals != null) {
                request.setAttribute("reducedMealsID", Integer.parseInt(reducedMeals));
            }
        }

        request = SetPageAttributeUtil.setControlPanelAttributes(request);
        request.getRequestDispatcher("/WEB-INF/camper/scholarship.jsp").forward(request, response);
    }

    // used when skipping scholarship application
    protected void skipForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentID = (int) request.getSession().getAttribute("studentID");

        Student student = DatabaseQueries.getStudent(studentID, false);
        if (student.getProgress() == 0 || student.getProgress() == 1) {
//            DatabaseUpdates.updateStudentProgress(studentID, 1);
            DatabaseUpdates.updateStudentProgress(studentID, 2);
        }

//        response.sendRedirect("/SummerCamp/medical");
        response.sendRedirect("/SummerCamp/campregistration");
    }

    // used when trying to save scholarship application
    protected void submitForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentID = (int) request.getSession().getAttribute("studentID");

        if (ModifyDataUtil.uploadScholarshipFiles(request, studentID).length() > 0) { // error in uploading file
            response.sendRedirect("/SummerCamp/applyforscholarship");
        }
        else {
            ModifyDataUtil.createReducedMeals(request, studentID);

            // update student's progress
            Student student = DatabaseQueries.getStudent(studentID, false);
            if (student.getProgress() == 0) {
//                DatabaseUpdates.updateStudentProgress(studentID, 1);

//                Hotfix notes
//                Setting to 1 would set step to "complete medical"
//                So automatically set to 2 to allow for apply to camp

                DatabaseUpdates.updateStudentProgress(studentID, 2);
            }

            request.getSession().removeAttribute("gradeReports");
            request.getSession().removeAttribute("reducedMealsVerifications");

//        response.sendRedirect("/SummerCamp/medical");
            response.sendRedirect("/SummerCamp/campregistration");
        }
    }
}

