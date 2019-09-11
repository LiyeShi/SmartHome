package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText activity_register_et_name;
    private EditText activity_register_et_pswd1;
    private Button activity_register_btn_register;
    private EditText activity_register_et_pswd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
         submit();
         textWatcher();

    }

    private void textWatcher() {
        final Drawable dr = getResources().getDrawable(R.drawable.a_chumo);
        dr.setBounds(0, 0, 10, 10); //必须设置大小，否则不显示
        //为邮箱输入框设置监听
        activity_register_et_name.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (activity_register_et_name.getText().length() > 0) {
                    if (!activity_register_et_name.getText().toString().matches("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
                        //如果输入的信息不合法，弹出提示
                        activity_register_et_name.setError("请输入正确的邮箱", dr);
                    }
                }

            }
        });

        //密码输入框设置监听
        activity_register_et_pswd1.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (activity_register_et_pswd1.getText().length() > 0) {
                    if (!activity_register_et_pswd1.getText().toString().matches("^\\d{6}$")) {
                        //如果输入的信息不合法，弹出提示
                        activity_register_et_pswd1.setError("密码长度不小于六位", dr);
                    }
                }

            }
        });
    }

    private void initView() {
        activity_register_et_name = (EditText) findViewById(R.id.activity_register_et_name);
        activity_register_et_pswd1 = (EditText) findViewById(R.id.activity_register_et_pswd1);
        activity_register_btn_register = (Button) findViewById(R.id.activity_register_btn_register);
        activity_register_et_pswd2 = (EditText) findViewById(R.id.activity_register_et_pswd2);

        activity_register_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_btn_register:
                String name = activity_register_et_name.getText().toString();
                String pswd = activity_register_et_pswd1.getText().toString();
                String pswd2 = activity_register_et_pswd2.getText().toString();

                try {

                    //【将用户名、密码添加到数据库中】
                    //打开或创建数据库："当前路径/musicplayer.db3"
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(RegisterActivity.this.getFilesDir().toString() + "/musicplayer.db3", null);
                    //在数据库中创建数据表users
                    db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");
                    //判断两次密码是否一致
                    if (pswd.equals(pswd2)&&pswd!=null&&name!=null) {
                        //向表中添加一条用户数据
                        db.execSQL("insert into users(name,pswd) values(?,?)", new String[]{name, pswd});
                        //关闭数据库
                        db.close();

                        //【将新注册的用户名、密码显示在返回的登录页面】

                        //传数据用的intent和bundle
                        Intent data = new Intent();
                        //向intent中塞入key为“name”和“pswd”的数据
                        data.putExtra("name", name);
                        data.putExtra("pswd", pswd);

                        // 第一个参数：传入一个结果码
                        // 第二个参数：回传的数据，一个Intent对象
                        setResult(Activity.RESULT_OK, data);

                        // 关闭页面
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(RegisterActivity.this, "该用户已存在，无需重复注册", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void submit() {
        // validate
        String name = activity_register_et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String pswd1 = activity_register_et_pswd1.getText().toString().trim();
        if (TextUtils.isEmpty(pswd1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }

}

