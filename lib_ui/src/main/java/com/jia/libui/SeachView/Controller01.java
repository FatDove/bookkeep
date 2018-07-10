package com.jia.libui.SeachView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.EditText;



/**
 * Created by wlw on 2018/3/16.
 */

public class Controller01 extends BaseController {
    private String mColor = "#4CAF50";//暗绿色 //控件的背景色
//    private String mColor = "#FFF48F";//黄色
    private int cx, cy, cr;  //圆的中心坐标点和 半径
    private RectF mRectF; //圆的外切矩形
    private int j = 10;
private EditText et;
    int rotate = 45; //画板旋转角度rotate
    public Controller01(EditText et) {
        mRectF = new RectF();
        this.et = et;
    }
    @Override
    public void draw(Canvas canvas, Paint paint) {
//        canvas.drawColor(Color.parseColor(mColor));
        canvas.drawColor(mySearchView.mViewBackground);
        switch (mState) {
            case STATE_ANIM_NONE:
                drawNormalView(paint, canvas);
                break;
            case STATE_ANIM_START:
                drawStartAnimView(paint, canvas);
                break;
            case STATE_ANIM_STOP:
//            	drawNormalView(paint, canvas);
                drawStopAnimView(paint, canvas);
                break;
        }
    }

    private void drawNormalView(Paint paint, Canvas canvas) {
//        cr =getwidth()/10;
        cr =getheight()/4;    //
        cx =getheight()/2;
        cy =getheight()/2;
//        cy =getwidth()/2;

        mRectF.left  = cx - cr;
        mRectF.right = cx + cr;
        mRectF.top = cy - cr;
        mRectF.bottom = cy + cr;

        canvas.save();
//        paint.reset();   //在这个重置了画笔
//        paint.setAntiAlias(true);
//        paint.setStrokeWidth(5);
//        paint.setStyle(Paint.Style.STROKE);

        canvas.rotate(rotate,cx,cy);  //画板旋转角度rotate
        canvas.drawLine(cx+cr,cy,cy+cr*2,cy,paint);
        canvas.drawArc(mRectF,
                0,  //圆的起始角度
                360,  //绘制路径的角度
                false,paint);
    }

    private void drawStartAnimView(Paint paint, Canvas canvas) {
        canvas.save();
        //0-1  morp 绘制的进度
        /**
         * -360 ~ 0 度 需要变换的范围
         * 0 ~ 0.5  实际变换进度
         * 转换公式：360*（mpro*2-1）
         */

        if (mpro <=0.5f ){
            //绘制圆和把手
            canvas.drawArc(mRectF,
                    rotate,
                    360*(mpro*2-1),
                    false,
                    paint);

            canvas.drawLine(mRectF.right-j,
                    mRectF.bottom-j,
                    mRectF.right+cr-j,
                    mRectF.bottom+cr-j,
                    paint);

        }else{
            /**
             * 0 ~ 1 度 需要变换的范围
             * 0.5~ 1  实际变换进度
             * 转换公式：（mpro*2-1）
             */
//            绘制把手
            canvas.drawLine(
                    mRectF.right-j+cr*(mpro*2-1),
                    mRectF.bottom-j+cr*(mpro*2-1),
                    mRectF.right+cr-j,
                    mRectF.bottom+cr-j,
                    paint);
        }
           //绘制底部的横线
        canvas.drawLine((mRectF.right-j+cr)*(1-mpro*0.9f),
                mRectF.bottom+cr -j,
                mRectF.right - j+cr,
                mRectF.bottom+cr-j,
                 paint
                );
        //让放大镜的 【外接矩形】 不断往右走
         mRectF.left = cx - cr + mpro*(getwidth()*0.8f-50);
         mRectF.right = cx + cr + mpro*(getwidth()*0.8f-50);
         mRectF.top = cy - cr;
         mRectF.bottom = cy + cr;

         canvas.restore();
    }

    private void drawStopAnimView(Paint paint, Canvas canvas) {

        canvas.save();
        //0-1  morp 绘制的进度

        if (mpro <=0.5f ){
            /**
             * 0 ~ 1 度 需要变换的范围
             * 0.0~ 0.5  实际变换进度
             * 转换公式：（mpro*2-1）
             */
//            绘制把手
            canvas.drawLine(
                    mRectF.right-j+cr*(1-mpro*2),
                    mRectF.bottom-j+cr*(1-mpro*2),
                    mRectF.right+cr-j,
                    mRectF.bottom+cr-j,
                    paint);


        }else{
            /**
             * -360 ~ 0 度 需要变换的范围
             * 0 ~ 0.5  实际变换进度
             * 转换公式：360*（mpro*2-1）
             */

            //绘制圆和把手
            canvas.drawArc(mRectF,
                    rotate,
                    360*(1-mpro*2),
                    false,
                    paint);

            canvas.drawLine(mRectF.right-j,
                    mRectF.bottom-j,
                    mRectF.right+cr-2*j,
                    mRectF.bottom+cr-2*j,
                    paint);

        }
        //绘制底部的横线
//        canvas.drawLine((mRectF.right-j+cr)*(1-mpro*0.8f),
        canvas.drawLine((mRectF.right-j+cr)*(0.2f+0.8f*mpro),
                mRectF.bottom+cr -j,
                mRectF.right - j+cr,
                mRectF.bottom+cr-j,
                paint
        );

        //让放大镜的 【外接矩形】不断往左走
        mRectF.left = cx - cr  + (1-mpro)*(getwidth()*0.8f-50);
        mRectF.right = cx + cr  + (1-mpro)*(getwidth()*0.8f-50);
        mRectF.top = cy - cr;
        mRectF.bottom = cy + cr;

        canvas.restore();

    }

    @Override
    public void startAnim() {
        super.startAnim();
        mState = STATE_ANIM_START;
        startViewAnimation().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                et.setVisibility(View.VISIBLE);
                et.setFocusable(true);
                et.setFocusableInTouchMode(true);
                et.requestFocus();
//                edit.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    @Override
    public void restartAnim() {
        super.restartAnim();
        mState = STATE_ANIM_STOP;
        startViewAnimation().addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.i("onAnimationStart", "关闭动画开始了: ");
                et.setText("");
                et.setVisibility(View.INVISIBLE);
            }

//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                Log.i("onAnimationEnd", "关闭动画结束了: ");
//                et.setText("");
//                et.setVisibility(View.INVISIBLE);
//            }
        });
    }
}
