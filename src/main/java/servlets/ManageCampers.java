package servlets;

import data.*;
import data.StringConstants;
import util.SetPageAttributeUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageCampers extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // used for accessing the manage campers page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        int emailID = (int) request.getSession().getAttribute("emailID");
        SetPageAttributeUtil.setParentControlPanelAttributes(request, emailID);
        request.getSession().setAttribute("studentID", null);
        request.getSession().setAttribute("student", null);
        request.getRequestDispatcher("/WEB-INF/parent/manageCampers.jsp").forward(request, response);
    }

    // used for going from manage campers to camper profile
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("studentID") != null) {
            // View Camper
            if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
                response.sendRedirect(SecurityChecker.LoginPage);
                return;
            }

            int studentID = Integer.parseInt(request.getParameter("studentID"));
            request = SetPageAttributeUtil.setCamperAttributes(request, studentID);
            request.getSession().setAttribute("isUpdate", "yes");

            request.getRequestDispatcher("/mycamps").forward(request, response);
        } else {
            // add new camper
            if (!SecurityChecker.enforceParentLogin(request, response)) {
                response.sendRedirect(SecurityChecker.LoginPage);
                return;
            }

            request.getSession().setAttribute("isUpdate", "no");
            request.getSession().setAttribute(StringConstants.STUDENT, null);
            //default new campers' oncampus status to false
            request.getSession().setAttribute("OnCampus", false);
            request.getRequestDispatcher("/WEB-INF/camper/camperProfile.jsp").forward(request, response);
        }
    }
}
