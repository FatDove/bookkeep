package com.wlw.bookkeeptool.Record.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.Record.Record_Info_Rv_Bean;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Adapter_record_info_rv extends BaseQuickAdapter<Record_Info_Rv_Bean, BaseViewHolder> {
    ArrayList<Record_Info_Rv_Bean> its;
    public Adapter_record_info_rv(ArrayList<Record_Info_Rv_Bean> its) {
        super(R.layout.item_record_info_rv, its);
        this.its = its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }
    @Override
    protected void convert(BaseViewHolder helper, Record_Info_Rv_Bean item){
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.food_name,item.getName());
        helper.setText(R.id.food_count_sum,item.getSum_count()+"");
        helper.setText(R.id.sum_price,item.getSum_price()+"元");

    }


}
