package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395684.ListAdapter;
import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;


public class AttendanceFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    private List<String> courseArrayList;
    private ArrayAdapter<String> courseAdapter;
    SQLiteDatabase db ;
    Button saveAttendance;
    int nowCoursePosition;
    int nowWeek;
    TextView chosenCourseTextView;
    Spinner weekSpinner;
    ListAdapter adapter;

    View view;

    private ListView courseListview;
    private ListView studentListView;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity());
        db= mySQLiteOpenHelper.getWritableDatabase();
        courseArrayList=new ArrayList<>();

        readCourseName();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_attendance, container, false);

        courseListview=(ListView) view.findViewById(R.id.lv_attendance_course);
        chosenCourseTextView =(TextView)view.findViewById(R.id.ChosenCourse);
        studentListView=(ListView)view.findViewById(R.id.lv_attendance_student);

        courseAdapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,courseArrayList);
        weekSpinner=(Spinner)view.findViewById(R.id.sp_week);
        courseListview.setAdapter(courseAdapter);
        chooseCourse();
        selectWeek();
        initData();
        return view;
    }
    private void readCourseName() {
        Cursor cursor = db.query("course",null,null,null,null,null,null);
        courseArrayList.clear();
        if (cursor.moveToNext()) {
            do {
                String name = cursor.getString(1);
                courseArrayList.add(name);
            } while (cursor.moveToNext());
        }

    }
    private void fresh(){
        courseAdapter.notifyDataSetChanged();
    }
    //获取当前选择的课程
    private void chooseCourse(){
        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nowCoursePosition=i;
                chosenCourseTextView.setText(courseArrayList.get(nowCoursePosition)+" be selected in week "+nowWeek);
            }
        });

    }
    //获取当前选择的week
    private void selectWeek(){
        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("maopaoweek "+(i+1));
                nowWeek=i+1;
                chosenCourseTextView.setText(courseArrayList.get(nowCoursePosition)+" be selected in week"+(i+1));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
    }

    private void getAttendance(){

    }
    private void initData() {
        // 默认显示的数据
        List<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("王五");


        adapter = new ListAdapter(getActivity());
        adapter.setData(list);
        studentListView.setAdapter(adapter);
    }

}