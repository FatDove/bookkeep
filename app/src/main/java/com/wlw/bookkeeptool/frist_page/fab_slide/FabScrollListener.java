package com.wlw.bookkeeptool.frist_page.fab_slide;


import android.support.v7.widget.RecyclerView;

/**
 * Created by FanJiaLong on 2018/2/8.
 */
public class FabScrollListener extends RecyclerView.OnScrollListener {
  private static final  int THRESHOLD = 20;
    private int distance = 0;
    private boolean visible = true;

private HideShowScrollListener hideShowScrollListener;
    public FabScrollListener(HideShowScrollListener hs) {
        hideShowScrollListener = hs;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /**
         * dy:Y轴方向的增量
         * 有正负
         */
        if (distance>THRESHOLD&&visible){
            visible= false;
            //隐藏动画
            hideShowScrollListener.Hide();
            distance=0;
        }else if (distance<-THRESHOLD&&!visible){
            visible=true;
            //显示动画
            hideShowScrollListener.Show();
            distance=0;
        }
        if (visible&&dy>0||(!visible&&dy<0)){
            distance+=dy;
        }
    }
}
