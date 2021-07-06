package repositories;

import database.DatabaseConnection;
import model.CampDto;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CampRepo {
    //? is in YYYY-MM-DD format
    private final static String getCampByDateSql =
            "SELECT campOfferedID, topic, campLevelDescription, gradelow.grade AS gradelow, gradehigh.grade AS gradehigh, datestart.date AS dateStart, dateend.date AS dateEnd " +
                    "FROM campoffered " +
                    "INNER JOIN camptopic ON camptopic.campTopicID=campoffered.campTopicID " +
                    "INNER JOIN date datestart ON datestart.dateID=campoffered.campStartDateID " +
                    "INNER JOIN date dateend ON dateend.dateID=campoffered.campEndDateID " +
                    "INNER JOIN camplevel ON camplevel.campLevelID = campoffered.campLevelID " +
                    "INNER JOIN grade gradelow ON gradelow.gradeID = campoffered.recommendedGradeLowID " +
                    "INNER JOIN grade gradehigh ON gradehigh.gradeID = campoffered.recommendedGradeHighID " +
                    "WHERE ? between datestart.date and dateend.date;";

    /**
     * Gets all camps on a specific date
     *
     * @param date The day being requested
     * @return List of CampDto for the day
     */
    public static List<CampDto> getCampByDate(LocalDate date) {
        ArrayList<CampDto> ret = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseConnection.getPreparedStatement(getCampByDateSql);
            ps.setDate(1, Date.valueOf(date));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret.add(new CampDto(
                        rs.getInt("campOfferedID"),
                        rs.getString("topic"),
                        rs.getString("campLevelDescription"),
                        rs.getString("gradelow"),
                        rs.getString("gradehigh"),
                        rs.getDate("dateStart"),
                        rs.getDate("dateEnd")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Cannot get camps by date");
            e.printStackTrace();
        }
        return ret;
    }
}
