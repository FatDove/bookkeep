package com.wlw.bookkeeptool.CustomerMenu;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.menuBean;

import java.util.ArrayList;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SelectMenuShow_Rv_Adapter extends BaseQuickAdapter<menuBean,BaseViewHolder> {

    Context context;
    ArrayList<menuBean> its;

    public SelectMenuShow_Rv_Adapter(Context context, ArrayList<menuBean> its) {
        super(R.layout.slide_showmenu_rv_item, its);
        this.context=context;
        this.its=its;
//                Log.i(TAG, "convert123: " + its.get(0).getFoodname().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, menuBean item) {
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.show_menu_rv_item_text,item.getFoodname()+"");
        helper.addOnClickListener(R.id.show_menu_rv_item_text);
    }


}
