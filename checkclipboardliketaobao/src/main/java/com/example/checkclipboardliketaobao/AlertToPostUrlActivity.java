package com.example.checkclipboardliketaobao;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class AlertToPostUrlActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Window window=getWindow();
//        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.flags= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//        wl.alpha=0.6f;//这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
//        window.setAttributes(wl);


        setContentView(R.layout.activity_alert_to_post_url);

        webView = ((WebView)findViewById(R.id.web_view));

        ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        if (!clipboard.hasPrimaryClip()) {
            Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
        }
        else {
            ClipData clipData = clipboard.getPrimaryClip();
            int count = clipData.getItemCount();
            CharSequence resultString = null;
            for (int i = 0; i < count; ++i) {

                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(this);

                resultString = str + "\n";
            }
            if(count > 0){

                ClipData.Item item = clipData.getItemAt(0);
                CharSequence str = item.coerceToText(this);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(str.toString());
            }
        }
    }
}
