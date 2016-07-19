package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    private int oldRight;
    public int oldBottom;
    private int width;
    private int height;
    private ViewGroup.LayoutParams lp;

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
                oldRight = iv.getRight();
                oldBottom = iv.getBottom();
                width = iv.getWidth();
                height = iv.getHeight();
            }
        });

//        oldTop = iv.getTop();
        btnStart = (Button) findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                vauleAnimationTest();
//                testChangeShape();
//                testChangeShape();
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click image", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_hi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testChangeShape();
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
        valueAnimatorRotation.setDuration(4000);
        valueAnimatorRotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
                iv.setRotation(curValue);
            }
        });

//        animatorSet.playTogether(valueAnimator, valueAnimatorRotation);

        ValueAnimator valueAnimatorSwitch = ValueAnimator.ofFloat(1f, 0.0f, 1f);
        valueAnimatorSwitch.setDuration(2000);
        valueAnimatorSwitch.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
//                iv.setRotationY(curValue);
//                iv.layout(oldLeft + curValue , oldTop, oldRight, oldBottom);
                //形變
                iv.setScaleX(curValue);
            }
        });

        animatorSet.play(valueAnimatorSwitch);
        animatorSet.start();
//        valueAnimator.start();
    }

    private void testChangeShape(){

        final Button btnHi = (Button) findViewById(R.id.btn_hi);
        final ViewGroup.LayoutParams lpBtnHi = btnHi.getLayoutParams();
        final int widthBtnHi = btnHi.getWidth();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(4000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                Log.v("test", " # curValue=" + curValue);
//                iv.getLayoutParams() = mEvaluator.evaluate(fraction, start, end);
                lpBtnHi.width = widthBtnHi + curValue;
                Log.v("test", " # lpBtnHi.width=" + lpBtnHi.width);
                btnHi.setLayoutParams(lpBtnHi);
            }
        });
        valueAnimator.start();


    }
}
