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
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.speech.SpeechError;
import com.iflytek.ui.SynthesizerDialog;
import com.iflytek.ui.SynthesizerDialogListener;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EnvirActivity extends Activity {

    private Button en_back;
    private TextView wendu;
    private TextView shidu;
    private TextView guangzhao;
    private SocketAsyn socketAsyn;
    private BufferedWriter writer = null;
    private EditText E;
    String msg;
    String array1 = "N/A";
    String array2 = "N/A";
    String array3 = "N/A";
    private Button btn;
    Vibrator vibrator;
    PendingIntent pendingIntent;


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
        setContentView(R.layout.activity_envir);

        wendu = (TextView) findViewById(R.id.at_en_bt2);
        shidu = (TextView) findViewById(R.id.at_en_bt3);
        guangzhao = (TextView) findViewById(R.id.at_en_bt4);
        btn = (Button) findViewById(R.id.at_en_bt5);
        E = (EditText) findViewById(R.id.E1);

        connet();

        Intent resultIntent = new Intent(this, BaojingActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        btn.setOnClickListener(new Button.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                sy();
            }
        });

        en_back = (Button)findViewById(R.id.en_back);
        en_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (socketAsyn != null && socketAsyn.getStatus() == AsyncTask.Status.RUNNING) {
                        socketAsyn.cancel(true); // 如果SocketAsyn还在运行，则先取消它，然后执行关闭activity代码
                    }
                    writer.close();
                } catch (Exception e) {
                    e.getStackTrace();
                }
                startActivity(new Intent(EnvirActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    public void connet() {
            socketAsyn = new SocketAsyn();
            socketAsyn.execute();
            mHandler.postDelayed(mRunnable, 5000);
    }

    protected void Data() {
        wendu.setText(array1);
        shidu.setText(array2);
        guangzhao.setText(array3);
    }
    public class SocketAsyn extends AsyncTask<Object, Object, String> {
        @Override
        protected String doInBackground(Object... params) {
            if (isCancelled())
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

    private void parseEasyJson(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String i = jsonObject.getString("device_id");
            int id = Integer.parseInt(i);
            String value = jsonObject.getString("device_value");
            switch (id) {
                case 1:
                    array1 = value;
                    break;
                case 2:
                    array2 = value;
                    break;
                case 3:
                    array3 = value;
                    break;
                case 8:
                    int yandu = Integer.parseInt(value);
                    if (yandu > 5000) {

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(EnvirActivity.this)
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

                    }
                    break;
                case 7:
                    int qidu = Integer.parseInt(value);
                    if (qidu > 5000) {

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(EnvirActivity.this)
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

                    }
                    break;
                case 156:
                    if(value.equals("true")){

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(EnvirActivity.this)
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

                    }
                    break;
                case 151:
                    if(value.equals("true")){

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(EnvirActivity.this)
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

                    }
                    break;
                case 154:
                    if(value.equals("true")){

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(EnvirActivity.this)
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

                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sy() {
        // 这是语言合成部分 同样需要实例化一个SynthesizerDialog ，并输入appid
        SynthesizerDialog syn = new SynthesizerDialog(EnvirActivity.this,
                "appid=50e1b967");
        syn.setListener(new SynthesizerDialogListener() {
            @Override
            public void onEnd(SpeechError arg0) {

            }
        });
        E.setText("温度："+array1 + "℃"+",湿度："+array2 + "% "+" ,光照："+array3+"lux");
        // 根据EditText里的内容实现语音合成
        syn.setText(E.getText().toString(), null);
        syn.show();
    }

    protected void onPause() {
        super.onPause();
        try {
            if (socketAsyn != null && socketAsyn.getStatus() == AsyncTask.Status.RUNNING) {
                socketAsyn.cancel(true); // 如果SocketAsyn还在运行，则先取消它，然后执行关闭activity代码
            }
            writer.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
