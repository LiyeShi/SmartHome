package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Gaiitem1Activity extends Activity{

    private ImageView gaiitem1_title_imv;
    SQLiteDatabase db;
    private EditText name;
    private EditText age;
    private EditText tel;
    private EditText adress;
    private Button updataok;
    private String strname="";
    private RadioGroup rg;
    private RadioButton m;
    private RadioButton w;
    private String xingbie="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item1_gai);

        gaiitem1_title_imv = (ImageView) findViewById(R.id.gaiitem1_title_imv);
        gaiitem1_title_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Gaiitem1Activity.this, item1Activity.class));
                finish();
            }
        });

        rg=(RadioGroup)findViewById(R.id.radiogroup);
        w=(RadioButton)findViewById(R.id.nv);
        m=(RadioButton)findViewById(R.id.nan);
        name=(EditText)findViewById(R.id.name

        );
        age=(EditText)findViewById(R.id.age);
        tel=(EditText)findViewById(R.id.tel

        );
        adress=(EditText)findViewById(R.id.adress);
        updataok = (Button)findViewById(R.id.ok);

        db=SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"mydata",null);

        Cursor cursor = db.rawQuery("select * from person",null);

        while (cursor.moveToNext()){
            strname=cursor.getString(1);
            name.setText(strname);
            String s=cursor.getString(2).toString().trim();
            if (s.equals("男")){
                m.setChecked(true);
                w.setChecked(false);
            }else{
                w.setChecked(true);
                m.setChecked(false);
            }
            age.setText(cursor.getString(3));
            tel.setText(cursor.getString(4));
            adress.setText(cursor.getString(5));
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.nan:xingbie="男";break;
                    case R.id.nv:xingbie="女";break;
                    default:break;
                }
            }
        });

        updataok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().trim().equals("")){
                    Toast.makeText(Gaiitem1Activity.this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strname.trim().equals("")){
                    db.execSQL("insert into person(name,sex,age,tel,adress) values('"+name.getText().toString().trim()+"','"+ xingbie +"','"+age.getText().toString().trim()+"','"+tel.getText().toString().trim()+"','"+adress.getText().toString().trim()+"')");
                    Toast.makeText(Gaiitem1Activity.this, "资料添加成功！", Toast.LENGTH_SHORT).show();
                }else{
                    db.execSQL("update person set 'name'='"+name.getText().toString().trim()+"','sex'='"+ xingbie +"','age'='"+age.getText().toString().trim()+"','tel'='"+tel.getText().toString().trim()+"','adress'='"+adress.getText().toString().trim()+"' where _id=1");
                    Toast.makeText(Gaiitem1Activity.this, "资料更新成功！", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.setClass(Gaiitem1Activity.this,item1Activity.class);
                startActivityForResult(intent, 0);
                db.close();
                finish();
            }
        });
    }
}
