package com.wlw.bookkeeptool.frist_page;

import android.content.Context;
import android.util.Log;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class first_page_adapter extends BaseQuickAdapter<today_order_bean,BaseViewHolder> {

    Context context;
    ArrayList<today_order_bean> its;

    public first_page_adapter(Context context, ArrayList<today_order_bean> its) {
        super(R.layout.today_order_item, its);
        this.context=context;
        this.its=its;
//                Log.i(TAG, "convert123: " + its.get(0).getAddress().toString());
    }
    @Override
    protected void convert(BaseViewHolder helper, today_order_bean item) {
        Log.i(TAG, "convert: " + item.toString());
//        helper.setText(R.id.importantCompany,item.getImportantCompany()+"");
//        helper.setText(R.id.responsePerson,item.getResponsePerson()+"");
//        helper.setText(R.id.address,item.getAddress()+"");
    }


}
