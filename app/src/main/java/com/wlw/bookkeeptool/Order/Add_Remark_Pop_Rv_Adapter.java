package com.wlw.bookkeeptool.Order;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class Add_Remark_Pop_Rv_Adapter extends BaseQuickAdapter<String,BaseViewHolder> {

    Context context;
    ArrayList<String> its;

    public Add_Remark_Pop_Rv_Adapter(Context context, ArrayList<String> its) {
        super(R.layout.slide_showmenu_rv_item, its);
        this.context=context;
        this.its=its;
//                Log.i(TAG, "convert123: " + its.get(0).getFoodname().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.show_menu_rv_item_text,item+"");
        helper.addOnClickListener(R.id.show_menu_rv_item_text);
    }


}
