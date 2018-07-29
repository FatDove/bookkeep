package com.wlw.bookkeeptool.frist_page.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.jia.base.BaseFragment;
import com.jia.base.BasePresenter;
import com.jia.libui.Navigation.impl.ChatNavigation;
import com.jia.libutils.DateUtils;
import com.jia.libutils.WindowUtils;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.Record.Record_Info_Activity;
import com.wlw.bookkeeptool.frist_page.adapter.Adapter_record_rv;
import com.wlw.bookkeeptool.tableBean.EveryDayTable;

import java.util.ArrayList;
import java.util.Calendar;

import litepal.LitePal;

import static com.wlw.bookkeeptool.MyApplication.UserName;

public class Record_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String SHOW_TAP_TARGET = "SHOW_TAP_TARGET";
    private TextView tv;
    private TextView star_date;
    private TextView end_date;
    private ImageView query;
    private RecyclerView record_rv;
    private TextView Total;
    private LinearLayout parentLayout;
    private ArrayList<EveryDayTable> dataList;
    private Adapter_record_rv adapter_record_rv;
    private boolean isGetData = false;
    @Override
    protected View initFragmentView(LayoutInflater inflater) {
        Toast.makeText(getActivity(), "2", Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.fg_record_layout, null);
        star_date = view.findViewById(R.id.star_date);
        end_date = view.findViewById(R.id.end_date);
        parentLayout = view.findViewById(R.id.parentLayout);
        query = view.findViewById(R.id.query);
        record_rv = view.findViewById(R.id.record_rv);
        Total = view.findViewById(R.id.Total);
        record_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        initNavigation();

        return view;
    }
    //点击事件
    public void  initevent(){
        star_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        query.setOnClickListener(this);
        adapter_record_rv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EveryDayTable everyDayTable = (EveryDayTable) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), Record_Info_Activity.class);
                intent.putExtra("DayID",everyDayTable.getId());
                getActivity().startActivity(intent);
            }
        });
    }
    //数据初始化 (默认查当前月)
    public void  initdata(){
        float sum = 0.0f;
        Calendar c = Calendar.getInstance();//
        int  mYear = c.get(Calendar.YEAR); // 获取当前年份
        int  mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int  mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前日期
        int  mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int  mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int  mMinute = c.get(Calendar.MINUTE);//分
        dataList = (ArrayList<EveryDayTable>) LitePal.where("year = "+mYear+"; month = "+mMonth+"").find(EveryDayTable.class);
        adapter_record_rv = new Adapter_record_rv(dataList);
        record_rv.setAdapter(adapter_record_rv);
        for (EveryDayTable e:dataList){
            sum+=e.getTotalPrice_day();
        }
        Total.setText(sum+"元");
    }
    //初始化Toolbar
    public void initNavigation() {
        ChatNavigation.Builder homeBuilder = new ChatNavigation.Builder(getContext(), parentLayout);
        homeBuilder.setTitleRes("流水账查询");
//        homeBuilder.setRightImageLeftRes(R.drawable.ic_delete_select).setRightImageLeftOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        homeBuilder.builder().build(); //builder是组装  build是创建
        AdaptationStatusbar();
    }
    //计算状态栏
    private void AdaptationStatusbar() {
        int statusheight = WindowUtils.getStatusHeight(getContext());
        LinearLayout layout = (LinearLayout) parentLayout.getChildAt(0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        layoutParams.height += statusheight;
        layout.setLayoutParams(layoutParams);
        layout.setPadding(0, layout.getPaddingTop() + statusheight, 0, 0);
    }
    @Override
    protected void initFragmentChildView(View view) {
//        showTapTarget();
    }

    @Override
    protected void initFragmentData(Bundle savedInstanceState){

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    //在切换时有刷新的效果
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            initdata();
            initevent();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.star_date:
                DateUtils.showDateDialog(getContext(),star_date);
                break;
            case R.id.end_date:
                DateUtils.showDateDialog(getContext(),end_date);
                break;
            case R.id.query:
                go_query(star_date.getText().toString(),end_date.getText().toString());
                break;
        }
    }
    //按日期去查询
    private void go_query(String Stardate, String Enddate) {
        dataList.clear();
        float sum=0.0f;
        Cursor cursor = LitePal.findBySQL("select * from everyDayTable where username='"+UserName+"'and shutDownTimeStr>='"+Stardate+"' and shutDownTimeStr<='"+Enddate+"'");
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String deskCount = cursor.getString(cursor.getColumnIndex("deskcount"));
            String shutDownTimeStr = cursor.getString(cursor.getColumnIndex("shutdowntimestr"))+"";
            float totalPrice_day = cursor.getFloat(cursor.getColumnIndex("totalprice_day"));
            EveryDayTable everyDayTable = new EveryDayTable();
            everyDayTable.setId(id);
            everyDayTable.setTotalPrice_day(totalPrice_day);
            everyDayTable.setShutDownTimeStr(shutDownTimeStr);
            everyDayTable.setDeskCount(deskCount);
            dataList.add(everyDayTable);
                sum+=totalPrice_day;
//            adapter_record_rv = new Adapter_record_rv(dataList);
//            record_rv.setAdapter(adapter_record_rv);
        }
            adapter_record_rv.notifyDataSetChanged();
            Total.setText(sum+"元");
    }

    /**
     * 每次切换fragment都要刷新调用的方法 onCreateAnimation
     */
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        //   进入当前Fragment
//        if (enter && !isGetData) {
//            isGetData = true;
//            //   这里可以做网络请求或者需要的数据刷新操作
////            GetData();
//        } else {
//            isGetData = false;
//        }
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }
    private void showTapTarget() {
//        if (!SPUtils.getInstance().getBoolean(SHOW_TAP_TARGET)) {
        if (true) {
            // 创建新功能引导
            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(view.findViewById(R.id.tv1), "点击这里，添加频道", "666666")
                                    .outerCircleColor(R.color.colorAccent)
                                    .outerCircleAlpha(0.96f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.colorAccent)
                                    .descriptionTextSize(10)
                                    .textColor(R.color.white)
                                    .textTypeface(Typeface.SANS_SERIF)
                                    .dimColor(R.color.white)
                                    .drawShadow(false)
                                    .cancelable(true)
                                    .tintTarget(false)
                                    .transparentTarget(false)
                                    .targetRadius(30),
                            TapTarget.forBounds(new Rect(100, 500, 500, 500), "Down", ":^)")
                                    .cancelable(false)
                                    .outerCircleColor(R.color.colorAccent)
                                    .outerCircleAlpha(0.96f)
                                    .targetCircleColor(R.color.white)
                                    .titleTextSize(20)
                                    .titleTextColor(R.color.colorAccent)
                                    .descriptionTextSize(10)
                                    .textColor(R.color.white)
                                    .textTypeface(Typeface.SANS_SERIF)
                                    .dimColor(R.color.white)
                                    .drawShadow(false)
                                    .cancelable(true)
                                    .tintTarget(false)
                                    .transparentTarget(false)
                                    .targetRadius(30).icon(getActivity().getResources().getDrawable(R.drawable.es_icon_qq))).start();

//
//            TapTargetView.showFor(getActivity(),
//                    TapTarget.forView(view.findViewById(R.id.iv_channel_add), "点击这里，添加频道", "66666666666666666")
//                            .outerCircleColor(R.color.colorAccent)
//                            .outerCircleAlpha(0.96f)
//                            .targetCircleColor(R.color.white)
//                            .titleTextSize(20)
//                            .titleTextColor(R.color.colorAccent)
//                            .descriptionTextSize(10)
//                            .textColor(R.color.white)
//                            .textTypeface(Typeface.SANS_SERIF)
//                            .dimColor(R.color.white)
//                            .drawShadow(false)
//                            .cancelable(true)
//                            .tintTarget(false)
//                            .transparentTarget(false)
//                            .targetRadius(30)).(new Rect(50,50,50,50), "Down", ":^)")
//                    .cancelable(false)
//                    .icon(getActivity().getResources().getDrawable(R.drawable.es_icon_qq));
            SPUtils.getInstance().put(SHOW_TAP_TARGET, true);
        }

    }

    @Override
    public void onResume(){
        initdata();
        initevent();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }
}