package com.example.checkclipboardliketaobao;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isOpen == true){
            isOpen = false;
        }else {
            clipboardChecker();
        }
    }
    private void clipboardChecker(){
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
                Log.v(TAG, TAG + "item : " + i + ": " + str);

                resultString = str + "\n";
            }
            if(count > 0){
                isOpen = true;
                startActivity(new Intent(this, AlertToPostUrlActivity.class));
            }
        }
    }
}
