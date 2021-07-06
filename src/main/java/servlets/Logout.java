package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class Logout extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        //String email = (String)request.getSession().getAttribute("email");
        //int emailID = DatabaseQueries.doesEmailExist(email);
        //Parent parent = DatabaseQueries.getParentByEID (emailID, email);

        //CC: Build the parent name.
        //String parentName = parent.getName();

//		if(parent.getName() == null || parent.getName().length() == 0) {
//			parentName = email;
//		}

        //String successMessage =  "You were logged out successfully!";
        //Enumeration<String> v = ;
        Enumeration<String> e = request.getSession().getAttributeNames();
        for (Enumeration<String> v = e; v.hasMoreElements(); ) {
            request.getSession().removeAttribute(v.nextElement());
        }

        request.getSession().invalidate();

        // CC: Set up our success message -- parent logged out successfully!
        //request.setAttribute ("successMess", successMessage);
        //request.getSession().setAttribute(StringConstants.ROLE, null);

        //change request to response so that the url updates for better UX
       // request.getRequestDispatcher("/register.jsp").forward(request, response);
        response.sendRedirect("/SummerCamp/register.jsp");
    }
}
