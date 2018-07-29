package com.wlw.bookkeeptool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.jia.libui.banner.Banner;
import com.jia.libui.banner.listener.OnBannerListener;
import com.jia.libui.banner.loader.ImageLoader;
import com.wlw.bookkeeptool.Order.OrderActivity;
import com.wlw.bookkeeptool.editor_page.see_and_editor_activity;
import com.wlw.bookkeeptool.frist_page.fab_slide.FabScrollListener;
import com.wlw.bookkeeptool.frist_page.fab_slide.HideShowScrollListener;
import com.wlw.bookkeeptool.frist_page.adapter.Adapter_today_order_rv;
import com.wlw.bookkeeptool.tableBean.EveryDeskTable;

import java.io.File;
import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;
import litepal.LitePal;


public class MainActivity2 extends AppCompatActivity implements OnBannerListener, HideShowScrollListener, View.OnClickListener {
    Context context;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private Banner banner;
    private Button editor;
    private Button record;
    private TextView tv;
    private EmptyRecyclerView todayOrder_RV;
    private FloatingActionMenu fab;
    private LinearLayout lay1;
    private ImageView emptyIv;
    private Adapter_today_order_rv first_page_adapter;
    private EmptyRecyclerView todayOrder;
    private FloatingActionButton fabWorkOut;
    private FloatingActionButton fabOrder;
    private ArrayList<EveryDeskTable> everyDeskTablelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context =this;
        File file = new File(Environment.getExternalStorageDirectory() + "/" + MyApplication.AppImgFile);
        if (!file.exists()) {
            file.mkdirs();
        }
//        showShare(); //分享
//        Platform qq = ShareSDK.getPlatform(QQ.NAME);
//        qq.setPlatformActionListener(this);
//        qq.SSOSetting(false);
//        if (!qq.isClientValid()) {
//            Toast.makeText(this, "QQ未安装,请先安装QQ", Toast.LENGTH_SHORT).show();
//        }
//        authorize(qq); //
//        loginByWeixin(); //微信第三方登录；
//        initBanner();
        initData();
        initView();
        initEvent();
    }

    private void initView() {
        editor = (Button) findViewById(R.id.editor);
        record = (Button) findViewById(R.id.record);
        tv = (TextView) findViewById(R.id.tv);
        todayOrder_RV = (EmptyRecyclerView) findViewById(R.id.today_order);

        emptyIv = (ImageView) findViewById(R.id.empty_iv);
        todayOrder_RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //        给recycleview添加 滑动监听
        todayOrder_RV.addOnScrollListener(new FabScrollListener(this));
        todayOrder_RV.setEmptyView(emptyIv);
        first_page_adapter = new Adapter_today_order_rv(this, everyDeskTablelist);
        todayOrder_RV.setAdapter(first_page_adapter);
        todayOrder = (EmptyRecyclerView) findViewById(R.id.today_order);
        fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.setClosedOnTouchOutside(true);
        fabWorkOut = (FloatingActionButton) findViewById(R.id.fab_work_out);
        fabOrder = (FloatingActionButton) findViewById(R.id.fab_order);
    }

    private void initEvent() {
        fabWorkOut.setOnClickListener(this);
        fabOrder.setOnClickListener(this);
        editor.setOnClickListener(this);
        record.setOnClickListener(this);

        first_page_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity2.this, position + "?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //  首次进入添加数据
    private void initData() {
        everyDeskTablelist = (ArrayList<EveryDeskTable>) LitePal.where("isCheckout == ?"  ,"0").find(EveryDeskTable.class,true);//激进查询
        int size = everyDeskTablelist.size();
    }
//    private void initBanner() {
//        banner = (Banner) findViewById(R.id.banner);
//        //放图片地址的集合
//        list_path = new ArrayList<>();
//        //放标题的集合
//        list_title = new ArrayList<>();
//
//        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
//        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
//        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
//        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
//        list_title.add("好好学习");
//        list_title.add("天天向上");
//        list_title.add("热爱劳动");
//        list_title.add("不搞对象");
//        //设置内置样式，共有六种可以点入方法内逐一体验使用。
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        //设置图片加载器，图片加载器在下方
//        banner.setImageLoader(new MyLoader());
//        //设置图片网址或地址的集合
//        banner.setImages(list_path);
//        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
//        banner.setBannerAnimation(Transformer.Default);
//        //设置轮播图的标题集合
//        banner.setBannerTitles(list_title);
//        //设置轮播间隔时间
//        banner.setDelayTime(3000);
//        //设置是否为自动轮播，默认是“是”。
//        banner.isAutoPlay(true);
//        //设置指示器的位置，小点点，左中右。
////        banner.setBannerStyle()
//        banner.setIndicatorGravity(BannerConfig.CENTER)
//                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
//                .setOnBannerListener(this)
//                //必须最后调用的方法，启动轮播图。
//                .start();
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_work_out:
                break;
            case R.id.fab_order:
                Intent intent2 = new Intent(context,OrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.record:
                break;
            case R.id.editor:
                Intent intent4 = new Intent(context,see_and_editor_activity.class);
                startActivity(intent4);
                break;
        }
    }
    private void showShare() {
        String Str = "我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-22我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-我是分享文本-33";
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("测试分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(Str);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);

    }


    //按钮的缩放动画
    public void scaleAnimation(Button bt) {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0.2f, 1f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.7f, 1f);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.7f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(bt, holder1, holder2, holder3);
        objectAnimator.setDuration(400);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getAnimatedFraction();
                animation.getAnimatedValue();
                animation.getCurrentPlayTime();
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        objectAnimator.start();
    }

    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Log.i("tag", "你点了第" + position + "张轮播图");
    }


    @Override
    public void Hide() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) fab.getLayoutParams();
        RelativeLayout.LayoutParams s = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        Log.i("fanjava", s.bottomMargin + "||" + layoutParams.bottomMargin);
        fab.close(true);
        fab.animate().translationY(fab.getHeight() + layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
    }

    @Override
    public void Show() {
        RelativeLayout.LayoutParams s = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    }
    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}