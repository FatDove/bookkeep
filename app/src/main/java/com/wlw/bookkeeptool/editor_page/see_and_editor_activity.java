package com.wlw.bookkeeptool.editor_page;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jia.libui.CoverFlow.CoverFlowAdapter;
import com.jia.libui.CoverFlow.CoverFlowViewPager;
import com.jia.libui.CoverFlow.OnPageSelectListener;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.menuBean;
import com.wlw.bookkeeptool.utils.FileMananger;
import com.wlw.bookkeeptool.utils.mWindowUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import litepal.LitePal;
import litepal.tablemanager.Connector;

/**
 * Created by wlw on 2018/7/6.
 */

public class see_and_editor_activity extends Activity {
    Context context;
    Activity activity;
    private CoverFlowViewPager vpConverFlow;
    private List<View> viewList = new ArrayList<>();
    private CoverFlowAdapter coverFlowAdapter;
    private String[] arrTitle = {"热", "凉", "主", "饮", "其他"};
    private int[] arrColor = {R.drawable.button_border_red, R.drawable.button_border_cyan, R.drawable.button_border_rice_white, R.drawable.button_border_green, R.drawable.button_border_yellow};
    private CoverFlowViewPager cover;
    private ImageView addMenu;
    private SeeEditorPopupWindow seeEditorPopupWindow;
    private final int IMAGE_Local = 1;
//    private int food_type;  //用来标记食欲的大类
    private EmptyRecyclerView emptyRecyclerView;
    private String foodtype = "0"; //查询菜品的类型 {0 1 2 3 4}
    //显示数据的集合
    ArrayList<menuBean> menuBeanList = new ArrayList<>();
    private FoodMenuShow_listAdapter foodMenuShow_listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
        setContentView(R.layout.see_and_editor_activity);
//        SQLiteDatabase db = Connector.getDatabase();
        initView();
        initdata(foodtype);
        initevent();

    }

    public void initdata(String foodtype){
        menuBeanList.clear();
        menuBeanList = (ArrayList<menuBean>) LitePal.where("foodtype = ?",foodtype).find(menuBean.class);
        foodMenuShow_listAdapter = new FoodMenuShow_listAdapter(context, menuBeanList);
        foodMenuShow_listAdapter.openLoadAnimation();//加载Item动画效果
        emptyRecyclerView.setAdapter(foodMenuShow_listAdapter);
    }

    private void initView() {
        vpConverFlow = (CoverFlowViewPager) findViewById(R.id.cover);
        for (int i = 0; i < arrTitle.length; i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.see_editor_vp_view, null);
            TextView vpText = v.findViewById(R.id.see_editor_vp_text);
            vpText.setBackground(getResources().getDrawable(arrColor[i]));
            vpText.setText(arrTitle[i]);
            viewList.add(v);
        }
        vpConverFlow.setViewList(viewList);
        vpConverFlow.setOnPageSelectListener(new OnPageSelectListener() {
            @Override
            public void select(int position) {
                foodtype = position+"";
                Toast.makeText(context, position+"?", Toast.LENGTH_SHORT).show();
                initdata(foodtype);
                initevent();
            }
        });
        addMenu = (ImageView) findViewById(R.id.add_menu);
        emptyRecyclerView = (EmptyRecyclerView) findViewById(R.id.see_editor_rv);
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }
     private void initevent(){
         addMenu.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ObjectAnimator ra = ObjectAnimator.ofFloat(v,"rotation", 0f, 180f);
                 ra.setDuration(1000);
                 ra.start();
                 openPopuWindow_add();
             }
         });
         foodMenuShow_listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
             @Override
             public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                 if (view.getId()==R.id.menu_show_item_edit){
                     openPopuWindow_edit((menuBean) adapter.getData().get(position));
                 }
             }
         });
     }

    //打开PopuWindow 展示 列表
    private void openPopuWindow_add() {
        seeEditorPopupWindow = new SeeEditorPopupWindow(context,foodtype);
        seeEditorPopupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        seeEditorPopupWindow.showAsDropDown(addMenu,0,0);//设置显示位置
        mWindowUtil.lightoff(activity);
        seeEditorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn(activity);
            }
        });
    }
    //打开PopuWindow 展示 列表
    private void openPopuWindow_edit(menuBean menuBean){
        seeEditorPopupWindow = new SeeEditorPopupWindow(context,menuBean);
        seeEditorPopupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        seeEditorPopupWindow.showAsDropDown(addMenu,0,0);//设置显示位置
        mWindowUtil.lightoff(activity);
        seeEditorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn(activity);
            }
        });
    }
    /**
     * 获取随机颜色，便于区分
     *
     * @return
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return r + g + b;
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
                seeEditorPopupWindow.set_img_to_popu(bmpDefaultPic);
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
