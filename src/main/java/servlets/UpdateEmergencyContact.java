package servlets;

import data.EmergencyContact;
import data.StringConstants;
import util.ModifyDataUtil;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// CC: Updates a parent and their address after initial registration.
public class UpdateEmergencyContact extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String errorMessage = "";
        String email = (String) request.getSession().getAttribute("email");
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        Integer pemailID = Integer.parseInt(request.getParameter("pemailID"));

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage += "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {

            Integer parentID = Integer.parseInt(request.getParameter("parentID"));
            EmergencyContact ec = ModifyDataUtil.buildEmergencyContact(request, parentID);

            String successMessage = "";
            if (parentID == -1) {
                errorMessage = "Failed to recover parent from the database.";
            } else {
                errorMessage = ModifyDataUtil.createOrUpdateEC(ec, true);
            }

            SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);
            if (errorMessage.length() > 0) {
                request.getSession().setAttribute("errorMessage", errorMessage);
                request.setAttribute("errorMessage", errorMessage);

            } else {
                successMessage += "Parent 2 information updated successfully!";
                request.getSession().setAttribute("successMessage", successMessage);
                request.setAttribute("successMessage", successMessage);

            }

            if (role.equals(StringConstants.PARENT)) {
                response.sendRedirect("/SummerCamp/login");
            } else {
                request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
            }
        }
    }
}