package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class item4Activity extends Activity {

    private ImageView item4_title_imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item4);


        item4_title_imv = (ImageView) findViewById(R.id.item4_title_imv);
        item4_title_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(item4Activity.this, MainActivity.class));
                finish();
            }
        });

    }
}