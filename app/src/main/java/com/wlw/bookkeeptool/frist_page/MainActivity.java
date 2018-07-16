package com.wlw.bookkeeptool.frist_page;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wlw.bookkeeptool.MyApplication;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.frist_page.fragment.All_order_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Mine_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Record_Fragment;
import com.wlw.bookkeeptool.frist_page.fragment.Today_Order_Fragment;
import com.wlw.bookkeeptool.frist_page.mlistener.Photo_Result_Listener;
import com.wlw.bookkeeptool.utils.mWindowUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Context context;
    private static final int TAB_TOADY_ORDER = 0;
    private static final int TAB_RECORD = 1;
    private static final int TAB_START_WORK = 2;
    private static final int TAB_ALL_ORDER = 3;
    private static final int TAB_MINE = 4;
    private final int IMAGE_Local = 8;
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

    public Photo_Result_Listener photo_result_listener;
    private TextView tv_foot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
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

        tv_foot = (TextView) findViewById(R.id.tv_foot);
        frag_container = (FrameLayout) findViewById(R.id.frag_container);
        today_order = (RelativeLayout) findViewById(R.id.today_order);
        record = (RelativeLayout) findViewById(R.id.record);
        today_order_icon = (ImageView) findViewById(R.id.today_order_icon);
        record_icon = (ImageView) findViewById(R.id.record_icon);
        all_order_icon = (ImageView) findViewById(R.id.all_order_icon);
        start_work_icon = (ImageView) findViewById(R.id.start_work_icon);
        start_work = (RelativeLayout) findViewById(R.id.start_work);
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
                ResetIcon();
                today_order_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_today_order1));

                break;
            case R.id.record:
                select(TAB_RECORD);
                ResetIcon();
                record_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_record1));

                break;
            case R.id.all_order:
                select(TAB_ALL_ORDER);
                ResetIcon();
                all_order_icon .setImageDrawable(getResources().getDrawable(R.mipmap.ic_all_order1));
                break;
            case R.id.mine:
                select(TAB_MINE);
                ResetIcon();
                mine_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mine1));

                break;
            case R.id.start_work:
                ObjectAnimator ra = ObjectAnimator.ofFloat(start_work_icon,"rotation", 0f, 180f);
                ra.setDuration(1000);
                ra.start();
                showpopu();
                break;
        }


    }
    //将底部图标重置
    private void ResetIcon(){
        today_order_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_today_order2));
        record_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_record2));
        all_order_icon .setImageDrawable(getResources().getDrawable(R.mipmap.ic_all_order2));
        mine_icon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mine2));
    }

    //打开PopuWindow 修改 列表
    private void showpopu(){
        fristPage_startWork_popupWindow = new FristPage_StartWork_PopupWindow(context);
        fristPage_startWork_popupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        fristPage_startWork_popupWindow.showAsDropDown(tv_foot,0,0);//设置显示位置
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


    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //本地图片处理
        if (resultCode == RESULT_OK && requestCode == IMAGE_Local) {
            Log.i("fjl","渲图");
            Uri uri = data.getData();

            Toast.makeText(this,uri + "0000",Toast.LENGTH_SHORT).show();
            Log.i("FanJava", uri.toString() + "666");
            //将从相册获取到的图片,做发送前  和 发送处理
            InputStream is = null; //设置一个输出流
            try {
                is = getContentResolver().openInputStream(uri);//从uri中获取一个流 赋值给 is；
                //1 将图片做压缩处理 否则太大（卡顿）
                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inSampleSize = 4;
                options1.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bmpDefaultPic = BitmapFactory.decodeStream(is,null,options1);

                FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                List<Fragment> fragments = fragmentManager.getFragments();
                for(Fragment fragment : fragments){
                    if(fragment != null && fragment.isVisible()&&fragment instanceof All_order_Fragment){
                        ((All_order_Fragment) fragment).bitmap_to_popu(bmpDefaultPic);
                    }
                }
//                seeEditorPopupWindow.set_img_to_popu(bmpDefaultPic);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {is.close();} catch (IOException e) {e.printStackTrace();}
            }

        }
//        //拍照处理
//        else if (resultCode == RESULT_OK&&requestCode == IMAGE_Camera) {
//
//            File newfile = new File(Camerafile.getAbsolutePath());
////          Toast.makeText(this, newfile.toString(), Toast.LENGTH_LONG).show();
//            //1 图像压缩
//            BitmapFactory.Options options1 = new BitmapFactory.Options();
//            options1.inSampleSize = 8;
//            options1.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            Bitmap bmpDefaultPic = BitmapFactory.decodeFile(newfile.toString(), options1);
////            SendImageDispose(bmpDefaultPic);//处理图片 进行了多步操作
//            SendImageWebService(bmpDefaultPic,null);
//
//        }
    }


}