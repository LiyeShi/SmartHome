package com.sict.soft.smarthome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.misc.objc.NSData;
import com.misc.objc.NSNotification;
import com.misc.objc.NSNotificationCenter;
import com.misc.objc.NSSelector;


import net.reecam.IpCamera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class JiankongActivity extends Activity {

    private Button jk_back;
    Button mButtonF;
    Button mButtonB;
    Button mButtonL;
    Button mButtonR;
    Button mButtonz;
    Button mButtonc;
    Button mButtonj;
    Button mButtonm;
    IpCamera camera;
    String res;
    Bitmap bit;
    int count=0;


    ImageView picture;
    TextView responseText;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiankong);

        jk_back = (Button) findViewById(R.id.jk_back);
        jk_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(JiankongActivity.this, MainActivity.class));
//                finish();
            }
        });

//            try{Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Blueto.onResume();
//                }
//            });
//                thread.start();
//                Toast.makeText(JiankongActivity.this,"小车连接成功！",Toast.LENGTH_SHORT).show();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        try {
            camera = new IpCamera("", "", "192.168.1.100", "80", "admin","", 100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mButtonz = (Button) findViewById(R.id.at_jk_car1);
        mButtonz.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                count=1;
                mButtonz.setBackground(getResources().getDrawable(R.drawable.che_jiu));
                mButtonc.setBackground(getResources().getDrawable(R.drawable.jiankong_xin));
                mButtonj.setBackground(getResources().getDrawable(R.drawable.jixie_xin));
            }
        });
        mButtonj = (Button) findViewById(R.id.at_jk_car2);
        mButtonj.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                count=3;
                mButtonz.setBackground(getResources().getDrawable(R.drawable.che_xin));
                mButtonc.setBackground(getResources().getDrawable(R.drawable.jiankong_xin));
                mButtonj.setBackground(getResources().getDrawable(R.drawable.jixie_jiu));
            }
        });
        mButtonc = (Button) findViewById(R.id.at_jk_jk);
        mButtonc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                count=2;
                mButtonz.setBackground(getResources().getDrawable(R.drawable.che_xin));
                mButtonc.setBackground(getResources().getDrawable(R.drawable.jiankong_jiu));
                mButtonj.setBackground(getResources().getDrawable(R.drawable.jixie_xin));
            }
        });

        //截屏
        mButtonm = (Button) findViewById(R.id.at_jk_sb);
        mButtonm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent=new Intent(JiankongActivity.this,FaceActivity.class);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        //前进
        mButtonF = (Button) findViewById(R.id.at_jk_up);
        mButtonF.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (count==1) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.qianjin();
                            break;
                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }else if (count==2){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.T_UP);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.PT_STOP);
                    }
                    return false;
                }else if(count==3){
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.jqian();
                            break;
                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }
                return false;
            }
        });

        //后退
        mButtonB = (Button) findViewById(R.id.at_jk_down);
        mButtonB.setOnTouchListener(new Button.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (count==1) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.houtui();
                            break;

                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }else if (count==2){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.T_DOWN);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.PT_STOP);
                    }
                    return false;
                }else if(count==3){
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.jqian();
                            break;

                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }
                return false;
            }
        });

        //左转
        mButtonL = (Button) findViewById(R.id.at_jk_left);
        mButtonL.setOnTouchListener(new Button.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (count==1) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.zuozhuan();
                            break;
                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }else if (count==2){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.P_LEFT);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.PT_STOP);
                    }
                    return false;
                }else if(count==3){
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.jqian();
                            break;
                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }
                return false;
            }
        });
        //右转
        mButtonR = (Button) findViewById(R.id.at_jk_right);
        mButtonR.setOnTouchListener(new Button.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (count==1) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.youzhuan();
                            break;

                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }else if (count==2){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.P_RIGHT);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        camera.ptz_control(IpCamera.PTZ_COMMAND.PT_STOP);
                    }
                    return false;
                }else if(count==3){
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Blueto.jqian();
                            break;

                        case MotionEvent.ACTION_UP:
                            Blueto.taiqi();
                            break;
                    }
                }
                return false;
            }
        });
    }


    //截屏发送图片方法



    /////////////////////////
    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                responseText.setText(response);
            }
        });
    }
    /////////////////////

    public void OnCameraStatusChanged(NSNotification note) {
        final TextView tv = (TextView) findViewById(R.id.camera_statuss);
        final String status = note.userInfo().get("status").toString();
        tv.post(new Runnable() {

            public void run() {
                tv.setText(status);
            }
        });

        if (status.equals("CONNECTED")) {
            camera.play_video();
        } else {

        }
    }

    public void OnImageChanged(NSNotification note) {
        if (!((IpCamera) note.object()).equals(camera)) {
            return;
        }
        // 解码并显示数据
        NSData data = (NSData) note.userInfo().get("data");
        final Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeByteArray(data.bytes(), 0,
                    data.length());
            bit = bitmap;
        } catch (OutOfMemoryError e) {
            NSNotificationCenter nc = NSNotificationCenter.defaultCenter();
            nc.removeObserver(this, IpCamera.IPCamera_Image_Notification,
                    camera);
            nc.addObserver(this, new NSSelector("OnImageChanged"),
                    IpCamera.IPCamera_Image_Notification, camera);
            System.gc();
            return;
        }
        // 启动线程显示视频数据
        final ImageView lv = (ImageView) findViewById(R.id.VIEW);
        lv.post(new Runnable() {
            public void run() {
                lv.setImageBitmap(bitmap);
                // bitmap.recycle();
            }
        });

    }


    public void onResume() {
        super.onResume();
        NSNotificationCenter nc = NSNotificationCenter.defaultCenter();
        nc.addObserver(this, new NSSelector("OnCameraStatusChanged"),
                IpCamera.IPCamera_CameraStatusChanged_Notification, camera);
        nc.addObserver(this, new NSSelector("OnVideoStatusChanged"),
                IpCamera.IPCamera_VideoStatusChanged_Notification, camera);
        nc.addObserver(this, new NSSelector("OnAudioStatusChanged"),
                IpCamera.IPCamera_AudioStatusChanged_Notification, null);
        nc.addObserver(this, new NSSelector("OnTalkStatusChanged"),
                IpCamera.IPCamera_TalkStatusChanged_Notification, camera);
        nc.addObserver(this, new NSSelector("OnImageChanged"),
                IpCamera.IPCamera_Image_Notification, camera);
        TextView tv = (TextView) findViewById(R.id.camera_statuss);
        String status = camera.camera_status.toString();

        status = camera.video_status.toString();
        // 恢复相机状态
        if (!camera.started) {
            camera.start();
        }

        status = camera.camera_status.toString();
        if (status.equals("CONNECTED")) {
            camera.play_video();
        }

    }

    public void onClick(View arg0) {

        try {
            saveBitmap(bit, "bitpic1.bmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
                Log.i("asd", "take");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        camera.stop();
    }

}
