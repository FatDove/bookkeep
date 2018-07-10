package com.jia.libui.CoverFlow;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class CoverFlowAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

    /**
     * 子元素的集合
     */
    private List<View> mViewList;

    /**
     * 滑动监听的回调接口
     */
    private OnPageSelectListener listener;

    private Context mContext;

    private float mPositionOffset=0;

    public CoverFlowAdapter(List<View> mImageViewList, Context context) {
        this.mViewList = mImageViewList;
        mContext = context;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        // 该方法回调ViewPager 的滑动偏移量
        if (mViewList.size() > 0 && position < mViewList.size()) {
            //当前手指触摸滑动的页面,从0页滑动到1页 offset越来越大，padding越来越大
            mViewList.get(position).setScaleX(1 - positionOffset * 0.17f);
            mViewList.get(position).setScaleY(1 - positionOffset * 0.17f);
            mViewList.get(position).setAlpha(1 - positionOffset * 0.5f);
//            mViewList.get(position).setTranslationX(dp2px(60) * positionOffset);

            // position+1 为即将显示的页面，越来越大
            if (position < mViewList.size() - 1) {
                mViewList.get(position + 1).setScaleX(0.83f + positionOffset * 0.17f);
                mViewList.get(position + 1).setScaleY(0.83f + positionOffset * 0.17f);
                mViewList.get(position + 1).setAlpha(0.5f + positionOffset * 0.5f);
            }
        }
        mPositionOffset=positionOffset;
    }

    @Override
    public void onPageSelected(int position) {
        // 回调选择的接口
        if (listener != null) {
            listener.select(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    public void setOnPageSelectListener(OnPageSelectListener listener) {
        this.listener = listener;
    }

}
