package database;

import data.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseConnection {
    private static Connection conn = null;

    public static void connectToDatabase() {
        try {
            Class.forName(SQLStatements.databaseDriver);
            conn = DriverManager.getConnection(SQLStatements.getDatabaseConnectionString());
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            System.out.println("cnfe:DatabaseConnection.connectToDatabase(): " + cnfe.getMessage());
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
            System.out.println("sqle:DatabaseConnection.connectToDatabase(): " + sqle.getMessage());
        }
    }

    static PreparedStatement parentPS(Parent parent, String sql) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveStrings(1, ps, parent.getFirstName(), parent.getLastName(), parent.getPhone());
        ps.setInt(4, parent.getEmailID());
        return ps;
    }

    static PreparedStatement addressPS(Address address, String sql) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveStrings(1, ps, address.getStreet(), address.getCountry(), address.getCity());
        ps.setInt(4, address.getStateID());
        ps.setString(5, address.getZip());
        return ps;
    }

    static PreparedStatement otherInfoPS(int studentID, String otherInfo, String sql) throws SQLException {
        PreparedStatement ps1 = DatabaseConnection.getPreparedStatement(sql);
        ps1.setString(1, otherInfo);
        ps1.setTimestamp(2, new Timestamp(new Date().getTime()));
        ps1.setInt(3, studentID);
        return ps1;
    }

    static void setConsecutiveStrings(int starting, PreparedStatement ps, String... params) throws SQLException {

        int i = starting;
        for (String param : params) {
            ps.setString(i, param);
            i++;
        }
    }

    static void setConsecutiveInts(int starting, PreparedStatement ps, Integer... params) throws SQLException {

        int i = starting;
        for (Integer param : params) {
            ps.setInt(i, param);
            i++;
        }
    }

    static void setConsecutiveBools(int starting, PreparedStatement ps, Boolean... params) throws SQLException {

        int i = starting;
        for (Boolean param : params) {
            ps.setBoolean(i, param);
            i++;
        }
    }


    static PreparedStatement staffPS(Staff staff, String fileLocation, String sql) throws SQLException {

        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveStrings(1, ps, staff.getFirstName(), staff.getLastName());
        ps.setInt(3, staff.getEmail().getEmailID());
        setConsecutiveStrings(4, ps, staff.getTitle(), staff.getCompany(), staff.getDescription());
        setConsecutiveBools(7, ps, staff.isInstructor(), staff.isAdmin());
        ps.setString(9, fileLocation);
        return ps;
    }

    static PreparedStatement studentPS(Student student, String sql) throws SQLException {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveStrings(1, ps, student.getFirstName(), student.getLastName(), student.getMiddleName(),student.getPreferredName(), student.getEmailAddress());
        //need to get genderid and schoolid... not accessed from camperprofile.jsp yet
        //setConsecutiveInts(3, ps, student.getGender().getGenderID(), student.getSchool().getSchoolID());
        //using dummies 1's for genderid schoolid
        setConsecutiveInts(6, ps, student.getGender().getGenderID(), student.getSchool().getSchoolID());
        setConsecutiveStrings(8, ps, student.getOtherSchool(), student.getDob());
        //need to get reducedmealid... not accessed from camperprofile.jsp yet
        ps.setInt(10, student.getReducedMealsID());
        setConsecutiveStrings(11, ps, student.getExperience(), student.getDiet());
        setConsecutiveBools(13, ps, student.getInterestedGirlsCamp(), student.getTransportTo(), student.getTransportFrom());
        ps.setInt(16,year); //this is needed for the updateStudentSQL
        return ps;
    }

    static PreparedStatement studentCamperSchoolsPS(Student student, int studentID, String sql) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ps.setInt(1,studentID);
        setConsecutiveStrings(2,ps,student.getFirstName(),student.getLastName());
        ps.setInt(4,student.getSchoolID());
        ps.setInt(5,year);
        return ps;
    }

    static PreparedStatement EmergencyContactPS(EmergencyContact emergencyContact, String sql) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveStrings(1, ps, emergencyContact.getFirstName(), emergencyContact.getLastName(), emergencyContact.getPhone());

        if (emergencyContact.getEmailID() > 0) {
            ps.setInt(4, emergencyContact.getEmailID());
        } else {
            ps.setNull(4, java.sql.Types.INTEGER);
        }
        ps.setInt(5, emergencyContact.getParentID());

        return ps;
    }

    static PreparedStatement campOfferedPS(int campTopicID, int campLevelID, int campStartDateID, int campEndDateID,
            int studentCapacity, String campDescription, String campRecommendedGrade, int campRecommendedGradeLowID,
            int campRecommendedGradeHighID, double campCost, String fileLocation, boolean remote, String campTA, String startTime, String endTime, String days, String sql) throws SQLException {
        PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
        setConsecutiveInts(1, ps, campTopicID, campLevelID, campStartDateID, campEndDateID, studentCapacity);
        setConsecutiveStrings(6, ps, campDescription, campRecommendedGrade);
        setConsecutiveInts(8, ps, campRecommendedGradeLowID, campRecommendedGradeHighID, (int) campCost);
        ps.setBoolean(11, campCost != 0);
        ps.setString(12, fileLocation);
        ps.setBoolean(13, remote);
        ps.setString(14, campTA);
        ps.setString(15, startTime);
        ps.setString(16, endTime);
        ps.setString(17, days);
      //aaron change
        return ps;
    }


    static String processIncomeString(String income) {
        String incomeStr = null;

        if (income == null) {
            incomeStr = "Income not provided";
        } else if (income.equals("0-20,000")) {
            incomeStr = "$0 - $20,000 per year";
        } else if (income.equals("20,001-40,000")) {
            incomeStr = "$20,001 -$ 40,000 per year";
        } else if (income.equals("40,001-60,000")) {
            incomeStr = "$40,001 - $60,000 per year";
        } else if (income.equals("60,001-80,000")) {
            incomeStr = "$60,001 - $80,000 per year";
        } else if (income.equals("80,001-100,000")) {
            incomeStr = "$80,001 - $100,000 per year";
        } else if (income.equals(">100,001")) {
            incomeStr = "Above $100,001 per year";
        } else if (income.equals("NA")) {
            incomeStr = "Prefer not to answer";
        }

        return incomeStr;
    }

    static Student processStudent(ResultSet rs) {
        try {
            int lastUpdatedYear = rs.getInt("lastUpdatedYear");
            int studentID = rs.getInt("studentID");
            String gender = rs.getString("gender");
            Gender genderObj = new Gender(rs.getInt("genderID"),
                ((gender == null) || (gender.isEmpty()) ? "Other" : gender));

            PreparedStatement ps = getPreparedStatement(SQLStatements.selectStudentCamperSQL);
            int year = Calendar.getInstance().get(Calendar.YEAR);
            ps.setInt(1,studentID);
            ps.setInt(2,year);
            ResultSet rs1 = ps.executeQuery();
            School schoolObj = null;

            //Check if its empty and if so then student needs to update school
            if(rs1.next() == false){
                //DB
                schoolObj =  new School(-2, "");
            }
            //Get school
            else{
                schoolObj = new School(rs.getInt("schoolID"),
                        rs.getString("school"));
            }
            Grade gradeObj = new Grade(rs.getInt("gradeID"),
                rs.getString("grade"));


            ReducedMeals reducedMealsObj = null;
            ArrayList<GradeReport> gradeReports = new ArrayList<GradeReport>();
            ArrayList<ReducedMealsVerification> reducedMealsVerifications = new ArrayList<ReducedMealsVerification>();
            reducedMealsObj = new ReducedMeals(rs.getInt("reducedMealsID"),
                    rs.getString("reducedMeals"));
            //Get grade reports of student
            PreparedStatement ps5 = getPreparedStatement(SQLStatements.getGradeReportsByStudentIDSQL);
            ps5.setInt(1, studentID);
            ResultSet rs5 = ps5.executeQuery();
            while (rs5.next()) {
                gradeReports.add(new GradeReport(
                        rs5.getInt("gradeReportID"),
                        rs5.getInt("studentID"),
                        rs5.getString("filePath"),
                        (rs5.getInt("deleted") != 0)));
            }
            //Get reduced meal verifications of student
            PreparedStatement ps6 = getPreparedStatement(SQLStatements.getReducedMealsVerificationsByStudentIDSQL);
            ps6.setInt(1, studentID);
            ResultSet rs6 = ps6.executeQuery();
            while (rs6.next()) {
                reducedMealsVerifications.add(new ReducedMealsVerification(rs6.getInt("reducedMealsVerificationID"),
                        rs6.getInt("studentID"),
                        rs6.getString("filePath"),
                        (rs6.getInt("deleted") != 0)));
            }

            Student student = new Student.StudentBuilder().name(rs.getString("fname"), rs.getString("mname"), rs.getString("lname"), rs.getString("pname"))
                .emailAddress(rs.getString("semail"))
                .gender(genderObj)
                .school(schoolObj)
                .otherSchool(rs.getString("otherSchool"))
                .grade(gradeObj)
                .dob(rs.getString("dateOfBirth"))
                .reducedMeals(reducedMealsObj)
                .diet(rs.getString("diet"))
                .experience(rs.getString("experience"))
                .transportTo(rs.getBoolean("transportationTo"))
                .transportFrom(rs.getBoolean("transportationFrom"))
                .interestedGirlsCamp(rs.getBoolean("interestedGirlsCamp"))
                .otherInfo(rs.getString("otherInfo"))
                .gradeReports(gradeReports)
                .reducedMealsVerification(reducedMealsVerifications)
                .ethnicity(DatabaseQueries.getEthnicitiesByStudentID(studentID))
                .pickupCode(rs.getString("pickupCode"))
                 .lastUpdatedYear(lastUpdatedYear)
                .progress(rs.getInt("progress"))
                .attended(rs.getBoolean("attended"))
                .build();
            student.setStudentID(studentID);
            return student;
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.processStudent(): " + sqle.getMessage());
        }
        return null;
    }


    public static PreparedStatement getPreparedStatement(String sql) {
        try {
            if (conn == null || conn.isClosed()) {
                connectToDatabase();
                if (conn == null) {
                    System.out.println("Could not get connection to database.");
                    return null;
                }
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.getPreparedStatement(" + sql + "): " + sqle.getMessage());
        }
        return ps;
    }

}