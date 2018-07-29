package com.wlw.bookkeeptool.Kitchen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jia.base.BaseActivity;
import com.jia.base.BasePresenter;
import com.jia.libutils.DateUtils;
import com.wlw.bookkeeptool.Kitchen.adapter.Adapter_kitchen_info_rv;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;

public class KitchenInfoActivity extends BaseActivity {
    Context context;
    private TextView desk_name;
    private TextView down_menu_time;
    private Button update;
    private RecyclerView see_menu_rv;
    private ImageView img;
    private LinearLayout parentLayout;
    private String down_menu_timeStr;
    private String deskNum;
    private Adapter_kitchen_info_rv adapter_kitchen_info_rv;


    @Override
    protected void initActivityView(Bundle savedInstanceState) {
        context = this;
        setContentView(R.layout.layout_kitchen_info);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        initData();
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        EveryDeskTable deskBean = (EveryDeskTable) intent.getSerializableExtra("DeskBean");
        if (deskBean==null){
            return;
        }
        down_menu_timeStr = DateUtils.BackDateStr(deskBean.getStartBillTime(), 3);
        deskNum = deskBean.getDeskNum();
        adapter_kitchen_info_rv = new Adapter_kitchen_info_rv(context, deskBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }

    @Override
    protected void initView() {

        desk_name = (TextView) findViewById(R.id.desk_name);
        down_menu_time = (TextView) findViewById(R.id.down_menu_time);
        update = (Button) findViewById(R.id.update);
        see_menu_rv = (RecyclerView) findViewById(R.id.see_menu_rv);
        img = (ImageView) findViewById(R.id.img);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        desk_name.setText(deskNum);
        down_menu_time.setText(down_menu_timeStr);
        see_menu_rv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        see_menu_rv.setAdapter(adapter_kitchen_info_rv);
    }
}
