package servlets;

import data.Staff;
import data.StringConstants;
import database.DatabaseDeletions;
import database.DatabaseQueries;
import database.DatabaseUpdates;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class EditStaff extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            List<Staff> staffList = DatabaseQueries.getStaffList();
            request.getSession().setAttribute("staffList", staffList);
            request.getRequestDispatcher("/WEB-INF/admin/editstaff.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (request.getParameter("delete") != null) {
                int staffID = Integer.parseInt(request.getParameter("staffID"));
                int updatedRows = DatabaseUpdates.updateStaffDeleted(staffID);
                if (updatedRows == 1) {
                    Staff staff = DatabaseQueries.getStaff(staffID);
                    if (staff != null) {
                        updatedRows = DatabaseDeletions.deleteLogin(staff.getEmail().getEmailID());
                    }
                }
                response.sendRedirect("/SummerCamp/editstaff");
            }
        }
    }
}
