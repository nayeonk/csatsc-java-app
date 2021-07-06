package servlets;

import data.*;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.EmailConstants;
import util.MailServer;
import util.SetPageAttributeUtil;

import enums.EmailType;

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
import java.util.Date;
import java.util.List;
import java.util.Random;

// CC: Dispatches a camp application.
public class ApplyForCamp extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static String generatePickupCode() {
        String SALTCHARS = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().setAttribute("errorMessage", "");
        request.getSession().setAttribute("successMessage", "");
        String errorMessage = "";
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        Integer pemailID = Integer.parseInt(request.getParameter("pemailID"));
        String studentName = request.getParameter("studentName");
        String pemail = request.getParameter("pemail");
        String sID = request.getParameter("studentID");
        int studentID = Integer.parseInt(sID);
        Student student = DatabaseQueries.getStudent(studentID, false);
        int reducedMealID = student.getReducedMealsID();
        List<GradeReport> gradeReports = DatabaseQueries.getGradeReports(student, false);
        List<ReducedMealsVerification> reducedMealsVerifications = DatabaseQueries.getReducedMealsVerifications(student, false);

        String recentGradeReportTime = "";
        if (!gradeReports.isEmpty()) {
            recentGradeReportTime = gradeReports.get(0).getReadableTimestamp();
        }
        String recentMealReportTime = "";
        if (!reducedMealsVerifications.isEmpty()) {
            recentMealReportTime = reducedMealsVerifications.get(0).getReadableTimestamp();
        }

