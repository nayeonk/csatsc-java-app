package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceDto {
    private final int id;
    private int campOfferedId;
    private int studentId;
    //generate by LocalDate obj = LocalDate.now();
    private LocalDate date;
    private int checkedInBy;
    //generate by LocalDateTime obj = LocalDateTime.now();
    private LocalDateTime checkedInTime;
    private int checkedOutBy;
    private LocalDateTime checkedOutTime;
    private boolean UCodeUsed;

    public AttendanceDto(int id, int campOfferedId, int studentId, LocalDate date, int checkedInBy, LocalDateTime checkedInTime, int checkedOutBy, LocalDateTime checkedOutTime, boolean UCodeUsed) {
        this.id = id;
        this.campOfferedId = campOfferedId;
        this.studentId = studentId;
        this.date = date;
        this.checkedInBy = checkedInBy;
        this.checkedInTime = checkedInTime;
        this.checkedOutBy = checkedOutBy;
        this.checkedOutTime = checkedOutTime;
        this.UCodeUsed = UCodeUsed;
    }

    public int getCampOfferedId() {
        return campOfferedId;
    }

    public void setCampOfferedId(int campOfferedId) {
        this.campOfferedId = campOfferedId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCheckedInBy() {
        return checkedInBy;
    }

    public void setCheckedInBy(int checkedInBy) {
        this.checkedInBy = checkedInBy;
    }

    public LocalDateTime getCheckedInTime() {
        return checkedInTime;
    }

    public void setCheckedInTime(LocalDateTime checkedInTime) {
        this.checkedInTime = checkedInTime;
    }

    public int getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(int checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }

    public LocalDateTime getCheckedOutTime() {
        return checkedOutTime;
    }

    public void setCheckedOutTime(LocalDateTime checkedOutTime) {
        this.checkedOutTime = checkedOutTime;
    }

    public boolean isUCodeUsed() {
        return UCodeUsed;
    }

    public void setUCodeUsed(boolean UCodeUsed) {
        this.UCodeUsed = UCodeUsed;
    }
}