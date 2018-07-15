package com.wlw.bookkeeptool.frist_page;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.wlw.bookkeeptool.Order.OrderActivity;
import com.wlw.bookkeeptool.R;


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
    public FristPage_StartWork_PopupWindow(Context context) {
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
                Toast.makeText(context, "下班", Toast.LENGTH_SHORT).show();
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
