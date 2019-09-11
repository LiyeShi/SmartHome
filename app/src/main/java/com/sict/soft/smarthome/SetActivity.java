package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/16.
 */

public class SetActivity extends Activity{

    private Button set_back;

    private TextView shebeiip;
    private TextView fuwuqiip;
    private TextView naoling;
    private TextView lianxiren;
    private Button btn;
    private Button no;
    private SQLiteDatabase db;
    private TextView shequ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        set_back = (Button) findViewById(R.id.set_back);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetActivity.this, MainActivity.class));
                finish();
            }
        });





        shebeiip = (TextView) findViewById(R.id.ShebeiIp);
        fuwuqiip = (TextView) findViewById(R.id.FuwuqiIp);
        naoling = (TextView) findViewById(R.id.Naoling);
        lianxiren = (TextView) findViewById(R.id.Lianxiren);
        btn = (Button) findViewById(R.id.button);
        no = (Button) findViewById(R.id.no);
        shequ=(TextView)findViewById(R.id.shequ) ;

        // 创建 “数据库 - mydata” 和 “数据表 - setting”
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "mydata", null);
        db.execSQL("create table if not exists setting(_id INTEGER PRIMARY KEY AUTOINCREMENT,shebeiip TEXT,fuwuqiip TEXT,naoling TEXT,lianxiren TEXT,five TEXT,six TEXT,seven TEXT,eight TEXT,nine TEXT,ten TEXT)");

        Cursor cursor = db.rawQuery("select * from setting where _id=1", null);
        while (cursor.moveToNext()) {
            shebeiip.setText(cursor.getString(1));
            fuwuqiip.setText(cursor.getString(2));
            naoling.setText(cursor.getString(3));
            lianxiren.setText(cursor.getString(4));
            shequ.setText(cursor.getString(5));
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,GaisetActivity.class);
                db.close();
                startActivity(intent);

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this,MainActivity.class);
                db.close();
                startActivity(intent);

            }
        });


    }
}
