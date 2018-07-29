package com.wlw.bookkeeptool.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;

public class AnimatorUtils {

    public static void scaleAnimation(View bt){
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha",1f,0.2f,1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX",1f,0.7f,1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY",1f,0.7f,1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(bt, holder1, holder2, holder3);
        objectAnimator.setDuration(400);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getAnimatedFraction();
                animation.getAnimatedValue();
                animation.getCurrentPlayTime();
            }
        });
        objectAnimator.start();
    }
}
