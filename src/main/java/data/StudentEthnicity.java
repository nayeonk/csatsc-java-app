package data;

import java.io.Serializable;

public class StudentEthnicity implements Serializable {

    private static final long serialVersionUID = 1L;
    private int studentEthnicityID;
    private int studentID;
    private int ethnicityID;
    private boolean valid;
    private String otherEthnicity;

    public StudentEthnicity() {
    }

    public StudentEthnicity(int studentEthnicityID, int studentID, int ethnicityID, boolean valid) {
        this.setStudentEthnicityID(studentEthnicityID);
        this.setStudentID(studentID);
        this.setEthnicityID(ethnicityID);
        this.setValid(valid);
    }

    public StudentEthnicity(int studentEthnicityID, int studentID, int ethnicityID, boolean valid, String otherEthnicity) {
        this.setStudentEthnicityID(studentEthnicityID);
        this.setStudentID(studentID);
        this.setEthnicityID(ethnicityID);
        this.setValid(valid);
        this.setOtherEthnicity(otherEthnicity);
    }

    public int getStudentEthnicityID() {
        return studentEthnicityID;
    }

    public void setStudentEthnicityID(int studentEthnicityID) {
        this.studentEthnicityID = studentEthnicityID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getEthnicityID() {
        return ethnicityID;
    }

    public void setEthnicityID(int ethnicityID) {
        this.ethnicityID = ethnicityID;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getOtherEthnicity() {
        return otherEthnicity;
    }

    public void setOtherEthnicity(String otherEthnicity) {
        this.otherEthnicity = otherEthnicity;
    }
}
