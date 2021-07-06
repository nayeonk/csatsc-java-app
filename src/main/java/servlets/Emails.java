package servlets;

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

public class Emails extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
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

            request.getRequestDispatcher("/WEB-INF/admin/emails.jsp").forward(request, response);
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
                request.getRequestDispatcher("/WEB-INF/admin/emails.jsp").forward(request, response);
            } else if (request.getParameter("open") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedOpened(campOfferedID);
                request.getRequestDispatcher("/WEB-INF/admin/emails.jsp").forward(request, response);
            } else if (request.getParameter("delete") != null) {
                int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
                DatabaseUpdates.updateCampOfferedDeleted(campOfferedID);
                request.getRequestDispatcher("/WEB-INF/admin/emails.jsp").forward(request, response);
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
