package com.jia.libutils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import java.lang.reflect.Field;

/**
 * Created by FanJiaLong on 2018/1/26.
 */
public class WindowUtils {
    //获取状态栏的高度
    public static int getStatusHeight(Context context) {
        int statusheight = 0;
        try {
            Class<?> clazz =  Class.forName("com.android.internal.R$dimen");

            Object object = clazz.newInstance();
            Field status_bar_height = clazz.getField("status_bar_height");
            String  heightStr = status_bar_height.get(object).toString();
            int heightid  = Integer.parseInt(heightStr);
            //heightid 拿到的是像素dp值的id
            // dp -----> px
            statusheight = context.getResources().getDimensionPixelSize(heightid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusheight;
    }
    public static void delayfinish(final Activity activity) {
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    //execute the task
                    activity.finish();
                }
            }, 1000);

    }

}
