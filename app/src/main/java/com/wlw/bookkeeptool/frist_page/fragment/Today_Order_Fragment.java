package com.wlw.bookkeeptool.frist_page.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
import com.jia.libutils.WindowUtils;
import com.wlw.bookkeeptool.CustomerMenu.CustomerMenu_infoActivity;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.frist_page.adapter.Adapter_today_order_rv;
import com.wlw.bookkeeptool.tableBean.everyDeskTable;

import java.util.ArrayList;
import java.util.Calendar;

import litepal.LitePal;

import static com.wlw.bookkeeptool.MyApplication.UserName;

public class Today_Order_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String SHOW_TAP_TARGET = "SHOW_TAP_TARGET";

    private TextView mDateYear;
    private TextView mDateMonthDay;
    private TextView mTv2;
    private TextView mOrderCount;
    private TextView mTv3;
    private TextView mUnCheckoutCount;
    private TextView mTv;
    private RecyclerView mTodayOrder;
    private ArrayList<everyDeskTable> everyDeskTablelist;
    private int checkout_count=0;
    private int allDesk_count=0;
    private Adapter_today_order_rv adapter_today_order_rv;
    private LinearLayout parentLayout;
    private WorkOutBroadcastReceiver workOutBroadcastReceiver;


    @Override
    protected View initFragmentView(LayoutInflater inflater) {
        Toast.makeText(getActivity(),  "1", Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.fg_today_order_layout, null);
        mTv2 = (TextView) view.findViewById(R.id.tv2);
        mTv3 = (TextView) view.findViewById(R.id.tv3);
        mTv = (TextView) view.findViewById(R.id.tv);
        parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
        mDateYear = (TextView) view.findViewById(R.id.date_year);
        mDateMonthDay = (TextView) view.findViewById(R.id.date_month_day);
        mOrderCount = (TextView) view.findViewById(R.id.order_count);
        mUnCheckoutCount = (TextView) view.findViewById(R.id.unCheckout_count);
        mTodayOrder = view.findViewById(R.id.today_order);
        initNavigation();

        workOutBroadcastReceiver = new WorkOutBroadcastReceiver();
        IntentFilter fff = new IntentFilter("WorkOut");
        getActivity().registerReceiver(workOutBroadcastReceiver,fff);

        return view;
    }

    @Override
    protected void initFragmentChildView(View view) {

//        showTapTarget();
    }

    @Override
    protected void initFragmentData(Bundle savedInstanceState){

    }
    
    private void initdata(){
        try{
            everyDeskTablelist = (ArrayList<everyDeskTable>) LitePal.where( "isEndwork = 0 ; username = "+UserName+"").find(everyDeskTable.class,true);//激进查询
            allDesk_count = everyDeskTablelist.size();
            checkout_count = LitePal.where("isEndwork = 0 ; username = "+UserName+"; isCheckout = 1 ").count(everyDeskTable.class);
        }catch (Exception e){
            Toast.makeText(getActivity(), "异常了", Toast.LENGTH_SHORT).show();
        }
        adapter_today_order_rv = new Adapter_today_order_rv(getActivity(),everyDeskTablelist);
        mTodayOrder.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mTodayOrder.setAdapter(adapter_today_order_rv);
        mOrderCount.setText(allDesk_count+"");
        mUnCheckoutCount.setText(checkout_count+"");
        Calendar c = Calendar.getInstance();//
        int  mYear = c.get(Calendar.YEAR); // 获取当前年份
        int  mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int  mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        int  mWay = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
        int  mHour = c.get(Calendar.HOUR_OF_DAY);//时
        int  mMinute = c.get(Calendar.MINUTE);//分
        int  mSecond = c.get(Calendar.SECOND);//分
        mDateYear.setText(mYear+"年");
        mDateMonthDay.setText(mMonth+"月"+mDay+"日");
        System.out.println(mYear+"年");
        System.out.println(mMonth+"月");
        System.out.println(mDay+"日");
        System.out.println(mWay);
        System.out.println(mHour);
        System.out.println(mMinute);

    }
    //初始化Toolbar
    public void initNavigation() {
        ChatNavigation.Builder homeBuilder = new ChatNavigation.Builder(getContext(),parentLayout);
        homeBuilder.setTitleRes("餐厅小助手");
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

    private void initevent() {
        adapter_today_order_rv.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                everyDeskTable everyDeskTable = (everyDeskTable) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), CustomerMenu_infoActivity.class);
                intent.putExtra("DeskID", everyDeskTable.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initdata();
        initevent();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(workOutBroadcastReceiver);
        super.onDestroy();
    }

    private void showTapTarget() {
//        if (!SPUtils.getInstance().getBoolean(SHOW_TAP_TARGET)) {
        if (true) {
            // 创建新功能引导
            new TapTargetSequence(getActivity())
                    .targets(
                            TapTarget.forView(view.findViewById(R.id.tv1), "点击这里，添加频道","666666")
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
                            TapTarget.forBounds(new Rect(100,500,500,500), "Down", ":^)")
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

    class WorkOutBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String isWorkOut = intent.getStringExtra("WorkOut");
            if(isWorkOut.equals("Yes")){
//                Log.i("onReceive", "新朋友"+newFriendRequest);
                 Toast.makeText(context,"打烊收工咯！可以去【流水记录】中查看。", Toast.LENGTH_SHORT).show();
                  initdata();
                  initevent();
            }
        }
    }


}