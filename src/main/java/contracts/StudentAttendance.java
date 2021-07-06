package contracts;

import data.Student;
import model.AttendanceDto;

public class StudentAttendance {
    public AttendanceDto attendance;
    public int campId;
    public Student student;
    

    StudentAttendance(AttendanceDto attendance, int campId, Student student) {
        this.attendance = attendance;
        this.campId = campId;
        this.student = student;
    }
}
