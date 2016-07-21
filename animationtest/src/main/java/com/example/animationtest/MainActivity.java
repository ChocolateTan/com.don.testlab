package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
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
    private boolean isMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFloatButton();
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
                vauleAnimationTest();
//                testChangeShape();
//                testChangeShape();
//                testRingIcon();
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
//                findViewById(R.id.ftn_menu).layout(oldLeft, oldTop + curValue, oldLeft + findViewById(R.id.ftn_menu).getWidth(), oldTop + curValue + findViewById(R.id.ftn_menu).getHeight());
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

        animatorSet.play(valueAnimator);
//        animatorSet.play(valueAnimatorSwitch);
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

    private void testRingIcon(){
//        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv, rotationHolder);
//        animator.setDuration(3000);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.start();

        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.25f, -40f);
        Keyframe frame3 = Keyframe.ofFloat(0.50f, 40f);
        Keyframe frame2 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame3, frame2);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(iv,frameHolder);
        animator.setDuration(3000);
        animator.start();
    }

    private void initFloatButton(){
        final FloatingActionButton ft = (FloatingActionButton) findViewById(R.id.ftn_menu);
        final FloatingActionButton ft1 = (FloatingActionButton) findViewById(R.id.ftn_menu1);
        final FloatingActionButton ft2 = (FloatingActionButton) findViewById(R.id.ftn_menu2);
        final FloatingActionButton ft3 = (FloatingActionButton) findViewById(R.id.ftn_menu3);
        final FloatingActionButton ft4 = (FloatingActionButton) findViewById(R.id.ftn_menu4);
        final FloatingActionButton ft5 = (FloatingActionButton) findViewById(R.id.ftn_menu5);

        ft1.setVisibility(View.GONE);
        ft2.setVisibility(View.GONE);
        ft3.setVisibility(View.GONE);
        ft4.setVisibility(View.GONE);
        ft5.setVisibility(View.GONE);

        ft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click 1", Toast.LENGTH_SHORT).show();
            }
        });
        ft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click 2", Toast.LENGTH_SHORT).show();
            }
        });
        ft3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click 3", Toast.LENGTH_SHORT).show();
            }
        });
        ft4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click 4", Toast.LENGTH_SHORT).show();
            }
        });
        ft5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click 5", Toast.LENGTH_SHORT).show();
            }
        });

        ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("test", "onClick");
                if(!isMenuOpen) {
                    doAnimation(ft1, 0, 500);
                    doAnimation(ft2, (int) (22.5 * 1), 500);
                    doAnimation(ft3, (int) (22.5 * 2), 500);
                    doAnimation(ft4, (int) (22.5 * 3), 500);
                    doAnimation(ft5, (int) (22.5 * 4), 500);
                }else{
                    doAnimationClose(ft1, 0, 500);
                    doAnimationClose(ft2, (int) (22.5 * 1), 500);
                    doAnimationClose(ft3, (int) (22.5 * 2), 500);
                    doAnimationClose(ft4, (int) (22.5 * 3), 500);
                    doAnimationClose(ft5, (int) (22.5 * 4), 500);
                }
            }
        });
    }

    private void doAnimation(final View view, final int degree, final int l){

        final int viewLeft = view.getLeft();
        final int viewTop = view.getTop();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, l);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                int currentX = (int) (Math.sin(degree*Math.PI/180) * current);
                int currentY = (int) (Math.cos(degree*Math.PI/180) * current);
                view.layout(viewLeft - currentX, viewTop - currentY, viewLeft + view.getWidth() - currentX, (viewTop - currentY + view.getHeight()));
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                view.setClickable(false);
                findViewById(R.id.ftn_menu).setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                findViewById(R.id.ftn_menu).setClickable(true);
                view.setClickable(true);
                isMenuOpen = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    private void doAnimationClose(final View view, final int degree, final int l){
        final int viewLeft = view.getLeft();
        final int viewTop = view.getTop();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, l);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current = (int) animation.getAnimatedValue();
                int currentX = (int) (Math.sin(degree*Math.PI/180) * current);
                int currentY = (int) (Math.cos(degree*Math.PI/180) * current);
                view.layout(viewLeft + currentX, viewTop + currentY, viewLeft + view.getWidth() + currentX, (viewTop + currentY + view.getHeight()));
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                findViewById(R.id.ftn_menu).setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
                view.setVisibility(View.GONE);
                findViewById(R.id.ftn_menu).setClickable(true);
                isMenuOpen = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
}
