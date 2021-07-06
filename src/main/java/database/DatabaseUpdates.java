package database;

import data.*;
import servlets.CreateStudent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import static database.DatabaseConnection.getPreparedStatement;

public class DatabaseUpdates {
    // CC: MODIFIES A PARENT ENTRY IN THE DATABASE
    public static void updateStudentCost(int cost, Student student, int campOfferedID) {
        try {
            PreparedStatement ps = getPreparedStatement(SQLStatements.updatStudentCampCostSQL);
            DatabaseConnection.setConsecutiveInts(1, ps, cost, student.getStudentID(), campOfferedID);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:StudentCampDatabaseConnection.updateStudentCost(): " + sqle.getMessage());
        }
    }


    public static void updateStudentStatus(String status, Student student, int campOfferedID) {
        String sql = SQLStatements.studentCampStatusToUpdateMap.get(status);

        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            ps.setInt(2, student.getStudentID());
            ps.setInt(3, campOfferedID);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:StudentCampDatabaseConnection.updateStudentStatus(): " + sqle.getMessage());
        }
    }

    public static int updateStaffDeleted(int staffID) {
        return updateIntParam(staffID, SQLStatements.updateStaffDeletedSQL);
    }

    public static int updateStaff(Staff staff, String fileLocation) {
        int updatedRows = 0;
        try {
            PreparedStatement ps = DatabaseConnection.staffPS(staff, fileLocation, SQLStatements.updateStaffSQL);
            ps.setInt(10, staff.getStaffID());
            updatedRows = ps.executeUpdate();
            ps.close();

        }
        catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection:updateStaff(" + staff.getFirstName() + " " + staff.getLastName() + "): " + sqle.getMessage());
        }

        return updatedRows;
    }

    private static int updateIntParam(int campOfferedID, String sql) {
        int updatedRows = 0;

        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setInt(1, campOfferedID);
            updatedRows = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.updateCampOfferedClosedSQL(" + campOfferedID + "): " + sqle.getMessage());
        }

        return updatedRows;
    }

    public static int updateCampOfferedClosed(int campOfferedID) {
        return updateIntParam(campOfferedID, SQLStatements.updateCampOfferedClosedSQL);
    }

    public static int updateCampOfferedOpened(int campOfferedID) {
        return updateIntParam(campOfferedID, SQLStatements.updateCampOfferedOpenedSQL);
    }

    public static int updateCampOfferedDeleted(int campOfferedID) {
        return updateIntParam(campOfferedID, SQLStatements.updateCampOfferedDeletedSQL);
    }

    public static int updateCampOffered(int campOfferedID, int campTopicID, int campLevelID, 
            int campStartDateID, int campEndDateID, int studentCapacity, String campDescription, 
            String campRecommendedGrade, int campRecommendedGradeLowID, int campRecommendedGradeHighID,
            double campCost, String fileLocation, boolean remote, String campTA, String startTime, String endTime, String days) {
        int updatedRows = 0;

        try {
          //aaron change
            PreparedStatement ps = DatabaseConnection.campOfferedPS(campTopicID, campLevelID, campStartDateID,
                campEndDateID, studentCapacity, campDescription, campRecommendedGrade, campRecommendedGradeLowID,
                campRecommendedGradeHighID, campCost, fileLocation, remote, campTA, startTime, endTime, days, SQLStatements.updateCampOfferedSQL);
            ps.setInt(18, campOfferedID);

            updatedRows = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:CampOfferedDatabaseConnection.updateCampOffered(" + campTopicID + ", " + campLevelID + ", " + campStartDateID + ", " + campEndDateID + ", " + studentCapacity + "):" + sqle.getMessage());
        }

        return updatedRows;
    }

    public static void modifyParent(Parent parent) {

        int parentID = parent.getParentID();

        try {
            PreparedStatement ps = DatabaseConnection.parentPS(parent, SQLStatements.updateParentSQL);//DatabaseConnection.getPreparedStatement (SQLStatements.updateParentSQL);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateParent(" + parentID + "): " + sqle.getMessage());
        }
    }

    public static void modifyParentIncome(int parentID, Integer incomeID) {

        try {
            PreparedStatement ps = getPreparedStatement(SQLStatements.updateParentIncomeSQL);
            DatabaseConnection.setConsecutiveInts(1, ps, incomeID, parentID);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateParent(" + parentID + ") income: " + sqle.getMessage());
        }
    }

    // CC: MODIFIES AN EMERGENCY CONTACT ENTRY IN THE DATABASE
    public static void modifyEmergencyContact(EmergencyContact emergencyContact) {

        int emergencyContactID = emergencyContact.getEmergencyContactID();

        try {
            PreparedStatement ps = DatabaseConnection.EmergencyContactPS(emergencyContact, SQLStatements.updateEmergencyContactSQL);//DatabaseConnection.getPreparedStatement (SQLStatements.updateEmergencyContactSQL);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateEmergencyContact(" + emergencyContactID + "): " + sqle.getMessage());
        }
    }

    // CC: MODIFIES AN ADDRESS ALREADY ENTERED INTO THE DATABASE
    public static void modifyAddress(Address address, int addressID) {

        try {
            PreparedStatement ps = DatabaseConnection.addressPS(address, SQLStatements.updateAddressSQL);
            ps.setInt(6, addressID);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateAddress(" + addressID + "): " + sqle.getMessage());
        }
    }

    // CC: MODIFIES A STUDENT ENTRY IN THE DATABASE
    public static void modifyStudent(Student student) {

        int studentID = student.getStudentID();

        try {
            //DB
            PreparedStatement ps = DatabaseConnection.studentPS(student, SQLStatements.updateStudentSQL);//getPreparedStatement (SQLStatements.updateStudentSQL);
            ps.setBoolean(17, student.getAttended());
            ps.setInt(18, student.getStudentID());
            ps.executeUpdate();

            int year = Calendar.getInstance().get(Calendar.YEAR);
            ps = getPreparedStatement(SQLStatements.selectStudentCamperSQL);
            ps.setInt(1,studentID);
            ps.setInt(2,year);
            ResultSet rs1 = ps.executeQuery();

            //Need to add entry
            if(rs1.next() == false){
                //Add entry for student
                PreparedStatement ps1 = DatabaseConnection.studentCamperSchoolsPS(student,studentID,SQLStatements.insertStudentCamperSchoolsSQL);
                ps1.executeUpdate();
                ps1.close();
            }
            //Just update tables
            else{
                ps = getPreparedStatement(SQLStatements.updateStudentCamperSchoolsSQL);
                ps.setInt(1,student.getSchoolID());
                ps.setInt(2,studentID);
                ps.setInt(3,year);
                ps.executeUpdate();
                ps.close();
            }
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateStudent(" + studentID + "): " + sqle.getMessage());
        }
    }

    public static void updateStudentProgress(int studentID, int progress) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.updateStudentProgress);//getPreparedStatement (SQLStatements.updateStudentSQL);
            ps.setInt(1, progress);
            ps.setInt(2, studentID);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateStudentProgress(" + studentID + "): " + sqle.getMessage());
        }
    }

    public static void modifyOtherInfo(int studentID, String otherInfo) {
        try {
            //First check if there is already other information for this child
            Boolean exists = false;
            PreparedStatement ps = getPreparedStatement(SQLStatements.getOtherInfoSQL);
            ps.setInt(1, studentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = true;
            }
            rs.close();
            ps.close();

            String sql = exists ? SQLStatements.modifyOtherInfoSQL : SQLStatements.insertOtherInfoSQL;
            PreparedStatement ps1 = DatabaseConnection.otherInfoPS(studentID, otherInfo, sql);
            ps1.executeUpdate();
            ps1.close();
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.modifyOtherInfo: " + sqle.getMessage());
        }
    }

    // CC: Updates the user's login information.
    public static void updateLogin(int emailID, String hashedPassword, String encodedSalt, boolean b) {

        try {
            PreparedStatement ps = getPreparedStatement(SQLStatements.updateLoginSQL);
            ps.setString(1, hashedPassword);
            ps.setString(2, encodedSalt);
            ps.setInt(3, emailID);

            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateLogin(" + emailID + "): " + sqle.getMessage());
        }
    }

    public static void invalidateStudentEthnicities(int studentID) {
        updateIntParam(studentID, SQLStatements.invalidateStudentEthnicitiesSQL);
    }

    public static void modifyDirectory(Student student, String absolutePath) {

        if (student == null) {
            student = CreateStudent.getStudent();
        }
        DatabaseInserts.insertStudentReducedMealsDirectory(absolutePath, student);
    }

    private static void changeStudentApplicationStatus(int studentID, int campOfferedID, String sql) {
        try {
            PreparedStatement ps = getPreparedStatement(sql);

            ps.setInt(1, 1);

            java.util.Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());

            ps.setTimestamp(2, timestamp);
            ps.setInt(3, campOfferedID);
            ps.setInt(4, studentID);

            ps.executeUpdate();
            ps.close();
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.insertStudentCamps: " + sqle.getMessage());
        }
    }
    private static void changeStudentApplicationStatus(int studentCampID, String sql) {
        try {
            PreparedStatement ps = getPreparedStatement(sql);

            ps.setInt(1, 1);

            java.util.Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());

            ps.setTimestamp(2, timestamp);
            ps.setInt(3, studentCampID);

            ps.executeUpdate();
            ps.close();
        }

        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.changeStudentApplicationStatus: " + sqle.getMessage());
        }
    }

    // CC: Confirm applications from the parent control panel.
    public static void confirmStudentApplication(int studentID, int campOfferedID) {
        changeStudentApplicationStatus(studentID, campOfferedID, SQLStatements.confirmStudentCampApplicationSQL);
    }
    public static void confirmStudentCampApplication(int studentCampID) {
        changeStudentApplicationStatus(studentCampID, SQLStatements.confirmStudentCampApplicationByStudentCampIDSQL);
    }

    // CC: Withdraw applications from the parent control panel.
    public static void withdrawStudentApplication(int studentID, int campOfferedID) {
        changeStudentApplicationStatus(studentID, campOfferedID, SQLStatements.withdrawStudentCampApplicationSQL);
    }
    public static void withdrawStudentCampApplication(int studentCampID) {
        changeStudentApplicationStatus(studentCampID, SQLStatements.withdrawStudentCampApplicationByStudentCampIDSQL);
    }

    public static void payForCamp(int studentID, int campOfferedID) {
        changeStudentApplicationStatus(studentID, campOfferedID, SQLStatements.payForCampSQL);
    }

    public static void verifyToken(String token) {
        try {
            PreparedStatement ps = getPreparedStatement(SQLStatements.verifyTokenSQL);
            ps.setString(1, token);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.verifyToken(" + token + "): " + sqle.getMessage());
        }
    }

    private static void updateEmail(String newStr, int emailSubjectID, String sql) {
        try {
            PreparedStatement ps = getPreparedStatement(sql);
            ps.setString(1, newStr);
            ps.setInt(2, emailSubjectID);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateEmailContent(" + emailSubjectID + "): " + sqle.getMessage());
        }
    }

    public static void updateEmailContent(String content, int emailSubjectID) {
        updateEmail(content, emailSubjectID, SQLStatements.updateEmailContentSQL);
    }

    public static void updateEmailSubject(String subject, int emailSubjectID) {
        updateEmail(subject, emailSubjectID, SQLStatements.updateEmailSubjectSQL);
    }

    public static void updateMedicalForm(MedicalForm mf) {
        try {
            modifyAddress(mf.getAddress(), mf.getAddressID());
            PreparedStatement ps = getPreparedStatement(SQLStatements.updateMedicalForm);
            DatabaseConnection.setConsecutiveStrings(1, ps, mf.getInsuranceCarrier(), mf.getNameOfInsured(), mf.getPolicyNumber(),
                mf.getPolicyPhone(), mf.getPhysicianName(), mf.getPhysicianPhone(), mf.getAllergiesJSON(), mf.getpMedsJSON(),
                mf.getNonPMedsJSON(), mf.getIllnessJSON(), mf.gethVisitsJSON(), mf.getStudentFName(), mf.getStudentLName(),
                mf.getParentPhone(), mf.getLegalGuardianName(), mf.getDateOfBirth(), mf.getEcName(), mf.getEcRelationship(),
                mf.getEcPhone());
            if (mf.getTetanusShot() == null) {
                ps.setNull(20, java.sql.Types.BIT);
            } else {
                ps.setBoolean(20, mf.getTetanusShot());
            }
            DatabaseConnection.setConsecutiveBools(21, ps, mf.getPhysicianPhoneCellOrWork(), mf.getParentPhoneCellOrHome(),
                mf.getEcPhoneCellOrHome());
            //ps.setBoolean(14, mf.getTetanusShot());
            ps.setInt(24, mf.getGenderID());
            ps.setInt(25, mf.getMedicalFormID());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseUpdates.updateMedicalForm():" + sqle.getMessage());
        }
    }
}
