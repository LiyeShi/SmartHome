package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Administrator on 2017/7/26.
 */

public class FaceActivity extends Activity {
    private Button fa_back;
    private ImageView tu_left;
    private ImageView tu_right;
    private TextView xiangsidu;
    Bitmap bit;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

        tu_left = (ImageView) findViewById(R.id.tu_left);
        tu_right = (ImageView) findViewById(R.id.tu_right);
        xiangsidu = (TextView) findViewById(R.id.xiangsidu);

        fa_back = (Button) findViewById(R.id.fa_back);
        fa_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FaceActivity.this, JiankongActivity.class));
                finish();
            }
        });


        Intent intent=getIntent();
        if(intent !=null)
        {
            byte [] bis=intent.getByteArrayExtra("bitmap");
            Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
            tu_left.setImageBitmap(bitmap);
            bit = bitmap;
        }
        encode();

    }

    private void encode() {
        //图片编码
        //convert to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        //base64 encode
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        String encodeString = new String(encode);
        sendRequestWithHttpURLConnection(encodeString);
    }

    private void sendRequestWithHttpURLConnection(final String encodeString) {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://192.168.0.106:11111/face");
                    connection = (HttpURLConnection) url.openConnection();//获取实例
                    connection.setRequestMethod("POST");//设置连接方式
                    connection.setConnectTimeout(8000);//链接超时时间数
                    connection.setReadTimeout(8000);//读取超时时间数
                    DataOutputStream dop = new DataOutputStream(connection.getOutputStream());
                    dop.writeBytes("image_ls="+ URLEncoder.encode(encodeString,"utf-8"));
                    InputStream in = connection.getInputStream();
                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();//关闭连接
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                parseEasyJson(value);
            }
        });
    }


    private void parseEasyJson(String msg) {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            String i = jsonObject.getString("person_name");
            String value = jsonObject.getString("similarity");
            xiangsidu.setText(value);
            switch (i) {
                case "乔雨":
                    tu_right.setImageResource(R.drawable.qy);
                    break;
                case "王佳伟":
                    tu_right.setImageResource(R.drawable.wjw);
                    break;
                default:
                    tu_right.setImageResource(R.drawable.wu);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
