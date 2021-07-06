package contracts;

import data.Student;
import model.AttendanceDto;
import model.CampDto;
import servlets.Attendance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceRetrieve {
    private final List<StudentAttendance> studentAttendance;
    private final List<CampDto> campsOffered;

    public AttendanceRetrieve(Map<Integer, List<Student>> campStudentMap, Map<Integer, AttendanceDto> attendance, List<CampDto> campsOffered) {
        this.studentAttendance = new ArrayList<>();

        campStudentMap
                .keySet()
                .forEach(
                        campId -> this.studentAttendance.addAll(
                                campStudentMap.get(campId)
                                        .stream()
                                        .map(
                                                student -> new StudentAttendance(
                                                        attendance.get(student.getStudentID()),
                                                        campId,
                                                        student
                                                )
                                        )
                                        .collect(Collectors.toList())
                        )
                );

        this.studentAttendance.sort(Comparator.comparing(a -> (a.student.getFirstName() + a.student.getLastName())));
        this.campsOffered = campsOffered;
    }
}
