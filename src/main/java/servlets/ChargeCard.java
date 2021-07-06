package servlets;

/*



LEGACY CODE

DO NOT USE - Stripe API not allowed as per USC Policy







 */


import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import database.DatabaseUpdates;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ChargeCard extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO update security
        String email = (String) request.getSession().getAttribute("email");
        String errorMessage = "";

        if (email == null) {
            errorMessage += "Please login first.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            String sID = request.getParameter("studentID");
            String cOfferedID = request.getParameter("campOfferedID");
            String campCost = request.getParameter("campCost");
            String successMessage = "";

            if (sID == null || cOfferedID == null) {
                errorMessage = "All fields must be filled.";
            } else {

                response.setContentType("text/plain");
                Enumeration<String> parameterNames = request.getParameterNames();
                String token = null;
                while (parameterNames.hasMoreElements()) {

                    String paramName = parameterNames.nextElement();
                    if (paramName.equals("stripeToken")) {
                        token = request.getParameter(paramName);
                    }

                    String[] paramValues = request.getParameterValues(paramName);
                    for (int i = 0; i < paramValues.length; i++) {
                        String paramValue = paramValues[i];
                    }
                }

                int cost = Integer.parseInt(campCost);
                Stripe.apiKey = "sk_test_6Sih793ToUekQ9JBoJNWbhDB";
                Map<String, Object> chargeParams = new HashMap<String, Object>();
                chargeParams.put("amount", cost);
                chargeParams.put("currency", "usd");
                chargeParams.put("source", token); // obtained with Stripe.js
                chargeParams.put("description", "Charge for test@example.com");

                try {
                    Charge.create(chargeParams);
                } catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
                        | APIException e) {

                    e.printStackTrace();
                    //errorMessage += "Transaction failed, please check credit card information.";
                }

                // TODO: Write to the database in the try-catch statement
                int studentID = Integer.parseInt(sID);
                int campOfferedID = Integer.parseInt(cOfferedID);

                DatabaseUpdates.payForCamp(studentID, campOfferedID);
            }

            if (errorMessage.length() > 0) {
                SetPageAttributeUtil.setParentControlPanelAttributes(request, email);

                // CC: Set up our success message -- student or camp application completed successfully!
                successMessage += "Camp payment completed succesfully!";
                request.setAttribute("successMessage", successMessage);
                request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
            } else {
                SetPageAttributeUtil.setParentControlPanelAttributes(request, email);

                // CC: Set up our success message -- student or camp application completed successfully!
                successMessage += "Camp payment completed succesfully!";
                request.setAttribute("successMessage", successMessage);
                request.getRequestDispatcher("/WEB-INF/parent/parentcontrolpanel.jsp").forward(request, response);
            }
        }
    }
}
