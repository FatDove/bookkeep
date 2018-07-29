package com.wlw.bookkeeptool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.wlw.bookkeeptool.Kitchen.KitchenActivity;
import com.wlw.bookkeeptool.frist_page.MainActivity;
import com.wlw.bookkeeptool.utils.AnimatorUtils;

/**
 * Created by wlw on 2018/7/3.
 */

public class RoleActivity extends Activity {
    Context context;
    private TextView staff_id;
    private LinearLayout im_front_staff;
    private LinearLayout im_real_staff;
    private String staffID;
    private Boolean isInto = true;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        staffID = SPUtils.getInstance().getString("staffID", "0");
        if (staffID.equals("0")){
            randomStaffID();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        setContentView(R.layout.role);
        initView();
        initEvent();
    }

    private void randomStaffID(){
      String staffID = (int)(Math.random()*100)+""+ (int)(Math.random())*100;
      SPUtils.getInstance().put("staffID",staffID);
    }

    private void initView() {
        staff_id = (TextView) findViewById(R.id.staff_id);
        im_front_staff = (LinearLayout) findViewById(R.id.im_front_staff);
        im_real_staff = (LinearLayout) findViewById(R.id.im_real_staff);
    }
    private void initEvent() {
        staff_id.setText("员工ID:  "+staffID);
        im_front_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInto){ return; } isInto=!isInto;
                AnimatorUtils.scaleAnimation(v);
              final  Intent i  = new Intent(context, MainActivity.class);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        //execute the task
                        startActivity(i);
                        finish();
                    }
                }, 400);

            }
        });
        im_real_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isInto){ return; } isInto=!isInto;
                AnimatorUtils.scaleAnimation(v);
                final  Intent i2  = new Intent(context, KitchenActivity.class);
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        //execute the task
                        startActivity(i2);
                        finish();
                    }
                }, 400);
            }

        });
    }
}
