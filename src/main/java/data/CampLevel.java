package data;

public class CampLevel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int campLevelID;
    private String campLevelDescription;

    public int getCampLevelID() {
        return campLevelID;
    }

    public void setCampLevelID(int campLevelID) {
        this.campLevelID = campLevelID;
    }

    public String getCampLevelDescription() {
        return campLevelDescription;
    }

    public void setCampLevelDescription(String campLevelDescription) {
        this.campLevelDescription = campLevelDescription;
    }
}
