package com.wlw.bookkeeptool.Order;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.menuBean;

import java.util.List;

import litepal.LitePal;
import litepal.tablemanager.Connector;

/**
 * Created by wlw on 2018/7/12.
 */

public class OrderActivity extends Activity {
    Context context;
    private TextView title;
    private FloatingActionButton fabSelectMenu;
    private ImageView desk;
    private EditText deskNum;
    private EmptyRecyclerView superRv;
    private Button submitOrder;
    private RelativeLayout slideMenu;
    private ListView imgList;
    private TextView typeShowId;
    private EmptyRecyclerView showMenuRv;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =this;
        setContentView(R.layout.order_activity);
                SQLiteDatabase db = Connector.getDatabase();

        initView();
        initevent();
        initdata();
    }

    private void initdata(){
         getData_for_imglist();
         getData_for_showMenuRv();
    }
    //    给showMenuR取数据
    private void getData_for_showMenuRv() {
        //这里分两种策略 1 一次性全查  2用哪个查哪个
        //目前预估数量不大 使用 1
        List<menuBean> allmenuBean = LitePal.findAll(menuBean.class);



    }

    //    给imglist取数据
    private void getData_for_imglist() {
        String[] arrTitle = {"热", "凉", "主", "饮", "其他"};
        ImgListAdapter imgListAdapter = new ImgListAdapter(context, arrTitle);
        imgList.setAdapter(imgListAdapter);
        imgListAdapter.notifyDataSetChanged();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        fabSelectMenu = (FloatingActionButton) findViewById(R.id.fab_select_menu);
        desk = (ImageView) findViewById(R.id.desk);
        deskNum = (EditText) findViewById(R.id.desk_num);
        superRv = (EmptyRecyclerView) findViewById(R.id.super_rv);
        submitOrder = (Button) findViewById(R.id.submit_order);
        slideMenu = (RelativeLayout) findViewById(R.id.slide_menu);
        imgList = (ListView) findViewById(R.id.img_list);
        typeShowId = (TextView) findViewById(R.id.type_show_id);
        showMenuRv = (EmptyRecyclerView) findViewById(R.id.show_menu_rv);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initevent() {
        fabSelectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(slideMenu);
            }
        });
        imgList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<menuBean> menuBeanList = LitePal.where("foodtype == ?", position+"").find(menuBean.class);
                //在这里更新右面的布局
            }
        });

    }
}
