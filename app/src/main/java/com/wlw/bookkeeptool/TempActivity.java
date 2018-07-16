package com.wlw.bookkeeptool;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jia.libui.MyControl.EmptyRecyclerView;

/**
 * Created by wlw on 2018/7/16.
 */

public class TempActivity extends Activity {
    private TextView mDateYear;
    private TextView mDateMonthDay;
    private TextView mTv2;
    private TextView mOrderCount;
    private TextView mTv3;
    private TextView mUnCheckoutCount;
    private TextView mTv;
    private EmptyRecyclerView mTodayOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_today_order_layout);
        initView();
    }

    private void initView() {
        mDateYear = (TextView) findViewById(R.id.date_year);
        mDateMonthDay = (TextView) findViewById(R.id.date_month_day);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mOrderCount = (TextView) findViewById(R.id.order_count);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mUnCheckoutCount = (TextView) findViewById(R.id.unCheckout_count);
        mTv = (TextView) findViewById(R.id.tv);
        mTodayOrder = (EmptyRecyclerView) findViewById(R.id.today_order);
    }
}
