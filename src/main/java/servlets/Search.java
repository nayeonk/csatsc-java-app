package servlets;

import data.StringConstants;
import data.StudentSearchResult;
import database.DatabaseQueries;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/search")
public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            // these hold the input values from the form
            String fname = request.getParameter(StringConstants.searchCampersFname);
            String lname = request.getParameter(StringConstants.searchCampersLname);
            String pfname = request.getParameter(StringConstants.searchCampersPFname);
            String plname = request.getParameter(StringConstants.searchCampersPLname);
            String email = request.getParameter(StringConstants.searchCampersEmail);

            // map that holds name of the value and the input value from the form
            Map<String, String> params = new HashMap<>();
            if ((fname != null) && (!fname.isEmpty())) {
                // studentFirstName, input
                params.put(StringConstants.searchCampersFname, fname);
            }
            if ((lname != null) && (!lname.isEmpty())) {
                params.put(StringConstants.searchCampersLname, lname);
            }
            if ((pfname != null) && (!pfname.isEmpty())) {
                params.put(StringConstants.searchCampersPFname, pfname);
            }
            if ((plname != null) && (!plname.isEmpty())) {
                params.put(StringConstants.searchCampersPLname, plname);
            }
            if ((email != null) && (!email.isEmpty())) {
                params.put(StringConstants.searchCampersEmail, email);
            }
            if (!params.isEmpty()) {
                List<StudentSearchResult> results = DatabaseQueries.searchCampers(params);
                request.setAttribute("results", results);
            }
            request.getRequestDispatcher("/WEB-INF/admin/search.jsp").forward(request, response);

        }
    }
}
