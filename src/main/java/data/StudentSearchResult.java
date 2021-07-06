package data;

public class StudentSearchResult {
    private final Student student;
    private final Parent parent;

    public StudentSearchResult(Student student, Parent parent) {
        this.student = student;
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public Parent getParent() {
        return parent;
    }
}
