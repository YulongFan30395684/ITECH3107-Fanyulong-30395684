package au.edu.federation.itech3107.studentattendance30395684;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AttendanceList extends AppCompatActivity {

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private List<String> studentAttendanceArrayList;
    private ArrayAdapter<String> studentAttendanceAdapter;
    SQLiteDatabase db ;
    View view;
    String nowCourse;

    int nowWeek;

    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        db= mySQLiteOpenHelper.getWritableDatabase();
        studentAttendanceArrayList=new ArrayList<>();
        mListview=findViewById(R.id.AttendListView);



        showNowState();
        readAttendedStudent(nowCourse,nowWeek);

    }
    //展示当前星期和课程的标题
    private void showNowState() {
        TextView nowState=findViewById(R.id.nowState);

        Intent intent =getIntent();

        nowCourse = intent.getStringExtra("nowCourseName");

        nowWeek = intent.getIntExtra("nowWeek",0);

        nowState.setText("The list of those who attended course "+nowCourse+" in Week "+nowWeek+" is as follows" );
    }
    private void readAttendedStudent(String course,int week) {
        studentAttendanceArrayList=mySQLiteOpenHelper.showAttendedStudents(course,week);

        studentAttendanceAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,studentAttendanceArrayList);

        mListview.setAdapter(studentAttendanceAdapter);

        if (studentAttendanceArrayList.size()==0){
            Toast.makeText(this, "No students were selected for attendance", Toast.LENGTH_SHORT).show();

        }

    }
}