package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CaxunActivity extends Activity {

    private Button cx_back;
    private TextView tv1;
    String name;
    String device_value;
    String timestamp;
    String response;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaxun);
        tv1 = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String PATH = intent.getStringExtra("PATH");
        text = intent.getStringExtra("text");
        gdddet("http://"+C.hou_IP+":11111"+PATH);
        cx_back = (Button) findViewById(R.id.cx_back);
        cx_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CaxunActivity.this, ShujuActivity.class));
                finish();
            }
        });

    }

    public void gdddet(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        String content = GetJson.readStream(is);
                        // Message msg = new Message();
                        // msg.obj = content;
                        //handler.sendMessage(msg);
                      if(text.equals("家庭安全报告")) { showResponse1(content);}
                        parseJSONWithJSONObject(content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        StringBuffer shuju = new StringBuffer();
        Log.i("JOSN",jsonData);
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name");
                //id = jsonOb//device_id = jsonObjectject.getString("id");
//                .getString("device_id");
                //transfer_type = jsonObject.getString("transfer_type");
                device_value = jsonObject.getString("device_value");
                // device_typ jsonObject.getString("timestamp");
               // timestamp = jsonObject.getString("device_type");
                timestamp = jsonObject.getString("timestamp");
                Log.i("time",timestamp);
                //shuju.append("  " + name + "：" + " " + device_value + "   " + "时间：" + timestamp + "\n");
                shuju.append("  " + name + "：" + " " + device_value + "   " + "时间：" + timestamp + "\n");

                //System.out.println("name："+name + "id：" +id+ "device_id："+device_id + "transfer_type：" +transfer_type+ "device_value：" +device_value+" device_type："+device_type + "timestamp："+timestamp);
            }
            response = shuju.toString();

            showResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showResponse1(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv1.append(response);
            }
        });
    }
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv1.append(response);
            }
        });
    }
}