package database;

import data.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseInserts {
    public static int insertLoginAudit(String email, int auth) {
        int loginAuditID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertLoginAuditSQL);
            ps.setString(1, email);
            ps.setString(2, "N/A");
            ps.setInt(3, auth);
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            loginAuditID = executeInsert(ps);

        }
        catch (SQLException sqle) {
            System.out.println(
                "sqle:DatabaseConnection.insertLoginAudit(" + email + "): " + sqle.getMessage());
        }

        return loginAuditID;
    }

    public static int insertMedicalForm(MedicalForm mf) {
        int medFormID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertMedicalForm);
            DatabaseConnection.setConsecutiveStrings(1, ps, mf.getInsuranceCarrier(), mf.getNameOfInsured(), mf.getPolicyNumber(),
                mf.getPolicyPhone(), mf.getPhysicianName(), mf.getPhysicianPhone(), mf.getAllergiesJSON(), mf.getpMedsJSON(),
                mf.getNonPMedsJSON(), mf.getIllnessJSON(), mf.gethVisitsJSON(), mf.getStudentFName(), mf.getStudentLName(),
                mf.getParentPhone(), mf.getLegalGuardianName(), mf.getLegalGuardianAgree(), mf.getDateOfBirth(), mf.getEcName(), mf.getEcRelationship(),
                mf.getEcPhone());
            if (mf.getTetanusShot() == null) {
                ps.setNull(21, java.sql.Types.BIT);
            } else {
                ps.setBoolean(21, mf.getTetanusShot());
            }
            DatabaseConnection.setConsecutiveBools(22, ps, mf.getPhysicianPhoneCellOrWork(), mf.getParentPhoneCellOrHome(),
                mf.getEcPhoneCellOrHome());
            DatabaseConnection.setConsecutiveInts(25, ps, mf.getGenderID(), insertAddress(mf.getAddress()), mf.getStudentID());
            medFormID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseInsert.insertMedicalForm():" + sqle.getMessage());
        }

        return medFormID;
    }


    public static void insertDirectory(String absolutePath, Student student) {
        insertStudentReducedMealsDirectory(absolutePath, student);
    }

    public static int insertStudentHistory(String status, Student student, int campOfferedID) {
        int studentCampHistoryID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.updateStudentHistory);
            ps.setInt(1, campOfferedID);
            ps.setInt(2, student.getStudentID());
            ps.setString(3, status);
            studentCampHistoryID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle: StudentCampDatabaseConnection.updateStudentHistory(): " + sqle.getMessage());
        }
        return studentCampHistoryID;
    }

    public static int insertStaff(Staff staff, String fileLocation) {
        int staffID = -1;
        try {
            staffID = executeInsert(DatabaseConnection.staffPS(staff, fileLocation, SQLStatements.insertStaffSQL));
        }
        catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection:insertStaff(" + staff.getFirstName() + " " + staff.getLastName() + "): " + sqle.getMessage());
        }
        return staffID;
    }

    public static List<Staff> addCampStaff(int staffID, int campID) {
        boolean alreadyAdded = false;
        List<Staff> campStaff = DatabaseQueries.getCampStaff(campID);
        for (Staff staff : campStaff) {
            if (staff.getStaffID() == staffID) {
                alreadyAdded = true;
            }
        }
        if (!alreadyAdded) {
            try {
                PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.addCampStaffSQL);
                ps.setInt(1, staffID);
                ps.setInt(2, campID);
                executeInsert(ps);
            }
            catch (SQLException sqle) {
                System.out.println("sqle:StaffDatabaseConnection:addCampStaff(id:" + staffID + "): " + sqle.getMessage());
            }
        }

        return DatabaseQueries.getCampStaff(campID);
    }

    public static int insertCampTopic(String topic) {
        return insertStringParam(topic, SQLStatements.insertCampTopicSQL);
    }

    public static int insertCampLevel(String level) {
        return insertStringParam(level, SQLStatements.insertCampLevelSQL);
    }

    public static int insertDate(String dateString) throws ParseException {
        int dateID = -1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        java.sql.Date date = new java.sql.Date(format.parse(dateString).getTime());
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertDateSQL);
            ps.setDate(1, date);
            dateID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.insertDate(" + dateString + "):" + sqle.getMessage());
        }
        return dateID;
    }
