package com.jia.libui.Navigation.impl;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jia.libui.Navigation.INavigation;
import com.jia.libui.R;


/**
 * Created by FanJiaLong on 2018/1/16.
 */
public class ChatNavigation extends BaseNavigation<ChatNavigation.HomeNavigationParams> {

    private ChatNavigation(HomeNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLeftLayoutID() {
//        return 0;
        return R.layout.mtoolbar_left_layout;
    }
    @Override
    public int bindCenterLayoutID() {
        //代码创建，布局创建都行
        return R.layout.mtoolbar_center_layout;
    }
    @Override
    public int bindRightLayoutID() {
        return R.layout.mtoolbar_right_layout;
    }

    @Override
    public void initbindLeftLayout(LinearLayout parent) {
        ImageView iv_Left_left=null;
        iv_Left_left = (ImageView) parent.findViewById(R.id.iv_Left_left);

        if (getParams().leftImageRightRes!=0){
//     iv_left.setImageDrawable(getDrawable(getParams().leftImageRightRes)); //src
            iv_Left_left.setBackground(getDrawable(getParams().leftImageRightRes));//background
            iv_Left_left.setOnClickListener(getParams().leftImageLeftOnClickListener);
        }else{
            iv_Left_left.setVisibility(View.GONE);
        }

    }

    @Override
    public void initbindCenterLayout(LinearLayout parent) {
        TextView textView = (TextView) parent.findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(getString(getParams().titleText))){
            textView.setVisibility(View.GONE);
        }else {
            textView.setText(getString(getParams().titleText));
        }
    }

    @Override
    public void initbindRightLayout(LinearLayout parent) {
        ImageView iv_right_left = null;
        ImageView iv_right_right = null;
        iv_right_left = (ImageView) parent.findViewById(R.id.iv_Right_left);
        iv_right_right = (ImageView) parent.findViewById(R.id.iv_Right_right);

        if (getParams().rightImageLeftRes!=0){
//        iv_left.setImageDrawable(getDrawable(getParams().rightImageLeftRes)); //src
            iv_right_left.setBackground(getDrawable(getParams().rightImageLeftRes));//background
            iv_right_left.setOnClickListener(getParams().rightImageLeftOnClickListener);
        }else {
            iv_right_left.setVisibility(View.GONE);
        }
        if (getParams().rightImageRightRes!=0){
            //        iv_right.setImageDrawable(getDrawable(getParams().rightImageRightRes)); //src
            iv_right_right.setOnClickListener(getParams().rightImageRightOnClickListener);//background
            iv_right_right.setBackground(getDrawable(getParams().rightImageRightRes));
        }else{
            iv_right_right.setVisibility(View.GONE);
        }


    }

    public static class Builder extends AbsNavigation.Builder{
        private HomeNavigationParams params;
        public Builder(Context context, ViewGroup parent){
            this.params = new HomeNavigationParams(context,parent);
            //在这里那到基础参数，在通过暴露的各种set方法，添加自己需要的参数，
        }
        //因为 这里Builder这个内部类不是抽象的了,所以他需要实现AbsNavigation.Builder中的抽象方法
        @Override
        public INavigation builder() {
            return new ChatNavigation(params);
        }

        public Builder setTitleRes(String titleRes) {
            this.params.titleText =titleRes;
            return this;
        }

        public Builder setLeftImageLeftRes(int leftImageLeftRes) {
            this.params.leftImageRightRes =leftImageLeftRes;
            return this;
        }

        public Builder setRightImageLeftRes(int rightImageLeftRes) {
            this.params.rightImageLeftRes =rightImageLeftRes;
            return this;
        }
        public Builder setRightImageRightRes(int rightImageRightRes) {
            this.params.rightImageRightRes =rightImageRightRes;
            return this;
        }
        public Builder setRightImageLeftOnClickListener(View.OnClickListener rightImageLeftOnClickListener){
            this.params.rightImageLeftOnClickListener =rightImageLeftOnClickListener;
            return this;
        }
        public Builder setRightImageRightOnClickListener(View.OnClickListener rightImageRightOnClickListener){
            this.params.rightImageRightOnClickListener =rightImageRightOnClickListener;
            return this;
        }
        public Builder setLeftImageLeftOnClickListener(View.OnClickListener leftImageLeftOnClickListener){
            this.params.leftImageLeftOnClickListener =leftImageLeftOnClickListener;
            return this;
        }

    }

    //在这里封装自己需要的参数
    protected  static class HomeNavigationParams extends AbsNavigation.NavigationParams{
        public View.OnClickListener rightImageRightOnClickListener;
        public HomeNavigationParams(Context context, ViewGroup parent) {
            super(context, parent);
        }
//        public Context context;
//        public ViewGroup parent;
//        public LayoutInflater inflater;
        public String titleText;   //相应位置的资源
        public int rightImageLeftRes=0;
        public int rightImageRightRes=0;
        public int leftImageRightRes=0;

        public View.OnClickListener rightImageLeftOnClickListener;  //相应位置的点击事件

        public View.OnClickListener leftImageLeftOnClickListener;
    }



    }


