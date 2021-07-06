package servlets;

import data.CampOffered;
import data.StringConstants;
import data.StudentCampApplication;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import enums.EmailType;
import util.EmailConstants;
import util.EmailParse;
import util.MailServer;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// CC: Dispatches a camp application.
public class ConfirmCamp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String errorMessage = "";
        request.getSession().setAttribute("errorMessage", "");
        request.getSession().setAttribute("successMessage", "");
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        String email = (String) request.getSession().getAttribute("email");
        int emailId = SecurityChecker.getEmailID(email);
        String studentName = request.getParameter("studentName");
        String parentEmail = request.getParameter("pemail");
        int parentEmailId = Integer.parseInt(request.getParameter("pemailID"));

        String button = request.getParameter("addThis");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (button.equals("Pay Now!")) {
            request.setAttribute("errorMessage", "Payment requests should redirect to the third party payment site.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            if (!SecurityChecker.userHasAccessToStudent(role, emailId, parentEmailId)) {
                errorMessage = "Please login first.";
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
            if (request.getParameter("studentID") == null || request.getParameter("campOfferedID") == null) {
                errorMessage = "All fields must be filled.";
            }

            String successMessage = null;

            int campOfferedID = Integer.parseInt(request.getParameter("campOfferedID"));
            int studentID = Integer.parseInt(request.getParameter("studentID"));

            CampOffered camp = DatabaseQueries.getCampOffered(campOfferedID);
            String campLevel = camp.getCampLevel();
            String campTopic = camp.getCampTopic();
            String campDescription = camp.getDescription();

            Date startTimeToDate = null;
            Date endTimeToDate = null;
            try {
                DateFormat timeToDateFormat = new SimpleDateFormat("hh:mm:ss");
                startTimeToDate = timeToDateFormat.parse(camp.getStartTime().toString());
                endTimeToDate = timeToDateFormat.parse(camp.getEndTime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String campTimes = camp.getDays() + " " + timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);

            Format formatter = new SimpleDateFormat("EEE, MMMM d");
            String campDates = formatter.format(camp.getStartDate()) + " - " + formatter.format(camp.getEndDate()) + ", " + campTimes;

            List<EmailParse> emailParts = new ArrayList<>(Arrays.asList(
                new EmailParse(EmailConstants.CAMP_LEVEL, "</p><p align=\"center\"><span style=\"font-size: 26px;\">" + campLevel),
                new EmailParse(EmailConstants.CAMP_TOPIC, campTopic + "</span><span style=\"font-size: 20px;\">"),
                new EmailParse(EmailConstants.CAMP_DATES, campDates + "</span><span style=\"font-size: 16px;\">"),
                new EmailParse(EmailConstants.CAMP_DESCRIPTION, campDescription + "</span></p><p style=\"font-size: 20px;\">", true)));

            if (button.equals("Withdraw")) {
                if (handleWithdraw(campOfferedID, studentID, studentName, parentEmail, emailParts, campLevel, campTopic)) {
                    successMessage = "Camp withdrawal successful!";
                } else {
                    errorMessage = "Cannot withdraw from this camp";
                }
            } else if (button.equals("Confirm")) {
                handleConfirm(campOfferedID, studentID, studentName, parentEmail, emailParts, campLevel, campTopic);
                successMessage = "Camp enrollment successful! Confirmation email sent.";
            }

            if (!errorMessage.isEmpty()) {
                SetPageAttributeUtil.setParentControlPanelAttributes(request, parentEmailId);
                request.getSession().setAttribute("successMessage", successMessage);
                request.setAttribute("successMessage", successMessage);

                if (role.equals(StringConstants.PARENT)) {
                    response.sendRedirect("/SummerCamp/login");
                } else {
                    request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", errorMessage);
                response.sendRedirect("/SummerCamp/login");
            }
        }
    }

    private void sendSuccessEmail(
        String parseVariables, EmailType emailType, String parentEmail,
        List<EmailParse> emailParts, String campLevel, String campTopic)
    {
        String content = MailServer.parseVars(emailParts, parseVariables);
        String sub = MailServer.parseSubject(DatabaseQueries.getEmailSubject(emailType), campLevel, campTopic);
        MailServer.sendEmail(parentEmail, sub, content, getServletContext());
    }

    private boolean handleWithdraw(
        int campOfferedID, int studentID, String studentName, String parentEmail,
        List<EmailParse> emailParts, String campLevel, String campTopic)
    {
        // this is to verify that the student is not withdrawing from a rejected camp
        ArrayList<StudentCampApplication> studentCamps = DatabaseQueries.getStudentCampsByStudentID(studentID);
        StudentCampApplication thisApp = null;
        for (StudentCampApplication sc : studentCamps) {
            if (sc.getCampOfferedID() == campOfferedID) {
                thisApp = sc;
                break;
            }
        }

        if (thisApp == null || thisApp.getRejected() == 1) {
            return false;
        } else {
            DatabaseUpdates.withdrawStudentApplication(studentID, campOfferedID);
            String parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(DatabaseQueries.getEmailContents(EmailType.WITHDRAW));
            sendSuccessEmail(parseVariables, EmailType.WITHDRAW, parentEmail, emailParts, campLevel, campTopic);
            return true;
        }
    }

    private void handleConfirm(
        int campOfferedID, int studentID, String studentName,
        String parentEmail, List<EmailParse> emailParts, String campLevel, String campTopic)
    {
        DatabaseUpdates.confirmStudentApplication(studentID, campOfferedID);
        String checkoutCode = DatabaseQueries.getPickupCode(Integer.toString(studentID));
        String parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(DatabaseQueries.getEmailContents(EmailType.ENROLLED_FREE));
        emailParts.add(new EmailParse(EmailConstants.PICKUP_CODE, "<strong>" + checkoutCode + "</strong>"));

        sendSuccessEmail(parseVariables, EmailType.ENROLLED_FREE, parentEmail, emailParts, campLevel, campTopic);
    }
}