package com.wlw.bookkeeptool.editor_page;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.menuBean;


import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class FoodMenuShow_listAdapter extends BaseQuickAdapter<menuBean,BaseViewHolder> {

    Context context;
    ArrayList<menuBean> its;

    public FoodMenuShow_listAdapter(Context context, ArrayList<menuBean> its) {
        super(R.layout.food_menu_show_list_item, its);
        this.context=context;
        this.its=its;
                Log.i(TAG, "convert123: " + its.get(0).getFoodname().toString());
    }

    @Override
    protected void convert(BaseViewHolder helper, menuBean item) {
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.food_name,item.getFoodname()+"");
        helper.setText(R.id.food_price,item.getPrice()+"元");
        helper.setText(R.id.food_description,item.getDescription()+"");
        //用Glide框架
        Glide.with(context)
                .load(item.getFoodimg_path())
                .error(R.mipmap.icon_stub)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                // .override(100, 100)
                .into((ImageView) helper.getView(R.id.menu_show_item_img));

    }


}
