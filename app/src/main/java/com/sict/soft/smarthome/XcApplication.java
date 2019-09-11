package com.sict.soft.smarthome;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by bkrc_logobkrc on 2018/1/10.
 */

public class XcApplication extends Application {
    // private static ScarApp instance;
    public  static String cameraip="192.168.1.101:81";

    public static ExecutorService executorServicetor = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        Intent sintent = new Intent();
        //ComponentName的参数1:目标app的包名,参数2:目标app的Service完整类名
        sintent.setComponent(new ComponentName("com.android.settings", "com.android.settings.ethernet.CameraInitService"));
        //设置要传送的数据
        sintent.putExtra("purecameraip","0.0.0.0");
        startService(sintent);
    }

}
