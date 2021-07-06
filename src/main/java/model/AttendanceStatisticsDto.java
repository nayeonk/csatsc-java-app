package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceStatisticsDto {
    private LocalDate attendanceDate;
    private String topic;
    private String campLevelDescription;
    private String studentFN;
    private String studentLN;
    private String gender;
    private String ethnicity;
    private String income;
    private String grade;
    private LocalDateTime checkInTime;
    private String checkInByFN;
    private String checkInByLN;
    private LocalDateTime checkOutTime;
    private String checkOutByFN;
    private String checkOutByLN;
    private boolean UCodeUsed;
    private boolean isDeleted;

    public AttendanceStatisticsDto(LocalDate attendanceDate, String topic, String campLevelDescription, String studentFN, String studentLN, String grade, LocalDateTime checkInTime, String checkInByFN, String checkInByLN, LocalDateTime checkOutTime, String checkOutByFN, String checkOutByLN, boolean UCodeUsed) {
        this.attendanceDate = attendanceDate;
        this.topic = topic;
        this.campLevelDescription = campLevelDescription;
        this.studentFN = studentFN;
        this.studentLN = studentLN;
        this.grade = grade;
        this.checkInTime = checkInTime;
        this.checkInByFN = checkInByFN;
        this.checkInByLN = checkInByLN;
        this.checkOutTime = checkOutTime;
        this.checkOutByFN = checkOutByFN;
        this.checkOutByLN = checkOutByLN;
        this.UCodeUsed = UCodeUsed;
    }

    public AttendanceStatisticsDto(LocalDate attendanceDate, String topic, String campLevelDescription, String studentFN, String studentLN, String gender, String ethnicity, String income, String grade, LocalDateTime checkInTime, String checkInByFN, String checkInByLN, LocalDateTime checkOutTime, String checkOutByFN, String checkOutByLN, boolean UCodeUsed, boolean isDeleted) {
        this.attendanceDate = attendanceDate;
        this.topic = topic;
        this.campLevelDescription = campLevelDescription;
        this.studentFN = studentFN;
        this.studentLN = studentLN;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.income = income;
        this.grade = grade;
        this.checkInTime = checkInTime;
        this.checkInByFN = checkInByFN;
        this.checkInByLN = checkInByLN;
        this.checkOutTime = checkOutTime;
        this.checkOutByFN = checkOutByFN;
        this.checkOutByLN = checkOutByLN;
        this.UCodeUsed = UCodeUsed;
        this.isDeleted = isDeleted;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCampLevelDescription() {
        return campLevelDescription;
    }

    public void setCampLevelDescription(String campLevelDescription) {
        this.campLevelDescription = campLevelDescription;
    }

    public String getStudentFN() {
        return studentFN;
    }

    public void setStudentFN(String studentFN) {
        this.studentFN = studentFN;
    }

    public String getStudentLN() {
        return studentLN;
    }

    public void setStudentLN(String studentLN) {
        this.studentLN = studentLN;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckInByFN() {
        return checkInByFN;
    }

    public void setCheckInByFN(String checkInByFN) {
        this.checkInByFN = checkInByFN;
    }

    public String getCheckInByLN() {
        return checkInByLN;
    }

    public void setCheckInByLN(String checkInByLN) {
        this.checkInByLN = checkInByLN;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getCheckOutByFN() {
        return checkOutByFN;
    }

    public void setCheckOutByFN(String checkOutByFN) {
        this.checkOutByFN = checkOutByFN;
    }

    public String getCheckOutByLN() {
        return checkOutByLN;
    }

    public void setCheckOutByLN(String checkOutByLN) {
        this.checkOutByLN = checkOutByLN;
    }

    public boolean isUCodeUsed() {
        return UCodeUsed;
    }

    public void setUCodeUsed(boolean UCodeUsed) {
        this.UCodeUsed = UCodeUsed;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}