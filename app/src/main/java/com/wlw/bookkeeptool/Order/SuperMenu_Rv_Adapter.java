package com.wlw.bookkeeptool.Order;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDishTable;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SuperMenu_Rv_Adapter extends BaseQuickAdapter<everyDishTable, BaseViewHolder> {

    Context context;
    ArrayList<everyDishTable> its;
    private RelativeLayout superRvItemLayout;
    private TextView superRvItemText;
    private ImageView minus;
    private LinearLayout numLayout;
    private TextView foodNum;
    private ImageView plus;
    private ImageView deleteData;

    public SuperMenu_Rv_Adapter(Context context, ArrayList<everyDishTable> its) {
        super(R.layout.slide_super_rv_item, its);
        this.context = context;
        this.its = its;
//                Log.i(TAG, "convert123: " + its.get(0).getFoodname().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, everyDishTable item) {
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.super_rv_item_text, item.getFoodname() + "");
        helper.setText(R.id.food_num, item.getFoodCount() + "");
        helper.addOnClickListener(R.id.minus);
        helper.addOnClickListener(R.id.plus);
        helper.addOnClickListener(R.id.delete_data);
    }


}
