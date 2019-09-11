package com.sict.soft.smarthome;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class LaorenActivity extends Activity implements View.OnClickListener{

    private Button lr_back;
    private TextView at_lr_bt1;
    private TextView at_lr_bt2;
    private TextView at_lr_bt3;
    private TextView at_lr_bt4;
    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laoren);

        init();

    }


    public void init(){
        lr_back = (Button)findViewById(R.id.lr_back);
        at_lr_bt1 = (TextView)findViewById(R.id.at_lr_bt1);
        at_lr_bt2 = (TextView)findViewById(R.id.at_lr_bt2);
        at_lr_bt3 = (TextView)findViewById(R.id.at_lr_bt3);
        at_lr_bt4 = (TextView)findViewById(R.id.at_lr_bt4);

        lr_back.setOnClickListener(this);
        at_lr_bt1.setOnClickListener(this);
        at_lr_bt2.setOnClickListener(this);
        at_lr_bt3.setOnClickListener(this);
        at_lr_bt4.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.at_lr_bt2:
                //获取闹钟的管理者
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                //获取当前系统的时间
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                //弹出时间对话框
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        //时间一到，发送广播（闹钟响了）
                        Intent intent = new Intent();
                        intent.setAction("com.example.naozhong.RING");
                        //将来时态的跳转
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(LaorenActivity.this, 0x101, intent, 0);
                        //设置闹钟
                        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
                break;
            case R.id.lr_back:
                startActivity(new Intent(LaorenActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.at_lr_bt1:
                startActivity(new Intent(LaorenActivity.this, JiarenActivity.class));
                break;
            case R.id.at_lr_bt3:
                Intent intent = new Intent(LaorenActivity.this, LifeActivity.class);
                intent.putExtra("PATH","/connhealth/life");
                startActivity(intent);
                break;
            case R.id.at_lr_bt4:
                startActivity(new Intent(LaorenActivity.this, XinlvActivity.class));
                break;
        }
    }
}