package com.sict.soft.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JingdongActivity extends Activity {

    private WebView activity_wv_jingdong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingdong);

        initView();


    }

    private void initView() {
        activity_wv_jingdong = (WebView) findViewById(R.id.activity_wv_jingdong);

        // WebView加载web资源
        activity_wv_jingdong.loadUrl("http://www.jd.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        activity_wv_jingdong.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }
}