package au.edu.federation.itech3107.studentattendance30395684;

public class Student {
    private String student_number;
    private String student_name;

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Student(String student_number, String student_name) {
        this.student_number = student_number;
        this.student_name = student_name;
    }
}
