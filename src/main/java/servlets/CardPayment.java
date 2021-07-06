package servlets;

import data.CampOffered;
import data.Parent;
import data.Student;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import enums.EmailType;
import util.EmailConstants;
import util.MailServer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

public class CardPayment extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static void sendConfirmation(int campOfferedID, int studentID, String emailTo, ServletContext servletContext) {
        String studentName = DatabaseQueries.getStudentName(studentID);
        CampOffered camp = DatabaseQueries.getCampOffered(campOfferedID);
        
        String campLevel = camp.getCampLevel();
        String campTopic = camp.getCampTopic();
        String campDescription = camp.getDescription();

        Format formatter = new SimpleDateFormat("EEE, MMMM d");
        String campStart = formatter.format(camp.getStartDate());
        String campEnd = formatter.format(camp.getEndDate());

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

        String campDates = campStart + " - " + campEnd + ", " + campTimes;

        String checkoutCode = DatabaseQueries.getPickupCode(Integer.toString(studentID));

        // enrollsuccessemail.html for free camps

        String parseVariables = parseEmail(DatabaseQueries.getEmailContents(EmailType.ENROLLED_FREE));

        int offset = parseVariables.indexOf(EmailConstants.STUDENT_NAME);
        while (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + studentName + parseVariables.substring(offset + 13);
            offset = parseVariables.indexOf("<studentName>");
        }
        offset = parseVariables.indexOf(EmailConstants.CAMP_LEVEL);
        if (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + "</p><p align=\"center\"><span style=\"font-size: 26px;\">"
                    + campLevel + parseVariables.substring(offset + 11);
        }
        offset = parseVariables.indexOf(EmailConstants.CAMP_TOPIC);
        if (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + campTopic + "</span><span style=\"font-size: 20px;\">"
                    + parseVariables.substring(offset + 11);
        }
        offset = parseVariables.indexOf(EmailConstants.CAMP_DATES);
        if (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + campDates + "</span><span style=\"font-size: 16px;\">"
                    + parseVariables.substring(offset + 11);
        }
        offset = parseVariables.indexOf(EmailConstants.CAMP_DESCRIPTION);
        if (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + campDescription +
                    "</span></p><p style=\"font-size: 20px;\">" + parseVariables.substring(offset + 17) + "</p>";
        }
        offset = parseVariables.indexOf(EmailConstants.PICKUP_CODE);
        if (offset >= 0) {
            parseVariables = parseVariables.substring(0, offset) + "<strong>" + checkoutCode +
                    "</strong>" + parseVariables.substring(offset + 12);
        }

        String content = parseVariables;
        String subject = parseSubject(DatabaseQueries.getEmailSubject(EmailType.ENROLLED_FREE), campLevel, campTopic);

