package com.wlw.bookkeeptool;


import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mob.MobSDK;

import litepal.LitePal;
import litepal.LitePalApplication;

//
///**
// * Created by wlw on 2018/6/21.
// */
//
public class MyApplication extends LitePalApplication {
    String string ="";
    public static String AppImgFile ="XiaoZhuShou";
    public static String UserName ="嘟小四";
    private static MyApplication sInstance;
    public static String staffID="";

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        MobSDK.init(this);
        LitePal.initialize(this); //初始化数据库工具
        Utils.init(this);
        staffID = SPUtils.getInstance().getString("staffID", "0");
    }
    private void initLeakCanary() {
//        // 内存泄露检查工具 leakcanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
    }
}
