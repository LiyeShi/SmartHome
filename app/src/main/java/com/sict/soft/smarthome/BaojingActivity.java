package com.sict.soft.smarthome;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class BaojingActivity extends Activity {

    private Button bj_back;
    private TextView yan;
    private TextView qiti;
    private TextView huo;
    private TextView hongwai;
    private TextView shengyin;
    private SocketAsyn socketAsyn;
    private BufferedWriter writer = null;
    String msg;
    String array1 = "安全";
    String array2 = "安全";
    String array3 = "安全";
    String array4 = "安全";
    String array5 = "安全";
    PendingIntent pendingIntent;
    Vibrator vibrator;

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        //接受的线程
        @Override
        public void run() {
            mHandler.postDelayed(mRunnable, 500);
            Data();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baojing);

        yan = (TextView)findViewById(R.id.at_en_bt4);
        qiti = (TextView)findViewById(R.id.at_en_bt3);
        huo = (TextView)findViewById(R.id.at_bj_bt5);
        hongwai = (TextView)findViewById(R.id.at_bj_bt1);
        shengyin = (TextView)findViewById(R.id.at_bj_bt2);

        connet();

        Intent resultIntent = new Intent(this, BaojingActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        bj_back = (Button) findViewById(R.id.bj_back);
        bj_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (socketAsyn != null && socketAsyn.getStatus() == AsyncTask.Status.RUNNING)
                    {
                        socketAsyn.cancel(true); // 如果SocketAsyn还在运行，则先取消它，然后执行关闭activity代码
                    }
                    writer.close();
                }catch(Exception e){
                    e.getStackTrace();
                }
                startActivity(new Intent(BaojingActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    public void connet() {
        if (true) {
            socketAsyn = new SocketAsyn();
            socketAsyn.execute();
            mHandler.postDelayed(mRunnable, 5000);
        } else {
            Toast.makeText(BaojingActivity.this, "未连接",Toast.LENGTH_SHORT).show();
        }
    }

    protected void Data() {
        yan.setText(array1);
        qiti.setText(array2);
        huo.setText(array3);
        hongwai.setText(array4);
        shengyin.setText(array5);
    }
    public class SocketAsyn extends AsyncTask<Object, Object, String> {
        @Override
        protected String doInBackground(Object... params) {
            if(isCancelled())
                return null;
            try {
                @SuppressWarnings("resource")
                Socket socket = new Socket(C.chuanIP, 51001);
                writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));
                InputStream inputStream = socket.getInputStream();
                DataInputStream input = new DataInputStream(inputStream);
                byte[] b = new byte[100000];
                while (true) {
                    int length = input.read(b);
                    msg = new String(b, 0, length, "gb2312");
                    parseEasyJson(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

    }


    private void parseEasyJson(String msg){
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String i = jsonObject.getString("device_id");
            int id=Integer.parseInt(i);
            String value = jsonObject.getString("device_value");
            switch(id){
                case 8:
                    array1 = value;
                    int yandu = Integer.parseInt(value);
                    if (yandu > 5000) {
                        yan.setTextColor(getResources().getColor(R.color.red));

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(BaojingActivity.this)
                                .setSmallIcon(R.drawable.yanz)
                                .setTicker("警报:烟雾过大！")
                                .setContentTitle("烟灾提醒：")
                                .setContentText("请立即核实是否有烟灾隐患！")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .build();
                        notify.flags = Notification.FLAG_AUTO_CANCEL;
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(1, notify);

                    }else{
                        yan.setTextColor(getResources().getColor(R.color.yanwu));
                    }
                    break;
                case 7:
                    array2 = value;
                    int qidu = Integer.parseInt(value);
                    if (qidu > 5000) {
                        qiti.setTextColor(getResources().getColor(R.color.red));
                        Vibrator vibrator;
                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(BaojingActivity.this)
                                .setSmallIcon(R.drawable.qiz)
                                .setTicker("警报:有可燃气体！")
                                .setContentTitle("可燃气体提醒：")
                                .setContentText("请立即核实可燃气体浓度是否超标！")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .build();
                        notify.flags = Notification.FLAG_AUTO_CANCEL;
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(1, notify);

                    }else{
                        qiti.setTextColor(getResources().getColor(R.color.qiti));
                    }
                    break;
                case 156:
                    if(value.equals("true")){
                        array3 = "危险";
                        huo.setTextColor(getResources().getColor(R.color.red));

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(BaojingActivity.this)
                                .setSmallIcon(R.drawable.huoz)
                                .setTicker("警报:有火焰！")
                                .setContentTitle("火灾提醒：")
                                .setContentText("请立即核实是否有火灾隐患！")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .build();
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(1, notify);

                    }else if(value.equals("false")){
                        array3 = "安全";
                        huo.setTextColor(getResources().getColor(R.color.huoyan));
                    }
                    break;
                case 151:
                    if(value.equals("true")){
                        array4 = "危险";
                        hongwai.setTextColor(getResources().getColor(R.color.red));

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(BaojingActivity.this)
                                .setSmallIcon(R.drawable.hongz)
                                .setTicker("警报:有人经过！")
                                .setContentTitle("红外提醒：")
                                .setContentText("请立即核实是否有人经过！")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .build();
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(1, notify);

                    }else if(value.equals("false")){
                        array4 = "安全";
                        hongwai.setTextColor(getResources().getColor(R.color.hongwai));
                    }
                    break;
                case 154:
                    if(value.equals("true")){
                        array5 = "危险";
                        shengyin.setTextColor(getResources().getColor(R.color.red));

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(BaojingActivity.this)
                                .setSmallIcon(R.drawable.shengz)
                                .setTicker("警报:声音过大！")
                                .setContentTitle("噪音提醒：")
                                .setContentText("请立即核实是否有噪音源！")
                                .setWhen(System.currentTimeMillis())
                                .setContentIntent(pendingIntent)
                                .build();
                        notify.flags |= Notification.FLAG_AUTO_CANCEL;
                        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(1, notify);

                    }else if(value.equals("false")){
                        array5 = "安全";
                        shengyin.setTextColor(getResources().getColor(R.color.shengyin));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
