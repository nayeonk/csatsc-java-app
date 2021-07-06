package servlets;

import data.CampOffered;
import data.StringConstants;
import data.Student;
import data.StudentCamp;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import enums.EmailType;
import util.EmailConstants;
import util.EmailParse;
import util.MailServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyCamps extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        // security check
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");
        if (!SecurityChecker.parentHasAccessToStudent(parentID, studentID)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // set student id as session attribute
        request.getSession().setAttribute("studentID", studentID);

        // get student and set as session attribute for navbar
        Student student = DatabaseQueries.getStudent(studentID, false);
        request.getSession().setAttribute("student", student);

        // get student camps and forward to next page
        List<StudentCamp> campsCurrentApplied = DatabaseQueries.getCurrentCampsAppliedByStudentID(studentID);
        request.setAttribute("campsCurrentApplied", campsCurrentApplied);

        List<CampOffered> campsAttended = DatabaseQueries.getPastCampsAttendedByStudentID(studentID);
        request.setAttribute("campsAttended", campsAttended);

        request.getRequestDispatcher("/WEB-INF/camper/mycamps.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        // this function is used to withdraw a student from camp, and also when redirecting from parent portal to this page
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        int studentID = (int) request.getSession().getAttribute("studentID");

        // get student and set as session attribute for navbar
        Student student = DatabaseQueries.getStudent(studentID, false);
        request.getSession().setAttribute("student", student);

        if (!SecurityChecker.parentHasAccessToStudent(parentID, studentID)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int emailID = (int) request.getSession().getAttribute(StringConstants.EMAIL_ID);

        if (request.getParameter("withdrawStudentCampID") != null) {
            int studentCampID = Integer.parseInt(request.getParameter("withdrawStudentCampID"));
            int campID = Integer.parseInt(request.getParameter("withdrawCampID"));

            // withdraw from camp
            if (!DatabaseQueries.isWithdrawn(studentCampID)) {
                DatabaseUpdates.withdrawStudentCampApplication(studentCampID);
                sendWithdrawEmail(studentID, campID, emailID);
            }
        } else if (request.getParameter("confirmStudentCampID") != null) {
            int studentCampID = Integer.parseInt(request.getParameter("confirmStudentCampID"));
            int campID = Integer.parseInt(request.getParameter("confirmCampID"));
            // confirm free camp
            if (!DatabaseQueries.isConfirmed(studentCampID)) {
                DatabaseUpdates.confirmStudentCampApplication(studentCampID);
                sendConfirmEmail(studentID, campID, emailID);
            }
        }

        List<StudentCamp> campsCurrentApplied = DatabaseQueries.getCurrentCampsAppliedByStudentID(studentID);
        request.setAttribute("campsCurrentApplied", campsCurrentApplied);

        List<CampOffered> campsAttended = DatabaseQueries.getPastCampsAttendedByStudentID(studentID);
        request.setAttribute("campsAttended", campsAttended);

        request.getRequestDispatcher("/WEB-INF/camper/mycamps.jsp").forward(request, response);
    }


    // copied from ConfirmCamps.java servlet

    private void sendWithdrawEmail(int studentID, int campID, int emailID) {
        CampOffered camp = DatabaseQueries.getCampOffered(campID);
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
        String campTimes = timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);

        Format formatter = new SimpleDateFormat("EEE, MMMM d");
        String campDates = formatter.format(camp.getStartDate()) + " - " + formatter.format(camp.getEndDate()) + ", " + campTimes;

        List<EmailParse> emailParts = new ArrayList<>(Arrays.asList(
                new EmailParse(EmailConstants.CAMP_LEVEL, "</p><p align=\"center\"><span style=\"font-size: 26px;\">" + campLevel),
                new EmailParse(EmailConstants.CAMP_TOPIC, campTopic + "</span><div style=\"font-size: 20px;\">"),
                new EmailParse(EmailConstants.CAMP_DATES, campDates + "</div><div style=\"font-size: 16px;\">"),
                new EmailParse(EmailConstants.CAMP_DESCRIPTION, campDescription + "</div></p>", false)));

        String studentName = DatabaseQueries.getStudentName(studentID);
        String parentEmail = DatabaseQueries.getEmail(emailID);

        String parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(DatabaseQueries.getEmailContents(EmailType.WITHDRAW));
        String content = MailServer.parseVars(emailParts, parseVariables);
        String sub = MailServer.parseSubject(DatabaseQueries.getEmailSubject(EmailType.WITHDRAW), campLevel, campTopic);
        MailServer.sendEmail(parentEmail, sub, content, getServletContext());
    }

    private void sendConfirmEmail(int studentID, int campID, int emailID) {
        CampOffered camp = DatabaseQueries.getCampOffered(campID);
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
        String campTimes = timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);

        Format formatter = new SimpleDateFormat("EEE, MMMM d");
        String campDates = formatter.format(camp.getStartDate()) + " - " + formatter.format(camp.getEndDate()) + ", " + campTimes;

        List<EmailParse> emailParts = new ArrayList<>(Arrays.asList(
                new EmailParse(EmailConstants.CAMP_LEVEL, "</p><p align=\"center\"><span style=\"font-size: 26px;\">" + campLevel),
                new EmailParse(EmailConstants.CAMP_TOPIC, campTopic + "</span><div style=\"font-size: 20px;\">"),
                new EmailParse(EmailConstants.CAMP_DATES, campDates + "</div><div style=\"font-size: 16px;\">"),
                new EmailParse(EmailConstants.CAMP_DESCRIPTION, campDescription + "</div></p>", false)));

        String studentName = DatabaseQueries.getStudentName(studentID);
        String parentEmail = DatabaseQueries.getEmail(emailID);

        String parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(DatabaseQueries.getEmailContents(EmailType.ENROLLED_FREE));
        String content = MailServer.parseVars(emailParts, parseVariables);
        String sub = MailServer.parseSubject(DatabaseQueries.getEmailSubject(EmailType.ENROLLED_FREE), campLevel, campTopic);
        MailServer.sendEmail(parentEmail, sub, content, getServletContext());
    }
}

