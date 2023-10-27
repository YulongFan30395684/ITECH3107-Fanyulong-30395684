package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;


public class StudentFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private List<String> studentArrayList;
    private ArrayAdapter<String> studentAdapter;
    SQLiteDatabase db ;
    View view;

    int nowPosition;

    private ListView mListview;

    public StudentFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity());
        db= mySQLiteOpenHelper.getWritableDatabase();
        studentArrayList=new ArrayList<>();

        readAllStudent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view=inflater.inflate(R.layout.fragment_student, container, false);
        mListview=(ListView) view.findViewById(R.id.lv_student);
        studentAdapter=new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,studentArrayList);

        mListview.setAdapter(studentAdapter);
        return view;
    }

    private void readAllStudent() {
        Cursor cursor = db.query("student",null,null,null,null,null,null);
        studentArrayList.clear();
        if (cursor.moveToNext()) {
            do {
                String name = cursor.getString(1);
                studentArrayList.add(name);
            } while (cursor.moveToNext());
        }

    }
    private void fresh(){
        studentAdapter.notifyDataSetChanged();
    }


}