package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;


public class CourseFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;
    private List<String> courseArrayList;
    private ArrayAdapter<String> courseAdapter;
    SQLiteDatabase db ;
    Button addCourse;
    int nowPosition;

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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id=nowPosition;
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getActivity(), id+"添加学生", Toast.LENGTH_SHORT).show();
                return true;
            case 1:

                mySQLiteOpenHelper.deleteCourse(courseArrayList.get(id));
                Toast.makeText(getActivity(), courseArrayList.get(id), Toast.LENGTH_SHORT).show();
                readCourseName();
                mListview.refreshDrawableState();


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
            }
        });
    }

    private void inputCourse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入课程");
        final EditText inputCourseName = new EditText(getActivity());
        inputCourseName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(inputCourseName);


        builder.setPositiveButton("Conform", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 获取输入框中的内容
                String inputText = inputCourseName.getText().toString();
                mySQLiteOpenHelper.insertCourseData(inputText);
                readCourseName();

                Toast.makeText(getActivity(), "Conform", Toast.LENGTH_SHORT).show();
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
}