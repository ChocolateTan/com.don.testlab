package com.don.hidetoolbarwithwebview;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

public class ObservableWebViewActivity extends AppCompatActivity {

    private static final String TAG = ObservableWebViewActivity.class.getSimpleName();
    private static final long ANIMATION_DURATION = 500;
    private ObservableWebView webview;
    private Toolbar toolbar;
    private AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_web_view);

        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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
        webview.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                int toolbarHeight = toolbar.getHeight();
                Log.e(TAG, TAG + " # scrollY=" + scrollY + " # firstScroll=" + firstScroll + " # dragging=" + dragging);

                if (!dragging) {
                    if (webview.getScrollY() == 0) {
                        appBar.animate().setDuration(ANIMATION_DURATION).translationY(0f);
                    }
                }
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                int toolbarHeight = toolbar.getHeight();
                if (scrollState == ScrollState.DOWN) {


                    if (webview.getScrollY() == 0) {
                        appBar.animate().setDuration(ANIMATION_DURATION).translationY(0f);
                        webview.animate().setDuration(ANIMATION_DURATION).translationY(toolbarHeight);
                    } else {
                        appBar.animate().setDuration(ANIMATION_DURATION).translationY(0);
                    }
                } else if (scrollState == ScrollState.UP) {
                    if (webview.getTranslationY() == 0) {
//                        Log.e(TAG, TAG + " # if(swipeRefreshLayout.getScrollY() == 0) # swipeRefreshLayout.getTranslationY()=" + swipeRefreshLayout.getTranslationY());

                    } else {
//                        Log.e(TAG, TAG + " # if(swipeRefreshLayout.getScrollY() == 0) else # swipeRefreshLayout.getTranslationY()=" + swipeRefreshLayout.getTranslationY());
                        webview.animate().setDuration(ANIMATION_DURATION).translationY(0);
                    }

                    if (appBar.getTranslationY() == 0) {
                        appBar.animate().setDuration(ANIMATION_DURATION).translationY(-toolbarHeight);
                    }

                } else {
                    Log.e(TAG, TAG + " # ScrollState OTHER");
                }
            }
        });
        webview.post(new Runnable() {
            @Override
            public void run() {

                int toolbarHeight = toolbar.getHeight();
                webview.animate().setDuration(ANIMATION_DURATION).translationY(toolbarHeight);

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
