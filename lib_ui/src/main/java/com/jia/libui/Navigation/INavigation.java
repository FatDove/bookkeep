package com.jia.libui.Navigation;

/**
 * Created by FanJiaLong on 2018/1/15.
 * 导航条规范
 */
public interface INavigation{
    /**
     * 导航条布局
     * @return
     */
    public int bindLayoutId();
    /**
     * 构建导航条
     * @return
     */
    public void build();  //方法名是Builder模式的通常写法

}
