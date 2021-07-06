package servlets;

import com.google.gson.Gson;
import contracts.AttendanceCreateOrDelete;
import contracts.AttendancePickup;
import contracts.AttendanceRetrieve;
import data.Staff;
import data.StringConstants;
import data.Student;
import database.DatabaseQueries;
import model.AttendanceDto;
import model.CampDto;
import repositories.AttendanceRepo;
import repositories.CampRepo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends HttpServlet {

    private static boolean hasPermissions(HttpServletRequest request) {
        return SecurityChecker.isAdmin(request) || SecurityChecker.isInstructor(request);
    }

    /**
     * Gets all camps for the specified date and a list of students with their
     * attendance record for that day.
     * <p>
     * QUERY PARAMS:
     * date: [required] format YYYY-MM-DD
     * <p>
     * RESPONSE:
     * AttendanceRetrieve
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Attendance.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String dateParam = request.getParameter("date");
        Map<Integer, AttendanceDto> attendance;
        if (dateParam == null || dateParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            LocalDate date = LocalDate.parse(dateParam);
            Map<Integer, List<Student>> campStudentMap = new HashMap<>();
            List<CampDto> camps = CampRepo.getCampByDate(date);

            // All camps
            attendance = AttendanceRepo.getByDate(date);

            // Get all students by iteratively getting all students per camp
            camps
                    .forEach(
                            c -> campStudentMap.put(
                                    c.getCampOfferedId(),
                                    DatabaseQueries.getNamesFromClassID(c.getCampOfferedId())
                            )
                    );

            AttendanceRetrieve attendanceRetrieve = new AttendanceRetrieve(
                    campStudentMap, attendance, camps
            );
            response.getWriter().print(new Gson().toJson(attendanceRetrieve));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Check in student
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Attendance.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        AttendanceCreateOrDelete attendanceCreate = new Gson().fromJson(
                request.getReader(), AttendanceCreateOrDelete.class
        );

        // Only admin is allowed to modify other days
        // Teachers may only edit "today"
        LocalDate today = LocalDate.now();
        LocalDate userSpecifiedDate = LocalDate.parse(attendanceCreate.date);
        if (!today.equals(userSpecifiedDate) && !SecurityChecker.isAdmin(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        Staff staff = DatabaseQueries.getStaffByEmailID(
                (Integer) request.getSession().getAttribute(StringConstants.EMAIL_ID)
        );

        // Checks if already exists
        int studentAttendanceId = AttendanceRepo.checkStudentIn(
                attendanceCreate.campId,
                attendanceCreate.studentId,
                LocalDate.parse(attendanceCreate.date),
                staff.getStaffID()
        );

        if (studentAttendanceId > -1) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            AttendanceDto updatedAttendanceDto = AttendanceRepo.getAttendanceDtoByStudentAttendanceId(studentAttendanceId);

            response.getWriter().print(new Gson().toJson(updatedAttendanceDto));
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Check out student
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!Attendance.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        AttendancePickup attendancePickup = new Gson().fromJson(
                request.getReader(), AttendancePickup.class
        );

        if (attendancePickup.useParentNameAsCode) {
            // Use Parent's Name as Code
            boolean matchesStudentParentsName = DatabaseQueries.verifyStudentParentName(attendancePickup.pickupCode, attendancePickup.studentId);

            if (!matchesStudentParentsName) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                Staff staff = DatabaseQueries.getStaffByEmailID(
                        (Integer) request.getSession().getAttribute(StringConstants.EMAIL_ID)
                );

                int studentAttendanceId = AttendanceRepo.checkStudentOut(
                        staff.getStaffID(),
                        attendancePickup.studentId,
                        LocalDate.parse(attendancePickup.date),
                        false
                );

                if (studentAttendanceId > -1) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    AttendanceDto updatedAttendanceDto = AttendanceRepo.getAttendanceDtoByStudentAttendanceId(studentAttendanceId);
                    response.getWriter().print(new Gson().toJson(updatedAttendanceDto));
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }

        } else {
            // Use pick up code

            boolean matchesStudentPickupCode = DatabaseQueries.verifyPickupCode(attendancePickup.pickupCode, attendancePickup.studentId);
            boolean matchesUniversalPickupCode = DatabaseQueries.verifyUniversalCode(attendancePickup.pickupCode);
            if (!matchesStudentPickupCode && !matchesUniversalPickupCode) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            } else {
                Staff staff = DatabaseQueries.getStaffByEmailID(
                        (Integer) request.getSession().getAttribute(StringConstants.EMAIL_ID)
                );

                int studentAttendanceId = AttendanceRepo.checkStudentOut(
                        staff.getStaffID(),
                        attendancePickup.studentId,
                        LocalDate.parse(attendancePickup.date),
                        matchesUniversalPickupCode
                );

                if (studentAttendanceId > -1) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    AttendanceDto updatedAttendanceDto = AttendanceRepo.getAttendanceDtoByStudentAttendanceId(studentAttendanceId);

                    response.getWriter().print(new Gson().toJson(updatedAttendanceDto));
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    /**
     * Deletes the attendance entry in the case a staff accidentally marks the wrong
     * student as present. (uncheck the check in box)
     * <p>
     * Not to be confused with check out student
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!Attendance.hasPermissions(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        AttendanceCreateOrDelete attendanceDelete = new Gson().fromJson(
                request.getReader(), AttendanceCreateOrDelete.class
        );

        if (attendanceDelete.isCheckIn) {
            // Undo Check In

            int studentAttendanceId = AttendanceRepo.undoCheckIn(
                    attendanceDelete.studentId,
                    LocalDate.parse(attendanceDelete.date),
                    attendanceDelete.campId
            );

            if (studentAttendanceId > -1) {
                response.setStatus(HttpServletResponse.SC_OK);
                AttendanceDto updatedAttendanceDto = AttendanceRepo.getAttendanceDtoByStudentAttendanceId(studentAttendanceId);
                response.getWriter().print(new Gson().toJson(updatedAttendanceDto));

            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            // Undo Check Out

            int studentAttendanceId = AttendanceRepo.undoCheckOut(
                    attendanceDelete.studentId,
                    LocalDate.parse(attendanceDelete.date),
                    attendanceDelete.campId
            );
            
            if (studentAttendanceId > -1) {
                response.setStatus(HttpServletResponse.SC_OK);
                AttendanceDto updatedAttendanceDto = AttendanceRepo.getAttendanceDtoByStudentAttendanceId(studentAttendanceId);
                response.getWriter().print(new Gson().toJson(updatedAttendanceDto));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
