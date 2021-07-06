package servlets;

import util.SetPageAttributeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerCheck extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getSession().getAttribute("temp") != null) {
            if (request.getSession().getAttribute("temp").equals("a")) {
                request.setAttribute("returnText", "b");

                request.getRequestDispatcher("/WEB-INF/common/servercheck.jsp").forward(request, response);
            }
        } else {

            request.setAttribute("returnText", "test");
            request.getRequestDispatcher("/WEB-INF/common/servercheck.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {


        SetPageAttributeUtil.setParentControlPanelAttributes(request, (String) request.getSession().getAttribute("email"));

        response.sendRedirect("/SummerCamp/login");

    }
}

