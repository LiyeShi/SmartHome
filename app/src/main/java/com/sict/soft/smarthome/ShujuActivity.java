package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShujuActivity extends Activity {

    private Button sj_back;
    private Button at_sj_bt1;
    private Button at_sj_bt2;
    private Button at_sj_bt3;
    private Button at_sj_bt4;
    private Button at_sj_bt5;
    private Button at_sj_bt6;
    private Button at_sj_bt7;
    private Button at_sj_bt8;
    private Button at_sj_bt9;
    private Button at_sj_bt10;
    private Button at_sj_bt11;
    private Button at_sj_bt12;
    private Button at_sj_bt13;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuju);

        sj_back = (Button) findViewById(R.id.sj_back);

        at_sj_bt1 = (Button) findViewById(R.id.at_sj_bt1);
        at_sj_bt2 = (Button) findViewById(R.id.at_sj_bt2);
        at_sj_bt3 = (Button) findViewById(R.id.at_sj_bt3);
        at_sj_bt4 = (Button) findViewById(R.id.at_sj_bt4);
        at_sj_bt5 = (Button) findViewById(R.id.at_sj_bt5);
        at_sj_bt6 = (Button) findViewById(R.id.at_sj_bt6);
        at_sj_bt7 = (Button) findViewById(R.id.at_sj_bt7);
        at_sj_bt8 = (Button) findViewById(R.id.at_sj_bt8);
        at_sj_bt9 = (Button) findViewById(R.id.at_sj_bt9);
        at_sj_bt10 = (Button) findViewById(R.id.at_sj_bt10);
        at_sj_bt11 = (Button) findViewById(R.id.at_sj_bt11);
        at_sj_bt12 = (Button) findViewById(R.id.at_sj_bt12);
        at_sj_bt13 = (Button) findViewById(R.id.at_sj_bt13);

        sj_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(ShujuActivity.this, MainActivity.class));
//                finish();
            }
        });



        at_sj_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =  new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/shidu");
                intent.putExtra("text","湿度");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/guangzhao");
                intent.putExtra("text","光照");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/shengyin");
                intent.putExtra("text","声音");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/huoyan");
                intent.putExtra("text","火焰");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/yanwunongdu");
                intent.putExtra("text","烟雾浓度");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/wendu");
                intent.putExtra("text","温度");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/fengshan");
                intent.putExtra("text","风扇");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/chumoanjian");
                intent.putExtra("text","触摸");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/rentihongwai");
                intent.putExtra("text","人体红外");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/xinlv");
                intent.putExtra("text","心率");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/keranqiti");
                intent.putExtra("text","可燃气体");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/diandeng");
                intent.putExtra("text","台灯");
                startActivity(intent);
//                finish();
            }
        });
        at_sj_bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent =new Intent(ShujuActivity.this, CaxunActivity.class);
                intent.putExtra("PATH","/jiekou/sjcx/fenxi");
                intent.putExtra("text","家庭安全报告");
                startActivity(intent);
//                finish();
            }
        });

    }
}
