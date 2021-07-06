package repositories;

import database.DatabaseConnection;
import model.AttendanceStatisticsDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceStatisticsRepo {

    private final static String getAllStudents =
            "SELECT DISTINCT attendancedate, topic, campLevelDescription, " +
                    "fname, lname, " +
                    "grade, checkInTime, checkin.firstName AS checkInByFN, " +
                    "checkin.lastName AS checkInByLN, checkOutTime, checkout.firstName AS checkOutByFN, " +
                    "checkout.lastName AS checkOutByLN, UCodeUsed " +
                    "FROM summercamps.studentattendance " +
                    "INNER JOIN student ON student.studentID = studentattendance.studentId " +
                    "INNER JOIN staff checkin ON checkin.staffID = checkedInBy " +
                    "INNER JOIN staff checkout ON checkout.staffID = checkedOutBy " +
                    "INNER JOIN campoffered ON campoffered.campOfferedID = studentattendance.campOfferedId " +
                    "INNER JOIN grade ON grade.gradeID = student.gradeID " +
                    "INNER JOIN camptopic ON camptopic.campTopicID=campoffered.campTopicID " +
                    "INNER JOIN camplevel ON camplevel.campLevelID = campoffered.campLevelID " +
                    "WHERE attendancedate BETWEEN ? AND ? " +
                    "ORDER BY attendancedate;";
//
//    private final static String getAllStudents =
//            "SELECT DISTINCT attendancedate, topic, campLevelDescription, " +
//                    "fname, lname, gender, ethnicity, income, " +
//                    "grade, checkInTime, checkin.firstName AS checkInByFN, " +
//                    "checkin.lastName AS checkInByLN, checkOutTime, checkout.firstName AS checkOutByFN, " +
//                    "checkout.lastName AS checkOutByLN, UCodeUsed, isDeleted " +
//                    "FROM summercamps.studentattendance " +
//                    "INNER JOIN student ON student.studentID = studentattendance.studentId " +
//                    "INNER JOIN staff checkin ON checkin.staffID = checkedInBy " +
//                    "INNER JOIN staff checkout ON checkout.staffID = checkedOutBy " +
//                    "INNER JOIN campoffered ON campoffered.campOfferedID = studentattendance.campOfferedId " +
//                    "INNER JOIN grade ON grade.gradeID = student.gradeID " +
//                    "INNER JOIN camptopic ON camptopic.campTopicID=campoffered.campTopicID " +
//                    "INNER JOIN camplevel ON camplevel.campLevelID = campoffered.campLevelID " +
//                    "INNER JOIN gender ON student.genderID = gender.genderID " +
//                    "INNER JOIN StudentEthnicity ON StudentEthnicity.studentID = studentattendance.studentId " +
//                    "INNER JOIN ethnicity ON ethnicity.ethnicityID = StudentEthnicity.ethnicityID " +
//                    "INNER JOIN studentparent ON studentparent.studentID = studentattendance.studentId " +
//                    "INNER JOIN ParentIncome ON ParentIncome.parentID = studentparent.parentID " +
//                    "INNER JOIN income ON income.incomeID = ParentIncome.incomeID " +
//                    "WHERE attendancedate BETWEEN ? AND ? " +
//                    "ORDER BY attendancedate;";

    //states
    //checked in
    //checked in checked out
    //never checked in

    /**
     * Used to get all students attendance
     * Note: checkOutTime, checkOutByFN, checkOutLN can potentially return as null
     * Note: check state of student by looking at the checkOutTime (if it is NULL)
     * Note: could also check isDeleted which is flipped when the student is checked in
     *
     * @return List<AttendanceStatisticsDto>
     */
    public static List<AttendanceStatisticsDto> getStudentAttbetweenDateRange(LocalDate initialDate, LocalDate secondDate) {
        List<AttendanceStatisticsDto> ret = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getAllStudents);
            ps.setDate(1, Date.valueOf(initialDate));
            ps.setDate(2, Date.valueOf(secondDate));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ret.add(new AttendanceStatisticsDto(
                        rs.getDate("attendancedate").toLocalDate(),
                        rs.getString("topic"),
                        rs.getString("campLevelDescription"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("grade"),
                        rs.getTimestamp("checkInTime") == null ? null : rs.getTimestamp("checkInTime").toLocalDateTime(),
                        rs.getString("checkInByFN"),
                        rs.getString("checkInByLN"),
                        rs.getTimestamp("checkOutTime") == null ? null : rs.getTimestamp("checkOutTime").toLocalDateTime(),
                        rs.getString("checkOutByFN"),
                        rs.getString("checkOutByLN"),
                        rs.getBoolean("UCodeUsed")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Cannot get attendance by date");
            e.printStackTrace();
        }
        return ret;
    }
}
