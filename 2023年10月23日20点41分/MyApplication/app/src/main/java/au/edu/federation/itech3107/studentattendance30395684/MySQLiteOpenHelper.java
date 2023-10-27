package au.edu.federation.itech3107.studentattendance30395684;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="mySQLite.db";
    public static final String TABLE_NAME_TEACHER="teacher";
    public static final String TABLE_NAME_STUDENT="student";
    public static final String TABLE_NAME_COURSE="course";
    public static final String CREAT_TABLE_SQL="create table "+TABLE_NAME_TEACHER+"(id integer primary key autoincrement,name text,password text);";
    public static final String CREAT_STUDENT_TABLE_SQL="create table "+TABLE_NAME_STUDENT+"(id integer primary key autoincrement,number text,name text);";
    public static final String CREAT_COURSE_TABLE_SQL="create table "+TABLE_NAME_COURSE+"(id integer primary key autoincrement,courseName text);";


    public MySQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREAT_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

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

    public void insertInitCourse(){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("courseName","ITECH3106");
        values.put("courseDate1",1);
        values.put("courseDate2",12);
        db.insert(TABLE_NAME_COURSE,null,values);

    }
    public void deleteCourse(String courseName){
        SQLiteDatabase db=getWritableDatabase();
        String string[]={courseName};
        db.delete(TABLE_NAME_COURSE," courseName = ?",string);


    }

}
