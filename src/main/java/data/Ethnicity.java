package data;

public class Ethnicity implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private int ethnicityID;
    private String ethnicity;
    private String timestamp;

    public Ethnicity(int ethnicityID, String ethnicity, String timestamp) {
        setEthnicityID(ethnicityID);
        setEthnicity(ethnicity);
        setTimestamp(timestamp);
    }

    public Ethnicity(String ethnicity) {
        setEthnicity(ethnicity);
    }

    public int getEthnicityID() {
        return this.ethnicityID;
    }

    public void setEthnicityID(int ethnicityID) {
        this.ethnicityID = ethnicityID;
    }

    public String getEthnicity() {
        return this.ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}