        MailServer.sendEmail(emailTo, subject, content, servletContext);
    }

    private static String parseSubject(String subject, String campLevel, String campTopic) {
        int offset = subject.indexOf(EmailConstants.CAMP_LEVEL);
        if (offset >= 0) {
            subject = subject.substring(0, offset) + campLevel + subject.substring(offset + 11);
        }
        offset = subject.indexOf(EmailConstants.CAMP_TOPIC);
        if (offset >= 0) {
            subject = subject.substring(0, offset) + campTopic + subject.substring(offset + 11);
        }
        return subject;
    }

    private static String parseEmail(String contents) {
        StringBuilder contentsFinal = new StringBuilder(contents);
        contentsFinal = parseLinks(contentsFinal);
        int offset = contentsFinal.indexOf("!");
        if (offset < 30 && offset > 0) {
            contentsFinal.insert(offset + 1, "</h1><p style=\"font-size: 20px;\">");
            contentsFinal.insert(0, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
        } else {
            offset = contentsFinal.indexOf("CS@SC Summer Camps");
            if (offset >= 0) {
                contentsFinal.insert(offset + 18, "</h1><p style=\"font-size: 20px;\">");
                contentsFinal.insert(offset, "<h1 align=\"center\" style=\"font-weight:200; font-size: 30px;\">");
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

    private static StringBuilder parseLogIns(StringBuilder contentsFinal) {

        int offset = contentsFinal.indexOf("log in");
        String register = "<a href=\"/SummerCamp/register.jsp\">";
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

    private static StringBuilder parseLinks(StringBuilder contentsFinal) {
        int offset = contentsFinal.indexOf("instruction");
        if (offset >= 0) {
            int instIndex = offset;
            offset = contentsFinal.indexOf("Click here", instIndex - 20);
            String instructions = "<a href=\"http://summercamp.usc.edu/wordpress/wp-content/uploads/2016/03/Paid-Camp-Instructions\">";
            if (offset >= 0 && offset > instIndex - 20) {
                contentsFinal.insert(offset, instructions);
                contentsFinal.insert(offset + instructions.length() + 10, "</a>");
            } else {
                offset = contentsFinal.indexOf("click here", instIndex - 20);
                if (offset >= 0 && offset > instIndex - 20) {
                    contentsFinal.insert(offset, instructions);
                    contentsFinal.insert(offset + instructions.length() + 10, "</a>");
                }
            }
        }

        offset = contentsFinal.indexOf("schedule");
        if (offset >= 0) {
            int scheduleIndex = offset;
            offset = contentsFinal.indexOf("Click here", scheduleIndex - 50);
            String schedule = "<a href=\"http://summercamp.usc.edu/schedule/\">";
            if (offset >= 0 && offset > scheduleIndex - 50) {
                contentsFinal.insert(offset, schedule);
                contentsFinal.insert(offset + schedule.length() + 10, "</a>");
            } else {
                offset = contentsFinal.indexOf("click here", scheduleIndex - 50);
                if (offset >= 0 && offset > scheduleIndex - 50) {
                    contentsFinal.insert(offset, schedule);
                    contentsFinal.insert(offset + schedule.length() + 10, "</a>");
                }
            }
        }

        offset = contentsFinal.indexOf("parking");
        if (offset >= 0) {
            int parkingIndex = offset;
            offset = contentsFinal.indexOf("Click here", parkingIndex - 40);
            String parking = "<a href=\"http://summercamp.usc.edu/directions-parking/\">";
            if (offset >= 0 && offset > parkingIndex - 40) {
                contentsFinal.insert(offset, parking);
                contentsFinal.insert(offset + parking.length() + 10, "</a>");
            } else {
                offset = contentsFinal.indexOf("click here", parkingIndex - 40);
                if (offset >= 0 && offset > parkingIndex - 40) {
                    contentsFinal.insert(offset, parking);
                    contentsFinal.insert(offset + parking.length() + 10, "</a>");
                }
            }
        }
        return contentsFinal;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        System.out.println("=============");
//        System.out.println("=============");
//        System.out.println("=============");
//        System.out.println("Card Payment");
//
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while (parameterNames.hasMoreElements()) {
//
//            String paramName = parameterNames.nextElement();
//            System.out.println(paramName);
//
//            String[] paramValues = request.getParameterValues(paramName);
//            for (int i = 0; i < paramValues.length; i++) {
//                String paramValue = paramValues[i];
//                System.out.println("\t" + paramValue);
//            }
//        }
//
//        System.out.println("=============");
//        System.out.println("=============");
//        System.out.println("=============");


        String campOfferedIDS = request.getParameter("camp_campID");
        String result = request.getParameter("ssl_result_message");
        String email = request.getParameter("ssl_email");
        String studentIdStr = request.getParameter("camp_studentID");
        String tokenStr = request.getParameter("token_keep");
        String resultNum = request.getParameter("ssl_result");
        request.getSession().setAttribute("errorMessage", "");
        request.getSession().setAttribute("successMessage", "");

        int campOfferedId = -1;

        if (result != null && resultNum != null) {
            int resultID = Integer.valueOf(resultNum);
            if (result.equalsIgnoreCase("INVALID CARD") || resultID == 1) {
                System.out.println("INVALID CARD");
                request.getSession().setAttribute("errorMessage", "INVALID CARD");
                response.sendRedirect("/SummerCamp/login");
            } else if (email == null) {
                System.out.println("NO EMAIL");
                request.getSession().setAttribute("tokenStr", "");
                request.setAttribute("errorMessage", "SESSION EXPIRED, PLEASE LOG IN FIRST");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (studentIdStr == null || campOfferedIDS == null || tokenStr == null) {
                System.out.println("NO STUDENTIDSTR OT NO CAMPOFFEREDID OR NO TOKENSTR");
                request.getSession().setAttribute("tokenStr", "");
                request.setAttribute("errorMessage", "Invalid request, no camp id or student id.");
                response.sendRedirect("/SummerCamp/login");
                //request.getSession().setAttribute("message", "Please try again");
            } else {
                request.getSession().setAttribute("tokenStr", "");
                int emailID = DatabaseQueries.doesEmailExist(email);
                Parent parent = DatabaseQueries.getParentByEID(emailID);
                int parentID = parent.getParentID();
                List<Student> studentArray = DatabaseQueries.getStudents(parentID, false);

                Student paidForStudent = null;
                int studentId = Integer.valueOf(studentIdStr);

                for (Student student : studentArray) {
                    if (student.getStudentID() == studentId) {
                        paidForStudent = student;
                    }
                }
                campOfferedId = Integer.valueOf(campOfferedIDS);
                emailID = SecurityChecker.getEmailID(email);
                //validate price here as well
                double price = DatabaseQueries.getPriceDouble(studentId, campOfferedId);
                DecimalFormat formattIt = new DecimalFormat("#0.00");
                String campPriceForStudent = String.valueOf(formattIt.format(price));

                if (request.getParameter("ssl_amount").equals(campPriceForStudent)) {
                    if (paidForStudent != null && DatabaseQueries.isAccepted(studentId, campOfferedId) && SecurityChecker.userHasAccessToStudent(emailID, studentId)) {
                        System.out.println("SUCCESS");
                        sendConfirmation(campOfferedId, studentId, email, getServletContext());

                        DatabaseUpdates.payForCamp(studentId, campOfferedId);
                        DatabaseUpdates.confirmStudentApplication(studentId, campOfferedId);

                        String successMessage = "Camp enrollment successful! Confirmation email sent.";
                        request.getSession().setAttribute("successMessage", successMessage);
                        request.getSession().setAttribute("tokenStr", "");
                        response.sendRedirect("/SummerCamp/login");
                    } else {
                        System.out.println("CRYPTIC");
                        request.getSession().setAttribute("errorMessage", "Some error occured, please email us");
                        request.getSession().setAttribute("tokenStr", "");
                        response.sendRedirect("/SummerCamp/login");
                    }
                } else {
                    System.out.println("PAYMENT AMOUNT WRONG");
                    request.getSession().setAttribute("errorMessage", "Payment amount is incorrect");
                    request.getSession().setAttribute("tokenStr", "");
                    response.sendRedirect("/SummerCamp/login");
                }
            }
        } else {
            System.out.println("CRYPTIC");
            request.getSession().setAttribute("errorMessage", "Some error occured, please make sure information was entered correctly");
            request.getSession().setAttribute("tokenStr", "");
            response.sendRedirect("/SummerCamp/login");
        }
    }
}
