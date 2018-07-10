package com.jia.libui.MyProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.jia.libui.R;


/**
 * Created by FanJiaLong on 2017/9/1.
 */
public class MyRoundProgressBa extends MyHorizontalProgressbar{
    private int mRadius = dp2px(30);
    private int mMaxPaintWidth;//用来计算view的大小  园的真实半径为mRadius + 画笔宽度的一半
    private RectF rectF;

    public MyRoundProgressBa(Context context) {
        this(context,null);
    }

    public MyRoundProgressBa(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRoundProgressBa(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mReachHeight = (int) (mUnReachHeight*1.0f); //这里内外圈的比例
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyRoundProgressBar); //在原有属性的基础上，添加自定义属性
        mRadius  = (int)ta.getDimension(R.styleable.MyRoundProgressBar_radius, mRadius);//当在XML中声明值时就会赋值在这里
        ta.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true); //锯齿
        mPaint.setDither(true);//不抖动
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);//方形 线帽
        mPaint.setStrokeJoin(Paint.Join.MITER);//锐角
        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mMaxPaintWidth = Math.max(mReachHeight,mUnReachHeight);
        //因为是原型 默认四个padding 一致
        int expect = mRadius*2+mMaxPaintWidth+getPaddingRight()+getPaddingLeft();
        int width = resolveSize(expect,widthMeasureSpec);
        int height = resolveSize(expect,heightMeasureSpec);

        int readWidth = Math.min(width,height);//因为要绘制一个圆，当设定的宽高不一样是，取小值
        mRadius = (readWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth)/2;
        setMeasuredDimension(readWidth,readWidth);//确定好测量的宽高

        rectF = new RectF(0,0,mRadius*2,mRadius*2);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String text = getProgress()+"%";
        float textWidth  = mPaint.measureText(text);
        float textHeight = (mPaint.descent()+mPaint.ascent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft()+mMaxPaintWidth/2,getPaddingTop()+mMaxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);

        //draw unreach bar
        mPaint.setColor(mUnReachColor);
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);
        //draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(rectF,0,sweepAngle,false,mPaint);
        // draw text
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        canvas.drawText(text,mRadius-textWidth/2,mRadius-textHeight/2,mPaint);


        canvas.restore();

    }
}
