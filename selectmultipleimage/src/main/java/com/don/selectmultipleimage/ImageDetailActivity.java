package com.don.selectmultipleimage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "DetailActivity:image";
//    public static final String EXTRA_TRANSITIONNAME = "transitionName";
    private ImageView iv;
    private String trName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        iv = (ImageView)findViewById(R.id.iv);
//        final DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .considerExifParams(true)
//                .build();

//        trName = getIntent().getStringExtra(EXTRA_TRANSITIONNAME);

//        ViewCompat.setTransitionName(iv, trName);
        iv.post(new Runnable() {
            @Override
            public void run() {
                ImageLoader.getInstance().displayImage(getIntent().getStringExtra(EXTRA_IMAGE), iv);
            }
        });
//        ImageLoader.getInstance().displayImage(getIntent().getStringExtra(EXTRA_IMAGE), iv, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                scheduleStartPostponedTransition(iv);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        });

//        iv.setImageResource(R.mipmap.ic_launcher);
    }

    public static void launch(AppCompatActivity activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, "myicon");
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
//        intent.putExtra(EXTRA_TRANSITIONNAME, transitionName);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }
    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }
}
