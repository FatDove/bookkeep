package com.wlw.bookkeeptool.AddMenu_Check;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlw.bookkeeptool.R;

/**
 * Created by wlw on 2018/7/12.
 */

public class ImgListAdapter extends BaseAdapter {

    private final Context context;
    private final String [] arrimglisttitle;
    private  LayoutInflater layoutInflater;
     public ImgListAdapter(Context context, String [] arrimglisttitle){
        this.context = context;
        this.arrimglisttitle = arrimglisttitle;
         layoutInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrimglisttitle.length;
    }

    @Override
    public Object getItem(int position) {
        return arrimglisttitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         convertView  =  layoutInflater.inflate(R.layout.slide_img_list_item,null,false);
        TextView textView = convertView.findViewById(R.id.img_list_item_text);
        textView.setText(arrimglisttitle[position]);
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_purple_round));
        if(position==0){
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_red_round));
        }else if(position==1){
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_cyan_round));
        }else if(position==2){
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_rice_white_round));
        }else if(position==3){
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_green_round));
        }else if(position==4){
        textView.setBackground(context.getResources().getDrawable(R.drawable.button_border_yellow_round));
        }
         return convertView;
    }
}
