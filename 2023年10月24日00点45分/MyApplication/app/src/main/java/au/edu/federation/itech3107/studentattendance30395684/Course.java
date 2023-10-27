package au.edu.federation.itech3107.studentattendance30395684;

public class Course {

    private String courseName;
    private Integer courseDate1;
    private Integer courseDate2;

    public Course(String courseName, Integer courseDate1, Integer courseDate2) {
        this.courseName = courseName;
        this.courseDate1 = courseDate1;
        this.courseDate2 = courseDate2;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseDate1() {
        return courseDate1;
    }

    public void setCourseDate1(Integer courseDate1) {
        this.courseDate1 = courseDate1;
    }

    public Integer getCourseDate2() {
        return courseDate2;
    }

    public void setCourseDate2(Integer courseDate2) {
        this.courseDate2 = courseDate2;
    }
}
