package com.wlw.bookkeeptool.Kitchen.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;

import java.util.Date;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Adapter_kitchen_info_rv extends BaseQuickAdapter<EveryDishTable, BaseViewHolder> {

    Context context;
    EveryDeskTable everyDeskTable;

    public Adapter_kitchen_info_rv(Context context, EveryDeskTable its) {
        super(R.layout.item_kitchen_info_rv, its.getEveryDishTableList());
        this.context = context;
        this.everyDeskTable = its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryDishTable item) {
        Log.i(TAG, "convert: " + item.toString());
        Date date = new Date();
        String result_time1 = TimeUtils.getFitTimeSpan(date.getTime(),item.getStartBillTime().getTime(), 3);
        if(TextUtils.isEmpty(result_time1)){
            result_time1 = "0分钟";
        }
        helper  .setText(R.id.down_menu_time, result_time1+"前")
                .setText(R.id.food_count, item.getFoodCount()+"")
                .setText(R.id.food_name, item.getFoodname())
                .setText(R.id.remark_tv, item.getRemark());

        if (item.getItemType()==0){
            helper.setVisible(R.id.jia,false);
        }else if (item.getItemType()==1){
            helper.setVisible(R.id.jia,true);
        }
        if (item.getIsfinish().equals("0")){
            helper.setVisible(R.id.finish_food,false);
        }else if (item.getIsfinish().equals("1")){
            helper.setVisible(R.id.finish_food,true);
        }

    }


}
