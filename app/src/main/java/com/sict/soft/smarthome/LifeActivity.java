package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import static com.sict.soft.smarthome.R.id.health_food;
import static com.sict.soft.smarthome.R.id.health_life;
import static com.sict.soft.smarthome.R.id.health_road;
/**
 * Created by Administrator on 2017/8/11.
 */

public class LifeActivity extends Activity implements View.OnClickListener{

    private Button life_back;
    private TextView content;
    private TextView title;
    private ImageView pic;
    private TextView weekend;
    private TextView food_bra_content;
    private TextView food_lunch_content;
    private TextView food_dinner_content;
    private LinearLayout lin;

    private Button food;
    private Button life;
    private Button road;



    String array0 = "星期";
    String array1 = "标题";
    String array2 = "内容";
    String array3 = "早餐内容";
    String array4 = "午餐内容";
    String array5 = "晚餐内容";
    Bitmap bitmap;

    String asd = "life";


    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        //接受的线程
        @Override
        public void run() {
            mHandler.postDelayed(mRunnable, 500);
            Data();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);

        food = (Button) findViewById(health_food);
        life = (Button) findViewById(R.id.health_life);
        road = (Button) findViewById(R.id.health_road);

        food.setOnClickListener(this);
        life.setOnClickListener(this);
        road.setOnClickListener(this);

        life_back = (Button) findViewById(R.id.life_back);
        life_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LifeActivity.this, LaorenActivity.class));
                Stop();
                finish();
            }
        });

        getTime();

        lin = (LinearLayout) findViewById(R.id.lin);
        content = (TextView) findViewById(R.id.content);
        title = (TextView) findViewById(R.id.title);
        weekend = (TextView) findViewById(R.id.weekend);
        pic = (ImageView)findViewById(R.id.pic);
        food_bra_content = (TextView) findViewById(R.id.food_bra_content);
        food_lunch_content = (TextView) findViewById(R.id.food_lunch_content);
        food_dinner_content = (TextView) findViewById(R.id.food_dinner_content);


        Intent intent = getIntent();
        String PATH = intent.getStringExtra("PATH");
        gdddet("http://"+C.hou_IP+":11111"+PATH);

        mHandler.postDelayed(mRunnable, 500);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case health_food:
                asd = "food";
                lin.setVisibility(View.VISIBLE);
                pic.setVisibility(View.VISIBLE);
                title.setVisibility(View.VISIBLE);
                food.setBackgroundColor(getResources().getColor(R.color.life_yes));
                life.setBackgroundColor(getResources().getColor(R.color.life_no));
                road.setBackgroundColor(getResources().getColor(R.color.life_no));
                gdddet("http://"+C.hou_IP+":11111/connhealth/food");
                Stop();
                break;
            case health_life:
                asd = "life";
                lin.setVisibility(View.GONE);
                pic.setVisibility(View.GONE);
                title.setVisibility(View.VISIBLE);
                life.setBackgroundColor(getResources().getColor(R.color.life_yes));
                food.setBackgroundColor(getResources().getColor(R.color.life_no));
                road.setBackgroundColor(getResources().getColor(R.color.life_no));
                gdddet("http://"+C.hou_IP+":11111/connhealth/life");
                Stop();
                break;
            case health_road:
                asd = "road";
                lin.setVisibility(View.GONE);
                pic.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                content.setText(CeceActivity.lin);
                road.setBackgroundColor(getResources().getColor(R.color.life_yes));
                life.setBackgroundColor(getResources().getColor(R.color.life_no));
                food.setBackgroundColor(getResources().getColor(R.color.life_no));
                Start();
                break;
        }
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
                        Log.i("aaa",content);
                        parseJSONWithJSONObject(content);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            if(asd=="food") {
                String breakfast = jsonObject.getString("breakfast");
                String lunch = jsonObject.getString("lunch");
                String dinner = jsonObject.getString("dinner");
                String picture = jsonObject.getString("picture");
                array3 = breakfast;
                array4 = lunch;
                array5 = dinner;
                array1 = title;
                array2 = content;
                stringtoBitmap(picture);
            }else if(asd == "life"){
                array1 = title;
                array2 = content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void Data() {
        if (asd=="food"){
            weekend.setText(array0);
            food_bra_content.setText(array3);
            food_lunch_content.setText(array4);
            food_dinner_content.setText(array5);
            pic.setImageBitmap(bitmap);
            title.setText(array1);
            content.setText(array2);
        }else if(asd=="road"){
            content.setText(CeceActivity.lin);
        }else{
            title.setText(array1);
            content.setText(array2);
        }
    }

    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    private void getTime(){
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        switch (week){
            case 1:
                array0 = "星期日";
                break;
            case 2:
                array0 = "星期一";
                break;
            case 3:
                array0 = "星期二";
                break;
            case 4:
                array0 = "星期三";
                break;
            case 5:
                array0 = "星期四";
                break;
            case 6:
                array0 = "星期五";
                break;
            case 7:
                array0 = "星期六";
                break;
        }
    }


    //悬浮按钮
    protected void Start() {
        Intent intent = new Intent(LifeActivity.this, FloatViewService.class);
        //启动FloatViewService
        startService(intent);
    }

    protected void Stop() {
        // 销毁悬浮窗
        Intent intent = new Intent(LifeActivity.this, FloatViewService.class);
        //终止FloatViewService
        stopService(intent);
    }

    @Override
    protected void onPause() {
        Stop();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Start();
        super.onRestart();
    }
}
