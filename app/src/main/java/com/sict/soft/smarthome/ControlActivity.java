package com.sict.soft.smarthome;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.iflytek.ui.SynthesizerDialog;
import com.iflytek.ui.SynthesizerDialogListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import static com.sict.soft.smarthome.R.id.at_co_bt1;
import static com.sict.soft.smarthome.R.id.at_co_bt3;

public class ControlActivity extends Activity {

    private Button co_back;
    private TextView mo;
    private Button deng,shan;
    private SocketAsyn socketAsyn;
    private BufferedReader in = null;
    private BufferedWriter writer = null;
    String msg;
    String array1;
    String array2;
    String array3="N/A";
    int a1=0;
    int a2=0;
    private EditText E;
    private Button ting;
    private RecognizerDialog rd;
    private Button shuo;
    protected static final String TAG = "ThirdActivity";
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
        setContentView(R.layout.activity_control);

        mo = (TextView)findViewById(at_co_bt3);
        deng = (Button)findViewById(at_co_bt1);
        shan = (Button)findViewById(R.id.at_co_bt2);
        ting = (Button) findViewById(R.id.at_co_bt6);
        shuo = (Button) findViewById(R.id.at_co_bt5);
        E = (EditText) findViewById(R.id.E2);

        Intent resultIntent = new Intent(this, BaojingActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            connet();
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            shuo.setOnClickListener(new Button.OnClickListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {

                    showReconigizerDialog();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            ting.setOnClickListener(new Button.OnClickListener() {

                @SuppressWarnings("deprecation")
                @Override
                public void onClick(View v) {
                    sy();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

        co_back = (Button) findViewById(R.id.co_back);

        co_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if (socketAsyn != null && socketAsyn.getStatus() == AsyncTask.Status.RUNNING)
                    {
                        socketAsyn.cancel(true); // 如果SocketAsyn还在运行，则先取消它，然后执行关闭activity代码
                    }
                    in.close();
                    writer.close();
                }catch(Exception e){
                    e.getStackTrace();
                }
                startActivity(new Intent(ControlActivity.this, MainActivity.class));
                 finish();
            }
        });
        deng.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(a1==1){

                        guandeng();
                    }else if(a1==2){
                        kaideng();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        shan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(a2==1){
                        guanshan();
                    }else if(a2==2){
                        kaishan();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void connet() {
        if (true) {
            socketAsyn = new SocketAsyn();
            socketAsyn.execute();
            mHandler.postDelayed(mRunnable, 2000);
        } else {
            Toast.makeText(ControlActivity.this, "未连接",Toast.LENGTH_SHORT).show();
        }
    }

    protected void Data() {
        mo.setText(array3);
    }

    public class SocketAsyn extends AsyncTask<Object, Object, String> {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected String doInBackground(Object... params) {
            if(isCancelled())
                return null;
            try {
                @SuppressWarnings("resource")
                Socket socket = new Socket(C.chuanIP, 51001);
                writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));
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

    public void kaideng() {

        String a = "{\"args\":{\"device_value\":\"true\",\"device_type\":24,\"device_id\":101},\"cmd\":\"set_switch\"}"+"\r\n";
        send(a);
    }

    public void guandeng() {
        String c = "{\"args\":{\"device_value\":\"false\",\"device_type\":24,\"device_id\":101},\"cmd\":\"set_switch\"}"+"\r\n";
        send(c);
    }

    public void kaishan() {
        String b = "{\"cmd\":\"set_switch\",\"args\":{\"device_value\":\"true\",\"device_type\":24,\"device_id\":102}}"+"\r\n";
        send(b);
    }

    public void guanshan() {
        String d = "{\"args\":{\"device_value\":\"false\",\"device_type\":24,\"device_id\":102},\"cmd\":\"set_switch\"}"+"\r\n";
        send(d);
    }

    public void send(String string) {
        try {
            writer.write(string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void parseEasyJson(String msg){
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String i = jsonObject.getString("device_id");
            int id=Integer.parseInt(i);
            String value = jsonObject.getString("device_value");
            switch(id){
                case 101:
                    if(value.equals("true")){
                        array1 = "打开";
                        a1=1;
                        deng. setBackgroundResource(R.drawable.dk);
                    }else if(value.equals("false")){
                        array1 = "关闭";
                        a1=2;
                        deng. setBackgroundResource(R.drawable.dg);
                    }
                    break;
                case 102:
                    if(value.equals("true")){
                        array2 = "打开";
                        a2=1;
                        shan. setBackgroundResource(R.drawable.sk);
                    }else if(value.equals("false")){
                        array2 = "关闭";
                        a2=2;
                        shan. setBackgroundResource(R.drawable.sg);
                    }
                    break;
                case 153:
                    if(value.equals("true")){
                        array3 = "开";
                    }else if(value.equals("false")){
                        array3 = "关";
                    }
                    break;
                case 8:
                    int yandu = Integer.parseInt(value);
                    if (yandu > 5000) {

                        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(new long[] { 1000, 2000, 1000,2000 }, -1);

                        Notification notify = new Notification.Builder(ControlActivity.this)
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

                        Notification notify = new Notification.Builder(ControlActivity.this)
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

                        Notification notify = new Notification.Builder(ControlActivity.this)
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

                        Notification notify = new Notification.Builder(ControlActivity.this)
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

                        Notification notify = new Notification.Builder(ControlActivity.this)
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
        SynthesizerDialog syn = new SynthesizerDialog(ControlActivity.this,
                "appid=50e1b967");
        syn.setListener(new SynthesizerDialogListener() {
            @Override
            public void onEnd(SpeechError arg0) {

            }
        });
        E.setText("灯泡："+array1+",风扇："+array2+",触摸："+array3);
        // 根据EditText里的内容实现语音合成
        syn.setText(E.getText().toString(), null);
        syn.show();
    }

    private void showReconigizerDialog() {
        rd = new RecognizerDialog(this ,"appid=50e1b967");
        // setEngine(String engine,String params,String grammar);
        /**
         * 识别引擎选择，目前支持以下五种 “sms”：普通文本转写 “poi”：地名搜索 “vsearch”：热词搜索 “vsearch”：热词搜索
         * “video”：视频音乐搜索 “asr”：命令词识别
         *
         * params 引擎参数配置列表
         * 附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：“area=安徽省合肥市”，无附加参数传null
         */
        rd.setEngine("sms", null, null);

        // 设置采样频率，默认是16k，android手机一般只支持8k、16k.为了更好的识别，直接弄成16k即可。
        rd.setSampleRate(SpeechConfig.RATE.rate16k);

        final StringBuilder sb = new StringBuilder();
        Log.i(TAG, "识别准备开始.............");

        // 设置识别后的回调结果
        rd.setListener(new RecognizerDialogListener() {
            @Override
            public void onResults(ArrayList<RecognizerResult> result,
                                  boolean isLast) {
                for (RecognizerResult recognizerResult : result) {
                    sb.append(recognizerResult.text);
                    Log.i(TAG, "识别一条结果为:" + recognizerResult.text);
                }
            }

            @Override
            public void onEnd(SpeechError error) {
                Log.i(TAG, "识别完成.............");
                // txt_result.setText(sb.toString());
                if (sb.toString().contains("风扇") && sb.toString().contains("开")) {

                    kaishan();

                }
                if (sb.toString().contains("风扇") && sb.toString().contains("关")) {

                    guanshan();

                }
                if (sb.toString().contains("开") && sb.toString().contains("灯")) {

                    kaideng();

                }
                if (sb.toString().contains("关") && sb.toString().contains("灯")) {
                    guandeng();

                }
            }
        });

        // txt_result.setText(""); // 先设置为空，等识别完成后设置内容
        rd.show();
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
