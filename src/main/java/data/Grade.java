package data;

public class Grade implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    private Integer gradeID;
    private String grade;
    private String timestamp;

    public Grade(Integer gradeID, String grade, String timestamp) {
        setGradeID(gradeID);
        setGrade(grade);
        setTimestamp(timestamp);
    }

    public Grade(Integer gradeID, String grade) {
        setGradeID(gradeID);
        setGrade(grade);
        setTimestamp(timestamp);
    }

    public Grade(String grade) {
        setGrade(grade);
    }

    public Grade(Integer gradeID) {
        setGradeID(gradeID);
    }

    public Integer getGradeID() {
        return this.gradeID;
    }

    public void setGradeID(Integer gradeID) {
        this.gradeID = gradeID;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}