package com.interactive.platform.actions;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class CircleLoadingAnimation extends Animation {

    private ProgressBar circle;

    private float oldSize;
    private float newSize;

    public CircleLoadingAnimation(ProgressBar circle, float newSize) {
        this.oldSize = circle.getSecondaryProgress();
        this.newSize = newSize;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        int upTo = (int) (oldSize + ((newSize - oldSize) * interpolatedTime));

        circle.setSecondaryProgress(upTo);
        circle.requestLayout();
    }
}