//aaron change
    public static int insertCampOffered(int campTopicID, int campLevelID, int campStartDateID, int campEndDateID, 
                                        int studentCapacity, String campDescription, String campRecommendedGrade, 
                                        int campRecommendedGradeLowID, int campRecommendedGradeHighID, double campCost,
                                        String fileLocation, boolean remote, String campTA, String startTime, String endTime, String days) {

        int campOfferedID = -1;
        try {
          //aaron change
            PreparedStatement ps = DatabaseConnection.campOfferedPS(campTopicID, campLevelID, campStartDateID,
                    campEndDateID, studentCapacity, campDescription, campRecommendedGrade, campRecommendedGradeLowID,
                    campRecommendedGradeHighID, campCost, fileLocation, remote, campTA, startTime, endTime, days, SQLStatements.insertCampOfferedSQL);


            campOfferedID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.insertCampOffered(" + campTopicID + ", " + campLevelID + ", " + campStartDateID + ", " + campEndDateID + ", " + studentCapacity + "):" + sqle.getMessage());
        }
        return campOfferedID;
    }


    public static int insertLogin(int emailID, String password, String salt, boolean admin) {
        int loginID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertLoginSQL);
            ps.setInt(1, emailID);
            ps.setString(2, password);
            ps.setString(3, salt);
            ps.setBoolean(4, admin);
            loginID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println(
                "sqle:DatabaseConnection.insertLogin(" + emailID + ", " + password + "): " + sqle.getMessage());
        }

        return loginID;
    }

    public static int insertParent(Parent parent) {
        int parentID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.parentPS(parent, SQLStatements.insertParentSQL);
            ps.setInt(5, insertAddress(parent.getAddress()));
            //TODO: Update SQL Database
            //ps.setBoolean(6, parent.getUSCEmployee());
            parentID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("Parent " + sqle.getMessage());
        }
        return parentID;
    }

    public static int insertParentIncome(int parentID, Integer incomeID) {
        int parentIncomeID = -1;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertParentIncomeSQL);
            ps.setInt(1, incomeID);
            ps.setInt(2, parentID);
            ps.setTimestamp(3, new Timestamp(new Date().getTime()));
            parentIncomeID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("Insert ParentIncome Error: " + sqle.getMessage());
        }

        return parentIncomeID;
    }

    // CC: Inserting the emergency contact information into the database.
    public static int insertEmergencyContact(EmergencyContact emergencyContact) {
        int emergencyContactID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.EmergencyContactPS(emergencyContact, SQLStatements.insertEmergencyContactSQL);
            ps.setInt(6, insertAddress(emergencyContact.getAddress()));
            emergencyContactID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("Emergency Contact " + sqle.getMessage());
        }
        return emergencyContactID;
    }

    // Updates the emergencyContact table

    public static int insertAddress(Address address) {
        int addressID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.addressPS(address, SQLStatements.insertAddressSQL);
            addressID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("Address " + sqle.getMessage());
        }
        return addressID;
    }

    public static int insertStudent(Student student) {
        int studentID = -1;
        try {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            PreparedStatement ps = DatabaseConnection.studentPS(student, SQLStatements.insertStudentSQL);
            ps.setInt(16, 8); //8th grade
            ps.setBoolean(17, student.getLegalAgree());
            ps.setString(18, student.getPickupCode());
            ps.setInt(19,year);

            ps.setInt(20, 0); //progress
            ps.setBoolean(21, student.getAttended()); //progress
            studentID = executeInsert(ps);

            PreparedStatement ps1 = DatabaseConnection.studentCamperSchoolsPS(student,studentID,SQLStatements.insertStudentCamperSchoolsSQL);
            ps1.executeUpdate();
            System.out.println("NEW STUDENTID EXECUTING: ");
            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("NEW STUDENTID: " + generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            ps1.close();

        }
        catch (SQLException sqle) {
            System.out.println("Student " + sqle.getMessage());
        }
        return studentID;
    }


    public static int insertStudentParent(StudentParent studentParent) {
        int studentParentID = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertStudentParentSQL);
            ps.setInt(1, studentParent.getStudentID());
            ps.setInt(2, studentParent.getParentID());
            studentParentID = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("StudentParent " + sqle.getMessage());
        }
        return studentParentID;
    }

    private static int executeInsert(PreparedStatement ps) throws SQLException {
        int id = -1;
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return id;
    }

    //Probably temporary until we get data populated in tables
    public static int insertGender(Gender gender) {
        return insertStringParam(gender.getGender(), SQLStatements.insertGenderSQL);
    }

    private static int insertStringParam(String param, String sql) {
        int id = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setString(1, param);
            id = executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertEmail(" + id + "): " + sqle.getMessage());
        }
        return id;
    }

    public static int insertEmail(String email) {
        return insertStringParam(email, SQLStatements.insertEmailSQL);
    }

    // CC: Adding reduced meals to the database
    public static void insertStudentReducedMealsDirectory(String absolutePath, Student student) {

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.modifyStudentReducedMealsSQL);
            ps.setString(1, absolutePath);
            ps.setInt(2, student.getStudentID());
            executeInsert(ps);
        }
        catch (SQLException sqle) {
            System.out.println("sqle:UploadDownloadFileServlet.insertDirectory(" + absolutePath + "):" + sqle.getMessage());
        }
        catch (Exception e) {
            System.out.println("Exception in insertDirectory().");
        }
    }


    // CC: Inserting student ethnicities into the database
    public static void insertStudentEthnicity(int studentID, List<StudentEthnicity> ethnicitiesToStore) {

        for (StudentEthnicity e : ethnicitiesToStore) {
            String sql = (e.getEthnicityID() == 6) ? SQLStatements.insertStudentEthnicitiesOtherSQL : SQLStatements.insertStudentEthnicitiesSQL;
            try {
                PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
                DatabaseConnection.setConsecutiveInts(1, ps, studentID, e.getEthnicityID(), 1);

                if (e.getEthnicityID() == 6) {
                    ps.setString(4, e.getOtherEthnicity());
                }

                executeInsert(ps);
            }
            catch (SQLException sqle) {
                System.out.println("sqle:UploadDownloadFileServlet.insertStudentEthnicity(" + studentID + ", " + e.getEthnicityID() + "):" + sqle.getMessage());
            }
        }
    }

    public static void insertStudentLegalAgree(int studentID, int legalAgreement) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertStudentLegalAgreeSQL);
            ps.setInt(1, legalAgreement);
            ps.setInt(2, studentID);

            ps.executeUpdate();
            ps.close();
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertStudentLegalAgree: " + sqle.getMessage());
        }
    }

    public static int insertOtherInfo(int studentID, String otherInfo) {
        int otherinfoID = -1;
        try {
            otherinfoID = executeInsert(DatabaseConnection.otherInfoPS(studentID, otherInfo, SQLStatements.insertOtherInfoSQL));
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertOtherInfo: " + sqle.getMessage());
        }

        return otherinfoID;
    }

    // CC: Sending student application to the database
    public static void insertStudentApplication(int studentID, int campID) {

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertStudentCampSQL);
            DatabaseConnection.setConsecutiveInts(1, ps, studentID, campID, 0, 0, 0, 0, 0, 0);
            ps.setTimestamp(9, new Timestamp(new Date().getTime()));

            ps.executeUpdate();
            ps.close();
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertStudentCamps: " + sqle.getMessage());
        }
    }

    public static void insertToken(String token, int email, Timestamp expiry) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertTokenSQL);
            ps.setString(1, token);
            ps.setInt(2, email);
            ps.setTimestamp(3, expiry);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertToken(" + token + "): " + sqle.getMessage());
        }
    }

    private static void insertFile(int id, String filePath, String sql) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, filePath);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertGradeReport(" + filePath + "): " + sqle.getMessage());
        }
    }

    public static void insertGradeReport(GradeReport report) {
        insertFile(report.getStudentID(), report.getFilePath(), SQLStatements.insertGradeReportSQL);
    }

    public static void insertInsuranceCard(int medFormID, String frontPath, String backPath) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.insertInsuranceCardSQL);
            ps.setInt(1, medFormID);
            ps.setString(2, frontPath);
            ps.setString(3, backPath);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseInserts.insertInsuranceCard(" + frontPath + "): " + sqle.getMessage());
        }
    }

    public static void insertGradeReport(int id, String filePath) {
        insertFile(id, filePath, SQLStatements.insertGradeReportSQL);
    }

    public static void insertReducedMealsVerification(int id, String filePath) {
        insertFile(id, filePath, SQLStatements.insertReducedMealsVerificationSQL);
    }

    public static void insertReducedMealsVerification(ReducedMealsVerification verification) {
        insertFile(verification.getStudentID(), verification.getFilePath(), SQLStatements.insertReducedMealsVerificationSQL);
    }

    public static int insertPickupCode(String sID, String newCode) {
        int insertSuccess = -1;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.getStudentPickupCode);
            ps.setString(1, sID);
            ResultSet rs = ps.executeQuery();
            String cCode = "";
            while (rs.next()) {
                cCode = rs.getString("pickupCode");
            }
            if (cCode == null) { //insert new code
                PreparedStatement ps2 = DatabaseConnection.getPreparedStatement(SQLStatements.updatePickupCodeSQL);
                ps2.setString(1, newCode);
                ps2.setString(2, sID);
                ps2.executeUpdate();
                ps2.close();
            }

            ps.close();
            rs.close();

        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.insertPickupCodeSQL(" + sID + "): " + sqle.getMessage());
        }

        return insertSuccess;
    }

    public static boolean insertUniversalCode(String newUniversalCode) {
        insertStringParam(newUniversalCode, SQLStatements.updateUniversalCodeSQL);
        return true;
    }


}
