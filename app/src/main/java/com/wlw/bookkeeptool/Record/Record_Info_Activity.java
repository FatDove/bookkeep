package com.wlw.bookkeeptool.Record;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jia.base.BaseActivity;
import com.jia.base.BasePresenter;
import com.jia.libui.Navigation.impl.ChatNavigation;
import com.jia.libutils.DateUtils;
import com.jia.libutils.WindowUtils;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.Record.adapter.Adapter_record_info_rv;
import com.wlw.bookkeeptool.tableBean.EveryDayTable;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;
import com.wlw.bookkeeptool.tableBean.EveryDishTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import litepal.LitePal;

/**
 * Created by wlw on 2018/7/19.
 */

public class Record_Info_Activity extends BaseActivity {
    Context context;
    private TextView that_time_date;
    private TextView that_time_weekday;
    private RecyclerView record_info_rv;
    private TextView big_Sum;
    private ImageView copy_img;
    private ImageView share_img;
    private LinearLayout parentLayout;
    private ArrayList<Record_Info_Rv_Bean> needlist= new ArrayList<>();
    private Adapter_record_info_rv adapter_record_info_rv;
    private int desk_count;
    private float totalPrice_day;
    private TextView desk_count_tv;
    private String time_date;
    private String time_weekday;

    @Override
    protected void initActivityView(Bundle savedInstanceState) {
        context = this;
        setContentView(R.layout.record_info_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int dayID = intent.getIntExtra("DayID", 0);
        EveryDayTable everyDayTables = LitePal.find(EveryDayTable.class,dayID,true);
        ArrayList<EveryDishTable> dataList = new ArrayList<>();
        //将查询出来的 所有单条数据都存在 一个dataList集合里
        for (EveryDeskTable deskTable :everyDayTables.getEveryDeskTableList())
     {
         EveryDeskTable everyDeskTable = LitePal.find(EveryDeskTable.class, deskTable.getId(), true);
         dataList.addAll(everyDeskTable.getEveryDishTableList());
     }
        //将dataList集合中的数据 按名称分类存在 HashMap中  HashMap的value也是 everyDishTable数组集合
        HashMap<String,ArrayList<EveryDishTable>> dataHashMap = new HashMap<>();
        for (EveryDishTable dishTable :dataList){
            if(dataHashMap.containsKey(dishTable.getFoodname())){
                dataHashMap.get(dishTable.getFoodname()).add(dishTable);
            }else{
                ArrayList<EveryDishTable> newDishTable = new ArrayList<>();
                newDishTable.add(dishTable);
                dataHashMap.put(dishTable.getFoodname(),newDishTable);
            }
        }
        //在遍历HashMap得到自己想要的数据
        for (Map.Entry<String, ArrayList<EveryDishTable>> entry : dataHashMap.entrySet()) {
            entry.getKey();
            float sum=0.0f;
            int count =0;
            ArrayList<EveryDishTable> valuelist = entry.getValue();
             for (EveryDishTable e : valuelist){
                 count+= e.getFoodCount();
                 sum+= e.getTotalPrice_dish();
             }
            Record_Info_Rv_Bean record_info_rvrv_bean = new Record_Info_Rv_Bean(entry.getKey(),sum,count);
            needlist.add(record_info_rvrv_bean);
        }
        //-------拿到结果----------
        desk_count = everyDayTables.getEveryDeskTableList().size();
        totalPrice_day = everyDayTables.getTotalPrice_day();
        adapter_record_info_rv = new Adapter_record_info_rv(needlist);
        record_info_rv.setAdapter(adapter_record_info_rv);
        desk_count_tv.setText("共"+desk_count+"单");
        big_Sum.setText(totalPrice_day+"元");
        time_date = DateUtils.BackDateStr(everyDayTables.getShutDownTime(),1);
        time_weekday = DateUtils.BackDateStr(everyDayTables.getShutDownTime(),2);
        that_time_date.setText(time_date);
        that_time_weekday.setText(time_weekday);
        desk_count_tv.setText(desk_count+"单");
         initevent();
    }

    private void initevent() {
        copy_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                //创建ClipData对象
                String cpoyText="";
                for (Record_Info_Rv_Bean r:needlist){
                    cpoyText+= r.getName()+"-"+r.getSum_count()+"-"+r.getSum_price()+"元\n";
                }
                String title = "【"+time_date+" "+time_weekday+" "+"盘点内容】\n";
                String title2 = "共"+desk_count+"单\n";
                String foot = "合计："+totalPrice_day+"元\n";
                String bottom = "【欢迎使用-餐厅小助手】";
                ClipData clipData = ClipData.newPlainText("盘点内容", title+title2+cpoyText+foot+bottom);
                //添加ClipData对象到剪切板中
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
    }
    @Override
    protected void initView() {
        that_time_date = (TextView) findViewById(R.id.that_time_date);
        desk_count_tv = (TextView) findViewById(R.id.desk_count_tv);
        that_time_weekday = (TextView) findViewById(R.id.that_time_weekday);
        record_info_rv = (RecyclerView) findViewById(R.id.record_info_rv);
        big_Sum = (TextView) findViewById(R.id.big_Sum);
        copy_img = (ImageView) findViewById(R.id.copy_img);
        share_img = (ImageView) findViewById(R.id.share_img);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        record_info_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        initNavigation();
    }

    //初始化Toolbar
    public void initNavigation() {
        ChatNavigation.Builder homeBuilder = new ChatNavigation.Builder(this,parentLayout);
        homeBuilder.setTitleRes("当日盘点");
        homeBuilder.builder().build(); //builder是组装  build是创建
        AdaptationStatusbar();
    }

    //计算状态栏
    private void AdaptationStatusbar() {
        int statusheight = WindowUtils.getStatusHeight(this);
        LinearLayout layout = (LinearLayout) parentLayout.getChildAt(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.height += statusheight;
        layout.setLayoutParams(layoutParams);
        layout.setPadding(0, layout.getPaddingTop() + statusheight, 0, 0);
    }

}
