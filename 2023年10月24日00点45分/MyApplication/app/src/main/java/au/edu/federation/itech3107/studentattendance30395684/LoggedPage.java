package au.edu.federation.itech3107.studentattendance30395684;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import au.edu.federation.itech3107.studentattendance30395684.fragment.AttendanceFragment;
import au.edu.federation.itech3107.studentattendance30395684.fragment.CourseFragment;
import au.edu.federation.itech3107.studentattendance30395684.fragment.StudentFragment;

public class LoggedPage extends AppCompatActivity  {
    public LinearLayout llStudent,llCourse,llAttendance;
    private ImageView ivStudent,ivCourse,ivAttendance;
    private TextView tvStudent,tvCourse,tvAttendance;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_page);
        initView();
        initEvent();
    }

    private void initEvent() {
        //初始化fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        StudentFragment studentFragment=new StudentFragment();
        fragmentTransaction.replace(R.id.fcv_fragment,studentFragment).commit();
        llStudent.setSelected(true);
        //对导航功能设置
        setNavFunction();

    }

    private void setNavFunction() {
        llStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                StudentFragment studentFragment=new StudentFragment();
                fragmentTransaction.replace(R.id.fcv_fragment,studentFragment).commit();
                ivStudent.setSelected(true);
                ivCourse.setSelected(false);
                ivAttendance.setSelected(false);

            }
        });
        llCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                CourseFragment courseFragment=new CourseFragment();
                fragmentTransaction.replace(R.id.fcv_fragment,courseFragment).commit();
                ivCourse.setSelected(true);
                ivAttendance.setSelected(false);
                ivStudent.setSelected(false);
            }
        });
        llAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                AttendanceFragment attendanceFragment=new AttendanceFragment();
                fragmentTransaction.replace(R.id.fcv_fragment,attendanceFragment).commit();
                ivAttendance.setSelected(true);
                ivCourse.setSelected(false);
                ivStudent.setSelected(false);
            }
        });
    }


    private void initView() {
        llStudent=findViewById(R.id.ll_student);
        llCourse=findViewById(R.id.ll_course);
        llAttendance=findViewById(R.id.ll_attendance);

        ivStudent=findViewById(R.id.iv_student);
        ivCourse=findViewById(R.id.iv_course);
        ivAttendance=findViewById(R.id.iv_attendance);
        tvStudent=findViewById(R.id.tv_student);
        tvCourse=findViewById(R.id.tv_course);
        tvAttendance=findViewById(R.id.tv_attendance);
    }


}