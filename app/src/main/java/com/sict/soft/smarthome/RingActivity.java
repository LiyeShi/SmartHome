package com.sict.soft.smarthome;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
//闹钟
public class RingActivity extends Activity {
    public MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        //时间一到跳转Activity,在这个Activity中播放音乐
        if(C.cun=="卡农"){
            mediaPlayer = MediaPlayer.create(this, R.raw.kanong);
        }else if(C.cun=="童话镇"){
            mediaPlayer = MediaPlayer.create(this, R.raw.tonghuazhen);
        }else if(C.cun=="上海三月"){
            mediaPlayer = MediaPlayer.create(this, R.raw.shanghaisanyue);
        }else if(C.cun=="问渔涟说"){
            mediaPlayer = MediaPlayer.create(this, R.raw.wenyulianshuo);
        }
        mediaPlayer.start();
    }
    public void stop(View view){
        mediaPlayer.stop();
        finish();
    }

}

