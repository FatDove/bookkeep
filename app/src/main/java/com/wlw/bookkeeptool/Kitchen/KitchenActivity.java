package com.wlw.bookkeeptool.Kitchen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jia.base.BaseActivity;
import com.jia.base.BasePresenter;
import com.jia.libui.CoverFlow.CoverFlowViewPager;
import com.jia.libui.CoverFlow.OnPageSelectListener;
import com.wlw.bookkeeptool.Kitchen.adapter.Adapter_kitchen_rv;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;

import java.util.ArrayList;
import java.util.List;

import litepal.LitePal;

import static com.wlw.bookkeeptool.MyApplication.UserName;

public class KitchenActivity extends BaseActivity {
Context context;
    private TextView front_people_count;
    private Button update;
    private CoverFlowViewPager front_people_vp;
    private RecyclerView see_menu_rv;
    private ImageView img;
    private LinearLayout parentLayout;
    private List<View> viewList = new ArrayList<>() ;
    private EveryDeskTable everyDeskTable;
    private String staffID;
    private ArrayList<String> staffID_list = new ArrayList<>(); //上面的 list 所需数据
    private ArrayList<EveryDeskTable> dataList; //下面的主要list 需要的数据
    private Adapter_kitchen_rv adapter_kitchen_rv;

    @Override
    protected void initActivityView(Bundle savedInstanceState) {
        context =this;
        setContentView(R.layout.layout_kitchen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        initData();
    }
    @Override
    protected void initView(){
          front_people_count  = findViewById(R.id.front_people_count);
          update  = findViewById(R.id.update);
          front_people_vp  = findViewById(R.id.front_people_vp);
          img  = findViewById(R.id.img);
          parentLayout  = findViewById(R.id.parentLayout);
          see_menu_rv  = findViewById(R.id.see_menu_rv);
          see_menu_rv.setLayoutManager(new GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false));
        for (int i = 0; i < staffID_list.size(); i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.see_editor_vp_view2, null);
            TextView vpText = v.findViewById(R.id.see_editor_vp_text);
            vpText.setText("员工:"+staffID_list.get(i));
            viewList.add(v);
        }
        filldata(staffID_list.get(0));
        front_people_vp.setViewList(viewList);
        front_people_vp.setOnPageSelectListener(new OnPageSelectListener(){
            @Override
            public void select(int position) {
                Toast.makeText(context, position+"?", Toast.LENGTH_SHORT).show();
                filldata(staffID_list.get(position));
            }
        });
    }

    private void initEvent() {
        adapter_kitchen_rv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EveryDeskTable everyDeskTable = (EveryDeskTable) adapter.getData().get(position);
                Intent intent = new Intent(context,KitchenInfoActivity.class);
                intent.putExtra("DeskBean",everyDeskTable);
                startActivity(intent);
            }
        });
    }

    //给当前的RecycView 填充数据
    private void filldata(String  staffID){
        Toast.makeText(context, "staffID", Toast.LENGTH_SHORT).show();
        ArrayList<EveryDeskTable> list = (ArrayList<EveryDeskTable>) LitePal.where("staff_id = ?", staffID).find(EveryDeskTable.class,true);
        dataList = list;
        adapter_kitchen_rv = new Adapter_kitchen_rv(context, dataList);
        see_menu_rv.setAdapter(adapter_kitchen_rv);
        initEvent();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @Override
    protected void initData() {
        dataList = new ArrayList<>();
        //0 测试数据  正式不用
        Intent intent = getIntent();
        String staffID = intent.getStringExtra("staffID");
        String menuData = intent.getStringExtra("menuData");
        Gson gson = new Gson();
        everyDeskTable = gson.fromJson(menuData, EveryDeskTable.class);
        everyDeskTable.getDeskNum();
        Toast.makeText(mContext, staffID+"||||"+menuData, Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext,   everyDeskTable.getDeskNum()+"??", Toast.LENGTH_SHORT).show();
        everyDeskTable.setQuery_role_id("1");
        if (!everyDeskTable.save())
        {
            Toast.makeText(context, "存不上啊", Toast.LENGTH_SHORT).show();
            return;
        }
        //1 先从本地数据库查数据
        SQLiteDatabase database = LitePal.getDatabase();
//        dataList = LitePal.where("query_role_id = 1 ; isEndwork = 0 ; username = "+UserName+"").find(EveryDeskTable.class,true);
        Cursor cursor = database.query
                (true, "EveryDeskTable", new String[]{"id","staff_id"}, "username= ? and query_role_id = ? and isEndwork = ?", new String[]{UserName,"1","0"}, "staff_id", null, null, null, null);
        while (cursor.moveToNext()){
            String staff_id = cursor.getString(cursor.getColumnIndex("staff_id"));
            staffID_list.add(staff_id);
        }
        staffID_list.add("9527");


//        for (int i = 0; i < arrTitle.length; i++) {
//            View v = LayoutInflater.from(this).inflate(R.layout.see_editor_vp_view, null);
//            TextView vpText = v.findViewById(R.id.see_editor_vp_text);
//            vpText.setText(arrTitle[i]);
//            viewList.add(v);
//        }
//        front_people_vp.setViewList(viewList);
//        front_people_vp.setOnPageSelectListener(new OnPageSelectListener() {
//            @Override
//            public void select(int position) {
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
