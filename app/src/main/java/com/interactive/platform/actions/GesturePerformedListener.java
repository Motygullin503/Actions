package com.interactive.platform.actions;

import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.widget.Toast;

import java.util.ArrayList;

public class GesturePerformedListener implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary mLibrary;
    private Context mContext;

    public GesturePerformedListener(Context context, GestureLibrary library) {

        mContext = context;
        mLibrary = library;

    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {


        ArrayList<Prediction> predictions =
                mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 4.0) {

            String action = predictions.get(0).name;

            Toast.makeText(mContext, action + ": "+ predictions.get(0).score, Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(mContext, String.valueOf(predictions.get(0).score), Toast.LENGTH_SHORT).show();
        }



    }
}
