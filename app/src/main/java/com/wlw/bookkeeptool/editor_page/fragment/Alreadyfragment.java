package com.wlw.bookkeeptool.editor_page.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jia.libui.MyControl.EmptyRecyclerView;
import com.wlw.bookkeeptool.R;

/**
 * Created by wlw on 2018/7/5.
 */

public class Alreadyfragment extends Fragment {
    private EmptyRecyclerView alreadyFragmentRv;
    private ImageView emptyIv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.already_fragment, null);
        initView();
        alreadyFragmentRv = (EmptyRecyclerView) v.findViewById(R.id.already_fragment_rv);
        emptyIv = (ImageView) v.findViewById(R.id.empty_iv);
        alreadyFragmentRv.setEmptyView(emptyIv);

        return v;
    }

    private void initView() {

    }
}
