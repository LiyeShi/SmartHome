package com.sict.soft.smarthome;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WangyiActivity extends Activity {

    private WebView activity_wv_wangyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wangyi);

        initView();


    }

    private void initView() {
        activity_wv_wangyi = (WebView) findViewById(R.id.activity_wv_wangyi);

        // WebView加载web资源
        activity_wv_wangyi.loadUrl("http://www.163.com");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开

        activity_wv_wangyi.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                activity_wv_wangyi.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
                activity_wv_wangyi.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
                activity_wv_wangyi.getSettings().setSupportZoom(true);//是否可以缩放，默认true
                activity_wv_wangyi.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
                activity_wv_wangyi.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
                activity_wv_wangyi.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
                activity_wv_wangyi.getSettings().setAppCacheEnabled(true);//是否使用缓存
                activity_wv_wangyi.getSettings().setDomStorageEnabled(true);//DOM Storage
                activity_wv_wangyi.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                return true;
            }
        });

    }
}