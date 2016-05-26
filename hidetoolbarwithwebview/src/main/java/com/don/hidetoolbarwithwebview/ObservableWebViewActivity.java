package com.don.hidetoolbarwithwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.ksoichiro.android.observablescrollview.ObservableWebView;

public class ObservableWebViewActivity extends AppCompatActivity {

    private ObservableWebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_web_view);


        webview = (ObservableWebView) findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl("http://huaban.com/");
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("test", "url=" + url);
            }
        });
        webview.loadUrl("http://36kr.com/");
    }
}
