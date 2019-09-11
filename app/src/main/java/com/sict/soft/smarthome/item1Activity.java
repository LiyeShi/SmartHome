package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


//个人中心
public class item1Activity extends Activity {

    private ImageView item1_title_imv;
    SQLiteDatabase db;
    private TextView name;
    private TextView sex;
    private TextView age;
    private TextView tel;
    private TextView adress;
    private Button updata;
    private Button no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item1);

        item1_title_imv = (ImageView) findViewById(R.id.item1_title_imv);
        item1_title_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(item1Activity.this, MainActivity.class));
                finish();
            }
        });

        // 创建 “数据库 - mydata” 和 “数据表 - person”
        db= SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"mydata",null);
        db.execSQL("create table if not exists person(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,sex TEXT,age TEXT,tel TEXT,adress TEXT,six TEXT,seven TEXT,eight TEXT,nine TEXT,ten TEXT)");

        // 绑定按钮
        name = (TextView)findViewById(R.id.name);
        sex = (TextView)findViewById(R.id.sex);
        age = (TextView)findViewById(R.id.age);
        tel = (TextView)findViewById(R.id.tel);
        adress = (TextView)findViewById(R.id.adress);
        updata =(Button)findViewById(R.id.updata);
        no =(Button)findViewById(R.id.no);

        // 查询数据库数据
        Cursor cursor = db.rawQuery("select * from person",null);
        while (cursor.moveToNext()){
            name.setText(cursor.getString(1));
            sex.setText(cursor.getString(2));
            age.setText(cursor.getString(3));
            tel.setText(cursor.getString(4));
            adress.setText(cursor.getString(5));
        }

        updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(item1Activity.this,Gaiitem1Activity.class);
                startActivityForResult(intent, 0);
                db.close();
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(item1Activity.this,MainActivity.class);
                startActivityForResult(intent, 0);
                db.close();
                finish();
            }
        });

    }
}
