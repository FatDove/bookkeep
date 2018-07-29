package com.wlw.bookkeeptool.Order;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jia.libui.MyDialog.MyDialog;
import com.jia.libutils.RxAndroidUtils.RxjavaUtil;
import com.jia.libutils.RxAndroidUtils.UITask;
import com.wlw.bookkeeptool.MyApplication;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;
import com.wlw.bookkeeptool.tableBean.menuBean;
import com.wlw.bookkeeptool.utils.CustomToast;
import com.wlw.bookkeeptool.utils.mWindowUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import litepal.LitePal;
import litepal.tablemanager.Connector;

import static com.wlw.bookkeeptool.MyApplication.UserName;

/**
 * Created by wlw on 2018/7/12.
 */

public class OrderActivity extends Activity {
    Context context;
    private TextView title;
    private ImageView fabSelectMenu;
    private ImageView desk;
    private EditText deskName;
    private Button submitOrder;
    private RelativeLayout slideMenu;
    private ListView imgList;
    private TextView typeShowTV;
    private DrawerLayout drawerLayout;
    private RecyclerView superRv;
    private RecyclerView showMenuRv;
    private OrderMenuShow_Rv_Adapter orderMenuShow_rv_adapter;
    private SuperMenu_Rv_Adapter superMenu_rv_adapter;
    private String[] arrTitle;
    private int[] arrColor;
    private AddRemark_PopupWindow addRemark_popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.order_activity);
        SQLiteDatabase db = Connector.getDatabase();
        BarUtils.setStatusBarColor(this, Color.parseColor("#FFDB42"), 0);
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
        ArrayList<EveryDishTable> superMenuBeans = new ArrayList<>();
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
        arrTitle = new String[]{"热", "凉", "主", "饮", "其他"};
        arrColor = new int[]{R.color.red,R.color.cyan , R.color.rice_white,R.color.green,R.color.yellow};
        ImgListAdapter imgListAdapter = new ImgListAdapter(context, arrTitle);
        imgList.setAdapter(imgListAdapter);
        imgListAdapter.notifyDataSetChanged();
    }

    private void initView() {

        title = (TextView) findViewById(R.id.title);
        fabSelectMenu = (ImageView) findViewById(R.id.fab_select_menu);
        desk = (ImageView) findViewById(R.id.desk);
        deskName = (EditText) findViewById(R.id.desk_name);
        superRv = (RecyclerView) findViewById(R.id.super_rv);
        submitOrder = (Button) findViewById(R.id.submit_order);
        slideMenu = (RelativeLayout) findViewById(R.id.slide_menu);
        imgList = (ListView) findViewById(R.id.img_list);
        typeShowTV   = (TextView) findViewById(R.id.type_show);
        showMenuRv = (RecyclerView) findViewById(R.id.show_menu_rv);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        showMenuRv.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        superRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void initevent() {

        fabSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                // 隐藏软键盘
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                drawerLayout.openDrawer(slideMenu);//打开侧边栏

            }
        });
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(superMenu_rv_adapter.getData().size()<1){
                    ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.toast_height));
                    CustomToast.showLong(R.layout.toast_bad_layout,"~请不要下空单哦~");
                    return;
                }
//                计算这一单的总价
                float allMenuPrice = 0.0f;
                EveryDeskTable everyDeskTable = new EveryDeskTable();
                for (int i = 0; i < superMenu_rv_adapter.getData().size(); i++) {
                    everyDeskTable.getEveryDishTableList().add(superMenu_rv_adapter.getData().get(i));
                    superMenu_rv_adapter.getData().get(i).save();//单条菜保存
                    allMenuPrice += superMenu_rv_adapter.getData().get(i).getTotalPrice_dish();
                }
                if (TextUtils.isEmpty(deskName.getText().toString())){
                    everyDeskTable.setDeskNum("散客");
                }else{
                    everyDeskTable.setDeskNum(deskName.getText().toString());
                }
                everyDeskTable.setUsername(UserName);
                everyDeskTable.setStartBillTime(new Date());
                float endPrice=(float)(Math.round(allMenuPrice*100)/100);//如果要求精确4位就*10000然后/10000</span>
                everyDeskTable.setTotalPrice_desk(endPrice);//当前消费 保留两位小数
                everyDeskTable.setIsCheckout("0");
                everyDeskTable.setStaff_id(MyApplication.staffID);
                everyDeskTable.setDesk_uuid(UUID.randomUUID().toString());
                everyDeskTable.setQuery_role_id("0");

                if(everyDeskTable.save()){//单桌菜保存
                    ToastUtils.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, getResources().getDimensionPixelSize(R.dimen.toast_height));
                    CustomToast.showShort(R.layout.toast_good_layout,"~下单成功~");
                    finish();
                }else{
                    Toast.makeText(context, "下单失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final List<menuBean> menuBean_type_list = LitePal.where("foodtype == ?", position + "").find(menuBean.class);
                //在这里更新右面的ShowMenuRv布局
                RxjavaUtil.doInUIThread(new UITask<String>() {
                    @Override
                    public void doInUIThread() {
                        orderMenuShow_rv_adapter.replaceData(menuBean_type_list);
                        typeShowTV.setText(arrTitle[position]);
                        typeShowTV.setTextColor(getResources().getColor(arrColor[position]));
                    }
                });
            }
        });
        orderMenuShow_rv_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                menuBean superMenu = (menuBean) adapter.getData().get(position);
                final EveryDishTable everyDishTable = new EveryDishTable(UserName, superMenu.getFoodname(),"0", 1, superMenu.getPrice(),superMenu.getPrice(),new Date(),0, UUID.randomUUID().toString(),"");
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
            public void onItemChildClick(BaseQuickAdapter adapter, final View view, final int position) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                final TextView remark_tv = (TextView) viewGroup.getChildAt(5);
                final EveryDishTable everyDishTable = (EveryDishTable) adapter.getData().get(position);
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
                      case R.id.remark_bt:
                          Toast.makeText(context, "加备注", Toast.LENGTH_SHORT).show();
//                          RxjavaUtil.doInUIThread(new UITask<String>() {
//                              @Override
//                              public void doInUIThread() {
////                                  remark_tv.setText("asda");
////                                  superMenu_rv_adapter.notifyDataSetChanged();
//                              }
//                          });
                          showpopu(remark_tv,everyDishTable);
                          break;

                }
            }
        });
    }
    //打开PopuWindow 修改 列表
    private void showpopu(TextView textView, EveryDishTable everyDishTable){
        addRemark_popupWindow = new AddRemark_PopupWindow(context,textView,everyDishTable);
        addRemark_popupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        addRemark_popupWindow.showAsDropDown(submitOrder,0,0);//设置显示位置
        mWindowUtil.lightoff(this);
        addRemark_popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn((Activity) context);
            }
        });
    }
}
