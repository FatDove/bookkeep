package com.wlw.bookkeeptool.editor_page;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.editor_page.fragment.Addfragment;
import com.wlw.bookkeeptool.editor_page.fragment.Alreadyfragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlw on 2018/7/5.
 */

public class editor_activity extends FragmentActivity {
Context context;
    private SegmentTabLayout oneLabel;
    private String[] listTitle;
    private ViewPager edit_vp;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.editor_activity);
        initdata();
        initView();
        event();
    }
    private void initdata() {
        listTitle = new String[]{"已有","新增"};
    }
    private void initView() {
        oneLabel = (SegmentTabLayout) findViewById(R.id.oneLabel);
        oneLabel.setTabData(listTitle);

        fragments.add(new Alreadyfragment());
        fragments.add(new Addfragment());

        edit_vp = findViewById(R.id.edit_vp);
        edit_vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


    }
    private void event(){

//标题栏与viewpager 相互监听
        oneLabel.setOnTabSelectListener(new OnTabSelectListener()  {
            @Override
            public void onTabSelect(int position) {
                edit_vp.setCurrentItem(position);
                Toast.makeText(context, position+"onTabSelect", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabReselect(int position) {
                Toast.makeText(context, position+"onTabReselect", Toast.LENGTH_SHORT).show();
            }
        });
        edit_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                oneLabel.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        edit_vp.setCurrentItem(0);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

}
