package servlets;

import data.StringConstants;
import database.DatabaseUpdates;
import util.PasswordUtil;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class UpdateLoginParent extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String errorMessage = "";
        String successMessage = "";
        String role = (String) request.getSession().getAttribute(StringConstants.ROLE);
        //Integer parentID = Integer.parseInt((String) request.getParameter("parentID"));
        Integer pemailID = Integer.parseInt(request.getParameter("pemailID"));
        String email = (String) request.getSession().getAttribute("email");

        if (!SecurityChecker.isValidEmail(email)) {
            errorMessage += "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String newPassword = request.getParameter("password");
            String newPassword2 = request.getParameter("password2");
            //int emailID = Integer.parseInt(request.getSession().getAttribute("emailID").toString());

            if (newPassword == null || newPassword2 == null || newPassword.length() == 0 || newPassword2.length() == 0) {
                errorMessage = "Both password fields must be filled.";
            } else {

                if (!newPassword.equals(newPassword2)) {
                    errorMessage = "Passwords must match.";
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

            if (errorMessage.length() > 0) {
                //if (role.equals(StringConstants.PARENT)){
                SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);
                //}
                request.getSession().setAttribute("errorMessage", errorMessage);

                response.sendRedirect("/SummerCamp/login");
            } else {

                //if (role.equals(StringConstants.PARENT)){
                SetPageAttributeUtil.setParentControlPanelAttributes(request, pemailID);
                //}

                // CC: Set up our success message -- account updated successfully!
                successMessage += "Account information updated successfully!";
                request.getSession().setAttribute("successMessage", successMessage);

                response.sendRedirect("/SummerCamp/login");
            }
        }
    }
}
