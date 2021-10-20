package servlets;

import data.*;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import database.SQLStatements;
import enums.EmailType;
import util.EmailConstants;
import util.EmailParse;
import util.MailServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.*;
import java.util.*;

public class CampStudents extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {

            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String campOfferedIDAsString = request.getParameter("campOfferedID");

            if (campOfferedIDAsString == null || campOfferedIDAsString.isEmpty()) {
                response.sendRedirect("/SummerCamp/admincontrolpanel");
            } else {
                request.getSession().setAttribute("campOfferedID", campOfferedIDAsString);
                int campOfferedID = Integer.parseInt((String) request.getSession().getAttribute("campOfferedID"));
                CampOffered campOffered = DatabaseQueries.getCampOffered(campOfferedID);

                if (campOffered != null) {
                    request.getSession().setAttribute("campOffered", campOffered);
                }
                String campPrice = "Free Camp";
                if (campOffered.getPrice() != 0) {
                    campPrice = "$" + campOffered.getPrice();
                    campPrice = new StringBuilder(campPrice).insert(campPrice.length() - 2, ".").append(" Paid Camp").toString();
                }
                request.getSession().setAttribute("campPriceAsInt", campOffered.getPrice() / 100.0);
                request.getSession().setAttribute("eCampPriceAsInt", campOffered.getEmployeePrice() / 100.0 );

                List<Camp> campList = new ArrayList<Camp>();
                List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();
                for (CampTopic campTopic : campTopicList) {
                    List<CampOffered> campOfferedList = DatabaseQueries.getCampsOffered(campTopic, true);
                    campTopic.setCampOfferedList(campOfferedList);
                    for (CampOffered c : campTopic.getCampOfferedList()) {
                        if (!campList.contains(c)) {
                            campList.add(new Camp(c));
                        }
                    }
                }
                List<StudentCampApplication> studentCampArray = DatabaseQueries.getStudentCamps();
                request.setAttribute("studentCampArray", studentCampArray);
                request.setAttribute("campArray", campList);
                request.setAttribute("campStatuses", SQLStatements.studentCampStatusList);
                List<String> allEmails = new ArrayList<String>();
                List<String> acceptEmails = new ArrayList<String>();
                List<String> confirmEmails = new ArrayList<String>();
                List<String> waitlistEmails = new ArrayList<String>();
                List<String> rejectEmails = new ArrayList<String>();
                List<String> withdrawnEmails = new ArrayList<String>();

                for (String studentCampStatus : SQLStatements.studentCampStatusList) {
                    //this is where the student is built, if a field is missing its pro
                    List<StudentCamp> studentCampList = DatabaseQueries.getStudentCamps(studentCampStatus, campOfferedID);
                    request.getSession().setAttribute(studentCampStatus + "StudentCampList", studentCampList);
                    Map<StudentCamp, Parent> studentParentMap = new HashMap<StudentCamp, Parent>();
                    for (StudentCamp studentCamp : studentCampList) {
                        Student student = studentCamp.getStudent();

                        int emailID = DatabaseQueries.getEmailIDAssociatedWithStudent(student.getStudentID());
                        String email = DatabaseQueries.getEmail(emailID);
                        if (DatabaseQueries.isAccepted(student.getStudentID(), campOfferedID) && !acceptEmails.contains(email)) {
                            for (StudentCampApplication s : studentCampArray) {
                                if (s.getStudentID() == student.getStudentID() && campOfferedID == s.getCampOfferedID()) {
                                }
                            }
                            request.setAttribute("studentCampArray", studentCampArray);
                            acceptEmails.add(email);
                        }
                        request.getSession().setAttribute(studentCampStatus + "StudentCampList", studentCampList);

                        if (DatabaseQueries.isConfirmed(student.getStudentID(), campOfferedID) && !confirmEmails.contains(email)) {
                            request.getSession().setAttribute("myPrice", Double.toString(DatabaseQueries.getPriceDouble(student.getStudentID(), campOfferedID)));
                            confirmEmails.add(email);
                        }
                        //int emailID = DatabaseQueries.getEmailIDAssociatedWithStudent(student.getStudentID());
                        if (DatabaseQueries.isAccepted(student.getStudentID(), campOfferedID) && !acceptEmails.contains(email)) {
                            acceptEmails.add(email);
                        }

                        if (DatabaseQueries.isWaitlisted(student.getStudentID(), campOfferedID) && !waitlistEmails.contains(email)) {
                            waitlistEmails.add(email);
                        }
                        if (DatabaseQueries.isRejected(student.getStudentID(), campOfferedID) && !rejectEmails.contains(email)) {
                            rejectEmails.add(email);
                        }
                        if (DatabaseQueries.isWithdrawn(student.getStudentID(), campOfferedID) && !withdrawnEmails.contains(email)) {
                            withdrawnEmails.add(email);
                        }
                        if (!allEmails.contains(email)) {

                            allEmails.add(email);
                        }

                        Parent parent = DatabaseQueries.getParentByEID(emailID);
                        studentParentMap.put(studentCamp, parent);
                    }
                    request.getSession().setAttribute(studentCampStatus + "ParentMap", studentParentMap);
                }
                String emailStr = Integer.toString(allEmails.size());
                request.getSession().setAttribute("allEmailList", allEmails);
                request.getSession().setAttribute("allEmailSize", emailStr);
                request.getSession().setAttribute("acceptEmailsLength", acceptEmails.size());
                request.getSession().setAttribute("acceptEmails", acceptEmails);
                request.getSession().setAttribute("confirmEmailsLength", confirmEmails.size());
                request.getSession().setAttribute("confirmEmails", confirmEmails);
                request.getSession().setAttribute("waitlistEmailsLength", waitlistEmails.size());
                request.getSession().setAttribute("waitlistEmails", waitlistEmails);
                request.getSession().setAttribute("rejectEmailsLength", rejectEmails.size());
                request.getSession().setAttribute("rejectEmails", rejectEmails);
                request.getSession().setAttribute("withdrawnEmailsLength", withdrawnEmails.size());
                request.getSession().setAttribute("withdrawnEmails", withdrawnEmails);

                request.getRequestDispatcher("/WEB-INF/admin/campstudents.jsp").forward(request, response);
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int sendEmail = 1;

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String submitValue = request.getParameter("submit");
            @SuppressWarnings("unchecked")
            List<StudentCamp> studentCampList = (List<StudentCamp>) request.getSession().getAttribute(submitValue + "StudentCampList");

            for (int i = 0; i < studentCampList.size(); i++) {
                Student currStudent = studentCampList.get(i).getStudent();
                int campOfferedID = Integer.parseInt((String) request.getSession().getAttribute("campOfferedID"));
                String status = "status-" + currStudent.getStudentID();
                String actualStatus = request.getParameter(status);
                if (!actualStatus.equals("noaction")) {
                    int parentID = DatabaseQueries.getParentID(currStudent.getStudentID());
                    int emailID = DatabaseQueries.getEmailID(parentID);
                    String studentName = DatabaseQueries.getStudentName(currStudent.getStudentID());
                    CampOffered camp = DatabaseQueries.getCampOffered(campOfferedID);

                    String campLevel = camp.getCampLevel();
                    String campTopic = camp.getCampTopic();
                    String campDescription = camp.getDescription();
                    boolean isPaidCamp = false;

                    if (camp.getPrice() != 0) {
                        isPaidCamp = true;
                    }

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
                    String campDates = formatter.format(camp.getStartDate()) + " - " + formatter.format(camp.getEndDate()) +
                            ", " + campTimes;

                    Format formatter1 = new SimpleDateFormat("M/d/yy");
                    String campDates1 = formatter1.format(camp.getStartDate()) + " - " + formatter1.format(camp.getEndDate()) +
                            ", " + campTimes;

                    Format formatter2 = new SimpleDateFormat("EEEE, MMMM d");
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, 7);
                    String deadlineDate = formatter2.format(calendar.getTime());

                    String emailTo = DatabaseQueries.getEmail(emailID);
                    String subject = "CS@SC Summer Camps: " + campLevel + " " + campTopic + " - Application Status Update";
                    String content = "";

                    List<EmailParse> emailParts = new ArrayList<EmailParse>(Arrays.asList(
                            new EmailParse(EmailConstants.CAMP_LEVEL, "</p><p align=\"center\"><span style=\"font-size: 26px;\">" + campLevel),
                            new EmailParse(EmailConstants.CAMP_TOPIC, campTopic + "</span><span style=\"font-size: 20px;\">"),
                            new EmailParse(EmailConstants.CAMP_DATES, campDates + "</span><span style=\"font-size: 16px;\">"),
                            new EmailParse(EmailConstants.CAMP_DESCRIPTION, campDescription + "</span></p><p align=\"center\" style=\"font-size:20px;\">")));

                    if (actualStatus.equals("paid")) {
                        DatabaseUpdates.payForCamp(currStudent.getStudentID(), campOfferedID);
                        DatabaseUpdates.confirmStudentApplication(currStudent.getStudentID(), campOfferedID);
                        String checkoutCode = DatabaseQueries.getPickupCode(Integer.toString(currStudent.getStudentID()));
                        subject = DatabaseQueries.getEmailSubject(EmailType.ENROLLED_PAID);// MailServer.parseSubject(DatabaseQueries.getEmailSubject(6), campLevel, campTopic);
                        //click here for parking and schedule
                        String parseVariables = MailServer.parseEmail(DatabaseQueries.getEmailContents(EmailType.ENROLLED_PAID));
                        parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(parseVariables);

                        String device = "";
                        if (campTopic.equals("Scratch Jr.")) {
                            device = "tablets";
                        } else {
                            device = "laptops";
                        }
                        List<EmailParse> toAdd = Arrays.asList(new EmailParse(EmailConstants.PICKUP_CODE, "<strong>" + checkoutCode + "</strong>"),
                                new EmailParse("laptop", device, 1),
                                new EmailParse("tablet", device, 1));
                        emailParts.addAll(toAdd);

                        content = MailServer.parseVars(emailParts, parseVariables); //parseVariables;
                    } else if (actualStatus.equals("withdraw")) {
                        DatabaseUpdates.withdrawStudentApplication(currStudent.getStudentID(), campOfferedID);
                        sendEmail = 0;
                    } else {
                        DatabaseInserts.insertStudentHistory(actualStatus, currStudent, campOfferedID);
                        DatabaseUpdates.updateStudentStatus(actualStatus, currStudent, campOfferedID);

                        String parseVariables = MailServer.parseEmail(DatabaseQueries.getEmailContents(EmailType.ACCEPT_PAID));
                        subject = DatabaseQueries.getEmailSubject(EmailType.ACCEPT_PAID);

                        if (actualStatus.equals("accept") && isPaidCamp) {
                            int cost = 0;
                            String value = request.getParameter("payment-field-" + currStudent.getStudentID());
                            String intValue = "";

                            int locationOfDecimal = value.indexOf('.');
                            if (locationOfDecimal >= 0) {
                                intValue = value.substring(0, value.indexOf('.')) + value.substring(value.indexOf('.') + 1);
                                int multByTen = value.length() - locationOfDecimal - 1;
                                cost = Integer.valueOf(intValue);
                                if (multByTen - 2 != 0) {
                                    cost *= (10 * Math.abs(multByTen - 2));
                                }
                            } else {
                                Double costDouble = (Double.valueOf(value) * 100.00);
                                cost = costDouble.intValue();
                                //STORE COST IN DATABASE
                            }

                            DatabaseUpdates.updateStudentCost(cost, currStudent, campOfferedID);
                            DecimalFormat formattIt = new DecimalFormat("#0.00");
                            String campPriceForStudent = String.valueOf(formattIt.format((double) cost / 100.00));
                            String checkoutCode = DatabaseQueries.getPickupCode("" + currStudent.getStudentID());

                            parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(parseVariables);
                            List<EmailParse> toAdd = Arrays.asList(
                                    new EmailParse(EmailConstants.CAMP_PRICE, campPriceForStudent + "</strong>"),
                                    new EmailParse(EmailConstants.DEADLINE_DATE, "<strong>" + deadlineDate + "</strong>", true),
                                    new EmailParse(EmailConstants.PICKUP_CODE, "<strong>" + checkoutCode + "</strong>", -1, true));
                            emailParts.addAll(toAdd);

                            content = MailServer.parseVars(emailParts, parseVariables);//parseVariables;

                        } else {

                            if (actualStatus.equals("accept")) {
                                subject = DatabaseQueries.getEmailSubject(EmailType.ACCEPT_FREE);
                                parseVariables = MailServer.parseEmail(DatabaseQueries.getEmailContents(EmailType.ACCEPT_FREE));
                                parseVariables = new EmailParse(EmailConstants.STUDENT_NAME, studentName).parseWhile(parseVariables);
                                emailParts.addAll(Arrays.asList(
                                        new EmailParse(EmailConstants.DEADLINE_DATE, "<strong>" + deadlineDate + ".</strong>", true)));

                                content = MailServer.parseVars(emailParts, parseVariables);
                            } else if (actualStatus.equals("waitlist")) {
                                subject = DatabaseQueries.getEmailSubject(EmailType.WAITLIST);
                                content = MailServer.emailWaitlistOrReject(EmailType.WAITLIST, studentName, campLevel, campTopic, campDates1);
                            } else if (actualStatus.equals("reject")) {
                                subject = DatabaseQueries.getEmailSubject(EmailType.REJECTED);
                                content = MailServer.emailWaitlistOrReject(EmailType.REJECTED, studentName, campLevel, campTopic, campDates1);

                            }
                        }
                    }
                    if (sendEmail == 1) {
                        subject = MailServer.parseSubject(subject, campLevel, campTopic);
                        MailServer.sendEmail(emailTo, subject, content, getServletContext());
                    }
                }
            }

            response.sendRedirect("/SummerCamp/campstudents?campOfferedID=" + request.getSession().getAttribute("campOfferedID"));
        }
    }

}
