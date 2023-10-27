package au.edu.federation.itech3107.studentattendance30395684.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.edu.federation.itech3107.studentattendance30395684.MySQLiteOpenHelper;
import au.edu.federation.itech3107.studentattendance30395684.R;


public class StudentFragment extends Fragment {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    public StudentFragment() {

    }

    public static StudentFragment newInstance(String param1, String param2) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySQLiteOpenHelper=new MySQLiteOpenHelper(getActivity());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_student, container, false);
    }


}