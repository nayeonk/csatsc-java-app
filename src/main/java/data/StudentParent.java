package data;

public class StudentParent implements java.io.Serializable {

    public static final long serialVersionUID = 1;

    int studentParentID;
    int studentID;
    int parentID;

    public StudentParent(int studentID, int parentID) {
        this.studentID = studentID;
        this.parentID = parentID;
    }

    public int getStudentParentID() {
        return studentParentID;
    }

    public void setStudentParentID(int studentParentID) {
        this.studentParentID = studentParentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }


}
