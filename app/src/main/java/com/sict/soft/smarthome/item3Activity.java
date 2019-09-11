package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class item3Activity extends Activity {

    private ImageView item3_title_imv;
    private Button tijiao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item3);

        tijiao = (Button) findViewById(R.id.tijiao);
        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(item3Activity.this,"提交成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(item3Activity.this, MainActivity.class));
                    finish();
            }
        });

        item3_title_imv = (ImageView) findViewById(R.id.item3_title_imv);
        item3_title_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(item3Activity.this, MainActivity.class));
                finish();
            }
        });

    }
}
