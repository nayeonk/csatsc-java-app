package servlets;

import com.google.gson.Gson;
import model.AttendanceStatisticsDto;
import repositories.AttendanceStatisticsRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AttendanceReport extends HttpServlet {

    private static boolean hasPermissions(HttpServletRequest request) {
        return SecurityChecker.isAdmin(request);
    }

    /**
     * Get all attendance statistics within date range
     * <p>
     * QUERY PARAMS:
     * startDate: [required] format YYYY-MM-DD
     * endDate: [required] format YYYY-MM-DD
     * <p>
     * RESPONSE:
     * List of attendance statistics
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AttendanceReport.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String startDateParam = request.getParameter("startDate");
        String endDateParam = request.getParameter("endDate");

        if (startDateParam == null || startDateParam.isEmpty() ||
                endDateParam == null || endDateParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        LocalDate startDate = LocalDate.parse(startDateParam);
        LocalDate endDate = LocalDate.parse(endDateParam);
        List<AttendanceStatisticsDto> attendanceStatistics =
                AttendanceStatisticsRepo.getStudentAttbetweenDateRange(startDate, endDate);
        response.getWriter().print(new Gson().toJson(attendanceStatistics));
    }
}
