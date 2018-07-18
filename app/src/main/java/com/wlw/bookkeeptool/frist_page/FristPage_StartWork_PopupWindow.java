package com.wlw.bookkeeptool.frist_page;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.jia.libutils.TimeUtil;
import com.wlw.bookkeeptool.Order.OrderActivity;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDayTable;
import com.wlw.bookkeeptool.tableBean.everyDeskTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import litepal.LitePal;

import static com.wlw.bookkeeptool.MyApplication.UserName;


/**
 * Created by wlw on 2017/8/30.
 */
public class FristPage_StartWork_PopupWindow extends PopupWindow {
private Context context;
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private LinearLayout end_work_ly;
    private LinearLayout down_order_ly;

    //添加时进来的构造
    public FristPage_StartWork_PopupWindow(Context context){
        this.context = context;
        //制定popupwindow的宽高
        calWidthAndHeight(context);
        popu_config(context);
        //PopupWindow基本属性设置-----↑↑↑↑↑↑↑↑↑↑↑
        initViews(mConvertView);
        initEvent();
    }
    private void initViews(View mConvertView) {
        end_work_ly = mConvertView.findViewById(R.id.end_work);
        down_order_ly = mConvertView.findViewById(R.id.down_order);
    }
    private void initEvent() {
        end_work_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<everyDeskTable> dataList = LitePal.where("isEndwork = 0 ; username = "+UserName+"").find(everyDeskTable.class,true);
                int count = 0;
                for (everyDeskTable e : dataList) {
                    if (e.getIsCheckout().equals("0")){
                        count++;
                    }
                }
                if (count>0){
                    Toast.makeText(context, "还有【"+count+"】桌未结账", Toast.LENGTH_SHORT).show();
                }else if(dataList.size()<=0){
                    Toast.makeText(context, "还没有交易哦~(⌒__⌒)~", Toast.LENGTH_SHORT).show();
                }else if (dataList.size()>0){
                    CheckOut_to_DB(dataList);
                }
                dismiss();
            }
        });
        down_order_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,OrderActivity.class));
                Toast.makeText(context, "下单", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
    //结账 存 数据库
    private void CheckOut_to_DB(List<everyDeskTable> dataList) {
        SQLiteDatabase database = LitePal.getDatabase();
        database.beginTransaction();
        try {
            everyDeskTable edt = new everyDeskTable();
            edt.setIsCheckout("1");
            edt.setIsEndwork("1");
            edt.updateAll("isEndwork = ?","0");
            everyDayTable everyDayTable = new everyDayTable();
            float totalPrice_day =0.0f;
            for (everyDeskTable e :dataList){
                everyDayTable.getEveryDeskTableList().add(e);
                totalPrice_day+= e.getTotalPrice_desk();
            }
            String dateStr = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
            everyDayTable.setShutDownTime(new Date());
            everyDayTable.setShutDownTimeStr(dateStr);
            everyDayTable.setTotalPrice_day(totalPrice_day);
            Calendar c = Calendar.getInstance();//
            int  mYear = c.get(Calendar.YEAR); // 获取当前年份
            int  mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
            int  mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
            int  mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
            int  mHour = c.get(Calendar.HOUR_OF_DAY);//时
            int  mMinute = c.get(Calendar.MINUTE);//分
            everyDayTable.setYear(mYear);
            everyDayTable.setMonth(mMonth);
            everyDayTable.setDay(mDay);
            everyDayTable.setUsername(UserName);
            everyDayTable.setDeskCount(dataList.size()+"");
            everyDayTable.save();
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Exception", "CheckOut_to_DB:异常了"+e.toString());
        }finally {
            database.endTransaction();
        }
        //收工之后发一条广播给 today_order_fragment
        Intent it = new Intent("WorkOut");
        it.putExtra("WorkOut", "Yes");
        context.sendBroadcast(it);
    }

    /**
     * 获取控件宽高
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        //通过上下文 获取服务  （屏幕服务）
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();//获取显示的“权利”
        wm.getDefaultDisplay().getMetrics(displayMetrics);//将权利在这个服务注册
        mWidth = displayMetrics.widthPixels;//获取屏幕宽高
        mHeight = (int) (displayMetrics.heightPixels * 0.3);
    }
    private void popu_config(Context context) {
        mConvertView = LayoutInflater.from(context).inflate(R.layout.popu_fristpage_startwork, null);
        //PopupWindow基本属性设置-----↓↓↓↓↓↓↓↓↓↓
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);//可触摸
        setTouchable(true);//可点击
        setOutsideTouchable(true);//边缘可点击
        setBackgroundDrawable(new BitmapDrawable());
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {//点击边缘消失
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }
}
