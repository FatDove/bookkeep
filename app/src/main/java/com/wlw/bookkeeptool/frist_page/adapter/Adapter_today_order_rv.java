package com.wlw.bookkeeptool.frist_page.adapter;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Adapter_today_order_rv extends BaseQuickAdapter<EveryDeskTable, BaseViewHolder> {

    Context context;
    ArrayList<EveryDeskTable> its;
    public Adapter_today_order_rv(Context context, ArrayList<EveryDeskTable> its) {
        super(R.layout.item_today_order, its);
        this.context = context;
        this.its = its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryDeskTable item){
        Log.i(TAG, "convert: " + item.toString());;
        helper.setText(R.id.desk_num,item.getDeskNum());
        helper.setText(R.id.now_Price,item.getTotalPrice_desk()+"元");
        String describeStr = "";
        for (EveryDishTable e :item.getEveryDishTableList()){
            describeStr+= e.getFoodname()+"("+e.getFoodCount()+")~";
        }
        helper.setText(R.id.menu_describe,describeStr);
        String time_str =  TimeUtils.date2String(item.getStartBillTime());
        String[] split = time_str.split(" ");
        helper.setText(R.id.place_order_time,split[1]);
        if (item.getIsCheckout().equals("1")){
        helper.setVisible(R.id.img_finish_check,true);
        }


    }


}
