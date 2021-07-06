package servlets;

import data.CampDate;
import data.CampOffered;
import data.CampTopic;
import data.StringConstants;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import enums.EmailType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffAssignment extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<CampDate> campDatesByYearList = DatabaseQueries.getCampStartDatesByYear();
            for (CampTopic campTopic : campTopicList) {
                List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, true, false);
                campTopic.setCampOfferedList(campOfferedList);
                List<CampOffered> allCampOfferedList = DatabaseQueries.getCampsOffered(campTopic, true, true);
                campTopic.setAllCampOfferedList(allCampOfferedList);
            }

            String datesString = "";
            Map<Integer, List<CampOffered>> campsByDate = new HashMap<Integer, List<CampOffered>>();
            for (CampDate campDate : campDatesByYearList) {
                datesString += (campDate.getDateID() + ",");
                List<CampOffered> campOfferedList = DatabaseQueries.getCampsOfferedByDateID(campDate.getDateID());
                campsByDate.put(campDate.getDateID(), campOfferedList);
            }
            request.getSession().setAttribute("datesString", datesString);
            request.getSession().setAttribute("dateList", campDatesByYearList); // list of dates for tabs
            request.getSession().setAttribute("campsByDate", campsByDate); // lists of camps per week
            request.getSession().setAttribute("campTopicList", campTopicList);
            request.getRequestDispatcher("/WEB-INF/admin/staffassignment.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (request.getParameter("close") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedClosed(campOfferedID);
                response.sendRedirect("/SummerCamp/staffassignment");
            } else if (request.getParameter("open") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedOpened(campOfferedID);
                response.sendRedirect("/SummerCamp/staffassignment");
            } else if (request.getParameter("delete") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedDeleted(campOfferedID);
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            } else if (request.getParameter("submit-acceptpaid") != null) {
                save("acceptpaid", EmailType.ACCEPT_PAID, request, response);
            } else if (request.getParameter("submit-rejected") != null) {
                save("rejected", EmailType.REJECTED, request, response);
            } else if (request.getParameter("submit-acceptfree") != null) {
                save("acceptfree", EmailType.ACCEPT_FREE, request, response);
            } else if (request.getParameter("submit-waitlist") != null) {
                save("waitlist", EmailType.WAITLIST, request, response);
            } else if (request.getParameter("submit-registered") != null) {
                save("registered", EmailType.REGISTERED, request, response);
            } else if (request.getParameter("submit-enrolledpaid") != null) {
                save("enrolledpaid", EmailType.ENROLLED_PAID, request, response);
            } else if (request.getParameter("submit-enrolledfree") != null) {
                save("enrolledfree", EmailType.ENROLLED_FREE, request, response);
            } else if (request.getParameter("submit-withdraw") != null) {
                save("withdraw", EmailType.WITHDRAW, request, response);
            }
        }
    }

    public void save(String button, EmailType emailType, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String subject = request.getParameter("subject-" + button);
        String content = request.getParameter("content-" + button);
        DatabaseUpdates.updateEmailContent(content, emailType.ordinal());
        DatabaseUpdates.updateEmailSubject(subject, emailType.ordinal());
        response.sendRedirect("/SummerCamp/emails");
    }
}
