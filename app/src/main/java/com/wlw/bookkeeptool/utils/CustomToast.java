package com.wlw.bookkeeptool.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.wlw.bookkeeptool.MyApplication;
import com.wlw.bookkeeptool.R;

public class CustomToast {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void showShort(@LayoutRes final int layoutRes,@NonNull final CharSequence text) {
        show(layoutRes,text, Toast.LENGTH_SHORT);
    }

    public static void showShort(@LayoutRes final int layoutRes,@StringRes final int resId) {
        show(layoutRes,resId, Toast.LENGTH_SHORT);
    }

    public static void showShort(@LayoutRes final int layoutRes,@StringRes final int resId, final Object... args) {
        show(layoutRes,resId, Toast.LENGTH_SHORT, args);
    }

    public static void showShort(@LayoutRes final int layoutRes,final String format, final Object... args) {
        show(layoutRes,format, Toast.LENGTH_SHORT, args);
    }

    public static void showLong(@LayoutRes final int layoutRes,@NonNull final CharSequence text) {
        show(layoutRes,text, Toast.LENGTH_LONG);
    }

    public static void showLong(@LayoutRes final int layoutRes,@StringRes final int resId) {
        show(layoutRes,resId, Toast.LENGTH_LONG);
    }

    public static void showLong(@LayoutRes final int layoutRes,@StringRes final int resId, final Object... args) {
        show(layoutRes,resId, Toast.LENGTH_LONG, args);
    }

    public static void showLong(@LayoutRes final int layoutRes,final String format, final Object... args) {
        show(layoutRes,format, Toast.LENGTH_LONG, args);
    }

    private static void show(@LayoutRes final int layoutRes,@StringRes final int resId, final int duration) {
        show(layoutRes,MyApplication.getInstance().getInstance().getResources().getString(resId), duration);
    }

    private static void show(@LayoutRes final int layoutRes,@StringRes final int resId, final int duration, final Object... args) {
        show(layoutRes,String.format(MyApplication.getInstance().getResources().getString(resId), args), duration);
    }

    private static void show(@LayoutRes final int layoutRes,final String format, final int duration, final Object... args) {
        show(layoutRes,String.format(format, args), duration);
    }

    private static void show(@LayoutRes int layoutRes,final CharSequence text, final int duration) {
        final int lyRes = layoutRes == 0 ? R.layout.toast_def : layoutRes;

        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                TextView toastView;
                if (duration == Toast.LENGTH_SHORT) {
                    toastView = (TextView) ToastUtils.showCustomShort(lyRes);
                } else {
                    toastView = (TextView) ToastUtils.showCustomLong(lyRes);
                }
                toastView.setText(text);
            }
        });
    }

    public static void cancel() {
        ToastUtils.cancel();
    }
}