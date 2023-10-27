package au.edu.federation.itech3107.studentattendance30395684;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber, etPassword,etConfirmPassword;
    private Button btnConform;
    private RadioGroup choose;
    private RadioButton radRegister, radLog;
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        setConfirm();
    }

    private void setConfirm() {
        radLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etConfirmPassword.setVisibility(View.INVISIBLE);
            }
        });
        radRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etConfirmPassword.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initView() {
        etNumber = findViewById(R.id.edTeacherNumber);
        etPassword = findViewById(R.id.edTeacherPassword);
        etConfirmPassword=findViewById(R.id.edTeacherPassword2);
        btnConform = findViewById(R.id.btnConform);
        choose=findViewById(R.id.radioGroup);
        radLog = findViewById(R.id.radLog);
        radRegister = findViewById(R.id.radRegister);
    }

    public void insert() {

        String teaNumber = etNumber.getText().toString().trim();
        String teaPassword = etPassword.getText().toString().trim();
        String tea_Confirm_Password=etConfirmPassword.getText().toString().trim();

        if(teaNumber.equals("")||teaPassword.equals("")){
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }else {
            if (tea_Confirm_Password.equals(teaPassword)){
                Teacher teacher = new Teacher();
                teacher.setTeacher_Number(teaNumber);
                teacher.setTeacher_password(teaPassword);

                //插入数据库中
                long rowid = mySQLiteOpenHelper.insertTeacherData(teacher);
                if (rowid != -1) {
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();

                }
            }
            else{

                Toast.makeText(this, "两次的密码不一致", Toast.LENGTH_SHORT).show();

            }
        }


    }

    private void login() {

        String teaNumber = etNumber.getText().toString().trim();
        String teaPassword = etPassword.getText().toString().trim();
        if(teaNumber.equals("")||teaPassword.equals("")){
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }
        else{
            Teacher teacher = new Teacher();
            teacher.setTeacher_Number(teaNumber);
            teacher.setTeacher_password(teaPassword);

            boolean check_result = mySQLiteOpenHelper.checkTeacherData(teacher);
            if (check_result !=false) {
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                logSuccessfully();

            } else {
                Toast.makeText(this, "用户名或密码错误，登陆失败", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void confirm(View view) {
        if (radRegister.isChecked()) {

            insert();
        }
        if (radLog.isChecked()) {

            login();
//            mySQLiteOpenHelper.insertInitCourse();
        }

    }
    public void logSuccessfully(){
        Intent intent = new Intent(MainActivity.this, LoggedPage.class);
        startActivity(intent);
    }


}