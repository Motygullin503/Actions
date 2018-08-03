package com.interactive.platform.actions;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class Ball extends AppCompatImageView {

    private float mPosX;
    private float mPosY;

    public Ball(Context context) {
        super(context);
    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public float getPosX() {
        return mPosX;
    }

    public void setPosX(float posX) {
        mPosX = posX;
    }

    public float getPosY() {
        return mPosY;
    }

    public void setPosY(float posY) {
        mPosY = posY;
    }


}
