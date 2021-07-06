package servlets;

import data.VerificationToken;
import database.DatabaseInserts;
import database.DatabaseQueries;
import util.EnvironmentConstants;
import util.MailServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForgotPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/common/forgotpassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //	String errorMessage = "";
        String userEmail = request.getParameter("e");

//		if (userEmail == null || userEmail.length() == 0) {
//			errorMessage = "No email address was entered";
//		}
//			
//		else {
        userEmail = userEmail.toLowerCase();

        int emailID = DatabaseQueries.doesEmailExist(userEmail);
        int loginID = DatabaseQueries.getLogin(emailID).getLoginID();
        if (loginID == -1) {
            response.getWriter().write("Login not found");
        } else {
            // JS: Generate new token and insert into table for password reset tokens
            VerificationToken verToken = new VerificationToken(emailID);
            String token = verToken.getToken();
            DatabaseInserts.insertToken(verToken.getToken(), emailID, verToken.getExpiryDate());

            // JS: Send email to user with a unique and secure link to reset password
            // resetpasswordemail.html
            //String emailTo = DatabaseQueries.getEmail(emailID);
            String subject = "CS@SC Summer Camps - Password Reset";
            String content = "";

            if (EnvironmentConstants.isProductionEnvironment()) {
                content = "<h1 align=\"center\" style=\"font-weight: 200; font-size: 32px;\">Password Reset</h1><p style=\"font-size: 20px;\">We received a request to reset the password for the CS@SC Summer Camps registration account associated with this email address. If you made this request, please follow the instructions below.<br><br><a href=\"http://summercamp.usc.edu/SummerCamp/resetpassword?"
                        + token
                        + "\">Click here</a> to reset your password or copy and paste this link into the address bar of your browser:<br>http://summercamp.usc.edu/SummerCamp/resetpassword?"
                        + token
                        + "</p><p style=\"font-size: 16px;\">Please note: For security purposes this link will expire within 30 minutes. Please update your password within 30 minutes of requesting the password update. If the link has already expired, please request an update to your password again here: <a href=\"http://localhost:8080/SummerCamp/forgotpassword\">Request a password update</a></p>";
            } else {
                content = "<h1 align=\"center\" style=\"font-weight: 200; font-size: 32px;\">Password Reset</h1><p style=\"font-size: 20px;\">We received a request to reset the password for the CS@SC Summer Camps registration account associated with this email address. If you made this request, please follow the instructions below.<br><br><a href=\"http://localhost:8080/SummerCamp/resetpassword?"
                        + token
                        + "\">Click here</a> to reset your password or copy and paste this link into the address bar of your browser:<br>http://localhost:8080/SummerCamp/resetpassword?"
                        + token
                        + "</p><p style=\"font-size: 16px;\">Please note: For security purposes this link will expire within 30 minutes. Please update your password within 30 minutes of requesting the password update. If the link has already expired, please request an update to your password again here: <a href=\"http://localhost:8080/SummerCamp/forgotpassword\">Request a password update</a></p>";
            }
            MailServer.sendEmail(userEmail, subject, content, getServletContext());
            response.getWriter().write("");
        }

    }
}
