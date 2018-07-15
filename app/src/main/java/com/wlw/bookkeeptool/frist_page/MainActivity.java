package com.wlw.bookkeeptool.frist_page;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.wlw.bookkeeptool.MyApplication;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.frist_page.fragment.All_order_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Mine_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Record_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Today_Order_Fragment;
import com.wlw.bookkeeptool.utils.mWindowUtil;

import java.io.File;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Context context;
    private static final int TAB_TOADY_ORDER = 0;
    private static final int TAB_RECORD = 1;
    private static final int TAB_START_WORK = 2;
    private static final int TAB_ALL_ORDER = 3;
    private static final int TAB_MINE = 4;
    private FrameLayout frag_container;
    private ImageView today_order_icon;
    private RelativeLayout today_order;
    private ImageView record_icon;
    private RelativeLayout record;
    private ImageView start_work_icon;
    private RelativeLayout start_work;
    private ImageView all_order_icon;
    private RelativeLayout all_order;
    private ImageView mine_icon;
    private RelativeLayout mine;
    private LinearLayout ll_bottom;

    private Fragment today_order_fragment;
    private Fragment record_fragment;
    private Fragment all_order_fragment;
    private Fragment mine_fragment;
    private FristPage_StartWork_PopupWindow fristPage_startWork_popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        context = this;
        File file = new File(Environment.getExternalStorageDirectory() + "/" + MyApplication.AppImgFile);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initView() {
        //状态变化时删除老的Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(R.id.frag_container);
        if (fragment != null) {
            ft.remove(fragment);
            fm.popBackStack();
            ft.commit();
        }
        select(TAB_TOADY_ORDER);

        frag_container = (FrameLayout) findViewById(R.id.frag_container);
        today_order_icon = (ImageView) findViewById(R.id.today_order_icon);
        today_order = (RelativeLayout) findViewById(R.id.today_order);
        record_icon = (ImageView) findViewById(R.id.record_icon);
        record = (RelativeLayout) findViewById(R.id.record);
        start_work_icon = (ImageView) findViewById(R.id.start_work_icon);
        start_work = (RelativeLayout) findViewById(R.id.start_work);
        all_order_icon = (ImageView) findViewById(R.id.all_order_icon);
        all_order = (RelativeLayout) findViewById(R.id.all_order);
        mine_icon = (ImageView) findViewById(R.id.mine_icon);
        mine = (RelativeLayout) findViewById(R.id.mine);
        mine.setOnClickListener(this);
        today_order.setOnClickListener(this);
        record.setOnClickListener(this);
        start_work.setOnClickListener(this);
        all_order.setOnClickListener(this);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.today_order:
                select(TAB_TOADY_ORDER);
                break;
            case R.id.record:
                select(TAB_RECORD);

                break;
            case R.id.all_order:
                select(TAB_ALL_ORDER);

                break;
            case R.id.mine:
                select(TAB_MINE);
                break;
            case R.id.start_work:
                ObjectAnimator ra = ObjectAnimator.ofFloat(start_work_icon,"rotation", 0f, 180f);
                ra.setDuration(1000);
                ra.start();
                showpopu();
                break;
        }


    }
    //打开PopuWindow 修改 列表
    private void showpopu(){
        fristPage_startWork_popupWindow = new FristPage_StartWork_PopupWindow(context);
        fristPage_startWork_popupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        fristPage_startWork_popupWindow.showAsDropDown(start_work,0,0);//设置显示位置
        mWindowUtil.lightoff(this);
        fristPage_startWork_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn((Activity) context);
            }
        });
    }
    private void select(int positon) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideAllFragments(transaction);
        switch (positon) {
            case TAB_TOADY_ORDER:
                if (today_order_fragment == null) {
                    today_order_fragment = new Today_Order_Fragment();
                    transaction.add(R.id.frag_container, today_order_fragment);
                } else {
                    transaction.show(today_order_fragment);
                }
                break;
            case TAB_RECORD:
                if (record_fragment == null) {
                    record_fragment = new Record_Fragment();
                    transaction.add(R.id.frag_container, record_fragment);
                } else {
                    transaction.show(record_fragment);
                }
                break;
            case TAB_ALL_ORDER:
                if (all_order_fragment == null) {
                    all_order_fragment = new All_order_Fragment();
                    transaction.add(R.id.frag_container, all_order_fragment);
                } else {
                    transaction.show(all_order_fragment);
                }
//                toolbar.setTitle("视频");
                break;
            case TAB_MINE:
                if (mine_fragment == null) {
                    mine_fragment = new Mine_Fragment();
                    transaction.add(R.id.frag_container, mine_fragment);
                } else {
                    transaction.show(mine_fragment);
                }
//                toolbar.setTitle("视频");
                break;
        }
        transaction.commit();
    }

    private void hideAllFragments(FragmentTransaction transaction) {
        if (today_order_fragment != null) transaction.hide(today_order_fragment);
        if (record_fragment != null) transaction.hide(record_fragment);
        if (all_order_fragment != null) transaction.hide(all_order_fragment);
        if (mine_fragment != null) transaction.hide(mine_fragment);
    }

}