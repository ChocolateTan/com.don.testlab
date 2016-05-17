package com.don.nodelist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_list_view).setOnClickListener(this);
        findViewById(R.id.btn_recycler_view).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_list_view:startActivity(new Intent(this, ListViewActivity.class));break;
            case R.id.btn_recycler_view:startActivity(new Intent(this, RecyclerViewActivity.class));break;
        }
    }
}
