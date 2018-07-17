package com.wlw.bookkeeptool.AddMenu_Check;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDishTable;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class addMenu_Super_Rv_Adapter extends BaseMultiItemQuickAdapter<everyDishTable, BaseViewHolder> {
    Context context;
    private static final int itemtype_def = 0;
    private static final int itemtype_add = 1;
    private static final int itemtype_edit = 2;
    ArrayList<everyDishTable> its;
    private RelativeLayout superRvItemLayout;
    private TextView superRvItemText;
    private ImageView minus;
    private LinearLayout numLayout;
    private TextView foodNum;
    private ImageView plus;
    private ImageView deleteData;

    public addMenu_Super_Rv_Adapter(List data) {
        super(data);
        addItemType(itemtype_def, R.layout.item_menu_info_super_rv01);
        addItemType(itemtype_add, R.layout.item_menu_info_super_rv02);
        addItemType(itemtype_edit, R.layout.item_menu_info_super_rv03);
    }

    @Override
    protected void convert(BaseViewHolder helper, everyDishTable item) {
        switch (helper.getItemViewType()) {
            case itemtype_def:
                helper.setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count, item.getFoodCount() + "")
                        .setText(R.id.Sum, item.getTotalPrice_dish() + "元")
                        .addOnClickListener(R.id.delete_view);
                break;
            case itemtype_add:
                helper.setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count, item.getFoodCount() + "")
                        .setText(R.id.Sum, item.getTotalPrice_dish() + "元")
                        .addOnClickListener(R.id.delete_view);
                break;
            case itemtype_edit:
                helper
                        .setText(R.id.food_name, item.getFoodname())
                        .setText(R.id.food_count_variable, item.getFoodCount()+"")
                        .addOnClickListener(R.id.minus)
                        .addOnClickListener(R.id.plus)
                        .addOnClickListener(R.id.add_menu)
                        .addOnClickListener(R.id.delete_view);
                break;
        }
    }
}
