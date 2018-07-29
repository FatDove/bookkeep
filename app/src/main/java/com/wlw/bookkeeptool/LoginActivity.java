package com.wlw.bookkeeptool;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.jia.base.BaseActivity;
import com.jia.base.BasePresenter;
import com.jia.libutils.SettingUtil;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by wlw on 2018/7/3.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    Context context;
    private ImageView img1;
    private MaterialEditText username;
    private RelativeLayout r1;
    private ImageView img2;
    private MaterialEditText pass;
    private RelativeLayout r2;
    private ImageView img3;
    private MaterialEditText boss_cord;
    private RelativeLayout r4;
    private RelativeLayout login_ll;
    private TextView register;
    private TextView forget;
    private LinearLayout parentLayout;


    @Override
    protected void initActivityView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
        context = this;

//        View parentLayout = findViewById(R.id.parentLayout);
//        Bitmap bitmap = ImageUtils.getBitmap(R.mipmap.login_bg);
//        Bitmap bitmap1 = ImageUtils.stackBlur(bitmap, 10);
//        parentLayout.setB
//        PermissionUtils.launchAppDetailsSettings();
//        isGranted();
        SettingUtil.isHavePermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        SettingUtil.goSettingPage(this);
//        SettingUtil.goPermissionPage(this);
        SettingUtil.obtainPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE);
        View viewById = findViewById(R.id.login_ll);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoleActivity.class);
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


        /**
         * Toast测试
         *  setGravity     : 设置吐司位置
         setBgColor     : 设置背景颜色
         setBgResource  : 设置背景资源
         setMsgColor    : 设置消息颜色
         setMsgTextSize : 设置消息字体大小
         showShort      : 显示短时吐司
         showLong       : 显示长时吐司
         showCustomShort: 显示短时自定义吐司
         showCustomLong : 显示长时自定义吐司
         cancel         : 取消吐司显示
         */
    }
    @Override
    protected  void initView() {

        username = (MaterialEditText) findViewById(R.id.username);
        pass = (MaterialEditText) findViewById(R.id.pass);
        boss_cord = (MaterialEditText) findViewById(R.id.boss_cord);
        login_ll = (RelativeLayout) findViewById(R.id.login_ll);
        register = (TextView) findViewById(R.id.register);
        forget = (TextView) findViewById(R.id.forget);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);

        BarUtils.setStatusBarAlpha(this, 90);
//        BarUtils.addMarginTopEqualStatusBarHeight();// 其实这个只需要调用一次即可
        forget.setOnClickListener(this);
        register.setOnClickListener(this);
        login_ll.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    private void submit() {
        // validate
        String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String passString = pass.getText().toString().trim();
        if (TextUtils.isEmpty(passString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View v){
         switch (v.getId()){
             case R.id.login_ll:
                 submit();
                 login();
                 break;
             case R.id.register:
                 Intent intent = new Intent(this,RegisterActivity.class);
                 startActivity(intent);
                 break;
             case R.id.forget:
//                 Intent intent = new Intent(this,ForgetActivity.class);
//                 startActivity(intent);
                 break;

         }
    }
    //请求登陆
    private void login(){

    }
}
