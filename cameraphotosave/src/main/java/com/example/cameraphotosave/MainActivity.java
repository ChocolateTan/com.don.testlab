package com.example.cameraphotosave;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_GET_THE_THUMBNAIL = 1000;
    static final int REQUEST_FULL_PHOTO = 2000;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView ivImg;
    private CoordinatorLayout coordinatorLayout;

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
        if(checker.lacksPermissions(PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }else{
            Snackbar.make(coordinatorLayout, "permission get", Snackbar.LENGTH_INDEFINITE).show();
//            dispatchTakePictureIntent();
            dispatchTakePictureFullIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_GET_THE_THUMBNAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, TAG + "requestCode=" + requestCode + " # resultCode=" + resultCode);

        if (requestCode == REQUEST_GET_THE_THUMBNAIL && resultCode == RESULT_OK) {
            //Get the Thumbnail
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImg.setImageBitmap(imageBitmap);
        }
    }



    String mCurrentPhotoPath;

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

    private void dispatchTakePictureFullIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cameraphotosave.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_FULL_PHOTO);
            }
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
            }
            else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG,"Permission is granted");
                return true;
            }
        }

        private boolean lacksPermission(String permission) {
            if (Build.VERSION.SDK_INT >= 23) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
            }
            else { //permission is automatically granted on sdk<23 upon installation
                Log.v(TAG,"Permission is granted");
                return true;
            }
        }

    }
}
