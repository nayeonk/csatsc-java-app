
package servlets;

import database.DatabaseQueries;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class UniqueEmail
 */

public class UniqueEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String email = request.getParameter("e");
        email = email.toLowerCase();
        String purpose = request.getParameter("purpose");
        int ID = DatabaseQueries.doesEmailExist(email);
        int loginID = DatabaseQueries.getLogin(ID).getLoginID();
        String message = "";
        if (purpose.equals("create")) {
            message = Integer.toString((ID != -1) ? loginID : ID);

        } else if (purpose.equals("login")) {
            String password = request.getParameter("password");

            if (loginID == -1) {
                message = "Login does not exist";
            } else if (!PasswordUtil.checkPassword(email, password)) {
                message = "Password is incorrect";
            }
        }
        response.getWriter().write(message);
    }
}
