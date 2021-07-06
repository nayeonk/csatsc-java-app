package servlets;

import data.StringConstants;
import database.DatabaseQueries;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchRedirectParent extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!SecurityChecker.isAdmin((String) request.getSession().getAttribute("email"))) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String email = request.getParameter("email");
            int emailID = DatabaseQueries.doesEmailExist(email.toLowerCase());
            request.getSession().setAttribute(StringConstants.EMAIL_ID, emailID);

            response.sendRedirect("/SummerCamp/managecampers");
        }

    }
}
