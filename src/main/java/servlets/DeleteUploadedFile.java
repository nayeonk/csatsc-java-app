package servlets;

import data.GradeReport;
import data.InsuranceCard;
import data.ReducedMealsVerification;
import data.StringConstants;
import database.DatabaseDeletions;
import database.DatabaseQueries;
import database.DatabaseUpdates;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUploadedFile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // verify they have access to the file
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        String errorMessage = "";
        String email = (String) request.getSession().getAttribute("email");
        int studentID = (int) request.getSession().getAttribute("studentID");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        int emailID = Integer.parseInt(request.getSession().getAttribute("emailID").toString());
        String id = request.getParameter("fileID");
        String kind = request.getParameter("fileKind");

        if ((id == null || id.isEmpty())) {
            errorMessage = "Object ID was not provided";
        } else if (kind.equals("reduced")) {
            int reducedMealsVerificationID = Integer.parseInt(id);
            if (!SecurityChecker.userHasAccessToReducedMealsVerification(emailID, reducedMealsVerificationID)) {
                response.getWriter().println("You do not have permission to delete this file");
            }

            ReducedMealsVerification rmv = DatabaseQueries.getReducedMealsVerificationByReducedMealVerificationID(reducedMealsVerificationID);

            if (rmv != null && !rmv.isDeleted()) {
                // the file exists
                DatabaseDeletions.deleteReducedMealsVerification(rmv);
                response.getWriter().print("");
            } else {
                // the file does not exist, or was already deleted
                response.getWriter().println("File does not exist!");
            }
            DatabaseUpdates.updateStudentProgress(studentID, 0);
        } else if (kind.equals("grade")) {
            int gradeReportID = Integer.parseInt(id);
            if (!SecurityChecker.userHasAccessToGradeReport(emailID, gradeReportID)) {
                //	SetPageAttributeUtil.setParentControlPanelAttributes(request, email);
                response.getWriter().println("You do not have permission to delete this file");
            }

            GradeReport gr = DatabaseQueries.getGradeReportByGradeReportID(gradeReportID);

            if (gr != null && !gr.isDeleted()) {
                // the file exists
                DatabaseDeletions.deleteGradeReport(gr);
                response.getWriter().print("");
            } else {
                // the file does not exist, or was already deleted
                response.getWriter().println("File does not exist!");
            }
            DatabaseUpdates.updateStudentProgress(studentID, 0);
        } else if (kind.equals("insurance")) {
            int insuranceCardID = Integer.parseInt(id);
            InsuranceCard ic = DatabaseQueries.getInsuranceCardByInsuranceCardID(insuranceCardID);

            if (ic != null && !ic.isDeleted()) {
                // the file exists
                DatabaseDeletions.deleteInsuranceCard(ic.getInsuranceCardID());
                response.getWriter().print("");
            } else {
                // the file does not exist, or was already deleted
                response.getWriter().println("File does not exist!");
            }
            DatabaseUpdates.updateStudentProgress(studentID, 1);
            response.getWriter().flush();
        }

        // Delete error message
        if (request.getAttribute("errorMessage") != null) {
            request.removeAttribute("errorMessage");
        }
    }
}
