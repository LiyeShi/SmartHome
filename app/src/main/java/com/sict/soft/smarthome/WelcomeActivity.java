package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeActivity extends Activity {

    private boolean isFirstIn = false;
    private static final int TIME = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 跳入主界面
                case GO_HOME:
                    goHome();
                    break;
                // 跳入引导页
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init(){
        SharedPreferences preferences=getSharedPreferences("Welcome",MODE_PRIVATE);
        isFirstIn=preferences.getBoolean("isFirstIn",true);
        if(!isFirstIn){
            mHandler.sendEmptyMessageDelayed(GO_HOME,TIME);
        }else{
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,TIME);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isFirstIn",false);
            editor.commit();
        }
    }

    private void goHome(){
        Intent i=new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
    private void goGuide(){
        Intent i=new Intent(WelcomeActivity.this,Guide.class);
        startActivity(i);
        finish();
    }
}
