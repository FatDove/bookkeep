package com.wlw.bookkeeptool.frist_page;

/**
 * Created by wlw on 2018/7/5.
 */

public class today_order_bean {
    String describe;

    public String getDescribe() {
        return describe == null ? "" : describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public today_order_bean(String describe) {
        this.describe = describe;
    }
}
