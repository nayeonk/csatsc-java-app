package servlets;

import data.Staff;
import data.StringConstants;
import database.DatabaseDeletions;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StaffAssignmentEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String cID = request.getParameter("campid");
            if (cID != null) {
                int campID = Integer.parseInt(cID);
                List<Staff> taList = DatabaseQueries.getCampStaff(campID);
                List<Staff> allTAList = DatabaseQueries.getAllCampStaffNames();
                for (int i = 0; i < allTAList.size(); i++) {

                }
                request.getSession().setAttribute("taList", taList);
                request.getSession().setAttribute("campid", campID);
                request.getSession().setAttribute("allTAList", allTAList);
            }

            request.getRequestDispatcher("/WEB-INF/admin/staffassignmentedit.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String cID = request.getParameter("campid");
            int campID = Integer.parseInt(cID);

            // adding a TA
            if (request.getParameterMap().containsKey("name")) {
                String name = request.getParameter("name");
                String[] pieces = name.split(" ");
                if (pieces.length == 2) {
                    String firstName = pieces[0];
                    String lastName = pieces[1];
                    int staffID = DatabaseQueries.getStaffByName(firstName, lastName);
                    if (staffID != -1) {
                        List<Staff> taList = DatabaseInserts.addCampStaff(staffID, campID);
                        request.setAttribute("taList", taList);
                        request.setAttribute("campid", campID);
                        request.getRequestDispatcher("/WEB-INF/admin/staffassignmentedit.jsp").forward(request, response);
                        response.getWriter().write("success");
                    } else {
                        if (cID != null) {
                            List<Staff> taList = DatabaseQueries.getCampStaff(campID);
                            request.getSession().setAttribute("taList", taList);
                            request.getSession().setAttribute("campid", campID);
                        }
                        response.sendRedirect("/SummerCamp/staffassignmentedit");
                        // TODO let admin know that the TA's name was not found in the database

                    }
                } else {
                    if (cID != null) {
                        List<Staff> taList = DatabaseQueries.getCampStaff(campID);
                        request.getSession().setAttribute("taList", taList);
                        request.getSession().setAttribute("campid", campID);
                    }
                    response.sendRedirect("/SummerCamp/staffassignmentedit");
                    // TODO show invalid name message
                    //response.getWriter().write("failure");
                }
            } // deleting a ta
            else if (request.getParameterMap().containsKey("taid")) {
                String sID = request.getParameter("taid");
                String modified = sID.replaceAll(" ", "");
                int staffID = Integer.parseInt(modified);
                boolean success = DatabaseDeletions.deleteCampStaff(staffID, campID);
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                if (success) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("failure");
                }
            } else {
                response.sendRedirect("/SummerCamp/staffassignmentedit");
            }
        }
    }

    public void save(String button, int index, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String subject = request.getParameter("subject-" + button);
        String content = request.getParameter("content-" + button);
        DatabaseUpdates.updateEmailContent(content, index);
        DatabaseUpdates.updateEmailSubject(subject, index);
        response.sendRedirect("/SummerCamp/staffassignmentedit");
    }
}
