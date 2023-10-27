package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;


public class AttendanceFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    private List<String> courseArrayList;
    private ArrayAdapter<String> courseAdapter;
    SQLiteDatabase db ;
    Button saveAttendance;
    int nowCoursePosition;
    TextView chosenCourse;

    View view;

    private ListView courseListview;

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
        chosenCourse=(TextView)view.findViewById(R.id.ChosenCourse);
        courseAdapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,courseArrayList);
        courseListview.setAdapter(courseAdapter);
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
    private void chooseCourse(){
        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nowCoursePosition=i;
                chosenCourse.setText(courseArrayList.get(nowCoursePosition));
            }
        });

    }
}