package com.jia.libui.MyProgress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.jia.libui.R;


/**
 * Created by wlw on 2017/8/31.
 */
public class MyHorizontalProgressbar extends ProgressBar {
/*设置一些初始常量，给用户默认使用的参数*/

    private static final int DEFAULT_TEXT_SIZE = 10;//sp 字体大小
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;// 字体颜色
    private static final int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;// 未加载的进度条颜色
    private static final int DEFAULT_HEIGHT_UNREACH = 2;//dp 未载的进度条高度
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;//  已加载的进度条颜色
    private static final int DEFAULT_HEIGHT_REACH = 2;//已加载的进度条高度
    private static final int DEFAULT_TEXT_OFFSET = 10;//

    //这些 属性是通用的会用于继承
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mUnReachHeight =dp2px(DEFAULT_HEIGHT_UNREACH) ;
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    //画笔
    protected Paint mPaint = new Paint();
    private int mRealWidth;//控件真实宽的， 控件宽度减去padding的值



    public MyHorizontalProgressbar(Context context) {
        this(context,null); //单参调用 双参
    }

    public MyHorizontalProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);//双参调用 三参
    }

    public MyHorizontalProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        obtainStyledAttrs(attrs);

    }


    /**
     * //获取自定义属性
     * @param attrs
     */
    private void obtainStyledAttrs(AttributeSet attrs) {
        //参数 自定义的属性集
         TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyHorizontalProgressbar);
         mTextSize = (int) ta.getDimension(R.styleable.MyHorizontalProgressbar_progress_text_size, mTextSize);
         mTextColor = ta.getColor(R.styleable.MyHorizontalProgressbar_progress_text_color,mTextColor);
         mTextOffset = (int) ta.getDimension(R.styleable.MyHorizontalProgressbar_progress_text_offset,mTextOffset);
         mReachColor = ta.getColor(R.styleable.MyHorizontalProgressbar_progress_reach_color,mReachColor);
         mReachHeight = (int) ta.getDimension(R.styleable.MyHorizontalProgressbar_progress_reach_height,mReachHeight);
         mUnReachColor = ta.getColor(R.styleable.MyHorizontalProgressbar_progress_unreach_color,mUnReachColor);
         mUnReachHeight = (int) ta.getDimension(R.styleable.MyHorizontalProgressbar_progress_unreach_height,mUnReachHeight);

         ta.recycle();
         mPaint.setTextSize(mTextSize);
    }
     //测量
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);//获取模式
        //对于水平控件而言 用户必须要给一个准确的值，所以不做测量
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取尺寸
        int height = measureHeight(heightMeasureSpec);//测量高度

        setMeasuredDimension(widthSize,height);//这个方法 明确了View的宽高 （测量完成）
        mRealWidth = getMeasuredWidth() - getPaddingLeft()-getPaddingRight();//实际绘制宽度等于测量宽度减去左右padding

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
     //draw rechbar
        canvas.translate(getPaddingLeft(),getHeight()/2);//将绘制点移动到 左起点，view的高度的中点
        boolean onNeedUnRech = false; //用于判断 UnRech 是否显示 （就是判断未下载不符的显示状态）
        float radio = getProgress() * 1.0f/getMax();   //计算加载进度比例 ， 当前进度值/总进度值
        float endX  =radio * mRealWidth - mTextOffset/2; //最终长度为， 比例乘以实际长度 - 字符间距的一半
        String text = radio*100+"%"; //文本显示内容
        int textWidth = (int)mPaint.measureText(text);//文本宽度
        float progressX = radio * mRealWidth;//下载完成的进度条长度
        if ((progressX+textWidth)>mRealWidth){
            progressX = mRealWidth-textWidth;
            onNeedUnRech = true;
        }
        if (endX>0){ //线条绘制reach bar
            mPaint.setColor(mReachColor);  //添加画笔颜色
            mPaint.setStrokeWidth(mReachHeight); //线条宽度
            canvas.drawLine(0,0,endX,0,mPaint);
        }

       //draw text
        mPaint.setColor(mTextColor);
        int y = (int) -(mPaint.descent()+mPaint.ascent()); //绘制y轴上的起点
        canvas.drawText(text,progressX,0,mPaint);

       //draw unreach bar
        if(!onNeedUnRech){
            float start = progressX + mTextOffset/2 +textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }


        canvas.restore();//恢复一下
    }

    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        //有三种模式 EXACTLY(精确),     UNSPECIFIED(),AT_MOST(后两种需要自己测量)
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);//获取模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//获取尺寸
        if (heightMode == MeasureSpec.EXACTLY){
            result = heightSize;
        }else
        {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            //比较三者的高度去最大值 作为控件的高度
            result = getPaddingTop()+getPaddingBottom()+ Math.max(Math.max(mReachHeight,mUnReachHeight), Math.abs(textHeight));
        }
        if (heightMode== MeasureSpec.AT_MOST) //但是当AT_MOST模式时 值不能超过heightSize
        {
            result  = Math.min(result,heightSize);
        }
        return result;
    }

    //尺寸dp转px
    protected int dp2px(int dpValue){
     return (int) TypedValue.applyDimension
             (TypedValue.COMPLEX_UNIT_DIP,dpValue,getResources().getDisplayMetrics());
    }
    //尺寸sp转px
    protected int sp2px(int dpValue){
        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_SP,dpValue,getResources().getDisplayMetrics());
    }

}
