package model;

import java.sql.Date;

public class CampDto {

    private int campOfferedId;
    private String topic;
    private String level;
    private String gradelow;
    private String gradehigh;
    private final Date dateStart;
    private final Date dateEnd;

    public CampDto(int campOfferedId, String topic, String level, String gradelow, String gradehigh, Date dateStart, Date dateEnd
    ) {
        this.campOfferedId = campOfferedId;
        this.topic = topic;
        this.level = level;
        this.gradelow = gradelow;
        this.gradehigh = gradehigh;

        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getCampOfferedId() {
        return campOfferedId;
    }

    public void setCampOfferedId(int campOfferedId) {
        this.campOfferedId = campOfferedId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGradelow() {
        return gradelow;
    }

    public void setGradelow(String gradelow) {
        this.gradelow = gradelow;
    }

    public String getGradehigh() {
        return gradehigh;
    }

    public void setGradehigh(String gradehigh) {
        this.gradehigh = gradehigh;
    }
}
