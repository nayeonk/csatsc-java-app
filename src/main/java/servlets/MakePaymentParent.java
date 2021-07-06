package servlets;

import data.StringConstants;
import data.Student;
import data.StudentCamp;
import database.DatabaseQueries;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakePaymentParent extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        // Generate payment token
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        request.getSession().setAttribute("tokenStr", token);

        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        List<Student> students = DatabaseQueries.getStudents(parentID, false);

        // List of List of StudentCamps
        // students -> plural
        Map<Integer, List<StudentCamp>> studentsAcceptedUnpaidCamps = new HashMap<>();

        for (Student student : students) {
            int studentID = student.getStudentID();
            List<StudentCamp> studentAcceptedCampArray = DatabaseQueries.getStudentAcceptedCampsSQL(studentID);
            List<StudentCamp> studentAcceptedUnpaidCamps = new ArrayList<>();
            for (StudentCamp studentCamp : studentAcceptedCampArray) {
                if (studentCamp.getCost() > 0 && !studentCamp.isPaid()) {
                    studentAcceptedUnpaidCamps.add(studentCamp);
                }
            }
            studentsAcceptedUnpaidCamps.put(studentID, studentAcceptedUnpaidCamps);
        }
        request.setAttribute("studentsAcceptedUnpaidCamps", studentsAcceptedUnpaidCamps);
        request.setAttribute("students", students);
        request.setAttribute("parent", DatabaseQueries.getParentByPID(parentID));

        request.getRequestDispatcher("/WEB-INF/parent/makePaymentParent.jsp").forward(request, response);

    }
}
