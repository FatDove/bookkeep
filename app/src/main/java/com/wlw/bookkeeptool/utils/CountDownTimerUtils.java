package com.wlw.bookkeeptool.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * 验证码倒计时工具类
 */
public class CountDownTimerUtils extends CountDownTimer {
    private TextView mTextView;
    private Context mContext;
    public CountDownTimerUtils(Context mContext, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);


        this.mTextView=textView;
        this.mContext=mContext;
        mTextView.setClickable(false);  //设置不可点击
    }

    @Override
    public void onTick(long millisUntilFinished) {
      //  mTextView.setBackgroundResource(R.drawable.bg_identify_code_press);//设置按钮为灰色(不可点击)
        mTextView.setText(millisUntilFinished/1000+"秒后可重新发送");

        //设置按钮上的文字，获取截取设置为红色
        ForegroundColorSpan span=new ForegroundColorSpan(Color.RED);
        SpannableString spannableString=new SpannableString(mTextView.getText().toString());
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mTextView.setText(spannableString);


}
    @Override
    public void onFinish() {
        mTextView.setText("重新获取验证码");
        mTextView.setClickable(true);//重新获得点击
        //  mTextView.setBackgroundResource(R.drawable.bg_identify_code_normal);  //还原背景色
    }

    }
