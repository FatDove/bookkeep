package com.wlw.bookkeeptool;


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

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this);
        LitePal.initialize(this); //初始化数据库工具

    }
}
