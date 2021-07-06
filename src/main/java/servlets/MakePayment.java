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
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.text.*;
import java.util.*;

public class MakePayment extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentAndStudentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        // Generate payment token
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        request.getSession().setAttribute("tokenStr", token);

        // Get student ID and parent ID from session
        int studentID = (int) request.getSession().getAttribute("studentID");
        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));

        // Filter camps that have not been paid for
        List<StudentCamp> studentAcceptedCampArray = DatabaseQueries.getStudentAcceptedCampsSQL(studentID);
        List<StudentCamp> studentAcceptedUnpaidCamps = new ArrayList<>();
        for (StudentCamp studentCamp : studentAcceptedCampArray) {
            if (studentCamp.getCost() > 0 && !studentCamp.isPaid()) {
                studentAcceptedUnpaidCamps.add(studentCamp);
            }
        }

        request.setAttribute("studentAcceptedUnpaidCamps", studentAcceptedUnpaidCamps);
        request.setAttribute("student", DatabaseQueries.getStudent(studentID, false));
        request.setAttribute("parent", DatabaseQueries.getParentByPID(parentID));

        request.getRequestDispatcher("/WEB-INF/camper/makePayment.jsp").forward(request, response);
    }
}
