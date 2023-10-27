package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import java.text.ParseException;
import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;
import au.edu.federation.itech3107.studentattendance30395684.Student;


public class CourseFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private List<String> courseArrayList;
    private ArrayAdapter<String> courseAdapter;
    SQLiteDatabase db ;
    Button addCourse;
    int nowPosition;
    String startTime ="";
    String endTime ="";

    Context context=this.getContext();
    View view;
//    private Context context=getActivity();

    private ListView mListview;


    public CourseFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity());
        db= mySQLiteOpenHelper.getWritableDatabase();
        courseArrayList=new ArrayList<>();

        readCourseName();


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
        //刷新当前课程界面
        private void fresh(){
            courseAdapter.notifyDataSetChanged();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_course, container, false);
        mListview=(ListView) view.findViewById(R.id.lv_course);

        courseAdapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,courseArrayList);

        mListview.setAdapter(courseAdapter);

        addcourse();
        deleteCourse();

        return view;
    }
    //显示对课程点击后的菜单
    private void deleteCourse() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nowPosition=i;
                mListview.showContextMenu();
            }
        });

        mListview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("More Actions...");
                contextMenu.add(1,0,1,"Add student");
                contextMenu.add(1,1,1,"Delete this course");
            }
        });

    }
    //对菜单事件监听
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id=nowPosition;
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getActivity(), "向 "+courseArrayList.get(nowPosition)+" 添加学生", Toast.LENGTH_SHORT).show();
                inputStudentWithCourse();
                return true;
            case 1:

                mySQLiteOpenHelper.deleteCourse(courseArrayList.get(id));
                Toast.makeText(getActivity(), "删除 "+courseArrayList.get(id)+" 成功", Toast.LENGTH_SHORT).show();
                readCourseName();
                fresh();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void addcourse() {
        addCourse=(Button)view.findViewById(R.id.add_course);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCourse();
//                selectCourseStartTime();
            }
        });
    }
    private void inputStudentWithCourse(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("请输入学生");
        final EditText inputStudentNumber = new EditText(getActivity());
        inputStudentNumber.setHint("Please input student number...");

        final EditText inputStudentName = new EditText(getActivity());
        inputStudentName.setHint("please input student name...");
        inputStudentNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        inputStudentName.setInputType(InputType.TYPE_CLASS_TEXT);

        LinearLayout ll=new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(inputStudentNumber);
        ll.addView(inputStudentName);
        builder.setView(ll);

        builder.setPositiveButton("Conform", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入框中的内容

                String inputNumber = inputStudentNumber.getText().toString();
                String inputName=inputStudentName.getText().toString();

                if (inputNumber.equals("")||inputName.equals("")){
                    Toast.makeText(getActivity(), "学生信息不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    mySQLiteOpenHelper.insertStudentCourse(courseArrayList.get(nowPosition),inputName);
                    mySQLiteOpenHelper.insertStudent(new Student(inputNumber,inputName));
                    Toast.makeText(getActivity(), "添加学生成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel add student", Toast.LENGTH_SHORT).show();

                readCourseName();
                // 关闭输入框
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //点击按钮后显示添加课程的输入框并监听点击事件
    private void inputCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入课程");
        final EditText inputCourseName = new EditText(getActivity());
        final TextView startTime=new TextView(getActivity());
        startTime.setText("Set start time");
        startTime.setPadding(10,20,10,10);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCourseStartTime();

            }
        });



        final TextView endTime=new TextView(getActivity());
        endTime.setText("Set start time");
        endTime.setPadding(10,50,10,10);
        inputCourseName.setInputType(InputType.TYPE_CLASS_TEXT);
        inputCourseName.setHint("CourseName...");
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCourseEndTime();
            }
        });

        LinearLayout ll=new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(inputCourseName);
        ll.addView(startTime);
        ll.addView(endTime);
        builder.setView(ll);

        builder.setPositiveButton("Conform", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入框中的内容

                String inputText = inputCourseName.getText().toString();
                if (inputText.length()==0){
                    Toast.makeText(getActivity(), "课程信息不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    isDateCorrect();

                    mySQLiteOpenHelper.insertCourseData(inputText);
                    readCourseName();
                    Toast.makeText(getActivity(), "Conform", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
                mySQLiteOpenHelper.deleteCourse("q");
                readCourseName();
                // 关闭输入框
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private int isDateCorrect(){
        int M1=Integer.parseInt(startTime.substring(5,7));
        int d1=Integer.parseInt(startTime.substring(8,10));

        int M2=Integer.parseInt(endTime.substring(5,7));
        int d2=Integer.parseInt(endTime.substring(8,10));


        if (true){
            return 1;
        }
        else {
            return 0;
        }
    }

    //选择课程开始日期
    private void selectCourseStartTime() {
        String s="";
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog dialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                startTime =(year+ "-" + monthOfYear + "-" + dayOfMonth);
                System.out.println("maopao"+Integer.parseInt(startTime.substring(8,10)));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }
    //选择课程结束日期
    private void selectCourseEndTime() {
        String s="";
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog dialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                endTime =(year+ "-" + monthOfYear + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

}