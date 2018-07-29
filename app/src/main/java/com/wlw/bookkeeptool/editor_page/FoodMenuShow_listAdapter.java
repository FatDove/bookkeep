package com.wlw.bookkeeptool.editor_page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.menuBean;

import java.util.ArrayList;

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
//                Log.i(TAG, "convert123: " + its.get(0).getFoodname().toString());
    }

    @Override
    protected void convert(final BaseViewHolder helper, menuBean item) {
        Log.i(TAG, "convert: " + item.toString());
        helper.setText(R.id.food_name,item.getFoodname()+"");
        helper.setText(R.id.food_price,item.getPrice()+"元");
        helper.setText(R.id.food_description,item.getDescription()+"");
        final ImageView view = helper.getView(R.id.menu_show_item_img);

        //用Glide框架
//        Glide.with(context)
//                .load(item.getFoodimg_path())
//                .error(R.mipmap.icon_stub)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                // .override(100, 100)
//                .into((ImageView) helper.getView(R.id.menu_show_item_img));
        Glide.with(context)
                .load(item.getFoodimg_path())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(180,180) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(drawable);   //设置背景
                        }
                    }        //设置宽高
                });



        helper.addOnClickListener(R.id.menu_show_item_edit);
        helper.addOnClickListener(R.id.delete_view);
    }


}
