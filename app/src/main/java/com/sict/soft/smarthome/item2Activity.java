package com.sict.soft.smarthome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.sict.soft.smarthome.view.ImageBarnnerFramLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class item2Activity extends Activity implements ImageBarnnerFramLayout.FramLayoutLisenner {

    private Button item2_title_imv;

    private ImageBarnnerFramLayout mGroup;

    private int[] ids = new int[]{
            R.drawable.menu_viewpager_1,
            R.drawable.menu_viewpager_2,
            R.drawable.menu_viewpager_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item2);

        //轮播图的实现
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        C.WITTH = dm.widthPixels;

        mGroup = (ImageBarnnerFramLayout) findViewById(R.id.lunbo_gongneng);
        mGroup.setLisenner(this);
        List<Bitmap> list = new ArrayList<>();
        for (int i=0; i < ids.length; i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),ids[i]);
//            bitmap = compressImage(bitmap,100,50);
            list.add(bitmap);
        }
        mGroup.addBitmap(list);

        item2_title_imv = (Button) findViewById(R.id.item2);
        item2_title_imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(item2Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void clickImageIndex(int pos) {

    }

    private static Bitmap compressImage(Bitmap image,int size,int options) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > size) {
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}
