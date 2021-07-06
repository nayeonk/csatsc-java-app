package database;

import data.GradeReport;
import data.ReducedMealsVerification;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseDeletions {
    public static int deleteLogin(int emailID) {
        int updatedRows = 0;
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.deleteLoginSQL);
            ps.setInt(1, emailID);
            updatedRows = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.deleteLogin(" + emailID + "): " + sqle.getMessage());
        }

        return updatedRows;
    }

    //	public static void removeEmergencyContactEmail(int emergencyContactID) {
//		try {
//			PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.removeEmergencyContactEmailSQL);
//			ps.setInt(1, emergencyContactID);
//						
//			ps.executeUpdate();
//			ps.close();
//		} catch(SQLException sqle) {
//			System.out.println("sqle:DatabaseConnection.removeEmergencyContactEmail(" + emergencyContactID + "): " + sqle.getMessage());
//		}	
//	}	
//	
    public static boolean deleteCampStaff(int staffID, int campID) {
        int updatedRows = 0;
        boolean result = false;

        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.deleteCampStaffSQL);
            ps.setInt(1, staffID);
            ps.setInt(2, campID);
            updatedRows = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:StaffDatabaseConnection:updateStaffDeleted(" + staffID + "): " + sqle.getMessage());
        }

        if (updatedRows > 0) result = true;

        return result;
    }

    public static void deleteGradeReport(GradeReport report) {
        report.setDeleted(true);
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.deleteGradeReportSQL);
            ps.setInt(1, report.getGradeReportID());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.deleteGradeReport(" + report.getFilePath() + "): " + sqle.getMessage());
        }
    }

    public static void deleteInsuranceCard(Integer insuranceCardID) {
        //report.setDeleted(true);
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.deleteInsuranceCardSQL);
            ps.setInt(1, insuranceCardID);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.deleteInsuranceCard(): " + sqle.getMessage());
        }
    }

    public static void deleteReducedMealsVerification(ReducedMealsVerification verification) {
        verification.setDeleted(true);
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(SQLStatements.deleteReducedMealsVerificationSQL);
            ps.setInt(1, verification.getReducedMealsVerificationID());
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqle) {
            System.out.println("sqle:DatabaseConnection.deleteReducedMealsVerification(" + verification.getFilePath() + "): " + sqle.getMessage());
        }
    }

}
