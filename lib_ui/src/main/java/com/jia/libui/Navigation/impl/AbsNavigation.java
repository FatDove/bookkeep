package com.jia.libui.Navigation.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.jia.libui.Navigation.INavigation;


/**
 * Created by FanJiaLong on 2018/1/15.
 * 完成基础功能的【初级】抽象类AbsNavigation（对所有的导航条抽象）
 */
public abstract class  AbsNavigation<P extends AbsNavigation.NavigationParams> implements INavigation {
    protected static final int DEFAULT_LAYOUT_ID = 0;
    public static final int DEFAULT_STRING_RES = 0;
    private  P params;
    private View contentView;

    public  AbsNavigation(P params) {
        this.params= params;
    }

    public P getParams() {//在子类里要用到
        return params;
    }

    public View getContentView() {//在子类里要用到
        return contentView;
    }

    //用来确定图片内容
    public Drawable getDrawable(int res){ //这里重写方便使用

        return getParams().context.getResources().getDrawable(res);
    }
    //用来确定文字内容
    public String getString(int res){
        if(DEFAULT_STRING_RES == res){
            return null;
        }
        return getParams().context.getResources().getString(res);
    }
    public String getString(String text){ //这里重写方便使用
        if(text==null||text==""){
            return null;
        }
        return text;
    }

    @Override
    public int bindLayoutId() {
        return  DEFAULT_LAYOUT_ID;
    }
    @Override
    public void build() {

        int bindLayoutID = bindLayoutId();//bindLayoutId() s
        if (bindLayoutID == DEFAULT_LAYOUT_ID){
            return ;
        }
        //对四个不能缺的 要素进行判空
        if (params==null||params.context==null||params.inflater==null||params.parent==null)
        {
            return ;
        }
        if (contentView==null){
            contentView = bindParent(bindLayoutID,params.parent);//得到布局文件生成的View(!一级父容器)
        }
    }

    /**
     * 给一个布局绑定父容器
     * @param layoutId
     * @param parent
     * @return
     */
    public ViewGroup bindParent(int layoutId,ViewGroup parent){
        if (layoutId==DEFAULT_LAYOUT_ID){
            return null;
        }
      View childView = params.inflater.inflate(layoutId,parent,false);
      return bindParent(childView,parent);
 }
    public ViewGroup bindParent(View childView,ViewGroup parent){
        // 将来刷新视图
        ViewParent viewParent = childView.getParent();
        if (viewParent != null) {
            ViewGroup viewGroup = (ViewGroup) viewParent;
            viewGroup.removeView(childView);
        }
        parent.addView(childView, 0);
        return (ViewGroup) childView;
    }
    /**
     * 构建者
     */
    public static abstract class Builder{
        public abstract INavigation builder();
    }
    //参数集
    public static class NavigationParams {
//        所有导航条必备的参数，上下文，父容器
        public Context context; //
        public ViewGroup parent;
        public LayoutInflater inflater;
        public NavigationParams(Context context, ViewGroup parent){
            this.context=context;
            this.parent=parent;
            this.inflater = LayoutInflater.from(context);
        }
    }
}
