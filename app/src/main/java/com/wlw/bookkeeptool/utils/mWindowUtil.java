package com.wlw.bookkeeptool.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by wlw on 2018/7/6.
 */

public class mWindowUtil {

    //屏幕调暗
    public static void lightoff(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        activity.getWindow().setAttributes(lp);
    }

    //屏幕调亮
    public static void lightOn(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);
    }
}
