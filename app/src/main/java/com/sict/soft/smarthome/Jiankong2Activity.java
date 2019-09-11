package com.sict.soft.smarthome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bkrcl.control_car_video.camerautil.CameraCommandUtil;

import java.util.Timer;
import java.util.TimerTask;

public class Jiankong2Activity extends Activity {
    private Button jk_back;
    private ProgressDialog dialog = null;
    private ImageView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_jiankong);

        jk_back = (Button) findViewById(R.id.jk_back);
        jk_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Jiankong2Activity.this, MainActivity.class));
//                finish();
            }
        });

        widget_init();
        Camer_Init();//摄像头初始化
        cameraCommandUtil = new CameraCommandUtil();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                search();                        //开启Service 搜索摄像头IP
            }
        }, 1200);


    }


    private void widget_init() {
        show = (ImageView) findViewById(R.id.VIEW);
        show.setOnTouchListener(new ontouchlistener1());
    }
    // 图片
    public static Bitmap bitmap;

    // 得到当前摄像头的图片信息
    public void getBitmap() {
        bitmap = cameraCommandUtil.httpForImage(IPCamera);
        phHandler.sendEmptyMessage(10);
    }

    // 开启线程接受摄像头当前图片
    private Thread phThread = new Thread(new Runnable() {
        public void run() {
            while (true) {
                getBitmap();
            }
        }
    });

    // 显示图片
    @SuppressLint("HandlerLeak")
    public Handler phHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                show.setImageBitmap(bitmap);
            }
        }
    };

    private String IPCamera;
    // 广播名称
    public static final String A_S = "com.a_s";
    // 摄像头工具
    private CameraCommandUtil cameraCommandUtil;
    // 广播接收器接受SearchService搜索的摄像头IP地址加端口
    private String purecameraip = null;

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {  //获取到摄像头后，重新进行ip的匹配
        public void onReceive(Context arg0, Intent arg1) {
            IPCamera = arg1.getStringExtra("IP");
            purecameraip = arg1.getStringExtra("pureip");
            setendip();
            phThread.start();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
//7                    dialog.dismiss();  //关闭对话框
                    dialog = null;
                }
            }, 500);
        }
    };

    // 图片区域滑屏监听点击和弹起坐标位置
    private final int MINLEN = 30;
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    private class ontouchlistener1 implements View.OnTouchListener  //通过滑屏监听实现了摄像头的上、下、左、右的控制
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO 自动生成的方法存根
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 点击位置坐标
                case MotionEvent.ACTION_DOWN:
                    x1 = event.getX();
                    y1 = event.getY();
                    break;
                // 弹起坐标
                case MotionEvent.ACTION_UP:
                    x2 = event.getX();
                    y2 = event.getY();
                    float xx = x1 > x2 ? x1 - x2 : x2 - x1;
                    float yy = y1 > y2 ? y1 - y2 : y2 - y1;
                    // 判断滑屏趋势
                    if (xx > yy) {
                        if ((x1 > x2) && (xx > MINLEN)) {        // left
                            Toast.makeText(Jiankong2Activity.this, "左转", Toast.LENGTH_SHORT).show();
                            XcApplication.executorServicetor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    cameraCommandUtil.postHttp(IPCamera, 4, 1);  //左
                                }
                            });

                        } else if ((x1 < x2) && (xx > MINLEN)) { // right
                            Toast.makeText(Jiankong2Activity.this, "右转", Toast.LENGTH_SHORT).show();
                            XcApplication.executorServicetor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    cameraCommandUtil.postHttp(IPCamera, 6, 1);  //右
                                }
                            });
                        }
                    } else {
                        if ((y1 > y2) && (yy > MINLEN)) {        // up
                            Toast.makeText(Jiankong2Activity.this, "抬头", Toast.LENGTH_SHORT).show();
                            XcApplication.executorServicetor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    cameraCommandUtil.postHttp(IPCamera, 0, 1);  //上
                                }
                            });
                        } else if ((y1 < y2) && (yy > MINLEN)) { // down
                            Toast.makeText(Jiankong2Activity.this, "低头", Toast.LENGTH_SHORT).show();
                            XcApplication.executorServicetor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    cameraCommandUtil.postHttp(IPCamera, 2, 1);  //下
                                }
                            });
                        }
                    }
                    x1 = 0;
                    x2 = 0;
                    y1 = 0;
                    y2 = 0;
                    break;
            }
            return true;
        }
    }

    private void Camer_Init() {
        //广播接收器注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(A_S);
        registerReceiver(myBroadcastReceiver, intentFilter);
        // 搜索摄像头图片工具
    }

    private void setendip() {
        Intent ipintent = new Intent();
        //ComponentName的参数1:目标app的包名,参数2:目标app的Service完整类名
        ipintent.setComponent(new ComponentName("com.android.settings", "com.android.settings.ethernet.CameraInitService"));
        //设置要传送的数据
        ipintent.putExtra("purecameraip", purecameraip);
        startService(ipintent);   //设为静态16.**时，可以不用发送
    }

    // 搜索摄像cameraIP进度条
    private void search() {
        Intent intent = new Intent();
        intent.setClass(Jiankong2Activity.this, SearchService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);  //卸载广播接收器
        if (dialog != null) {
            dialog.dismiss();
        }
    }



}
