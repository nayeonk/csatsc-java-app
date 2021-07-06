package servlets;

import data.VerificationToken;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class ResetPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String tokenString = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/common/resetpassword.jsp").forward(request, response);
        tokenString = request.getQueryString();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        //String errorMessage = "";

        VerificationToken verToken = DatabaseQueries.findToken(tokenString);
        String token = tokenString;
        int emailID = -1;
        boolean expired = false;
        boolean verified = false;
        if (verToken != null) {
            emailID = verToken.getEmailID();
            expired = verToken.hasExpired();
            verified = verToken.isVerified();
        }

        String newPassword = request.getParameter("p1");
        //String newPassword2 = request.getParameter("password2");

        if (token == null || token.length() != 36 || verToken == null) {
            response.getWriter().write("Invalid URL. Please request a new password reset.");
        } else if (expired || verified) {
            response.getWriter().write("Sorry, this link is expired. Please request a new password reset.");
        }

//		else if (newPassword == null || newPassword2 == null || newPassword.length() == 0 || newPassword2.length() == 0) {
//			errorMessage = "Both password fields must be filled";
//		}
//		else if (!newPassword.equals(newPassword2)) {
//			errorMessage = "Passwords must match";
//		}

//		else if(newPassword.length()<6 || !(newPassword.matches(".*\\d+.*"))){
//			//if Password is less than 6 characters in length or doesn't contains a number
//			errorMessage = "Password must be at least 6 characters and have at least one number";
//		}
//			
        else {
            try {
                DatabaseUpdates.verifyToken(token);
                String salt = PasswordUtil.createSalt();
                String hashedPassword = PasswordUtil.hashPassword(newPassword, salt);
                boolean admin = DatabaseQueries.isAdmin(emailID);
                DatabaseUpdates.updateLogin(emailID, hashedPassword, salt, admin);
                response.getWriter().write("");
            }

            catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                response.getWriter().write(e.getMessage());
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
