package com.jia.libui.Navigation.impl;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jia.libui.R;


/**
 * Created by FanJiaLong on 2018/1/15.
 * 完成一种大类型（左中右，左中，右中 等等）的【中级】抽象类导航条（只针对左中右这一类的导航条抽象）
 */
public abstract class BaseNavigation<P extends AbsNavigation.NavigationParams> extends AbsNavigation<P>  {

    public BaseNavigation(P params) {
        super(params);
    }

//    这里覆盖的了父类的方法
    @Override
    public int bindLayoutId() {
        //代码创建||布局文件创建
        return R.layout.toolbar_default_layuout;   //待绑定的父容器布局
    }

    @Override
    public void build() {
        //一定要调用父类的方法
        super.build();
        ////动态的场景导航条布局
        //1 分好左 中 右三块 （完成大致的场景划分）//绑定的顺序从右开始
        //2 把二级布局绑到给一级布局
        LinearLayout rightLayout = creatChildLayout(1.0f);
        LinearLayout centerLayout = creatChildLayout(2.0f);
        LinearLayout leftLayout = creatChildLayout(1.0f);  //参数是权重

        /**
         * 回调定义好的规范方法
         */
        //把三级布（左中右）局绑定到二级布局  （目前最小就是三级）
        bindParent(bindLeftLayoutID(),leftLayout);
        bindParent(bindCenterLayoutID(),centerLayout);
        bindParent(bindRightLayoutID(),rightLayout);
        //初始化左中右布局中的内容
        initbindLeftLayout(leftLayout);
        initbindCenterLayout(centerLayout);
        initbindRightLayout(rightLayout);

    }
    /**
     * 动态创建布局, (!二级父容器)
     * @param weight
     * @return
     */
    private LinearLayout creatChildLayout(float weight){
        LinearLayout linearLayout = new LinearLayout(getParams().context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        linearLayout.setBackgroundColor(color);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight=weight;
        linearLayout.setLayoutParams(layoutParams);

        bindParent(linearLayout,(ViewGroup) getContentView());
        return linearLayout;
   }

    public abstract int bindLeftLayoutID();
    public abstract int bindCenterLayoutID();
    public abstract int bindRightLayoutID();

    /**
     * 初始化左边的布局
     * @param parent
     * @return
     */
    public abstract void initbindLeftLayout(LinearLayout parent);
    /**
     * 初始化中间的布局
     * @param parent
     * @return
     */
    public abstract void initbindCenterLayout(LinearLayout parent);
    /**
     * 初始化右边的布局
     * @param parent
     * @return
     */
    public abstract void initbindRightLayout(LinearLayout parent);


}
