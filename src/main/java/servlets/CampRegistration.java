package servlets;


import data.*;
import database.DatabaseInserts;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import enums.EmailType;
import util.EmailConstants;
import util.MailServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.*;

//camp comparator
class SortCamp implements Comparator<Camp>{
    public int compare(Camp a, Camp b){
        //compare levels if they are not equal
        if(a.getCampLevel().compareTo(b.getCampLevel()) != 0)
            return a.getCampLevel().compareTo(b.getCampLevel());
        else{
            //if levels are equal, compare start dates
            if(a.getCampStartDate().compareTo(b.getCampStartDate()) != 0)
                return a.getCampStartDate().compareTo(b.getCampStartDate());
            else{
                //if start dates are equal, compare days
                if(a.getCampDays().compareTo(b.getCampDays()) != 0)
                    return a.getCampDays().compareTo(b.getCampDays());
                else{
                    //if days are equal, compare time and return anyways
                        return a.getCampTime().compareTo(b.getCampTime());
                }
            }
        }
    }
}

public class CampRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        int studentID = (int) request.getSession().getAttribute("studentID");
        List<List<Camp>> allEligibleCamps = getEligibleCamps(request, response);
        List<StudentCamp> campsCurrentApplied = DatabaseQueries.getCurrentCampsAppliedByStudentID(studentID);
        List<CampLevel> campLevels = DatabaseQueries.getCurrentCampLevels();
        List<CampTopic> campTopics = DatabaseQueries.getCurrentCampTopics();

        for (List<Camp> topic : allEligibleCamps) {
            for (Camp c : topic) {
                for (StudentCamp sc : campsCurrentApplied) {
                    if (sc.getCampOfferedID() == c.getCampOfferedID()) {
                        c.setApplied(true);
                    }
                }
            }
        }
        request.setAttribute("allEligibleCamps", allEligibleCamps);
        request.setAttribute("campLevels", campLevels);
        request.setAttribute("campTopics", campTopics);

        // get student and set as session attribute for navbar
        Student student = DatabaseQueries.getStudent(studentID, false);
        request.getSession().setAttribute("student", student);
        request.getRequestDispatcher("/WEB-INF/camper/campRegistration.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentID = (Integer) request.getSession().getAttribute("studentID");

        String[] checkboxes = request.getParameterValues("checkbox");
        ArrayList<String> checked = new ArrayList<>();
        for (String checkbox : checkboxes) {
            if (checkbox.charAt(0) == '1') {
                checked.add(checkbox);
            }
        }

        // update student's progress
        Student student = DatabaseQueries.getStudent(studentID, false);
        String email = (String) request.getSession().getAttribute("email");
        ArrayList<CampOffered> appliedCamps = new ArrayList<>();

        for (String campID : checked) {
            int campIdInt = Integer.parseInt(campID.substring(2));
            DatabaseInserts.insertStudentApplication(studentID, campIdInt);

            CampOffered camp = DatabaseQueries.getCampOffered(campIdInt);
            appliedCamps.add(camp);

            if (student.getProgress() == 2) {
                DatabaseUpdates.updateStudentProgress(studentID, 3);
            }
        }

        if (!appliedCamps.isEmpty()) {
            sendRegisteredEmail(email, student.getName(), appliedCamps);
        }

        response.sendRedirect("/SummerCamp/mycamps");
    }

    protected List<List<Camp>> getEligibleCamps(HttpServletRequest request, HttpServletResponse response) {

        // get all the current camp topics
        List<CampTopic> campTopicList = DatabaseQueries.getCampTopics();

        List<StudentCampApplication> studentCampArray = DatabaseQueries.getStudentCamps();
        request.setAttribute("studentCampArray", studentCampArray);

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
                if (eligible) {
                    eligibleCamps.add(new Camp(c));
                }
            }
            allEligibleCamps.add(eligibleCamps);
        }

        //sort camps (by level, day, date, time)
        for(int i = 0; i < allEligibleCamps.size(); i++)
            Collections.sort(allEligibleCamps.get(i), new SortCamp());

        return allEligibleCamps;
    }

    private void sendRegisteredEmail(String emailTo, String studentName, ArrayList<CampOffered> appliedCamps) {
        StringBuilder contentsFinal = new StringBuilder();
        contentsFinal.append("<h1 style=\"font-weight:200; font-size: 30px;\">Thank you for registering!</h1>");

        contentsFinal.append("<p style=\"font-size: 20px;\">");
        contentsFinal.append("We received " + studentName + "'s registration for the following camp(s):");
        contentsFinal.append("</p>");

        for (CampOffered camp : appliedCamps) {
            contentsFinal.append("<p align=\"center\" style=\"font-size: 22px;\">");
            contentsFinal.append(camp.getCampTopic() + " " + camp.getCampLevel() + "<br />" + camp.getDateString() + "<br>" + camp.getTimeString());
            contentsFinal.append("</p><hr />");
        }

        contentsFinal.append("<p style=\"font-size: 18px;\">");
        contentsFinal.append("Once all applications have been processed, you will receive another email with the status of your application soon. You can also check your status by <a href=\"https://summercamp.usc.edu/SummerCamp/register.jsp\">logging in to your account</a>.");
        contentsFinal.append("</p>");

        String subject = DatabaseQueries.getEmailSubject(EmailType.REGISTERED);
        MailServer.sendEmail(emailTo, subject, contentsFinal.toString(), getServletContext());
    }
}
