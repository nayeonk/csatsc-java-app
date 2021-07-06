package servlets;

import data.*;
import database.DatabaseQueries;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class SecurityChecker {

    public static String LoginPage = "/SummerCamp/login";

    public static boolean isValidEmail(String email) {
        return email != null && getEmailID(email) >= 0;
    }

    public static int getEmailID(String email) {
        return DatabaseQueries.doesEmailExist(email);
    }

    public static boolean parentHasAccessToStudent(int parentID, int studentID) {
        return parentID == DatabaseQueries.getParentID(studentID);
    }

    static boolean userHasAccessToStudent(int emailID, int studentID) {
        if (isAdmin(emailID)) {
            return true;
        }
        Parent parent = DatabaseQueries.getParentByEID(emailID);
        return parent.getParentID() == DatabaseQueries.getParentID(studentID);
    }

    static boolean userHasAccessToStudent(String role, int emailID, int parentEmailID) {
        if (role.equals(StringConstants.ADMIN)) {
            return true;
        }

        return emailID == parentEmailID;
    }

    static boolean userHasAccessToGradeReport(int emailID, int gradeReportID) {
        if (isAdmin(emailID)) {
            return true;
        }
        Parent parent = DatabaseQueries.getParentByEID(emailID);
        GradeReport gradeReport = DatabaseQueries.getGradeReportByGradeReportID(gradeReportID);
        //This will let us check the year
        String[] date = gradeReport.getReadableTimestamp().split(" ");
        int gradeDate = Integer.parseInt(date[3]);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //end of section

        int studentID = gradeReport.getStudentID();
        return parent.getParentID() == DatabaseQueries.getParentID(studentID);
    }

    static boolean userHasAccessToReducedMealsVerification(int emailID, int reducedMealsVerificationID) {
        if (isAdmin(emailID)) {
            return true;
        }
        Parent parent = DatabaseQueries.getParentByEID(emailID);
        ReducedMealsVerification rmv = DatabaseQueries.getReducedMealsVerificationByReducedMealVerificationID(reducedMealsVerificationID);
        int studentID = rmv.getStudentID();
        return parent.getParentID() == DatabaseQueries.getParentID(studentID);
    }

    static boolean userHasUpdatedGradeAndMealVerification(int emailID, String recentReducedMealsTime, String recentGradeReportTime, int reduceMealsID) {
        if (isAdmin(emailID)) {
            return true;
        }

        if (recentGradeReportTime.isEmpty() || (recentReducedMealsTime.isEmpty() & reduceMealsID < 3)) {
            return false;
        }

        boolean gradeUpdated = false;
        //Reduced Meals ID (1 & 2 require a recent updated file)
        boolean mealsUpdated = reduceMealsID >= 3;

        //current year
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        //Check there is a grade report up to date
        String[] dateGradeString = recentGradeReportTime.split(" ");
        int gradeDate = Integer.parseInt(dateGradeString[3]);
        //end of section
        gradeUpdated = gradeUpdated || (year == gradeDate);

        //Check there is a reduced meals verification file up to date
        if (!mealsUpdated) {
            String[] dateMealString = recentReducedMealsTime.split(" ");
            int mealDate = Integer.parseInt(dateMealString[3]);
            mealsUpdated = mealsUpdated || (mealDate == year);
        }

        return gradeUpdated && mealsUpdated;
    }

    public static boolean isAdmin(int emailID) {
        return DatabaseQueries.isAdmin(emailID);
    }

    public static boolean isAdmin(String email) {
        return isValidEmail(email) && DatabaseQueries.isAdmin(getEmailID(email));
    }

    static boolean isInstructor(int emailID) {
        return DatabaseQueries.isInstructor(emailID);
    }

    public static boolean isInstructor(String email) {
        return isValidEmail(email) && DatabaseQueries.isInstructor(getEmailID(email));
    }

    static boolean isInstructor(HttpServletRequest request) {
        int emailID = (Integer) request.getSession().getAttribute(StringConstants.EMAIL_ID);
        return SecurityChecker.isInstructor(emailID);
    }

    static boolean isAdmin(HttpServletRequest request) {
        int emailID = (Integer) request.getSession().getAttribute(StringConstants.EMAIL_ID);
        return SecurityChecker.isAdmin(emailID);
    }

    static boolean enforceParentLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // If no role
        if (request.getSession().getAttribute(StringConstants.ROLE) == null) {
            return false;
        }

        // Allow admin access to this page
        if (request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            request.setAttribute(StringConstants.IS_ADMIN, true);
            request.setAttribute(StringConstants.IS_ADMIN_NAME, request.getSession().getAttribute("name"));
            return true;
        }

        // If not parent, return false
        return request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.PARENT);
    }

    static boolean enforceParentAndStudentLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // If no role
        if (request.getSession().getAttribute(StringConstants.ROLE) == null) {
            return false;
        }

        if (request.getSession().getAttribute("studentID") == null) {
            if (request.getParameter("studentID") == null) {
                return false;
            }
        }

        // Allow admin access to this page
        if (request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            request.setAttribute(StringConstants.IS_ADMIN, true);
            request.setAttribute(StringConstants.IS_ADMIN_NAME, request.getSession().getAttribute("name"));
            return true;
        }

        // If not parent, return false
        return request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.PARENT);
    }
}
