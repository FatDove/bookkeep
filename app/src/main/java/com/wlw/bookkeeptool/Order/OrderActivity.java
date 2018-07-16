package com.wlw.bookkeeptool.Order;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.jia.libui.MyDialog.MyDialog;
import com.jia.libutils.RxAndroidUtils.RxjavaUtil;
import com.jia.libutils.RxAndroidUtils.UITask;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDeskTable;
import com.wlw.bookkeeptool.tableBean.everyDishTable;
import com.wlw.bookkeeptool.tableBean.menuBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import litepal.LitePal;
import litepal.tablemanager.Connector;

import static android.widget.GridLayout.HORIZONTAL;
import static com.wlw.bookkeeptool.MyApplication.UserName;

/**
 * Created by wlw on 2018/7/12.
 */

public class OrderActivity extends Activity {
    Context context;
    private TextView title;
    private FloatingActionButton fabSelectMenu;
    private ImageView desk;
    private EditText deskNum;
    private EmptyRecyclerView superRv;
    private Button submitOrder;
    private RelativeLayout slideMenu;
    private ListView imgList;
    private TextView typeShowId;
    private EmptyRecyclerView showMenuRv;
    private DrawerLayout drawerLayout;
    private OrderMenuShow_Rv_Adapter orderMenuShow_rv_adapter;
    private SuperMenu_Rv_Adapter superMenu_rv_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.order_activity);
        SQLiteDatabase db = Connector.getDatabase();

        initView();
        initdata();
        initevent();
    }

    private void initdata() {
        getData_for_imglist();
        getData_for_showMenuRv();
        getData_for_superMenuRv();
    }

    //    给SuperMenuRv取数据
    private void getData_for_superMenuRv() {
        ArrayList<everyDishTable> superMenuBeans = new ArrayList<>();
        superMenu_rv_adapter = new SuperMenu_Rv_Adapter(context, superMenuBeans);
        superRv.setAdapter(superMenu_rv_adapter);

    }

    //    给showMenuR取数据
    private void getData_for_showMenuRv() {
        //这里分两种策略 1 一次性全查  2用哪类查哪类
        //目前预估数量不大 使用 1
        ArrayList<menuBean> allmenuBean = (ArrayList<menuBean>) LitePal.findAll(menuBean.class);
        orderMenuShow_rv_adapter = new OrderMenuShow_Rv_Adapter(context, allmenuBean);
        showMenuRv.setAdapter(orderMenuShow_rv_adapter);
    }

    //    给imglist取数据
    private void getData_for_imglist() {
        String[] arrTitle = {"热", "凉", "主", "饮", "其他"};
        ImgListAdapter imgListAdapter = new ImgListAdapter(context, arrTitle);
        imgList.setAdapter(imgListAdapter);
        imgListAdapter.notifyDataSetChanged();
    }

    private void initView() {
        
        title = (TextView) findViewById(R.id.title);
        fabSelectMenu = (FloatingActionButton) findViewById(R.id.fab_select_menu);
        desk = (ImageView) findViewById(R.id.desk);
        deskNum = (EditText) findViewById(R.id.desk_num);
        superRv = (EmptyRecyclerView) findViewById(R.id.super_rv);
        submitOrder = (Button) findViewById(R.id.submit_order);
        slideMenu = (RelativeLayout) findViewById(R.id.slide_menu);
        imgList = (ListView) findViewById(R.id.img_list);
        typeShowId = (TextView) findViewById(R.id.type_show_id);
        showMenuRv = (EmptyRecyclerView) findViewById(R.id.show_menu_rv);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        showMenuRv.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        superRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void initevent() {
        fabSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(slideMenu);
            }
        });
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String username;
//                String deskNum; //顾客所在的桌子号
                String totalPrice_desk; // 这一桌的总价
                Date startBillTime; //下单时间
                Date endBillTime;  //每桌结账时间
                String is_shutDown;
//                计算这一单的总价


                float allMenuPrice = 0.0f;
                everyDeskTable everyDeskTable = new everyDeskTable();
                for (int i = 0; i < superMenu_rv_adapter.getData().size(); i++) {
                    everyDeskTable.getEveryDeskTableList().add(superMenu_rv_adapter.getData().get(i));
                    superMenu_rv_adapter.getData().get(i).save();//单条菜保存
                    allMenuPrice += superMenu_rv_adapter.getData().get(i).getTotalPrice_dish();
                }
                everyDeskTable.setDeskNum(deskNum.getText().toString());
                everyDeskTable.setUsername(UserName);
                everyDeskTable.setStartBillTime(new Date());

                float endPrice=(float)(Math.round(allMenuPrice*100)/100);//如果要求精确4位就*10000然后/10000</span>
                everyDeskTable.setTotalPrice_desk(endPrice);//当前消费 保留两位小数
                everyDeskTable.setIs_shutDown("0");
                if(everyDeskTable.save()){//单桌菜保存
                    Toast.makeText(context, "下单成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                }

//                Comment comment1 = new Comment();
//                comment1.setContent("好评！");
//                comment1.setPublishDate(new Date());
//                comment1.save();
//                Comment comment2 = new Comment();
//                comment2.setContent("赞一个");
//                comment2.setPublishDate(new Date());
//                comment2.save();
//                News news = new News();
//                news.getCommentList().add(comment1);
//                news.getCommentList().add(comment2);
//                news.setTitle("第二条新闻标题");
//                news.setContent("第二条新闻内容");
//                news.setPublishDate(new Date());
//                news.setCommentCount(news.getCommentList().size());
//                news.save();

            }
        });
        imgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                final everyDishTable everyDishTable = new everyDishTable(UserName, superMenu.getFoodname(), 1, superMenu.getPrice(),superMenu.getPrice(), new Date());
                RxjavaUtil.doInUIThread(new UITask<String>() {
                    @Override
                    public void doInUIThread() {
                        superMenu_rv_adapter.addData(everyDishTable);
                    }
                });
            }
        });
        superMenu_rv_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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

                                everyDishTable.setTotalPrice_dish(foodCount * everyDishTable.getUnitPrice_dish() );
                                superMenu_rv_adapter.notifyDataSetChanged();
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
                                everyDishTable.setTotalPrice_dish(foodCount * everyDishTable.getUnitPrice_dish() );
                                superMenu_rv_adapter.notifyDataSetChanged();
                                Toast.makeText(context, "加", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.delete_data:
                        RxjavaUtil.doInUIThread(new UITask<String>() {
                            @Override
                            public void doInUIThread() {
                                showDig();
                                Toast.makeText(context, "删", Toast.LENGTH_SHORT).show();
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
                                        superMenu_rv_adapter.remove(position);
                                        superMenu_rv_adapter.notifyDataSetChanged();
                                    }
                                });
                                builde.create().show();
                            }
                        });
                        break;
                }
            }
        });
    }
}
