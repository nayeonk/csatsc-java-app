package servlets;

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
import java.util.ArrayList;
import java.util.List;

public class InstructorControlPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (request.getSession().getAttribute("email") == null || !DatabaseConnection.isAdmin(Integer.parseInt(request.getSession().getAttribute("emailID").toString()))) {
        //if (!SecurityChecker.isInstructor((String) request.getSession().getAttribute("email"))) {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.INSTRUCTOR)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
            List<String> acceptPaidEmails = new ArrayList<>();
            List<String> rejectedEmails = new ArrayList<>();
            List<String> acceptFreeEmails = new ArrayList<>();
            List<String> waitlistEmails = new ArrayList<>();
            List<String> registeredEmails = new ArrayList<>();
            List<String> enrolledPaidEmails = new ArrayList<>();
            List<String> enrolledFreeEmails = new ArrayList<>();
            List<String> withdrawEmails = new ArrayList<>();
            List<String> allEmails = DatabaseQueries.getAllEmails();

            String acceptParsedVariables = "campLevel, campTopic, studentName, campDates, "
                    + "campDescription, deadlineDate, campPrice";
            String acceptFreeParsedVariables = "campLevel, campTopic, studentName, campDates, "
                    + "campDescription, deadlineDate";
            String rejectParsedVariables = "campLevel, campTopic, studentName, campDates1";
            String waitlistParsedVariables = "campLevel, campTopic, studentName, campDates1";
            String registerParsedVariables = "studentName, camps, notificationDate";
            String enrollParsedVariables = "campLevel, campTopic, studentName, campDates, "
                    + "campDescription, pickupCode";
            String withdrawParsedVariables = "campLevel, campTopic, studentName, campDates, "
                    + "campDescription";
            String subjectParsedVariables = "campLevel, campTopic";
            String none = "";

            acceptPaidEmails.add(acceptParsedVariables);
            acceptPaidEmails.add(subjectParsedVariables);
            acceptPaidEmails.add(DatabaseQueries.getEmailSubject(EmailType.ACCEPT_PAID));
            acceptPaidEmails.add(DatabaseQueries.getEmailContents(EmailType.ACCEPT_PAID));
            request.getSession().setAttribute("acceptPaidEmails", acceptPaidEmails);

            rejectedEmails.add(rejectParsedVariables);
            rejectedEmails.add(subjectParsedVariables);
            rejectedEmails.add(DatabaseQueries.getEmailSubject(EmailType.REJECTED));
            rejectedEmails.add(DatabaseQueries.getEmailContents(EmailType.REJECTED));
            request.getSession().setAttribute("rejectedEmails", rejectedEmails);


            acceptFreeEmails.add(acceptFreeParsedVariables);
            acceptFreeEmails.add(subjectParsedVariables);
            acceptFreeEmails.add(DatabaseQueries.getEmailSubject(EmailType.ACCEPT_FREE));
            acceptFreeEmails.add(DatabaseQueries.getEmailContents(EmailType.ACCEPT_FREE));
            request.getSession().setAttribute("acceptFreeEmails", acceptFreeEmails);

            waitlistEmails.add(waitlistParsedVariables);
            waitlistEmails.add(subjectParsedVariables);
            waitlistEmails.add(DatabaseQueries.getEmailSubject(EmailType.WAITLIST));
            waitlistEmails.add(DatabaseQueries.getEmailContents(EmailType.WAITLIST));
            request.getSession().setAttribute("waitlistEmails", waitlistEmails);

            registeredEmails.add(registerParsedVariables);
            registeredEmails.add(none);
            registeredEmails.add(DatabaseQueries.getEmailSubject(EmailType.REGISTERED));
            registeredEmails.add(DatabaseQueries.getEmailContents(EmailType.REGISTERED));
            request.getSession().setAttribute("registeredEmails", registeredEmails);

            enrolledPaidEmails.add(enrollParsedVariables);
            enrolledPaidEmails.add(subjectParsedVariables);
            enrolledPaidEmails.add(DatabaseQueries.getEmailSubject(EmailType.ENROLLED_PAID));
            enrolledPaidEmails.add(DatabaseQueries.getEmailContents(EmailType.ENROLLED_PAID));
            request.getSession().setAttribute("enrolledPaidEmails", enrolledPaidEmails);

            enrolledFreeEmails.add(enrollParsedVariables);
            enrolledFreeEmails.add(subjectParsedVariables);
            enrolledFreeEmails.add(DatabaseQueries.getEmailSubject(EmailType.ENROLLED_FREE));
            enrolledFreeEmails.add(DatabaseQueries.getEmailContents(EmailType.ENROLLED_FREE));
            request.getSession().setAttribute("enrolledFreeEmails", enrolledFreeEmails);

            withdrawEmails.add(withdrawParsedVariables);
            withdrawEmails.add(subjectParsedVariables);
            withdrawEmails.add(DatabaseQueries.getEmailSubject(EmailType.WITHDRAW));
            withdrawEmails.add(DatabaseQueries.getEmailContents(EmailType.WITHDRAW));
            request.getSession().setAttribute("withdrawEmails", withdrawEmails);

            request.getSession().setAttribute("allEmails", allEmails);

            for (CampTopic campTopic : campTopicList) {
                List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, true, false);
                campTopic.setCampOfferedList(campOfferedList);
                List<CampOffered> allCampOfferedList = DatabaseQueries.getCampsOffered(campTopic, true, true);
                campTopic.setAllCampOfferedList(allCampOfferedList);
            }

            request.getSession().setAttribute("campTopicList", campTopicList);

            request.getRequestDispatcher("/WEB-INF/instructor/instructorcontrolpanel.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.INSTRUCTOR)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (request.getParameter("close") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedClosed(campOfferedID);
                response.sendRedirect("/SummerCamp/instructorcontrolpanel");
            } else if (request.getParameter("open") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedOpened(campOfferedID);
                response.sendRedirect("/SummerCamp/instructorcontrolpanel");
            } else if (request.getParameter("delete") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedDeleted(campOfferedID);
                response.sendRedirect("/SummerCamp/instructorcontrolpanel");
            } else if (request.getParameter("submit-acceptpaid") != null) {
                save("acceptpaid", 1, request, response);
            } else if (request.getParameter("submit-rejected") != null) {
                save("rejected", 2, request, response);
            } else if (request.getParameter("submit-acceptfree") != null) {
                save("acceptfree", 3, request, response);
            } else if (request.getParameter("submit-waitlist") != null) {
                save("waitlist", 4, request, response);
            } else if (request.getParameter("submit-registered") != null) {
                save("registered", 5, request, response);
            } else if (request.getParameter("submit-enrolledpaid") != null) {
                save("enrolledpaid", 6, request, response);
            } else if (request.getParameter("submit-enrolledfree") != null) {
                save("enrolledfree", 7, request, response);
            } else if (request.getParameter("submit-withdraw") != null) {
                save("withdraw", 8, request, response);
            }

        }
    }

    public void save(String button, int index, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String subject = request.getParameter("subject-" + button);
        String content = request.getParameter("content-" + button);
        DatabaseUpdates.updateEmailContent(content, index);
        DatabaseUpdates.updateEmailSubject(subject, index);
        response.sendRedirect("/SummerCamp/instructorcontrolpanel");
    }
}
