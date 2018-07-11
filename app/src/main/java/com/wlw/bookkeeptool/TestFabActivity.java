package com.wlw.bookkeeptool;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.clans.fab.FloatingActionMenu;

/**
 * Created by wlw on 2018/7/11.
 */

public class TestFabActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fabtest);

        final FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
    }
}
