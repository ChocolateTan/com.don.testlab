package com.don.selectmultipleimage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnSelect;
    static final int REQUEST_GET_THE_THUMBNAIL = 1000;
    static final int REQUEST_FULL_PHOTO = 2000;

    private ImageView ivImg;
    private CoordinatorLayout coordinatorLayout;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImg = (ImageView) findViewById(R.id.iv_img);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
            }
        });
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .showImageForEmptyUri(R.mipmap.photo_place_holder_image)
//                .showImageOnFail(R.mipmap.photo_place_holder_image)
//                .showImageOnLoading(R.mipmap.photo_place_holder_image)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
//                .diskCache(new UnlimitedDiskCache(cacheDir))
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(30 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectActivity.class));
            }
        });

        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission();
            }
        });
        findViewById(R.id.btn_view_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageActivity.class));
            }
        });
    }

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //Snackbar.make(coordinatorLayout, "no permission", Snackbar.LENGTH_INDEFINITE).show();
    private void permission() {
        PermissionsChecker checker = new PermissionsChecker(this);
        if (checker.lacksPermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        } else {
            Snackbar.make(coordinatorLayout, "permission get", Snackbar.LENGTH_INDEFINITE).show();
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            File file = new File(createImageFile());
            Uri imageUri = null;
            try {
                imageUri = Uri.fromFile(createImageFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, REQUEST_GET_THE_THUMBNAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, TAG + "requestCode=" + requestCode + " # resultCode=" + resultCode + " # mCurrentPhotoPath=" + mCurrentPhotoPath);

        if (requestCode == REQUEST_GET_THE_THUMBNAIL && resultCode == RESULT_OK) {
            //Get the Thumbnail//将保存在本地的图片取出并缩小后显示在界面上
//            Bitmap bitmap = BitmapFactory.decodeFile(new File(mCurrentPhotoPath));
            //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常


            //将处理过的图片显示在界面上，并保存到本地

//            ivImg.setImageBitmap(bitmap);
//            bitmap.recycle();
        }
    }


    class PermissionsChecker {

        private final Context context;

        public PermissionsChecker(Context context) {
            this.context = context;
        }

        public boolean lacksPermissions(String... permissions) {
            if (Build.VERSION.SDK_INT >= 23) {
                for (String permission : permissions) {
                    if (lacksPermission(permission)) {
                        return true;
                    }
                }
                return false;
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG, "Permission is granted");
                return false;
            }
        }

        private boolean lacksPermission(String permission) {
            if (Build.VERSION.SDK_INT >= 23) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG, "Permission is granted");
                return false;
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
}
