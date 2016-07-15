package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int x = 100;
    private int rotation = 360;
    private ImageView iv;
    private Button btnStart;
    private int oldTop = 0;
    private int oldLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.iv_img);
        iv.post(new Runnable() {


            @Override
            public void run() {
//                oldTop = (int) iv.getY();
                oldTop = iv.getTop();
                oldLeft = iv.getLeft();
            }
        });

//        oldTop = iv.getTop();
        btnStart = (Button) findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vauleAnimationTest();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click image", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void vauleAnimationTest(){
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 400);
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
//                Log.v("test", " # curValue=" + curValue);
                //45度移動
                iv.layout(oldLeft + curValue, oldTop + curValue, oldLeft + curValue + iv.getWidth(), oldTop + curValue + iv.getHeight());
//                //垂直移動
//                iv.layout(oldLeft, oldTop + curValue, oldLeft + iv.getWidth(), oldTop + curValue + iv.getHeight());
//                //放大
//                iv.layout(oldLeft, oldTop, oldLeft + curValue / 100 + iv.getWidth(), oldTop + curValue / 100 + iv.getHeight());
//                //縮小
//                iv.layout(oldLeft + curValue + 1, oldTop + curValue + 1, oldLeft + iv.getWidth()+ curValue, oldTop + iv.getHeight()+ curValue);
            }
        });
        ValueAnimator valueAnimatorRotation = ValueAnimator.ofInt(0, 360);
        valueAnimatorRotation.setDuration(1000);
        valueAnimatorRotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                iv.setRotation(curValue);
            }
        });

        animatorSet.playTogether(valueAnimator, valueAnimatorRotation);
        animatorSet.start();
//        valueAnimator.start();
    }
}
