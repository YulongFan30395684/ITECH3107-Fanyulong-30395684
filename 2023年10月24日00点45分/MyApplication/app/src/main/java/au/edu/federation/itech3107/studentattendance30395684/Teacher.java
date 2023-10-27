package au.edu.federation.itech3107.studentattendance30395684;

public class Teacher {

    private String teacher_Number;
    private String teacher_password;



    public String getTeacher_Number() {
        return teacher_Number;
    }

    public void setTeacher_Number(String teacher_Number) {
        this.teacher_Number = teacher_Number;
    }

    public String getTeacher_password() {
        return teacher_password;
    }

    public void setTeacher_password(String teacher_password) {
        this.teacher_password = teacher_password;
    }

    @Override
    public String toString() {
        return "Teacher{" +

                ", teacher_name='" + teacher_Number + '\'' +
                ", teacher_password='" + teacher_password + '\'' +
                '}';
    }
}
