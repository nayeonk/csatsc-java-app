package servlets;

import data.GradeReport;
import data.InsuranceCard;
import data.ReducedMealsVerification;
import data.StringConstants;
import database.DatabaseQueries;
import util.FileUploadUtil;
import util.MailServer;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@MultipartConfig
public class ViewUploadedPicture extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // verify they have access to the image
        String errorMessage = "";
        request.getSession().setAttribute("errorMessage", "");
        request.getSession().setAttribute("successMessage", "");
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        String email = (String) request.getSession().getAttribute("email");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

        int emailID = SecurityChecker.getEmailID(email);
        String kind = request.getParameter("kind");

        String pageToSendThemTo = "/parent/manageCampers.jsp";
        if (role.equals(StringConstants.ADMIN)) {
            pageToSendThemTo = "/WEB-INF/admin/admincontrolpanel.jsp";
        }

        switch (kind) {
            case "reduced": {
                int id = Integer.parseInt(request.getParameter("id")); // id is index of desired reduced meal verification
                int reducedMealsVerificationID;

                if(role.equals("admin")) {
                    reducedMealsVerificationID = id;
                } else {
                    List<ReducedMealsVerification> reducedMealsVerifications = (List<ReducedMealsVerification>) request.getSession().getAttribute("reducedMealsVerifications");
                    if (id >= reducedMealsVerifications.size()) {
                        response.sendRedirect("/SummerCamp/applyforscholarship");
                        return;
                    }
                    reducedMealsVerificationID = reducedMealsVerifications.get(id).getReducedMealsVerificationID();//Integer.parseInt((String) request.getSession().getAttribute("reducedMealsVerificationID"));

                }

                ReducedMealsVerification rmv = DatabaseQueries.getReducedMealsVerificationByReducedMealVerificationID(reducedMealsVerificationID);

                if (!SecurityChecker.userHasAccessToReducedMealsVerification(emailID, reducedMealsVerificationID)) {
                    SetPageAttributeUtil.setParentControlPanelAttributes(request, email);
                    errorMessage = "Please login first.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(pageToSendThemTo).forward(request, response);
                }

                if (rmv.isDeleted() && !role.equals(StringConstants.ADMIN)) {
                    errorMessage = "File does not exist.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(pageToSendThemTo).forward(request, response);
                }
                FileUploadUtil.outputFile(rmv.getFilePath(), response, getServletContext());
                break;
            }
            case "grade": {

                System.out.println("Password");
                System.out.println(MailServer.getPassword(getServletContext()));

                int id = Integer.parseInt(request.getParameter("id")); // id is index of desired grade report

                int gradeReportID;

                // If admin []
                if(role.equals("admin")) {
                    gradeReportID = id;
                } else {
                    List<GradeReport> gradeReports = (List<GradeReport>) request.getSession().getAttribute("gradeReports");
                    if (id >= gradeReports.size()) {
                        response.sendRedirect("/SummerCamp/applyforscholarship");
                        return;
                    }
                    gradeReportID = gradeReports.get(id).getGradeReportID();
                }

                GradeReport gr = DatabaseQueries.getGradeReportByGradeReportID(gradeReportID);

                if (!SecurityChecker.userHasAccessToGradeReport(emailID, gradeReportID)) {
                    SetPageAttributeUtil.setParentControlPanelAttributes(request, email);
                    errorMessage = "Please login first.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(pageToSendThemTo).forward(request, response);
                }

                if (gr.isDeleted() && !role.equals(StringConstants.ADMIN)) {
                    errorMessage = "File does not exist.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(pageToSendThemTo).forward(request, response);
                }
                FileUploadUtil.outputFile(gr.getFilePath(), response, getServletContext());
                break;
            }
            case "insurance":
                int insuranceCardID = (int) request.getSession().getAttribute("insuranceCardID");
                InsuranceCard ic = DatabaseQueries.getInsuranceCardByInsuranceCardID(insuranceCardID);
                if (ic.isDeleted() && !role.equals(StringConstants.ADMIN)) {
                    errorMessage = "File does not exist.";
                    request.setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(pageToSendThemTo).forward(request, response);
                }

                String side = request.getParameter("side");
                if (side.equals("front")) {
                    FileUploadUtil.outputFile(ic.getFrontFilePath(), response, getServletContext());
                } else if (side.equals("back")) {
                    FileUploadUtil.outputFile(ic.getBackFilePath(), response, getServletContext());
                }
                break;
        }
    }
}
