package com.don.selectmultipleimage.selectpic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.don.selectmultipleimage.ImageBean;
import com.don.selectmultipleimage.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    private static final String TAG = SelectActivity.class.getSimpleName();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_done);
//        recyclerView.setHasFixedSize(true);
//                 RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//                 recyclerView.setLayoutManager(layoutManager);
//                 initData();
//                 adapter = new PersonAdapter(personList);
//                 adapter.setOnRecyclerViewListener(this);
//                 recyclerView.setAdapter(adapter);

        List<String> list = getSystemPhotoList(this);
        ArrayList<ImageBean> m = new ArrayList<>();
        for(int i=list.size() - 1; i>=0; i--){
            ImageBean item = new ImageBean();
            item.setImageName(list.get(i));
            item.setImageUrl("file://"+list.get(i));
            m.add(item);
        }

//        getP();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(gridLayoutManager);
        ImageAdapter adapter = new ImageAdapter(this, this, recyclerView, gridLayoutManager);
//        adapter.setHasStableIds(true);
        adapter.setData(m);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        getSystemPhotoList(this);
    }

    public List<String> getSystemPhotoList(Context context) {
        List<String> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0) return null; // 没有图片
        while (cursor.moveToNext()) {
            int index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(index); // 文件地址
            File file = new File(path);
            if (file.exists()) {
                result.add(path);
                Log.i(TAG, path);
            }
        }

        result.addAll(getP());
        return result;
    }

    public List<String> getP() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
//        String filePath = Environment.getExternalStorageDirectory().toString() + File.separator
//                + "image";
        // 得到该路径文件夹下所有的文件
//        File fileAll = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES););

        File fileAll = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = fileAll.listFiles();
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsImageFile(file.getPath())) {
                imagePathList.add(file.getPath());
            }
        }
        // 返回得到的图片列表
        return imagePathList;
    }

    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }


}
