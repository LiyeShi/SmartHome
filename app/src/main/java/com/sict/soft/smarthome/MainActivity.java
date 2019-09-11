package com.sict.soft.smarthome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sict.soft.smarthome.view.ImageBarnnerFramLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements ImageBarnnerFramLayout.FramLayoutLisenner,View.OnClickListener{

    //侧滑栏
    private SlidingMenu mLeftMenu ;

    // 初始化顶部栏显示

    private Button title_search;


//侧滑栏文字
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private Button tuichu;


//    主界面控制按钮
    private Button at_main_bt1;
    private Button at_main_bt2;
    private Button at_main_bt3;
    private Button at_main_bt4;
    private Button at_main_bt5;
    private Button at_main_bt6;
    private Button at_main_bt7;
    private Button at_main_bt8;
    private Button at_main_bt9;

    private SQLiteDatabase db;

    private ImageBarnnerFramLayout mGroup;

    private int[] ids = new int[]{
            R.drawable.menu_viewpager_1,
            R.drawable.menu_viewpager_1,
            R.drawable.menu_viewpager_1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        // 创建 “数据库 - mydata” 和 “数据表 - setting”
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString() + "mydata", null);
        db.execSQL("create table if not exists setting(_id INTEGER PRIMARY KEY AUTOINCREMENT,shebeiip TEXT,fuwuqiip TEXT,naoling TEXT,lianxiren TEXT,five TEXT,six TEXT,seven TEXT,eight TEXT,nine TEXT,ten TEXT)");

        Cursor cursor = db.rawQuery("select * from setting where _id=1", null);
        while (cursor.moveToNext()) {
            C.chuanIP = cursor.getString(1);
            C.hou_IP = cursor.getString(2);
            C.cun = cursor.getString(3);
            C.lian = cursor.getString(4);
            C.shequ = cursor.getString(5);
        }

        ExitApplication.getInstance().addActivity(this);

        getTime3();

        //轮播图的实现
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.WITTH = dm.widthPixels;
        mGroup = (ImageBarnnerFramLayout) findViewById(R.id.image_group);
        mGroup.setLisenner(this);
        List<Bitmap> list = new ArrayList<>();
        for (int i=0; i < ids.length; i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),ids[i]);
            list.add(bitmap);
        }
        mGroup.addBitmap(list);

        init();

    }

    public void init(){
        //侧滑栏
        mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
        // 初始化页面标题栏 返回键
        title_search = (Button) findViewById(R.id.title_search);
        //初始化侧滑栏的控件
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_2 = (TextView)findViewById(R.id.tv_2);
        tv_3 = (TextView)findViewById(R.id.tv_3);
        tv_4 = (TextView)findViewById(R.id.tv_4);
        tuichu = (Button)findViewById(R.id.tuichu);
        //主界面控制按钮
        at_main_bt1 = (Button)findViewById(R.id.at_main_bt1);
        at_main_bt2 = (Button)findViewById(R.id.at_main_bt2);
        at_main_bt3 = (Button)findViewById(R.id.at_main_bt3);
        at_main_bt4 = (Button)findViewById(R.id.at_main_bt4);
        at_main_bt5 = (Button)findViewById(R.id.at_main_bt5);
        at_main_bt6 = (Button)findViewById(R.id.at_main_bt6);
        at_main_bt7 = (Button)findViewById(R.id.at_main_bt7);
        at_main_bt8 = (Button)findViewById(R.id.at_main_bt8);
        at_main_bt9 = (Button)findViewById(R.id.at_main_bt9);

        title_search.setOnClickListener(this);
        tv_1.setOnClickListener(this);
        tv_2.setOnClickListener(this);
        tv_3.setOnClickListener(this);
        tv_4.setOnClickListener(this);
        tuichu.setOnClickListener(this);
        at_main_bt1.setOnClickListener(this);
        at_main_bt2.setOnClickListener(this);
        at_main_bt3.setOnClickListener(this);
        at_main_bt4.setOnClickListener(this);
        at_main_bt5.setOnClickListener(this);
        at_main_bt6.setOnClickListener(this);
        at_main_bt7.setOnClickListener(this);
        at_main_bt8.setOnClickListener(this);
        at_main_bt9.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_search:
                startActivity(new Intent(MainActivity.this, NetActivity.class));
//                finish();
            break;
            case R.id.tv_1:
                startActivity(new Intent(MainActivity.this, item1Activity.class));
//                finish();
                break;
            case R.id.tv_2:
                startActivity(new Intent(MainActivity.this, item2Activity.class));
//                finish();
                break;
            case R.id.tv_3:
                startActivity(new Intent(MainActivity.this, item3Activity.class));
//                finish();
                break;
            case R.id.tv_4:
                Toast.makeText(MainActivity.this,"已是最新版本",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tuichu:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
                break;
            case R.id.at_main_bt1:
                startActivity(new Intent(MainActivity.this, EnvirActivity.class));
//                finish();
                break;
            case R.id.at_main_bt2:
                startActivity(new Intent(MainActivity.this, ControlActivity.class));
//                finish();
                break;
            case R.id.at_main_bt3:
                startActivity(new Intent(MainActivity.this, Jiankong2Activity.class));
//                finish();
                break;
            case R.id.at_main_bt4:
                startActivity(new Intent(MainActivity.this, BaojingActivity.class));
//                finish();
                break;
            case R.id.at_main_bt5:
                startActivity(new Intent(MainActivity.this, LaorenActivity.class));
//                finish();
                break;
            case R.id.at_main_bt6:
                startActivity(new Intent(MainActivity.this, ShujuActivity.class));
//                finish();
                break;
            case R.id.at_main_bt7:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.at_main_bt8:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
                break;
            case R.id.at_main_bt9:
                startActivity(new Intent(MainActivity.this, SetActivity.class));
                break;
        }
    }

    //轮播图点击事件
    @Override
    public void clickImageIndex(int pos) {
        if(pos==2){
            Intent intent = new Intent(MainActivity.this,MainWeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void toggleMenu(View view)
    {
        mLeftMenu.toggle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
//轮播图
    public void getTime3(){
        Calendar calendar = Calendar.getInstance();
        int created = calendar.get(Calendar.HOUR_OF_DAY);
        int siji = calendar.get(Calendar.MONTH);
        int si = siji + 1;

        if(created>=5&&created<8){
            ids[1] = R.drawable.q_zao;
        }else if(created>=8&&created<12){
            ids[1] = R.drawable.q_morning;
        }else if(created>=12&&created<18){
            ids[1] = R.drawable.q_afternoon;
        }else if(created>=18&&created<20){
            ids[1] = R.drawable.q_evening;
        }else{
            ids[1] = R.drawable.q_wan;
        }

        if(si>=1&&si<=3){
            ids[2] = R.drawable.q_sping;
        }else if(si>=4&&si<=6){
            ids[2] = R.drawable.q_summer;
        }else if(si>=7&&si<=9){
            ids[2] = R.drawable.q_autumn;
        }else{
            ids[2] = R.drawable.q_winter;
        }
    }


    // 处理键盘事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("确定要退出程序?");
            builder.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ExitApplication.getInstance().exit();
//                            dialog.dismiss();
//                            finish();
                        }
                    });
            builder.setNeutralButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}

