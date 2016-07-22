package com.example.gltest;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
//        GLSurfaceView view = new GLSurfaceView(this);
//        view.setRenderer(new OpenGLRenderer());
//        setContentView(view);
        setContentView(R.layout.activity_main);

        surfaceView = (GLSurfaceView)findViewById(R.id.surfaceView);
        surfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

            }

            @Override
            public void onSurfaceChanged(GL10 gl10, int i, int i1) {

            }

            @Override
            public void onDrawFrame(GL10 gl10) {
                gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                        GL10.GL_DEPTH_BUFFER_BIT);
            }
        });
    }
}
