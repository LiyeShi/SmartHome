package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登录页面
 * @author wangli
 *
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText activity_login_et_name;
    private EditText activity_login_et_pswd;
    private Button activity_login_btn_register;
    private Button activity_login_btn_login;
    private CheckBox activity_login_cb_keep_password;
    private String TAG="LoginActivity";

    private Button laoren;
    private Button jiaren;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ExitApplication.getInstance().addActivity(this);
        initView();
        //【记住密码】功能
        //加载页面时，通过读取SharePreferences文件，确认“记住密码”多选框是否应该处于被选中状态，以及被记住的用户名密码是什么
        SharedPreferences preferences = getSharedPreferences("MusicRecord",MODE_PRIVATE);
        //如果从preference中读到，是“记住密码”的状态，则从preference里读取用户名密码并显示在文本框里
        //关于preference.getBoolean("", false)：参数1，key，根据key读取preference里的信息；参数2，如果key所对应的变量不存在的话，默认返回什么值
        if(preferences.getBoolean("keep",false)){
            //设置用户名、密码输入框中，填入preference中存储的用户名、密码
            activity_login_et_name.setText(preferences.getString("name",null));
            activity_login_et_pswd.setText(preferences.getString("pswd",null));
            //设置“记住密码”多选框为选中状态
            activity_login_cb_keep_password.setChecked(true);
        }

    }

    private void initView() {
        activity_login_et_name = (EditText) findViewById(R.id.activity_login_et_name);
        activity_login_et_pswd = (EditText) findViewById(R.id.activity_login_et_pswd);
        activity_login_btn_register = (Button) findViewById(R.id.activity_login_btn_register);
        activity_login_btn_login = (Button) findViewById(R.id.activity_login_btn_login);
        activity_login_cb_keep_password = (CheckBox) findViewById(R.id.activity_login_cb_keep_password);
        activity_login_btn_register.setOnClickListener(this);
        activity_login_btn_login.setOnClickListener(this);
//        laoren = (Button) findViewById(R.id.laoren);
//        jiaren = (Button) findViewById(R.id.jiaren);
//        laoren.setOnClickListener(this);
//        jiaren.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btn_register:
                //注意：本页面可能是从不同页面跳转过来的
                //如果为首次运行该页面，则两个输入框显示为空；如果为注册页面跳转回来的，则两个输入框显示注册页面传回来的用户名、密码
                //因为要确认是否是从注册页面跳回，需要接收result值，此时使用startActivityForResult方法
                Intent it_register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(it_register,0);
                break;
            case R.id.activity_login_btn_login:
                //【判断该用户名密码是否在数据库中存在】
                // 获取用户输入的用户名、密码
                String username = activity_login_et_name.getText().toString();
                String password = activity_login_et_pswd.getText().toString();
                //根据用户名从数据库中取出其密码

                try{
                    //打开或创建数据库musicplayer.db3
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(LoginActivity.this.getFilesDir()+"/musicplayer.db3", null);
                    //打开或创建数据库中的数据表users
                    db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");

                    //查询
                    Cursor cursor = db.rawQuery("select pswd from users where name=?", new String[]{username});
                    String pswd = null;
                    while(cursor.moveToNext()){
                        pswd = cursor.getString(0);
                    }

                    //如果password正确，则登陆；否则弹出Toast进行提示
                    if(pswd!=null&&pswd.equals(password)){
                        if(count == 1){
                            Intent it_home = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(it_home);
                        }else if(count == 2){
                            Toast.makeText(LoginActivity.this, "进入家人端", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    }
                    //关闭数据库
                    db.close();
                }catch(Exception e){
                    Log.i(TAG,"用户名密码读取错误");
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }

                //【记住密码】功能
                SharedPreferences preferences = getSharedPreferences("MusicRecord",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                if(activity_login_cb_keep_password.isChecked()){
                    editor.putBoolean("keep",true);
                    editor.putString("name",username);
                    editor.putString("pswd",password);
                    editor.commit();
                }else{
                    editor.putBoolean("keep",false);
                    editor.commit();
                }
                break;
//            case R.id.laoren:
//                count = 1;
//                laoren.setBackgroundColor(getResources().getColor(R.color.xuan));
//                jiaren.setBackgroundColor(getResources().getColor(R.color.white));
//                break;
//            case R.id.jiaren:
//                count = 2;
//                jiaren.setBackgroundColor(getResources().getColor(R.color.xuan));
//                laoren.setBackgroundColor(getResources().getColor(R.color.white));
//                break;
        }
    }

//    private void submit() {
//        // validate
//        String name = activity_login_et_name.getText().toString().trim();
//        String pswd = activity_login_et_pswd.getText().toString().trim();
//        Log.i(TAG, "submit: true");
//        if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(pswd)){
//            Toast.makeText(this,"请输入用户名和密码",Toast.LENGTH_LONG).show();
//        }
//        else if (TextUtils.isEmpty(name)) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        else if (TextUtils.isEmpty(pswd)) {
//            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // TODO validate success, do something
//    }

    /*
	 * 该方法主要是接收从其他页面返回来的数据
	 * requestCode：请求码 —— 用于区分到底是从哪一个页面来的
	 * resultCode：结果码 —— 用于区分到底是从哪一个页面返回来的
	 * data：数据，Intent类型。对应的是其他页面返回来的数据
	 *
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果是从注册页面返回
        if(resultCode ==Activity.RESULT_OK){
            if(data!=null) {
                //填充两个输入框为注册页面传回来的用户名、密码
                activity_login_et_name.setText(data.getStringExtra("name"));
                activity_login_et_pswd.setText(data.getStringExtra("pswd"));
            }
        }

    }
}
