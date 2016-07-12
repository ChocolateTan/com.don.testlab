package com.don.selectmultipleimage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = ImageActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        List<String> list = getSystemPhotoList(this);
        ArrayList<ImageBean> m = new ArrayList<>();
        for(int i=list.size() - 1; i>=0; i--){
            ImageBean item = new ImageBean();
            item.setImageName(list.get(i));
            item.setImageUrl("file://"+list.get(i));
            m.add(item);
        }

        Point outPoint = new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(outPoint);

        mAdapter = new ImageAdapter(this, m, outPoint);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new NoAlphaItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    private class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final DisplayImageOptions options;
        private final Point mOutPoint;
        private final AppCompatActivity mActivity;
        private ArrayList<ImageBean> mData = new ArrayList<>();

        ImageAdapter(ImageActivity imageActivity, ArrayList<ImageBean> data, Point outPoint){
            this.mData = data;
            this.mOutPoint = outPoint;
            this.mActivity = imageActivity;

            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .considerExifParams(true)
                    .build();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_recycler_view, null);
            return new ItemView(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemView view = (ItemView) holder;
            view.setData(mActivity, mData.get(position), position);
        }

        @Override
        public int getItemCount() {
            Log.v(TAG, TAG + " # mData.size()=" + mData.size());
            return mData.size();
        }

        private class ItemView extends RecyclerView.ViewHolder{

            private final ImageView iv;

            public ItemView(View itemView) {
                super(itemView);
                 iv = (ImageView) itemView.findViewById(R.id.iv_img);
            }

            public void setData(final AppCompatActivity mActivity, final ImageBean bean, final int position){
                int width = mOutPoint.x / 2;
                ViewGroup.LayoutParams lp = iv.getLayoutParams();
                lp.height = width;
                lp.width = width;
                iv.setLayoutParams(lp);

//                if (iv.getTag() != null && iv.getTag().equals(bean.getImageUrl())) {
//
//                }else{
                    ImageLoader.getInstance().displayImage(bean.getImageUrl(), iv, options);
                    iv.setTag(bean.getImageUrl());
//                }
//                final String trName = "imageTransition" + position;
//                ViewCompat.setTransitionName(iv, trName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        itemView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                ImageDetailActivity.launch(mActivity, iv, bean.getImageUrl());
//                            }
//                        });

                        ImageDetailActivity.launch(mActivity, iv, bean.getImageUrl());
                    }
                });
            }
        }
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
