package data;

public class Gender implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private Integer genderID;
    private String gender;
    private String timestamp;

    public Gender(Integer genderID, String gender, String timestamp) {
        setGenderID(genderID);
        setGender(gender);
        setTimestamp(timestamp);
    }

    public Gender(Integer genderID, String gender) {
        setGenderID(genderID);
        setGender(gender);
    }

    public Gender(String gender) {
        setGender(gender);
    }

    public Integer getGenderID() {
        return this.genderID;
    }

    public void setGenderID(Integer genderID) {
        this.genderID = genderID;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}