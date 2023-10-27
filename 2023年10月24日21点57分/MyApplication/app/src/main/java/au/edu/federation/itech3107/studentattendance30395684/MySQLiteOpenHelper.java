package au.edu.federation.itech3107.studentattendance30395684;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="mySQLite.db";
    public static final String TABLE_NAME_TEACHER="teacher";
    public static final String TABLE_NAME_STUDENT="student";
    public static final String TABLE_NAME_COURSE="course";
    public static final String TABLE_NAME_STUDENT_COURSE="sc";
    public static final String CREAT_TABLE_SQL="create table "+TABLE_NAME_TEACHER+"(id integer primary key autoincrement,name text,password text);";
    public static final String CREAT_STUDENT_TABLE_SQL="create table "+TABLE_NAME_STUDENT+"(number text primary key not null,name text);";
    public static final String CREAT_COURSE_TABLE_SQL="create table "+TABLE_NAME_COURSE+"(id integer primary key autoincrement,courseName text);";
    public static final String CREAT_STUDENT_COURSE_TABLE_SQL="create table "+TABLE_NAME_STUDENT_COURSE+"(id integer primary key autoincrement,courseName text,studentName text," +
            "week1,week2,week3,week4,week5,week6,week7,week8,week9,week10,week11,week12);";



    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 15);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAT_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL(CREAT_STUDENT_TABLE_SQL);
    }
    public long insertTeacherData(Teacher teacher){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("name",teacher.getTeacher_Number());
        values.put("password",teacher.getTeacher_password());
        return db.insert(TABLE_NAME_TEACHER,null,values);
    }
    public long insertCourseData(String courseName){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("courseName",courseName);

        return db.insert(TABLE_NAME_COURSE,null,values);
    }
    public long insertStudentCourse(String courseName,String studentName){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("courseName",courseName);
        values.put("studentName",studentName);


        return db.insert(TABLE_NAME_STUDENT_COURSE,null,values);
    }
    public long insertStudent(Student student){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("number",student.getStudent_number());
        values.put("name",student.getStudent_name());

        return db.insert(TABLE_NAME_STUDENT,null,values);
    }
    //判断输入的数据是否正确可以登录
    public boolean checkTeacherData(Teacher teacher){
        boolean result=false;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_TEACHER, null, "name like ?", new String[]{teacher.getTeacher_Number()}, null, null, null);
        if (cursor!=null){
            while(cursor.moveToNext()){
                String input_name=cursor.getString(1);
                String input_password= cursor.getString(2);
                if (teacher.getTeacher_Number().equals(input_name)&&teacher.getTeacher_password().equals(input_password))
                {
                    result=true;
                }
            }

        }
        return result;

    }
    public void deleteCourse(String courseName){
        SQLiteDatabase db=getWritableDatabase();
        String string[]={courseName};
        db.delete(TABLE_NAME_COURSE," courseName = ?",string);
    }
    public ArrayList<String> getStudentAttendance(String courseName){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME_STUDENT_COURSE, null, "courseName like ?", new String[]{courseName}, null, null, null);
        ArrayList<String> studentInCourseList=new ArrayList<>();
        if (cursor!=null){
            while(cursor.moveToNext()){
                String studentName=cursor.getString(2);
                studentInCourseList.add(studentName);
            }

        }return studentInCourseList;

    }
    //清空所有的表
    public void deleteAllTableData(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME_STUDENT_COURSE);
    }
    //将学生与课程和星期联系起来
    public void setStudentWithWeek(String studentName,String courseName,String nowWeek){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(nowWeek, 1);
        String whereClause = "courseName" + "='"+courseName+"' AND " + "studentName" + "='" + studentName+ "'";
        db.update(TABLE_NAME_STUDENT_COURSE, values, whereClause, null);
    }
    public void showStudentsAttendCourse(){

    }



}
