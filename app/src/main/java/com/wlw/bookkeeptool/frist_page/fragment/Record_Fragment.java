package com.wlw.bookkeeptool.frist_page.fragment;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.jia.base.BaseFragment;
import com.jia.base.BasePresenter;
import com.wlw.bookkeeptool.R;

public class Record_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String SHOW_TAP_TARGET = "SHOW_TAP_TARGET";

    @Override
    protected View initFragmentView(LayoutInflater inflater) {
        Toast.makeText(getActivity(),  "2", Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.fg_today_order_layout, null);
        return view;
    }

    @Override
    protected void initFragmentChildView(View view) {


//        showTapTarget();
    }

    @Override
    protected void initFragmentData(Bundle savedInstanceState) {


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    @Override
    public void onClick(View view) {

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
}