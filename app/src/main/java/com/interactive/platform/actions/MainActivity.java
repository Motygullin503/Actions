package com.interactive.platform.actions;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView ball;
    ImageView ramp;
    PropertyValuesHolder pvhX;
    PropertyValuesHolder pvhY;
    float translationX = pxToDp(200f);
    float translationY = pxToDp(70f);
    float rotation = pxToDp(16f);
    boolean onwards = true;
    ObjectAnimator mRotation;
    ObjectAnimator translation;
    PathMeasure pm;
    float point[] = {0f, 0f};
    float startAngle = 0f;
    float sweepAngle = -180f;
    float prevAngle = 0f;

    Path path = new Path();
    ViewGroup container;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ball = findViewById(R.id.ball);
        ramp = findViewById(R.id.ramp);
        container = findViewById(R.id.container);

        mRotation = ObjectAnimator.ofFloat(ramp, "rotation", rotation);
        mRotation.setDuration(5000);
        mRotation.start();

        SensorEvent mSensorEvent = new SensorEvent(this);

        pvhX = PropertyValuesHolder.ofFloat("translationX", translationX);
        pvhY = PropertyValuesHolder.ofFloat("translationY", translationY);


//        translation = ObjectAnimator.ofPropertyValuesHolder(ball, pvhX, pvhY);
//        translation.setDuration(5000);
//        translation.start();

//        translation.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//                translationX = -translationX;
//
//                rotation = -rotation;
//
//
//
//                pvhX = PropertyValuesHolder.ofFloat("translationX", translationX);
//                pvhY = PropertyValuesHolder.ofFloat("translationY", translationY);
//
//                mRotation.setFloatValues(rotation);
//                mRotation.start();
//
//                translation.setValues(pvhX, pvhY);
//                translation.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });


    }


    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static float pxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }


    private class SensorEvent implements SensorEventListener {

        float[] history = new float[1];
        String[] direction = {"NONE"};
        String previous  = "NONE";

        public SensorEvent(Context context) {

            SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

        }

        @Override
        public void onSensorChanged(android.hardware.SensorEvent event) {

            float xChange = history[0] - event.values[0];

            history[0] = event.values[0];


            if (xChange > 3 && (previous.equals("RIGHT") || previous.equals("NONE"))) {

                direction[0] = "LEFT";
                previous = direction[0];
                startAngle = sweepAngle;
                sweepAngle = -sweepAngle;

                Path path = new Path();
                path.arcTo(new RectF(0, 0, 1000, 400), startAngle, sweepAngle);
                pm = new PathMeasure(path, false);

                ValueAnimator a = ValueAnimator.ofFloat(0.0f, 1.0f);

                ValueAnimator.AnimatorUpdateListener listener =
                        new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                float val = animation.getAnimatedFraction();
                                pm.getPosTan(pm.getLength() * val, point, null);
                                ball.setTranslationX(point[0]);
                                ball.setTranslationY(point[1]);
                            }
                        };


                a.setDuration(5000);
                a.setInterpolator(new AccelerateDecelerateInterpolator());
                a.addUpdateListener(listener);
                a.start();

            } else if (xChange < -3 && (previous.equals("LEFT") || previous.equals("NONE") )) {
                direction[0] = "RIGHT";
                previous = direction[0];
                startAngle = 180 - startAngle;
                sweepAngle = -sweepAngle;

                Path path = new Path();
                path.arcTo(new RectF(-600, 0, 600, 600), startAngle, sweepAngle);
                pm = new PathMeasure(path, false);

                ValueAnimator a = ValueAnimator.ofFloat(0.0f, 1.0f);

                ValueAnimator.AnimatorUpdateListener listener =
                        new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {

                                float val = animation.getAnimatedFraction();
                                pm.getPosTan(pm.getLength() * val, point, null);
                                ball.setTranslationX(point[0]);
                                ball.setTranslationY(point[1]);
                            }
                        };


                a.setDuration(5000);
                a.setInterpolator(new AccelerateDecelerateInterpolator());
                a.addUpdateListener(listener);
                a.start();

            }

            Log.d("direction", direction[0]);




        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


    }

}


//    ObjectAnimator scaleDown;
//    CircleLoadingAnimation mLoadingAnimation;
//    boolean isLongPressed = false;
//
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        ImageView iv2  = findViewById(R.id.image2);
//        final ImageView iv = findViewById(R.id.image);
//        final ProgressBar pb = findViewById(R.id.pb);
//        createAnimation(iv2, pb);
//
////        iv.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                scaleDown.start();
////            }
////        });
//
//        iv.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                iv.setVisibility(View.INVISIBLE);
//                mLoadingAnimation.start();
//                isLongPressed = true;
//                return true;
//            }
//        });
//
//        iv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scaleDown.start();
//            }
//        });
//
//
//        iv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.onTouchEvent(event);
//
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                    if (isLongPressed) {
//
//                        isLongPressed = false;
//                        iv.setVisibility(View.VISIBLE);
//
//
//                        mLoadingAnimation.reset();
//                        pb.setSecondaryProgress(0);
//                    }
//                }
//                return false;
//            }
//
//
//        });
//
//
////        iv.setOnTouchListener(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////
////                switch (event.getAction()) {
////
////                    case MotionEvent.ACTION_POINTER_DOWN:
////                        scaleDown.start();
////                        iv.setVisibility(View.INVISIBLE);
////
////                        mLoadingAnimation.start();
////                        break;
////                    case MotionEvent.ACTION_UP:
////                        iv.setVisibility(View.VISIBLE);
////                        pb.setSecondaryProgress(0);
////                        mLoadingAnimation.cancel();
////                        break;
////                }
////
////                return true;
////            }
////        });
//
//
//    }
//
//
//    private void createAnimation(ImageView iv, ProgressBar progressBar) {
//        scaleDown = ObjectAnimator.ofPropertyValuesHolder(
//                iv,
//                PropertyValuesHolder.ofFloat("scaleX", 0.8f),
//                PropertyValuesHolder.ofFloat("scaleY", 0.8f));
//        scaleDown.setDuration(100);
//        scaleDown.setInterpolator(new FastOutSlowInInterpolator());
//        scaleDown.setRepeatCount(1);
//        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
//
//
//        mLoadingAnimation = new CircleLoadingAnimation(progressBar, 100);
//        mLoadingAnimation.setDuration(3000);
//        mLoadingAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        progressBar.setAnimation(mLoadingAnimation);
//    }
//
//    private void addGestureRepeatAction() {
//
////        GestureRepeatAction mAction = new GestureRepeatAction(this, container);
////        mAction.addAction();
//    }

