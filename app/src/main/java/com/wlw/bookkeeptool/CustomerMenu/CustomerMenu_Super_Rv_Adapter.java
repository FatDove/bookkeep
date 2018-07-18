package com.wlw.bookkeeptool.CustomerMenu;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDishTable;

import java.util.Date;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CustomerMenu_Super_Rv_Adapter extends BaseMultiItemQuickAdapter<everyDishTable, BaseViewHolder> {
    Context context;
    private static final int itemtype_def = 0;
    private static final int itemtype_add = 1;
    private static final int itemtype_edit = 2;


    public CustomerMenu_Super_Rv_Adapter(List data) {
        super(data);
        addItemType(itemtype_def, R.layout.item_menu_info_super_rv01);
        addItemType(itemtype_add, R.layout.item_menu_info_super_rv02);
        addItemType(itemtype_edit, R.layout.item_menu_info_super_rv03);
    }

    @Override
    protected void convert(BaseViewHolder helper, everyDishTable item) {
        switch (helper.getItemViewType()) {
            case itemtype_def:
                Date date = new Date();
                String result_time1 = TimeUtils.getFitTimeSpan(date.getTime(),item.getStartBillTime().getTime(), 3);
                if(TextUtils.isEmpty(result_time1)){
                     result_time1 = "0分钟";
                 }
                if(item.getIsfinish().equals("1")){
                    helper.setVisible(R.id.finish_food,true);
                 }
                    helper.setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count, item.getFoodCount() + "")
                        .setText(R.id.Sum, item.getTotalPrice_dish() + "元")
                        .setText(R.id.down_menu_time, result_time1+ "前")
                        .addOnClickListener(R.id.delete_view)
                        .addOnClickListener(R.id.slidingContentView)
                        .addOnClickListener(R.id.up_food);


                break;
            case itemtype_add:
                Date date2 = new Date();
//                long result_time = date.getTime() - item.getStartBillTime().getTime();
                String result_time2 = TimeUtils.getFitTimeSpan(date2.getTime(),item.getStartBillTime().getTime(), 3);
                if(TextUtils.isEmpty(result_time2)){
                    result_time2 = "0分钟";
                }
                if(item.getIsfinish().equals("1")){
                    helper.setVisible(R.id.finish_food,true);
                }
                helper.setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count, item.getFoodCount() + "")
                        .setText(R.id.Sum, item.getTotalPrice_dish() + "元")
                        .setText(R.id.down_menu_time, result_time2+"前")
                        .addOnClickListener(R.id.delete_view)
                        .addOnClickListener(R.id.slidingContentView)
                        .addOnClickListener(R.id.up_food);
                break;
            case itemtype_edit:
                helper
                        .setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count_variable, item.getFoodCount()+"")
                        .addOnClickListener(R.id.minus)
                        .addOnClickListener(R.id.plus)
                        .addOnClickListener(R.id.add_menu)
                        .addOnClickListener(R.id.delete_view)
                        .addOnClickListener(R.id.slidingContentView);

                break;
        }
    }
}
