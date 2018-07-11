package com.wlw.bookkeeptool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jia.libutils.SettingUtil;
import com.wlw.bookkeeptool.editor_page.see_and_editor_activity;

/**
 * Created by wlw on 2018/7/3.
 */

public class LoginActivity extends Activity {
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context =this;
        setContentView(R.layout.login);
//        PermissionUtils.launchAppDetailsSettings();
//        isGranted();

        SettingUtil.isHavePermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
//        SettingUtil.goSettingPage(this);
//        SettingUtil.goPermissionPage(this);
        SettingUtil.obtainPermissions(this,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE);

        View viewById = findViewById(R.id.r3);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if(Build.VERSION.SDK_INT >= 9){
//            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//            intent.setData(Uri.fromParts("package", getPackageName(), null));
//        } else if(Build.VERSION.SDK_INT <= 8){
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
//            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
//        }
//        startActivity(intent);

//        List<String> permissions = getPermissions();
//        permissions.toString();
//动态申请内存存储权限
//        getPermissions          : 获取应用权限
//        isGranted               : 判断权限是否被授予
//        launchAppDetailsSettings: 打开应用具体设置
//        permission              : 设置请求权限
//        rationale               : 设置拒绝权限后再次请求的回调接口
//        callback                : 设置回调
//        theme                   : 设置主题
//        request                 : 开始请求
//        Log.i("fanjava", "permissions: "+permissions.toString());
//        boolean ishave = isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE);
//        Toast.makeText(this, ishave+"?", Toast.LENGTH_SHORT).show();
//        Log.i("fanjava", "permissions: "+ishave);
//        PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE);
//        PermissionUtils.
//        List<String> permissions2 = getPermissions();
//        Log.i("fanjava", "permissions2: "+permissions2.toString());
//
//        new PermissionUtils.OnRationaleListener() {
//            @Override
//            public void rationale(ShouldRequest shouldRequest) {
//                shouldRequest.toString();
//            }
//        };

    }
}
