package com.wlw.bookkeeptool.CustomerMenu;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.jia.libui.MyDialog.MyDialog;
import com.jia.libui.Navigation.impl.ChatNavigation;
import com.jia.libutils.RxAndroidUtils.RxjavaUtil;
import com.jia.libutils.RxAndroidUtils.UITask;
import com.jia.libutils.WindowUtils;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDeskTable;
import com.wlw.bookkeeptool.tableBean.everyDishTable;
import com.wlw.bookkeeptool.tableBean.menuBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import litepal.LitePal;
import litepal.tablemanager.Connector;

import static com.wlw.bookkeeptool.MyApplication.UserName;

/**
 * Created by wlw on 2018/7/12.
 */

public class CustomerMenu_infoActivity extends Activity {
    Context context;
    private OrderMenuShow_Rv_Adapter orderMenuShow_rv_adapter;
    private addMenu_Super_Rv_Adapter addMenu_super_rv_adapter;
    private DrawerLayout mDrawerLayout;
    private TextView mDeskNum;
    private TextView mDownMenuTime;
    private TextView mNowPrice;
    private RecyclerView mSuperRv;
    private TextView mTv1;
    private LinearLayout mImgAdd;
    private LinearLayout mImgCheckOut;
    private TextView mTv2;
    private RelativeLayout mSlideMenu;
    private ListView mImgList;
    private TextView mTypeShowId;
    private RecyclerView mShowMenuRv;
    private LinearLayout parentLayout;
    private everyDeskTable everyDKTbean;//从上个界面 传过来的（桌的）实体

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.addmenu_check_activity);
        SQLiteDatabase db = Connector.getDatabase();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        initView();
        initdata();
        initevent();
        initNavigation();
    }

    private void initdata() {
        getData_for_imglist();
        getData_for_showMenuRv();
        getData_for_superMenuRv();
    }
    //    给SuperMenuRv取数据
    private void getData_for_superMenuRv() {
        Intent intent = getIntent();
        int DeskID = intent.getIntExtra("DeskID", 0);
        everyDKTbean = LitePal.find(everyDeskTable.class, DeskID,true);
        mNowPrice.setText(everyDKTbean.getTotalPrice_desk() + "元");
        String strDate = TimeUtils.date2String(everyDKTbean.getStartBillTime());
        mDownMenuTime.setText(strDate);
        mDeskNum.setText(everyDKTbean.getDeskNum() + "");
        addMenu_super_rv_adapter = new addMenu_Super_Rv_Adapter(everyDKTbean.getEveryDeskTableList());
        mSuperRv.setAdapter(addMenu_super_rv_adapter);
    }

    //    给showMenuR取数据
    private void getData_for_showMenuRv() {
        //这里分两种策略 1 一次性全查  2用哪类查哪类
        //目前预估数量不大 使用 1
        ArrayList<menuBean> allmenuBean = (ArrayList<menuBean>) LitePal.findAll(menuBean.class);
        orderMenuShow_rv_adapter = new OrderMenuShow_Rv_Adapter(context, allmenuBean);
        mShowMenuRv.setAdapter(orderMenuShow_rv_adapter);
    }
    //    给imglist取数据
    private void getData_for_imglist() {
        String[] arrTitle = {"热", "凉", "主", "饮", "其他"};
        ImgListAdapter imgListAdapter = new ImgListAdapter(context, arrTitle);
        mImgList.setAdapter(imgListAdapter);
        imgListAdapter.notifyDataSetChanged();
    }
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDeskNum = (TextView) findViewById(R.id.desk_num);
        mDownMenuTime = (TextView) findViewById(R.id.down_menu_time);
        mNowPrice = (TextView) findViewById(R.id.now_price);
        mSuperRv = (RecyclerView) findViewById(R.id.super_rv);
        mTv1 = (TextView) findViewById(R.id.tv1);
        mImgAdd = (LinearLayout) findViewById(R.id.img_add);
        mImgCheckOut = (LinearLayout) findViewById(R.id.img_check_out);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mSlideMenu = (RelativeLayout) findViewById(R.id.slide_menu);
        mImgList = (ListView) findViewById(R.id.img_list);
        mTypeShowId = (TextView) findViewById(R.id.type_show_id);
        parentLayout = findViewById(R.id.parentLayout);
        mShowMenuRv = (EmptyRecyclerView) findViewById(R.id.show_menu_rv);
        mShowMenuRv.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        mSuperRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void initevent() {
        mImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnimation(v);
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        mImgCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnimation(v);
                the_order_checkout();
            }
        });
        mImgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final List<menuBean> menuBean_type_list = LitePal.where("foodtype == ?", position + "").find(menuBean.class);
                //在这里更新右面的ShowMenuRv布局
                RxjavaUtil.doInUIThread(new UITask<String>() {
                    @Override
                    public void doInUIThread() {
                        orderMenuShow_rv_adapter.replaceData(menuBean_type_list);
                    }
                });
            }
        });
        orderMenuShow_rv_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                menuBean superMenu = (menuBean) adapter.getData().get(position);
                final everyDishTable everyDishTable = new everyDishTable(UserName, superMenu.getFoodname(), 1, superMenu.getPrice(), superMenu.getPrice(), new Date(), 2);
                RxjavaUtil.doInUIThread(new UITask<String>() {
                    @Override
                    public void doInUIThread() {
                        addMenu_super_rv_adapter.addData(everyDishTable);
                    }
                });
            }
        });
        addMenu_super_rv_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final everyDishTable everyDishTable = (com.wlw.bookkeeptool.tableBean.everyDishTable) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.minus:
                        RxjavaUtil.doInUIThread(new UITask<String>() {
                            @Override
                            public void doInUIThread() {
                                int foodCount = everyDishTable.getFoodCount();
                                if (foodCount > 1) {
                                    everyDishTable.setFoodCount(foodCount -= 1);
                                } else {
                                    Toast.makeText(context, "已经是最小值", Toast.LENGTH_SHORT).show();
                                }
//                                BigDecimal b1 = new BigDecimal(Float.toString(everyDishTable.getTotalPrice_dish()));
//                                BigDecimal v = b1.multiply(BigDecimal.valueOf(foodCount));
//                                String s = v.toString();
                                everyDishTable.setTotalPrice_dish(foodCount * everyDishTable.getUnitPrice_dish());
                                addMenu_super_rv_adapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    case R.id.plus:
                        RxjavaUtil.doInUIThread(new UITask<String>() {
                            @Override
                            public void doInUIThread() {
                                int foodCount = everyDishTable.getFoodCount();
                                everyDishTable.setFoodCount(foodCount += 5);
                                float v1 = foodCount * everyDishTable.getTotalPrice_dish();
                                everyDishTable.setTotalPrice_dish(foodCount * everyDishTable.getUnitPrice_dish());
                                addMenu_super_rv_adapter.notifyDataSetChanged();
                                Toast.makeText(context, "加", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.delete_view:
                        RxjavaUtil.doInUIThread(new UITask<String>() {
                            @Override
                            public void doInUIThread(){
                                showDig();
                                Toast.makeText(context, "删",Toast.LENGTH_SHORT).show();
                            }
                            private void showDig() {
                                MyDialog.Builde builde = new MyDialog.Builde(context);
                                builde.setMessage("您确定清除【" + everyDishTable.getFoodname() + "】？");
                                builde.setTitle("提示");
                                builde.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builde.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        SQLiteDatabase database = LitePal.getDatabase();
                                        database.beginTransaction();
                                        try{
                                            everyDishTable everyDT = addMenu_super_rv_adapter.getData().get(position);
                                            if (everyDT.isSaved()){
                                                everyDT.delete();
                                            }
                                            float s = everyDKTbean.getTotalPrice_desk() - everyDT.getTotalPrice_dish();
                                            everyDKTbean.setTotalPrice_desk(s);
                                            everyDKTbean.save();
                                            addMenu_super_rv_adapter.remove(position);
                                            addMenu_super_rv_adapter.notifyDataSetChanged();
                                            mNowPrice.setText(s+"");
//                                            int a = 1/0;
                                            database.setTransactionSuccessful();
                                        }catch (Exception e){
                                            Log.i("FANJAVA", "异常了");
                                        }finally {
                                            database.endTransaction();
                                        }
                                    }
                                });
                                builde.create().show();
                            }
                        });
                        break;
                    case R.id.add_menu:
                        RxjavaUtil.doInUIThread(new UITask<String>() {
                            @Override
                            public void doInUIThread() {
                                SQLiteDatabase database = LitePal.getDatabase();
                                database.beginTransaction(); //开始
                                try{
                                    everyDishTable.setItemType(1);
                                    everyDishTable.save();//单条菜保存;
                                    float s = everyDishTable.getTotalPrice_dish() + everyDKTbean.getTotalPrice_desk();
                                    everyDKTbean.getEveryDeskTableList().add(everyDishTable);
                                    everyDKTbean.setTotalPrice_desk(s);
                                    everyDKTbean.save();
                                    addMenu_super_rv_adapter.notifyDataSetChanged();
                                    mNowPrice.setText(s + "元");
                                    Toast.makeText(context, "加", Toast.LENGTH_SHORT).show();
                                    database.setTransactionSuccessful(); //中间不出错就成功
                                }catch (Exception e){
                                    Log.i("FANJAVA", "异常了");
                                }finally {
                                    database.endTransaction(); // 事物结束
                                }

                            }
                        });
                        break;

                }
            }
        });
    }

    //初始化Toolbar
    public void initNavigation() {
        ChatNavigation.Builder homeBuilder = new ChatNavigation.Builder(this, parentLayout);
        homeBuilder.setTitleRes("顾客餐单详情");
        homeBuilder.setLeftImageLeftRes(R.mipmap.ic_callback1).setLeftImageLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        homeBuilder.setRightImageLeftRes(R.drawable.ic_delete_select).setRightImageLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_the_order();
            }
        });

        homeBuilder.builder().build(); //builder是组装  build是创建
        AdaptationStatusbar();
    }

    //删除这张餐单
    private void delete_the_order() {
        Toast.makeText(context, "删除此餐单", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_remind2).setTitle("重要提示").setMessage("您是否要删除此菜单全部数据")
                .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (everyDKTbean.isSaved()){
                    everyDKTbean.delete();
                  }
                dialog.dismiss();
                finish();
            }
        }).show();
    }
    //此餐单结账
    private void the_order_checkout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_check_1).setTitle("结账").setMessage("您是现在要结账吗？")
                .setNegativeButton("等等",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton("嗯，现在",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (everyDKTbean.isSaved()){
                    everyDKTbean.delete();
                }
                dialog.dismiss();
            }
        }).show();
    }

    //计算状态栏
    private void AdaptationStatusbar() {
        int statusheight = WindowUtils.getStatusHeight(this);
        LinearLayout layout = (LinearLayout) parentLayout.getChildAt(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.height += statusheight;
        layout.setLayoutParams(layoutParams);
        layout.setPadding(0, layout.getPaddingTop() + statusheight, 0, 0);
    }
    public void scaleAnimation(View bt){
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha",1f,0.2f,1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX",1f,0.7f,1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY",1f,0.7f,1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(bt, holder1, holder2, holder3);
        objectAnimator.setDuration(400);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getAnimatedFraction();
                animation.getAnimatedValue();
                animation.getCurrentPlayTime();
            }
        });
        objectAnimator.start();
    }

}
