package servlets;

import data.Parent;
import data.Staff;
import data.StringConstants;
import database.DatabaseInserts;
import database.DatabaseQueries;
import util.SetPageAttributeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends HttpServlet {
    //debugging admin login
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("email") == null) {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (SecurityChecker.isValidEmail((String) request.getSession().getAttribute("email"))) {

            String email = (String) request.getSession().getAttribute("email");
            String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
            int emailId = SecurityChecker.getEmailID(email);

            if (SecurityChecker.isAdmin(emailId)) {
                //if (role.equals(StringConstants.ADMIN))	{
                request.getSession().setAttribute(StringConstants.ROLE, StringConstants.ADMIN);
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            }
            //else if (role.equals(StringConstants.INSTRUCTOR)) {
            else if (SecurityChecker.isInstructor(emailId)) {
                request.getSession().setAttribute(StringConstants.ROLE, StringConstants.INSTRUCTOR);
                response.sendRedirect("/SummerCamp/instructorcontrolpanel");
            } else {
                SetPageAttributeUtil.setParentControlPanelAttributes(request, email);
                request.getSession().setAttribute(StringConstants.ROLE, StringConstants.PARENT);
                request.setAttribute("flaggg", request.getParameter("okay")); ////////////Just setting a fake variable from GET


                request.getRequestDispatcher("/managecampers").forward(request, response);
            }

        } else {
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO: FOR TESTING
        String email = request.getParameter("email");

        int emailID = DatabaseQueries.doesEmailExist(email.toLowerCase());
        request.getSession().setAttribute(StringConstants.EMAIL_ID, emailID);
        request.getSession().setAttribute(StringConstants.EMAIL, email);

        DatabaseInserts.insertLoginAudit(email, 1);
        boolean isStaff = false;
        String redirect = "";
        //redirect to admin control panel if login is for an admin
        if (DatabaseQueries.isAdmin(emailID)) {
            request.getSession().setAttribute(StringConstants.ROLE, StringConstants.ADMIN);
            isStaff = true;
            redirect = "/SummerCamp/admincontrolpanel";

        } else if (DatabaseQueries.isInstructor(emailID)) {
            request.getSession().setAttribute(StringConstants.ROLE, StringConstants.INSTRUCTOR);
            isStaff = true;
            redirect = "/SummerCamp/instructorcontrolpanel";
        }

        if (isStaff) {
            Staff staff = DatabaseQueries.getStaffByEmailID(emailID);
            request.getSession().setAttribute("name", staff.getFirstName() + " " + staff.getLastName());
            response.sendRedirect(redirect);
        } else {
            request.getSession().setAttribute(StringConstants.ROLE, StringConstants.PARENT);
            Parent parent = DatabaseQueries.getParentByEID(emailID);
            Boolean parentProfileComplete = true;

            try {
                if (parent.getFirstName().isEmpty() || parent.getLastName().isEmpty() || parent.getPhone().isEmpty() || parent.getAddress().equals("") || parent.getIncome().isEmpty()) {
                    parentProfileComplete = false;
                }
            }catch (Exception e){
                parentProfileComplete = false;
            }

            request.getSession().setAttribute("parentProfileComplete", parentProfileComplete);

            if (parentProfileComplete) {
                response.sendRedirect("/SummerCamp/managecampers");
            } else {
                response.sendRedirect("/SummerCamp/parentregistration");
            }
        }
    }
}
