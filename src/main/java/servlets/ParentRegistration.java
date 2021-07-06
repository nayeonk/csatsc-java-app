package servlets;

import data.*;
import database.DatabaseQueries;
import database.DatabaseUpdates;
import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParentRegistration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // used for accessing the manage campers page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecurityChecker.enforceParentLogin(request, response)) {
            response.sendRedirect(SecurityChecker.LoginPage);
            return;
        }

        int emailID = (int) request.getSession().getAttribute("emailID");
        SetPageAttributeUtil.setParentControlPanelAttributes(request, emailID);

        int parentID = Integer.parseInt((String) request.getSession().getAttribute("parentID"));
        request.getSession().setAttribute("parentID", "" + parentID);

        Parent parent = DatabaseQueries.getParentByPID(parentID);
        request.setAttribute("parent", parent);

        Boolean parentProfileComplete = true;

        if (parent.getFirstName().isEmpty() || parent.getLastName().isEmpty() || parent.getPhone().isEmpty() || parent.getAddress().equals("") || parent.getIncome().isEmpty()) {
            parentProfileComplete = false;
        }

        request.getSession().setAttribute("parentProfileComplete", parentProfileComplete);

        EmergencyContact contact = DatabaseQueries.getEmergencyContact(parentID);
        if (contact.getEmergencyContactID() != 0) {
            request.setAttribute("emergency_contact", contact);
        }

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