package servlets;

import data.StringConstants;
import database.DatabaseUpdates;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class UpdateLoginAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {
            String errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/admin/updateloginadmin.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = "";
        String successMessage = "";
        if (!request.getSession().getAttribute(StringConstants.ROLE).equals(StringConstants.ADMIN)) {

            errorMessage = "Please login first. ";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String newPassword = request.getParameter("password");
            String newPassword2 = request.getParameter("password2");

            if (newPassword == null || newPassword2 == null || newPassword.length() == 0 || newPassword2.length() == 0) {
                errorMessage = "Both password fields must be filled.";
            } else {
                if (!newPassword.equals(newPassword2)) {
                    errorMessage = "Passwords must match.";
                } else {
                    try {
                        String salt = PasswordUtil.createSalt();
                        String hashedPassword = PasswordUtil.hashPassword(newPassword, salt);

                        int emailID = Integer.parseInt(request.getSession().getAttribute("emailID").toString());
                        DatabaseUpdates.updateLogin(emailID, hashedPassword, salt, true);

                        successMessage = "Password updated successfully!";
                    }

                    catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                        // TODO Auto-generated catch block
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (errorMessage.length() > 0) {
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("/WEB-INF/admin/updateloginadmin.jsp").forward(request, response);
            } else {
                request.setAttribute("successMessage", successMessage);
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }
    }
}
