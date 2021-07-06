package repositories;

import database.DatabaseConnection;
import model.AttendanceDto;
import model.CampDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversalPickupCodeRepo {
    private final static String getAllUniversalPickupCodes = "SELECT * FROM universalpickupcode;";
    private final static String createNewUniversalPickupCode = "INSERT into universalpickupcode(pickupCode) VALUES(?)";
    private final static String deleteUniversalPickupCode = "DELETE FROM universalpickupcode WHERE pickupCode=?";


    /**
     * Creates a new universal pickup code
     *
     * @return A map of a code's id to the code value
     */
    public static Map<Integer, String> getAllUniversalPickupCodes() {
        Map<Integer, String> upcMap = new HashMap<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getAllUniversalPickupCodes);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("universalCodeID"));
                String code =  rs.getString("pickupCode");
                upcMap.put(id, code);
            }
            return upcMap;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return upcMap;
    }

    /**
     * Creates a new universal pickup code
     *
     * @param pickupCode The universal pickup code being created
     * @return boolean If SQL query is successful
     */
    public static boolean createUniversalPickupCode(String pickupCode) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(createNewUniversalPickupCode);
            ps.setString(1, pickupCode);
            ps.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new universal pickup code
     *
     * @param pickupCode The universal pickup code being deleted
     * @return boolean If SQL query is successful
     */
    public static boolean deleteUniversalPickupCode(String pickupCode) {
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(deleteUniversalPickupCode);
            ps.setString(1, pickupCode);
            ps.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}