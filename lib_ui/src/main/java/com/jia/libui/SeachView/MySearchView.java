package com.jia.libui.SeachView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jia.libui.R;


/**
 * Created by wlw on 2018/3/16.
 */

public class MySearchView  extends View {


    //这些 属性是通用的会用于继承
    public int mPaintWidth = 10;
    public int mPaintColor = 0xFFFC00D1;
    public int mPaintStyle = 1;
    public int mViewBackground = 0x5290374;
    public int radius = getHeight()/4;
    BaseController baseController;
    private Paint mPaint;

    public MySearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MySearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setColor(mPaintColor);
        if (mPaintStyle==1){
            mPaint.setStyle(Paint.Style.FILL);
        }else if (mPaintStyle==0){
            mPaint.setStyle(Paint.Style.STROKE);
        }

    }
    public void setController(BaseController baseController){
        this.baseController = baseController;
        this.baseController.setSearchView(this);  //在初始化 baseController的时候，去设置SearchView
        this.baseController.setSearchView(this);
        invalidate();
//        Color.parseColor(mColor)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        baseController.draw(canvas,mPaint);
    }
    public void startAnimation(){
        if (baseController!=null){
            baseController.startAnim();
        }

    }
    public void restartAnimation(){
        if (baseController!=null){
            baseController.restartAnim();
        }
    }
    private void obtainStyledAttrs(AttributeSet attrs) {
        //参数 自定义的属性集
        // <attr name="radius" format="dimension"></attr>
//    <attr name="paintWidth" format="dimension"></attr>
//    <attr name="paintColor" format="color"></attr>
//    <attr name="viewBackground" format="color"></attr>
//    <attr name="paintStyle">
//        <enum name="STROKE" value="0" />
//        <enum name="FILL" value="1" />
//    </attr>

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MySearchViewAttr);
        mPaintWidth = (int) ta.getDimension(R.styleable.MySearchViewAttr_paintWidth, mPaintWidth);
//        radius = (int) ta.getDimension(R.styleable.MySearchViewAttr_radius, radius);
        mPaintColor = ta.getColor(R.styleable.MySearchViewAttr_paintColor,mPaintColor);
        mPaintStyle = ta.getInt(R.styleable.MySearchViewAttr_paintStyle,mPaintStyle);
        mViewBackground = ta.getColor(R.styleable.MySearchViewAttr_viewBackground,mViewBackground);

        ta.recycle();
//        mPaint.setTextSize(mTextSize);
    }
}
