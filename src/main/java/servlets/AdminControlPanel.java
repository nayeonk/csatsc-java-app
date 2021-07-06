package servlets;

import data.CampOffered;
import data.CampTopic;
import data.StringConstants;
import database.DatabaseQueries;
import database.DatabaseUpdates;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdminControlPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {

            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            for (CampTopic campTopic : campTopicList) {
                List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, false, false);
                CampOffered.SortLevel s = new CampOffered.SortLevel();
                Collections.sort(campOfferedList, s);
                campTopic.setCampOfferedList(campOfferedList);
                List<CampOffered> allCampOfferedList = DatabaseQueries.getCampsOffered(campTopic, true, true);
                Collections.sort(allCampOfferedList, s);
                campTopic.setAllCampOfferedList(allCampOfferedList);
            }
            Collections.sort(campTopicList, new Comparator<CampTopic>() {
                @Override
                public int compare(final CampTopic object1, final CampTopic object2) {
                    return object1.getTopic().compareTo(object2.getTopic());
                }
            });


            request.getSession().setAttribute("campTopicList", campTopicList);
            request.getRequestDispatcher("/WEB-INF/admin/admincontrolpanel.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (request.getParameter("close") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedClosed(campOfferedID);
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            } else if (request.getParameter("open") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedOpened(campOfferedID);
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            } else if (request.getParameter("delete") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedDeleted(campOfferedID);
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            }
        }
    }
}
