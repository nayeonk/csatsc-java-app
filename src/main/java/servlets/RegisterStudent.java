package servlets;

import data.Parent;
import database.DatabaseQueries;
import util.ModifyDataUtil;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterStudent extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getSession().setAttribute("errorMessage", "");
        request.getSession().setAttribute("successMessage", "");
        String errorMessage = "";
        String email = (String) request.getSession().getAttribute("email");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage += "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

        processParent(request);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/WEB-INF/parent/registerstudent.jsp").forward(request, response);
    }

    private void processParent(HttpServletRequest request) throws IOException, ServletException {
        Integer parentID = Integer.parseInt(request.getParameter("parentID"));
        request.setAttribute("student", ModifyDataUtil.buildStudent(request));
        Parent parent = DatabaseQueries.getParentByPID(parentID);
        request.setAttribute("parent", parent);
        SetPageAttributeUtil.setMultiChoiceFields(request);
    }

}
