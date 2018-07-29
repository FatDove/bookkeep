package com.wlw.bookkeeptool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by wlw on 2018/7/2.
 */

public class RegisterActivity extends Activity {
    /**
     * 短信验证的回调监听
     */
    private EventHandler ev = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) { //回调完成
                //提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap<number,code>
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e("TAG", "提交验证码成功" + data.toString());
                    HashMap<String, Object> mData = (HashMap<String, Object>) data;
                    String country = (String) mData.get("country");//返回的国家编号
                    String phone = (String) mData.get("phone");//返回用户注册的手机号
                    Log.e("TAG", country + "====" + phone);
                    if (phone.equals(number)) {
                        runOnUiThread(new Runnable() {//更改ui的操作要放在主线程，实际可以发送hander
                            @Override
                            public void run() {
                                showDailog("恭喜你！通过验证");
                                dialog.dismiss();
                                //    Toast.makeText(MainActivity.this, "通过验证", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showDailog("验证失败");
                                dialog.dismiss();
                                //     Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//获取验证码成功
                    Log.e("TAG", "获取验证码成功");
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {//返回支持发送验证码的国家列表

                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };
    private String         number;
    private ProgressDialog dialog;
    private LinearLayout activityMain;
    private EditText etPhone;
    private EditText etSecurity;
    private TextView send_code;
    private Button btnRegister;

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        Toast.makeText(this, "1111111", Toast.LENGTH_SHORT).show();
        //提交验证码成功,如果验证成功会在data里返回数据。data数据类型为HashMap<number,code>
        MobSDK.init(RegisterActivity.this, "wx8ec0cc8ae6d89066",
                "2a53d1db69bba449b30cf7fd0c8695c7");
        SMSSDK.registerEventHandler(ev);
        initView();

    }

    /**
     * 向服务器提交验证码，在监听回调中判断是否通过验证
     *
     * @param v
     */
    public void testSecurity(View v) {
        String security = etSecurity.getText().toString();
        if (!TextUtils.isEmpty(security)) {
            dialog = ProgressDialog.show(this, null, "正在验证...", false, true);
            //提交短信验证码
            SMSSDK.submitVerificationCode("+86", number, security);//国家号，手机号码，验证码
            Toast.makeText(this, "提交了注册信息:" + number, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 获取验证码
     * @param v
     */
    public void getSecurity(View v) {
        number = etPhone.getText().toString().trim();
        //发送短信，传入国家号和电话---使用SMSSDK核心类之前一定要在MyApplication中初始化，否侧不能使用
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            SMSSDK.getVerificationCode("+86", number);
            Toast.makeText(this, "发送成功:" + number, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(ev);
    }
    //验证结果弹窗
    private void showDailog(String text) {
        new AlertDialog.Builder(this)
                .setTitle(text)
                .setPositiveButton("确定", null)
                .show();
    }
    private void initView() {
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etSecurity = (EditText) findViewById(R.id.et_security);
        send_code =  findViewById(R.id.send_code);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }
}