package data;

public class School implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private Integer schoolID;
    private String school;
    private String timestamp;

    public School(Integer schoolID, String school, String timestamp) {
        setSchoolID(schoolID);
        setSchool(school);
        setTimestamp(timestamp);
    }

    public School(Integer schoolID, String school) {
        setSchoolID(schoolID);
        setSchool(school);
    }

    public School(String school) {
        setSchool(school);
    }

    public School(Integer schoolID) {
        setSchoolID(schoolID);
    }

    public Integer getSchoolID() {
        return this.schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}