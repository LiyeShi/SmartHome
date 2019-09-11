package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Created by lenovo on 2017/5/4.
 */
public class NetActivity extends Activity {
    private Button search_back_imv;
    private Button at_sc_bt1;
    private Button at_sc_bt2;
    private Button at_sc_bt3;
    private Button at_sc_bt4;
    private Button at_sc_bt5;
    private Button at_sc_bt6;
    private Button at_sc_bt7;
    private Button at_sc_bt8;
    private Button at_sc_bt9;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        search_back_imv = (Button) findViewById(R.id.search_back_imv);
        search_back_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, MainActivity.class));
                finish();
            }
        });

        //百度
        at_sc_bt1= (Button) findViewById(R.id.at_sc_bt1);
        at_sc_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, BaiduActivity.class));

            }
        });
        //网易
        at_sc_bt2= (Button) findViewById(R.id.at_sc_bt2);
        at_sc_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, WangyiActivity.class));
            }
        });

        //唯品会
        at_sc_bt3= (Button) findViewById(R.id.at_sc_bt3);
        at_sc_bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, VIPActivity.class));
            }
        });

        //天猫
        at_sc_bt4= (Button) findViewById(R.id.at_sc_bt4);
        at_sc_bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, TaobaoActivity.class));
            }
        });
        //亚马逊
        at_sc_bt5= (Button) findViewById(R.id.at_sc_bt5);
        at_sc_bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, AmazonActivity.class));
            }
        });

        //去哪儿
        at_sc_bt6= (Button) findViewById(R.id.at_sc_bt6);
        at_sc_bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, QunaerActivity.class));
            }
        });

        //聚美
        at_sc_bt7= (Button) findViewById(R.id.at_sc_bt7);
        at_sc_bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, JumeiActivity.class));
            }
        });


        //京东
        at_sc_bt8= (Button) findViewById(R.id.at_sc_bt8);
        at_sc_bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, JingdongActivity.class));
            }
        });
        //美团
        at_sc_bt9= (Button) findViewById(R.id.at_sc_bt9);
        at_sc_bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NetActivity.this, MeituanActivity.class));
            }
        });




    }


}




