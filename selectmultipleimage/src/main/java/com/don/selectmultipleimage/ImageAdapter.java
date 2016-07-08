package com.don.selectmultipleimage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.ExifInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by don on 16/05/11.
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final RecyclerView mRecyclerView;
    private final GridLayoutManager mGridLayoutManager;
    private final AppCompatActivity mAct;
    ArrayList<ImageBean> mData = new ArrayList<>();
    private DisplayImageOptions options;
    private int selectCount = 0;

    public ImageAdapter(Context ctx, AppCompatActivity act, RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        this.mContext = ctx;
        this.mAct = act;
        this.mRecyclerView = recyclerView;
        this.mGridLayoutManager = gridLayoutManager;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
                .considerExifParams(true)
                .build();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_pic, null);
        ImageViewHolder holder = new ImageViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Point size = new Point();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);

        final ImageViewHolder viewHolder = (ImageViewHolder) holder;
        ViewGroup.LayoutParams lp = viewHolder.ivImage.getLayoutParams();
        lp.height = size.x / 3;
        lp.width = size.x / 3;

        viewHolder.ivImage.setLayoutParams(lp);

        ViewGroup.LayoutParams lp2 = viewHolder.ivFrame.getLayoutParams();
        lp2.height = size.x / 3;
        lp2.width = size.x / 3;

        viewHolder.ivFrame.setLayoutParams(lp2);

        Log.i("image degree", mData.get(position).getImageUrl() + " # " + readPictureDegree(mData.get(position).getImageName()));
//        viewHolder.ivImage.setTag(mData.get(position).getImageUrl());
        ImageLoader.getInstance().displayImage(mData.get(position).getImageUrl(), viewHolder.ivImage, options);
//        viewHolder.ivImage.setImageResource(R.mipmap.ic_launcher);
        if (mData.get(position).getSelected() != 0) {

            viewHolder.ivFrame.setVisibility(View.VISIBLE);
            viewHolder.tvNum.setVisibility(View.VISIBLE);
        } else {

            viewHolder.ivFrame.setVisibility(View.GONE);
            viewHolder.tvNum.setVisibility(View.GONE);
        }

        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.get(position).getSelected() != 0) {

                    findNeedToUpdateItem(mData.get(position).getSelected());
                    mData.get(position).setSelected(0);
                    selectCount = selectCount - 1;

                    viewHolder.ivFrame.setVisibility(View.GONE);
                    viewHolder.tvNum.setVisibility(View.GONE);

                    notifyItemChanged(position);
                } else {
                    selectCount = selectCount + 1;
                    mData.get(position).setSelected(selectCount);
                    viewHolder.ivFrame.setVisibility(View.VISIBLE);
                    viewHolder.tvNum.setVisibility(View.VISIBLE);

                    viewHolder.tvNum.setText("" + mData.get(position).getSelected());
//                    scaleUpAnimation(viewHolder.ivImage, mData.get(position).getImageUrl());
                    ImageDetailActivity.launch(mAct, viewHolder.ivImage, mData.get(position).getImageUrl());
//                    mContext.startActivity();
                }
            }
        });

        viewHolder.tvNum.setText("" + mData.get(position).getSelected());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<ImageBean> data) {
        this.mData = data;
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivImage;
        private final ImageView ivFrame;
        private final TextView tvNum;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_img);
            ivFrame = (ImageView) itemView.findViewById(R.id.iv_frame);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);

            ivFrame.setVisibility(View.GONE);
            tvNum.setVisibility(View.GONE);
        }
    }

    /**
     * can not read path -> file://xxxxxxxxxxxx
     * but remove file:// work ok
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
        }
        return degree;
    }

    private void findNeedToUpdateItem(int cancelCurrentNum) {
        for (int i = 0, size = mData.size(); i < size; i++) {
            if (mData.get(i).getSelected() > cancelCurrentNum) {
                mData.get(i).setSelected(mData.get(i).getSelected() - 1);
                updateItemView(i);
            }
        }
    }

    private void updateItemView(int position) {
        //得到第一个可显示控件的位置，
        int visiblePosition = mGridLayoutManager.findFirstVisibleItemPosition();//.getFirstVisiblePosition();
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        int index = position - visiblePosition;
        Log.i("test", "visiblePosition=" + visiblePosition + " # index=" + index);
        if (index >= 0) {
            //得到要更新的item的view
            View view = mRecyclerView.getChildAt(index);
            //从view中取得holder
            if (null == view || null == view.getTag()) return;
            ImageViewHolder holder = (ImageViewHolder) view.getTag();
            int old = Integer.parseInt(holder.tvNum.getText().toString());
            old = old - 1;
            holder.tvNum.setText("" + old);
        }
    }

    public ArrayList<ImageBean> getSelectImage(){
        ArrayList<ImageBean> list = new ArrayList<>();
        for(int i = 0, size = mData.size(); i < size ; i ++){
            if(mData.get(i).getSelected() > 0){
                list.add(mData.get(i));
            }
        }

        return list;
    }

    private void scaleUpAnimation(View view, String url) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int)view.getWidth()/2, (int)view.getHeight()/2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        startNewAcitivity(options, url);
    }

    private void startNewAcitivity(ActivityOptionsCompat options, String url) {
        Intent intent = new Intent(mAct,ImageDetailActivity.class);
        intent.putExtra(ImageDetailActivity.EXTRA_IMAGE, url);
        ActivityCompat.startActivity(mAct, intent, options.toBundle());
    }
}
