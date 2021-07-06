package repositories;

import database.DatabaseConnection;
import model.AttendanceDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class AttendanceRepo {
    private final static String checkin = "INSERT into studentattendance(campOfferedId,studentId, attendancedate, checkedInBy, checkInTime, checkedOutBy, checkOutTime, UCodeUsed) VALUES(?,?,?,?,CURTIME(),NULL,NULL,0)";
    private final static String updateCheckin = "UPDATE studentattendance SET checkInTime=CURTIME(), checkedInBy=? WHERE id=?";

    private final static String initialcheck = "SELECT * FROM studentattendance WHERE studentId=? AND attendancedate=?";
    private final static String checkout = "UPDATE studentattendance SET checkOutTime=CURTIME(),checkedOutBy=?, UCodeUsed=? WHERE id = ?";
    private final static String undoCheckIn = "UPDATE studentattendance SET checkInTime=NULL, checkedInBy=NULL WHERE studentId=? AND attendancedate=? AND campOfferedId=?";
    private final static String undoCheckOut = "UPDATE studentattendance SET checkOutTime=NULL, checkedOutBy=NULL WHERE studentId=? AND attendancedate=? AND campOfferedId=?";
    private final static String getbyDate = "SELECT * FROM studentattendance WHERE attendancedate=?";

    private final static String getAttendanceById = "SELECT * FROM studentattendance WHERE id=?";

    //Depreciated
    private final static String getbyDayandCampId = "SELECT * FROM studentattendance WHERE date=? AND campId=? AND isDeleted=0";


    /**
     * Used to insert a student into the Attendance database upon checkin
     *
     * @param campOfferedId The class the student is in
     * @param studentId     The id of the student
     * @param date          The day of checkin
     * @param staffIn       The Id of the staff that checked the student in
     * @return boolean
     */
    public static int checkStudentIn(int campOfferedId, int studentId, LocalDate date, int staffIn) {
        // Check if existing entry

        int existingEntryId = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(initialcheck);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existingEntryId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        // Now update
        try {
            if (existingEntryId > -1) {
                // If existing, update
                PreparedStatement ps = DatabaseConnection.getPreparedStatement(updateCheckin);
                ps.setInt(1, staffIn);
                ps.setInt(2, existingEntryId);
                ps.executeUpdate();

                return existingEntryId;

            } else {
                // If new, add
                PreparedStatement ps = DatabaseConnection.getPreparedStatement(checkin);
                ps.setInt(1, campOfferedId);
                ps.setInt(2, studentId);
                ps.setDate(3, Date.valueOf(date));
                ps.setInt(4, staffIn);
                ps.executeUpdate();

                // Code below gets the inserted AttendanceDto id
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return -1;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
    }

    /**
     * Used to get a student's AttendanceDto by an studentattendance id
     *
     * @param studentAttendanceId The class the student is in
     * @return AttendanceDto
     */
    public static AttendanceDto getAttendanceDtoByStudentAttendanceId(int studentAttendanceId) {

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getAttendanceById);
            ps.setInt(1, studentAttendanceId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AttendanceDto(
                        rs.getInt("id"),
                        rs.getInt("campOfferedId"),
                        rs.getInt("studentId"),
                        rs.getDate("attendancedate").toLocalDate(),
                        rs.getInt("checkedInBy"),
                        rs.getTimestamp("checkInTime") == null ? null : rs.getTimestamp("checkInTime").toLocalDateTime(),
                        rs.getInt("checkedOutBy"),
                        rs.getTimestamp("checkOutTime") == null ? null : rs.getTimestamp("checkOutTime").toLocalDateTime(),
                        rs.getBoolean("UCodeUsed"));
            }

            return null;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return null;
        }
    }

    /**
     * Used to update the studentattendance database to check a student out
     *
     * @param staffOut  The Id of the staff that checked the student out
     * @param studentId The id of the student
     * @param date      The day of checkout
     * @param UCodeUsed boolean if UCode was used
     * @return int
     */
    public static int checkStudentOut(int staffOut, int studentId, LocalDate date, boolean UCodeUsed) {
        //check if the student was checkedin before checking them out
        int studentAttendanceId = -1;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(initialcheck);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studentAttendanceId = rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(checkout);
            ps.setInt(1, staffOut);
            ps.setBoolean(2, UCodeUsed);
            ps.setInt(3, studentAttendanceId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not check student out");
            e.printStackTrace();
            return -1;
        }

        return studentAttendanceId;
    }

    /**
     * Uncheck a student via changing their Delete boolean their entry in the table
     *
     * @param studentId     The id of the student
     * @param date          The day of use
     * @param campOfferedId The camp
     * @return boolean If SQL query is successful
     */
    public static int undoCheckIn(int studentId, LocalDate date, int campOfferedId) {
        int studentAttendanceId = -1;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(initialcheck);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studentAttendanceId = rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(undoCheckIn);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ps.setInt(3, campOfferedId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete student attendance entry");
            e.printStackTrace();
            return -1;
        }

        return studentAttendanceId;
    }

    /**
     * Uncheck a student via changing their Delete boolean their entry in the table
     *
     * @param studentId     The id of the student
     * @param date          The day of use
     * @param campOfferedId The camp
     * @return boolean If SQL query is successful
     */
    public static int undoCheckOut(int studentId, LocalDate date, int campOfferedId) {
        int studentAttendanceId = -1;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(initialcheck);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                studentAttendanceId = rs.getInt("id");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(undoCheckOut);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ps.setInt(3, campOfferedId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not delete student attendance entry");
            e.printStackTrace();
            return -1;
        }

        return studentAttendanceId;
    }

    /**
     * Get AttendanceDto checked in by date
     *
     * @param date The day being requested
     * @return Map of Integer student id to AttendanceDto
     */
    public static Map<Integer, AttendanceDto> getByDate(LocalDate date) {
        HashMap<Integer, AttendanceDto> ret = new HashMap<>();

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getbyDate);
            ps.setDate(1, Date.valueOf(date));


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

//                System.out.println("CHECK OUT TIME");
//                System.out.println(rs.getTimestamp("checkOutTime"));
//                System.out.println(rs.getTimestamp("checkOutTime") == null);

                ret.put(rs.getInt("studentId"),
                        new AttendanceDto(
                                rs.getInt("id"),
                                rs.getInt("campOfferedId"),
                                rs.getInt("studentId"),
                                rs.getDate("attendancedate").toLocalDate(),
                                rs.getInt("checkedInBy"),
                                rs.getTimestamp("checkInTime") == null ? null : rs.getTimestamp("checkInTime").toLocalDateTime(),
                                rs.getInt("checkedOutBy"),
                                rs.getTimestamp("checkOutTime") == null ? null : rs.getTimestamp("checkOutTime").toLocalDateTime(),
                                rs.getBoolean("UCodeUsed")));
            }
        } catch (Exception e) {
            System.out.println("Could not get by date");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Get AttendanceDto checked in by date and campId
     *
     * @param date   The day being requested
     * @param campId the class being requested
     * @return Map of Integer student id to AttendanceDto
     */
    @Deprecated
    public static Map<Integer, AttendanceDto> getByDateAndCampId(LocalDate date, int campId) {
        HashMap<Integer, AttendanceDto> ret = new HashMap<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getbyDayandCampId);
            ps.setDate(1, Date.valueOf(date));
            ps.setInt(2, campId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("studentId"),
                        new AttendanceDto(
                                rs.getInt("id"),
                                rs.getInt("campId"),
                                rs.getInt("studentId"),
                                rs.getDate("attendancedate").toLocalDate(),
                                rs.getInt("checkedInBy"),
                                rs.getTimestamp("checkInTime").toLocalDateTime(),
                                rs.getInt("checkedOutBy"),
                                rs.getTimestamp("checkOutTime").toLocalDateTime(),
                                rs.getBoolean("UCodeUsed"))
                );
            }
        } catch (Exception e) {
            System.out.println("Could not get by date");
            e.printStackTrace();
        }
        return ret;
    }
}