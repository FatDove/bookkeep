package com.wlw.bookkeeptool.Kitchen.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jia.libutils.DateUtils;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Adapter_kitchen_rv extends BaseQuickAdapter<EveryDeskTable, BaseViewHolder> {

    Context context;
    ArrayList<EveryDeskTable> its;
    private TextView desk_name;
    private TextView menu_count;
    private TextView down_menu_Time;
    private TextView up_ment_count;
    private TextView menuStr_tv;
    public Adapter_kitchen_rv(Context context, ArrayList<EveryDeskTable> its) {
        super(R.layout.item_kitchen_rv, its);
        this.context = context;
        this.its = its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, EveryDeskTable item) {
        Log.i(TAG, "convert: " + item.toString());
        List<EveryDishTable> everyDishTableList = item.getEveryDishTableList();
        String  menuStr_tv="";
        int  up_menu_count=0;
        String down_menu_Time="";
        Date startBillTime = item.getStartBillTime();
        down_menu_Time = DateUtils.BackDateStr(startBillTime,4);
        for (EveryDishTable e:everyDishTableList){
            menuStr_tv += e.getFoodname()+"("+e.getFoodCount()+")["+e.getRemark()+"]\n";
            if (e.getIsfinish().equals("1")){
                up_menu_count++;
            }
        }

                helper.setText(R.id.desk_name,item.getDeskNum())
                        .setText(R.id.menu_count,everyDishTableList.size()+"道")
                        .setText(R.id.down_menu_Time,down_menu_Time)
                        .setText(R.id.up_menu_count,up_menu_count+"道")
                        .setText(R.id.menuStr_tv,menuStr_tv);


    }


}
