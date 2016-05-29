package com.don.hidetoolbarwithwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NestedScrollViewActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_view);

        webview = (WebView) findViewById(R.id.web_view);
        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl("http://huaban.com/");
        webview.getSettings().setJavaScriptEnabled(true);webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("test", "url=" + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.loadUrl("http://36kr.com/");
    }
    @Override
    public void onResume() {
        super.onResume();
        if (null != webview) {
            webview.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != webview) {
            webview.onPause();
        }
    }
}
