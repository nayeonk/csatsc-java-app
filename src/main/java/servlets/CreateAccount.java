package servlets;

import api.Mailchimp;
import data.Address;
import data.Income;
import data.Parent;
import data.Parent.ParentBuilder;
import data.State;
import database.DatabaseInserts;
import database.DatabaseQueries;
import util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CreateAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("should not be here: create account");
        String email = request.getParameter("email");
        String email2 = request.getParameter("email2");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String uscemployee = request.getParameter("uscemployee");

        String errorMessage = "";

//        if (!Mailchimp.subscribe(email)) {
//            System.out.println("email not added to subscibers");
//        }

//		if (email == null || email2 == null || email.length() == 0 || email2.length() == 0) {
//			errorMessage = "Both email fields must be filled.";
//		}
//		else {
//			email = email.toLowerCase();
//			email2 = email2.toLowerCase();
//			
//			if (!email.equalsIgnoreCase(email2)) {
//				errorMessage = "Email addresses must match.";
//			}
//			
//			else if(!ValidateEmail.isValidEmailAddress(email)){
//				//If email address is invalid
//				errorMessage = "Email address is invalid.";
//			}
//
//	
//			else { // emails match and addressses are valid
        // NOW WHAT HAPPENS IF THE EMAIL ADDRESS EXISTS BUT THE PASSWORD HAS NOT BEEN ENTERED YET
        // I DON'T WANT IT TO SAY THAT THE EMAIL ALREADY EXISTS
        int emailID = DatabaseQueries.doesEmailExist(email);
        if (emailID == -1) {
//					// if the email exists, check to see if there is already a login for this email address
//					int loginID = DatabaseQueries.getLogin(emailID).getLoginID();
//					if (loginID != -1) {
//						// this means that there is already a login associated with this username
//						errorMessage = "Email address already exists.";
//					}
//					else {
//						// this means the email address exists but there is no password or login associated with it
//						request.getSession().setAttribute("emailID", "" + emailID);
//					}
//				}
//				else {
            // email wasn't in the table yet, so put it in
            emailID = DatabaseInserts.insertEmail(email);

            request.getSession().setAttribute("emailID", emailID);
        }

//				// only check the password if there is no error with other email addresses
//				if (errorMessage.length() == 0) {
//					if (password == null || password2 == null || password.length() == 0 || password2.length() == 0) {
//						errorMessage = "Both password fields must be filled.";
//					}
//					else {
//						if (!password.equals(password2)) {
//							errorMessage = "Passwords must match.";
//						}
//						
//						else if(password.length()<6 || !(password.matches(".*\\d+.*"))){
//							//if Password is less than 6 characters in length or doesn't contains a number
//							errorMessage= "Password must be at least 6 characters and have at least one number";
//						}
//						
//						else { // passwords match and no error
        // insert password in Login table
        try {
            String salt = PasswordUtil.createSalt();
            String hashedPassword = PasswordUtil.hashPassword(password, salt);

            int loginID = DatabaseInserts.insertLogin(emailID, hashedPassword, salt, false);
            request.getSession().setAttribute("loginID", loginID);
            request.getSession().setAttribute("login", email);
            request.getSession().setAttribute("email", email);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //}
//					}
//				}
//			}
//		}

//		if (errorMessage.length() > 0) {
//			//Invalid login, track their login to the LoginAudit table with authentication=0
//			int loginAuditID= DatabaseInserts.insertLoginAudit(email, 0);
//			
//			request.setAttribute("errorMessage", errorMessage);
//			request.setAttribute("email", email);
//			request.setAttribute("email2", email2);
//			request.setAttribute("password", password);
//			request.setAttribute("password2", password2);			
//			request.getRequestDispatcher("/register.jsp").forward(request, response);
//		}
//		
//		else {
        //Valid login, track their login to the LoginAudit table with authentication=1
        int loginAuditID = DatabaseInserts.insertLoginAudit(email, 1);

        // CC: We need to create an empty parent now, just in case something happens during initial registration.
        ParentBuilder builder = new ParentBuilder();

        //int emailID = DatabaseQueries.doesEmailExist(email);
        builder.emailID(emailID);
        builder.name("", "");
        builder.phone("");
        builder.address(new Address("", "", "", 0, ""));

        //TODO: Update database to include a uscemployee field, need to update all insert statements
        if(uscemployee == null)
            builder.uscEmployee(false);
        else
            builder.uscEmployee(true);

        Parent parent = builder.build();
        int parentID = DatabaseInserts.insertParent(parent);
        request.getSession().setAttribute("parentID", "" + parentID);

        request.getSession().setAttribute("parentProfileComplete", false);

        List<State> stateArray = DatabaseQueries.getStates();
        request.setAttribute("stateArray", stateArray);
        List<Income> incomeArray = DatabaseQueries.getIncomes();
        request.setAttribute("incomeArray", incomeArray);

        ArrayList<String> countryArray = new ArrayList<String>();
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            countryArray.add(obj.getDisplayCountry());
        }
        request.setAttribute("countryArray", countryArray);

        if (request.getParameter("state") == null || request.getParameter("state").length() == 0) {
            request.setAttribute("state", "5"); // Set CA as the default
        }

        if (request.getParameter("e_state") == null || request.getParameter("e_state").length() == 0) {
            request.setAttribute("e_state", "5");
        }

        request.getRequestDispatcher("/WEB-INF/parent/parentRegistration.jsp").forward(request, response);
    }
}

