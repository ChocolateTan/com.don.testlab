package com.don.selectmultipleimage.showimagedetail;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.don.selectmultipleimage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

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
//        iv.post(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoader.getInstance().displayImage(getIntent().getStringExtra(EXTRA_IMAGE), iv);
//            }
//        });
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        final ViewGroup.LayoutParams lp = iv.getLayoutParams();
        lp.height = point.y;
        lp.width = point.x;
        iv.setLayoutParams(lp);

//        final DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .showImageOnLoading(R.mipmap.ic_launcher)
//                .considerExifParams(true)
//                .build();
//
//        iv.post(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoader.getInstance().displayImage(getIntent().getStringExtra(EXTRA_IMAGE), iv, new ImageSize(lp.width, lp.height));
//            }
//        });

//        DrawableRequestBuilder<String> thumbnailRequest = Glide
//                .with( this)
//                .load( R.mipmap.ic_launcher);
//        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).resize(point.y, point.x).centerCrop().into(iv);
//        iv.post(new Runnable() {
//            @Override
//            public void run() {
//        DrawableRequestBuilder thumbnailRequest =
        Glide.with(ImageDetailActivity.this).load(getIntent().getStringExtra(EXTRA_IMAGE)).thumbnail(
                Glide.with(this).load(R.mipmap.ic_launcher))
                .into(iv);
//            }
//        });

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
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(transitionView, ((BitmapDrawable)((ImageView)transitionView).getDrawable()).getBitmap(), 0, 0);
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out);
        Intent intent = new Intent(activity, ImageDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
//        intent.putExtra(EXTRA_TRANSITIONNAME, transitionName);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
//        activity.startActivity(intent);
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
