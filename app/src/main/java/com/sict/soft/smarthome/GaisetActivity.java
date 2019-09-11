package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class GaisetActivity extends Activity{
    private EditText shebeiip;
    private EditText fuwuqiip;
    private EditText lianxiren;
    private Button btn;
    private SQLiteDatabase db;
    private EditText shequ;
    private String shebei = "";

    private Button gai_set_back;
    private Spinner sp;
    public String naoling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_gai);

        gai_set_back = (Button) findViewById(R.id.gai_set_back);
        gai_set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GaisetActivity.this, SetActivity.class));
                finish();
            }
        });

        sp = (Spinner) findViewById(R.id.spinner1);
        shijian();

        shebeiip = (EditText) findViewById(R.id.ShebeiIp);
        fuwuqiip = (EditText) findViewById(R.id.FuwuqiIp);
        lianxiren = (EditText) findViewById(R.id.Lianxiren);
        btn = (Button) findViewById(R.id.button);
        shequ=(EditText)findViewById(R.id.shequ) ;

        // 创建 “数据库 - mydata” 和 “数据表 - setting”
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "mydata", null);
        db.execSQL("create table if not exists setting(_id INTEGER PRIMARY KEY AUTOINCREMENT,shebeiip TEXT,fuwuqiip TEXT,naoling TEXT,lianxiren TEXT,five TEXT,six TEXT,seven TEXT,eight TEXT,nine TEXT,ten TEXT)");

        Cursor cursor = db.rawQuery("select * from setting where _id=1", null);
        while (cursor.moveToNext()) {
            shebeiip.setText(cursor.getString(1));
            fuwuqiip.setText(cursor.getString(2));
            lianxiren.setText(cursor.getString(4));
            shequ.setText(cursor.getString(5));
        }
        shebei = shebeiip.getText().toString().trim();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shebeiip.getText().toString().trim().equals("") || fuwuqiip.getText().toString().trim().equals("")) {
                    Toast.makeText(GaisetActivity.this, "设备IP 和 服务器IP 必填！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (shebei.toString().trim().equals("")) {
                    db.execSQL("insert into setting(shebeiip,fuwuqiip,naoling,lianxiren,five) values('" + shebeiip.getText().toString().trim() + "','" + fuwuqiip.getText().toString().trim() + "','" +naoling+ "','" + lianxiren.getText().toString().trim() + "','"+ shequ.getText().toString().trim() +"')");
                    Toast.makeText(GaisetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("update setting set 'shebeiip'='" + shebeiip.getText().toString().trim() + "','fuwuqiip'='" + fuwuqiip.getText().toString().trim() + "','naoling'='" + naoling+ "','lianxiren'='" + lianxiren.getText().toString().trim() + "','five'='"+shequ.getText().toString().trim()+"' where _id=1");
                    Toast.makeText(GaisetActivity.this, "更改设置成功！", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(GaisetActivity.this,SetActivity.class);
                db.close();
                startActivity(intent);
                finish();
            }
        });
    }

    public void shijian(){
        List<String> list = new ArrayList<String>();
        list.add("卡农");
        list.add("童话镇");
        list.add("上海三月");
        list.add("问渔涟说");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取Spinner控件的适配器
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                if(position==0){
                    naoling = "卡农";
                }else if(position==1){
                    naoling = "童话镇";
                }else if(position==2){
                    naoling = "上海三月";
                }else if(position==3){
                    naoling = "问渔涟说";
                }
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
