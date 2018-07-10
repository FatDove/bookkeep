package com.jia.libui.SeachView;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;


/**
 * Created by wlw on 2018/3/16.
 */

public abstract class BaseController {
    public static final int STATE_ANIM_NONE = 0;
    public static final int STATE_ANIM_START = 1;
    public static final int STATE_ANIM_STOP = 2;
    public static final int DEFAULT_ANIM_TIME = 5000;
    public static final float DEFAULT_ANIM_STARTF = 0;
    public static final float DEFAULT_ANIM_ENDF = 1;

    public int mState = STATE_ANIM_NONE;

    public float mpro=-1;  //动画的进度实际0-1
    protected MySearchView mySearchView;
    private EditText editText;

    public abstract void draw (Canvas canvas, Paint paint);

    public void startAnim(){

    }
    public void restartAnim(){

    }
    public int getwidth(){
        return  mySearchView.getWidth();
    }
    public int getheight(){
        return mySearchView.getHeight();
    }

    public void setSearchView(MySearchView mySearchView){
     this.mySearchView = mySearchView;
    }
    public void setEditText(EditText editText){
        this.editText = editText;
    }
    public ValueAnimator startViewAnimation(){
        ValueAnimator valueAnimator =  ValueAnimator.ofFloat(0f,1f);//参数是动画起止的标识量（）
        valueAnimator.setDuration(800l);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mpro =  (float) animation.getAnimatedValue();
                mySearchView.invalidate();
            }

        });

        valueAnimator.setStartDelay(10);
        valueAnimator.start();
        mpro=0;
        return valueAnimator;
    }

}
