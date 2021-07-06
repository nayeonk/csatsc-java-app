package servlets;

import com.google.gson.Gson;
import repositories.UniversalPickupCodeRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UniversalPickupCode extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static boolean hasPermissions(HttpServletRequest request) {
        return SecurityChecker.isAdmin(request) || SecurityChecker.isInstructor(request);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!UniversalPickupCode.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.getWriter().print(new Gson().toJson(UniversalPickupCodeRepo.getAllUniversalPickupCodes()));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!UniversalPickupCode.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String universalPickupCode = request.getParameter("universalPickupCode");
        UniversalPickupCodeRepo.createUniversalPickupCode(universalPickupCode);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!UniversalPickupCode.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String universalPickupCode = request.getParameter("universalPickupCode");
        UniversalPickupCodeRepo.deleteUniversalPickupCode(universalPickupCode);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