        String email = (String) request.getSession().getAttribute("email");
        int emailID = SecurityChecker.getEmailID(email);
        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage += "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (!SecurityChecker.userHasAccessToStudent(role, emailID, pemailID)) {
            errorMessage += "There was an error with your session. Please log in again.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (!SecurityChecker.userHasUpdatedGradeAndMealVerification(pemailID, recentMealReportTime, recentGradeReportTime, reducedMealID)) {
            errorMessage += "The grade report and/or reduced meal verification is not recent. Please update or remove old files before applying.";
            request.setAttribute("errorMessage", errorMessage);
            SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);

            request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
        } else {            //apply for camp, generate code once
            String[] cOfferedIDs = request.getParameterValues("campOffered");
            String legalAgreement = request.getParameter("legalAgree");
            String button = request.getParameter("button");
            String successMessage = "";

            if (button.equalsIgnoreCase("Apply for Camp")) {
                if (request.getParameter("applycamppath") != null) { //don't show error message when redirecting from parentcontrolpanel
                    //verify that all fields were filled
                    if (sID == null || cOfferedIDs == null) {
                        errorMessage = "All required fields must be filled";
                    } else if (legalAgreement == null) {
                        errorMessage = "You must agree to the terms and conditions";
                    }
                }

                if (errorMessage.length() > 0 || request.getParameter("applycamppath") == null) {
                    request.setAttribute("errorMessage", errorMessage);
                    request.setAttribute("studentID", sID);


                    // CC: Populate the page with camp information
                    // TODO: ONCE CAMP INFORMATION IS PROPERLY ADDED TO THE DATABASE, FIX THIS
                    List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();

                    // CC: Now, we need to get the camps a student has already applied to.
                    List<StudentCampApplication> studentCampArray = DatabaseQueries.getStudentCamps();
                    request.setAttribute("studentCampArray", studentCampArray);

                    List<StudentCampApplication> appliedCampArray = DatabaseQueries.getStudentCampsByStudentID(Integer.parseInt(sID));
                    request.setAttribute("appliedCampArray", appliedCampArray);
                    request.setAttribute("campTopicList", campTopicList);
                    request.setAttribute("numTopics", campTopicList.size());
                    List<List<Camp>> allEligibleCamps = new ArrayList<List<Camp>>();

                    for (CampTopic campTopic : campTopicList) {
                        List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, false);
                        campTopic.setCampOfferedList(campOfferedList);

                        List<Camp> eligibleCamps = new ArrayList<Camp>();
                        for (CampOffered c : campTopic.getCampOfferedList()) {
                            // eligible camps are the ones that the student haven't applied to nor have been waitlisted or rejected
                            // should only show a camp again if the student only has withdrawn
                            boolean eligible = true;
                            for (StudentCampApplication app : appliedCampArray) {
                                if (app.getCampOfferedID() == c.getCampOfferedID()) {
                                    if (app.getStatus() != "Withdrawn") {
                                        eligible = false;
                                        break;
                                    }
                                }
                            }
                            if (eligible) {
                                eligibleCamps.add(new Camp(c));
                            }
                        }
                        allEligibleCamps.add(eligibleCamps);
                    }

                    // Re-populate all fields
                    request.setAttribute("allEligibleCamps", allEligibleCamps);
                    request.setAttribute("campOffered", cOfferedIDs);
                    request.setAttribute("legalAgreement", legalAgreement);
                    request.setAttribute("pemail", pemail);
                    request.setAttribute("pemailID", pemailID);
                    request.setAttribute("studentName", studentName);

                    request.getRequestDispatcher("/WEB-INF/parent/applyforcamp.jsp").forward(request, response);
                } else {
                    String newCode = generatePickupCode();
                    DatabaseInserts.insertPickupCode(sID, newCode);

                    // update student's progress
                    if (student.getProgress() == 2) {
                        DatabaseUpdates.updateStudentProgress(studentID, 3);
                    }

                    // Insert student application to DB
                    for (String id : cOfferedIDs) {
                        int campID = Integer.parseInt(id);
                        DatabaseInserts.insertStudentApplication(studentID, campID);
                    }

                    // Update student legal agreement attribute with new settings
                    int legalAgree = 0;
                    if (legalAgreement.equals("yes")) {
                        legalAgree = 1;
                    }
                    DatabaseInserts.insertStudentLegalAgree(studentID, legalAgree);

                    // JS: Get names of camps applied to for the registration confirmation email [campLevel campTopic (campStart - campEnd)]
                    StringBuilder builder = new StringBuilder();
                    for (String id : cOfferedIDs) {
                        int campID = Integer.parseInt(id);
                        CampOffered camp = DatabaseQueries.getCampOffered(campID);
                        Format dateFormatter = new SimpleDateFormat("M/d/yy");
                        String campStart = dateFormatter.format(camp.getStartDate());
                        String campEnd = dateFormatter.format(camp.getEndDate());
                        String campDates = campStart + " - " + campEnd;

                        Date startTimeToDate = null;
                        Date endTimeToDate = null;
                        try {
                            DateFormat timeToDateFormat = new SimpleDateFormat("hh:mm:ss");
                            startTimeToDate = timeToDateFormat.parse(camp.getStartTime().toString());
                            endTimeToDate = timeToDateFormat.parse(camp.getEndTime().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        String campTimes = timeFormat.format(startTimeToDate) + " - " + timeFormat.format(endTimeToDate);

                        builder.append(camp.getCampLevel());
                        builder.append(" ");
                        builder.append(camp.getCampTopic());
                        builder.append(" (");
                        builder.append(campDates);
                        builder.append(", ");
                        builder.append(campTimes);
                        builder.append(")<br>");
                    }
                    String combinedString = builder.toString();
                    String camps = combinedString.substring(0, combinedString.length() - 4);
                    String notificationDate = "April 30, 2017";


                    String parseVariables = parseEmail(DatabaseQueries.getEmailContents(EmailType.REGISTERED));
                    int offset = parseVariables.indexOf(EmailConstants.STUDENT_NAME);
                    while (offset >= 0) {
                        parseVariables = parseVariables.substring(0, offset) + studentName + parseVariables.substring(offset + 13);
                        offset = parseVariables.indexOf(EmailConstants.STUDENT_NAME);
                    }
                    offset = parseVariables.indexOf(EmailConstants.CAMPS);
                    if (offset >= 0) {
                        parseVariables = parseVariables.substring(0, offset) + "</p><p align=\"center\" style=\"font-size:22px;\">"
                                + camps + "</p><p style=\"font-size:18px;\">" + parseVariables.substring(offset + 7);
                    }
                    offset = parseVariables.indexOf(EmailConstants.NOTIFICATION_DATE);
                    if (offset >= 0) {
                        parseVariables = parseVariables.substring(0, offset) + notificationDate + parseVariables.substring(offset + 18);
                    }
                    String content = parseVariables;
                    String subject = DatabaseQueries.getEmailSubject(EmailType.REGISTERED);
                    MailServer.sendEmail(pemail, subject, content, getServletContext());

                    if (role.equals(StringConstants.PARENT)) {
                        SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);
                    }

                    List<StudentCampApplication> appliedCampArray = DatabaseQueries.getStudentCampsByStudentID(Integer.parseInt(sID));
                    request.getSession().setAttribute("appliedCampArray", appliedCampArray);

                    // CC: Set up our success message -- student or camp application completed successfully!
                    successMessage += "Camp application successful! Confirmation email sent. Please click on a student button to manage that student's camp applications and enrollment.";
                    request.getSession().setAttribute("successMessage", successMessage);

                    if (role.equals(StringConstants.PARENT)) {
                        response.sendRedirect("/SummerCamp/login");
                    } else {
                        request.setAttribute("successMessage", successMessage);
                        request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
                    }
                }
            } else if (button.equalsIgnoreCase("Cancel")) {
                List<StudentCampApplication> appliedCampArray = DatabaseQueries.getStudentCampsByStudentID(Integer.parseInt(sID));
                request.getSession().setAttribute("appliedCampArray", appliedCampArray);
                if (role.equals(StringConstants.PARENT)) {
                    SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);
                    response.sendRedirect("/SummerCamp/login");
                } else {
                    request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
                }
            }
        }
    }

    private String parseEmail(String contents) {
        StringBuilder contentsFinal = new StringBuilder(contents);
        int offset = contentsFinal.indexOf("CS@SC Summer Camps");
        if (offset >= 0) {
            contentsFinal.insert(offset + 18, "</h1><p style=\"font-size: 20px;\">");
            contentsFinal.insert(offset, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
        } else {
            offset = contentsFinal.indexOf("!");
            if (offset < 50 && offset > 0) {
                contentsFinal.insert(offset + 1, "</h1><p style=\"font-size: 20px;\">");
                contentsFinal.insert(0, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
            }
        }
        contentsFinal = parseLogIns(contentsFinal);
        offset = contentsFinal.indexOf("\n");
        while (offset >= 0) {
            contentsFinal.replace(offset, offset + 1, "<br>");
            offset = contentsFinal.indexOf("\n", offset + 4);
        }
        return contentsFinal.toString();
    }

    private StringBuilder parseLogIns(StringBuilder contentsFinal) {

        int offset = contentsFinal.indexOf("log in");
        String register = "<a href=\"http://summercamp.usc.edu:8080/SummerCamp/register.jsp\">";
        while (offset >= 0) {
            contentsFinal.insert(offset + 6, "</a>");
            contentsFinal.insert(offset, register);
            offset = contentsFinal.indexOf("log in", offset + 6 + register.length());
        }

        offset = contentsFinal.indexOf("logging in");
        while (offset >= 0) {
            contentsFinal.insert(offset + 10, "</a>");
            contentsFinal.insert(offset, register);
            offset = contentsFinal.indexOf("logging in", offset + 10 + register.length());
        }
        offset = contentsFinal.indexOf("Log in");
        while (offset >= 0) {
            contentsFinal.insert(offset + 6, "</a>");
            contentsFinal.insert(offset, register);
            offset = contentsFinal.indexOf("log in", offset + 6 + register.length());
        }

        offset = contentsFinal.indexOf("Logging in");
        while (offset >= 0) {
            contentsFinal.insert(offset + 10, "</a>");
            contentsFinal.insert(offset, register);
            offset = contentsFinal.indexOf("logging in", offset + 10 + register.length());
        }
        return contentsFinal;
    }
}