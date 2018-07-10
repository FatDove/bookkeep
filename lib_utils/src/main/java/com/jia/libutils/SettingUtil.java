package com.jia.libutils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

import rx.Observer;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by wlw on 2018/7/4.
 *   获取权限 和 判断权限
 *   设置页面 和 权限页面
 */
public class SettingUtil {

 public  static  void  goSettingPage(Activity activity){
     Intent mItent1=new Intent(Settings.ACTION_SETTINGS);
     activity.startActivity(mItent1);
 }

 public  static  void  goPermissionPage(Activity activity){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        startActivity(intent);
    }

 public static void obtainPermissions(final Activity activity, final String... permissions ){
  //例子：Manifest.permission.WRITE_EXTERNAL_STORAGE
     RxPermissions rxPermissions = new RxPermissions(activity);
     rxPermissions
             .request(permissions)
             .subscribe(new Observer<Boolean>() {
                 @Override
                 public void onNext(Boolean aBoolean) {
                     Toast.makeText(activity,"部分功能无法使用", Toast.LENGTH_SHORT).show();
                     Log.i("fan_java", aBoolean+"|||||");
                 }
                 @Override
                 public void onCompleted() {
                     Log.i("fan_java", "同意"+"|||||");
                 }
                 @Override
                 public void onError(Throwable e) {
                     Log.i("fan_java", e+"|||||");
                 }
             });
 }
    public static Boolean isHavePermission(Activity activity,final String permission){
        int i = ContextCompat.checkSelfPermission(activity, permission);//0 表示有这个权限
          if (i==0){
              return true;
          }
        return false;
    }

}
