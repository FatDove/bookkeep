package com.wlw.bookkeeptool.Order;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by wlw on 2017/8/30.
 */
public class AddRemark_PopupWindow extends PopupWindow {
private Context context;
    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private RecyclerView add_remark_rv;
    private EditText remark_content;
    private Button submit_remark;
    private String [] remark_data_arr = new String[]{"不辣","微辣","中辣","特辣","火大点","火小点","不要葱","不要蒜"};
    private  Add_Remark_Pop_Rv_Adapter add_remark_pop_rv_adapter;
    private ArrayList<String> remark_data_list = new ArrayList<>();
    private Set<String> remark_data_list_set = new HashSet<>();
    private TextView textView;
    private EveryDishTable everyDishTable;

    public AddRemark_PopupWindow(Context context, TextView textView, EveryDishTable everyDishTable) {
        this.context = context;
        this.textView = textView;
        this.everyDishTable = everyDishTable;
        calWidthAndHeight(context);
        popu_config(context);
        initdata();
        initViews(mConvertView);
        initEvent();
    }
    private void initViews(View mConvertView) {
        add_remark_rv = mConvertView.findViewById(R.id.add_remark_rv);
        remark_content = mConvertView.findViewById(R.id.remark_content);
        submit_remark = mConvertView.findViewById(R.id.submit_remark);
        add_remark_rv.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL, false));
        add_remark_pop_rv_adapter = new Add_Remark_Pop_Rv_Adapter(context,remark_data_list);
        add_remark_rv.setAdapter(add_remark_pop_rv_adapter);
    }
    private void initEvent() {
        add_remark_pop_rv_adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String itemStr = (String) adapter.getData().get(position);
                String text = remark_content.getText().toString();
                text+=itemStr+";";
                remark_content.setText(text);
                everyDishTable.setRemark(text);
            }
        });
        submit_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(remark_content.getText().toString());
                dismiss();
            }
        });
    }
    private void initdata(){
        for (int j=0;j<remark_data_arr.length;j++){
            remark_data_list.add(remark_data_arr[j]);
        }
////        Set<String> remark_data = SPUtils.getInstance().getString("remark_data");
//        Iterator<String> iterator = remark_data.iterator();
//        for (String str : remark_data) {
//            remark_data_list.add(str);
//        }
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
        mHeight = (int) (displayMetrics.heightPixels * 0.5);
    }
    private void popu_config(Context context) {
        mConvertView = LayoutInflater.from(context).inflate(R.layout.popu_add_remark, null);
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
