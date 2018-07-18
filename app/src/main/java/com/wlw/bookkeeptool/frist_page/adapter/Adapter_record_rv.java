package com.wlw.bookkeeptool.frist_page.adapter;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDayTable;
import com.wlw.bookkeeptool.tableBean.everyDishTable;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Adapter_record_rv extends BaseQuickAdapter<everyDayTable, BaseViewHolder> {
    ArrayList<everyDayTable> its;
    public Adapter_record_rv(ArrayList<everyDayTable> its) {
        super(R.layout.item_record_list, its);
        this.its = its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, everyDayTable item){
        Log.i(TAG, "convert: " + item.toString());;
        helper.setText(R.id.desk_num,item.getDeskCount());
        helper.setText(R.id.income,item.getTotalPrice_day()+"元");
        String date = item.getShutDownTimeStr();
        helper.setText(R.id.date,date);
    }


}
