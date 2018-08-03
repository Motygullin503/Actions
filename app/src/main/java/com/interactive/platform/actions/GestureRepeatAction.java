package com.interactive.platform.actions;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class GestureRepeatAction {

    private GestureLibrary gLibrary;

    private ViewGroup mContainer;
    private View mView;
    private GestureOverlayView overlay;
    private Activity mActivity;


    public GestureRepeatAction(Activity activity, ViewGroup container) {

        LayoutInflater inflater = LayoutInflater.from(activity);

        mView = inflater.inflate(R.layout.gesture_overlay, null);

        mContainer = container;
        mActivity = activity;

        overlay = mView.findViewById(R.id.overlay);
        mContainer.bringToFront();


        gLibrary = GestureLibraries.fromRawResource(mActivity, R.raw.gestures);
        gLibrary.load();

        overlay.addOnGesturePerformedListener(new GesturePerformedListener(mActivity, gLibrary));


    }

    public void addAction() {

        mContainer.addView(mView);

    }






}
