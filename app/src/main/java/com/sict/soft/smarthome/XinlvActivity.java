package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
//心率
public class XinlvActivity extends Activity {

    private Button xl_back;
    private TextView tv_xl;
    String msg;
    String array = "N/A";
    private int num=0;
    private SocketAsyn socketAsyn;
    private BufferedReader in = null;
    private BufferedWriter writer = null;


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
        setContentView(R.layout.activity_xinlv);
        tv_xl = (TextView)findViewById(R.id.tv_xl);
        try {
            connet();
        }catch(Exception e){
            e.printStackTrace();
        }

        xl_back = (Button) findViewById(R.id.xl_back);

        xl_back.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(XinlvActivity.this, LaorenActivity.class));
                finish();
            }
        });
    }

    public void connet() {
            socketAsyn = new SocketAsyn();
            socketAsyn.execute();
            mHandler.postDelayed(mRunnable, 2000);
    }

    protected void Data() {
        tv_xl.setText(array);
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
    private void parseEasyJson(String msg){
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String i = jsonObject.getString("device_id");
            int id=Integer.parseInt(i);
            String value = jsonObject.getString("device_value");
            switch(id){
                case 32:
                    array = value;
                    int arr = Integer.parseInt(array);
                    if(arr >=120||arr<=40){
                        num++;
                    }
            }
            if (num>=10){
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                //拆分短信内容（手机短信长度限制）
                List<String> divideContents = smsManager.divideMessage("乐老提醒：心率发生异常！");
                for (String text : divideContents) {
                    smsManager.sendTextMessage(C.lian, null, text, null, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
