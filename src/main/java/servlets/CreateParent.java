package servlets;

import data.EmergencyContact;
import data.Parent;
import database.DatabaseUpdates;
import util.ModifyDataUtil;
import util.PasswordUtil;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

// CC: Updates a parent and their address after initial registration.
public class CreateParent extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Session variable "successMessage" is legacy code, not sure if it's used later so just left it here for now - Fall 2020
        request.getSession().setAttribute("successMessage", "");


        String errorMessage = "";
        String email = (String) request.getSession().getAttribute("email");

        // Validate Email.
        if (email == null) {
            errorMessage = "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));

        EmergencyContact emergencyContact = ModifyDataUtil.buildEmergencyContact(request, parentID);//ecBuilder.build();
        Parent parent = ModifyDataUtil.buildParent(request, parentID);
        parent.setEmail(email);

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage = "Internal failure detected.";
        } else {
            // Tries to update parent in database, or returns an error message if form validation fails
            errorMessage = ModifyDataUtil.modifyParent(parent, true);

            // Tries to update emergency contact information, or returns an error message if form validation fails
            if (errorMessage.length() == 0) {
                errorMessage = ModifyDataUtil.createOrUpdateEC(emergencyContact, true);
            }

            String newPassword = request.getParameter("password");
            String newPassword2 = request.getParameter("password2");

            if ((newPassword != null && newPassword2 != null) && (newPassword.length() > 0 || newPassword2.length() > 0)) {
                Integer pemailID = Integer.parseInt(request.getParameter("pemailID"));

                if (newPassword.length() == 0 || newPassword2.length() == 0) {
                    errorMessage = "Both password fields must be filled.";
                } else {
                    if (!newPassword.equals(newPassword2)) {
                        errorMessage = "Passwords must match.";
                    } else if (newPassword.length() < 6 || newPassword.chars().allMatch(Character::isDigit) || newPassword.chars().allMatch(Character::isLetter)) {
                        errorMessage += "Password must be at least 6 characters and have at least one number and at least one letter <br>";
                    } else { // passwords match and no error
                        // insert password in Login table
                        try {
                            String salt = PasswordUtil.createSalt();
                            String hashedPassword = PasswordUtil.hashPassword(newPassword, salt);

                            DatabaseUpdates.updateLogin(pemailID, hashedPassword, salt, false);
                        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                            // TODO Auto-generated catch block
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // Handle Errors.
        SetPageAttributeUtil.setParentControlPanelAttributes(request, parent.getEmailID());
        if (errorMessage.length() > 0) {
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("parent", parent);
            request.getRequestDispatcher("/WEB-INF/parent/parentRegistration.jsp").forward(request, response);
        } else {
            // CC: Set up our success message -- account created successfully!
            String successMessage = "Account created successfully!";
            request.getSession().setAttribute("successMessage", successMessage);
            response.sendRedirect("/SummerCamp/login");
        }
    }
}
