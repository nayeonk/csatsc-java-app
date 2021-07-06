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

public class ViewApplications extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        List<Student> students = DatabaseQueries.getStudents(parentID, false);

        // List of List of StudentCamps
        // students -> plural
        Map<Integer, List<StudentCamp>> studentsAppliedCamps = new HashMap<>();

        for (Student student : students) {
            int studentID = student.getStudentID();
            List<StudentCamp> studentAppliedCampArray = DatabaseQueries.getAllCurrentCampsAppliedByStudentID(studentID);
            studentsAppliedCamps.put(studentID, studentAppliedCampArray);
        }
        request.setAttribute("studentsAppliedCamps", studentsAppliedCamps);
        request.setAttribute("students", students);
        request.setAttribute("parent", DatabaseQueries.getParentByPID(parentID));

        request.getRequestDispatcher("/WEB-INF/parent/viewApplications.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
