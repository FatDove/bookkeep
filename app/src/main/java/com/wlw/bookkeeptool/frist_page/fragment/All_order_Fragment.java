package com.wlw.bookkeeptool.frist_page.fragment;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.jia.base.BaseFragment;
import com.jia.base.BasePresenter;
import com.jia.libui.CoverFlow.CoverFlowAdapter;
import com.jia.libui.CoverFlow.CoverFlowViewPager;
import com.jia.libui.CoverFlow.OnPageSelectListener;
import com.jia.libui.MyControl.EmptyRecyclerView;
import com.jia.libui.MyDialog.MyDialog;
import com.jia.libui.Navigation.impl.ChatNavigation;
import com.jia.libutils.WindowUtils;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.editor_page.FoodMenuShow_listAdapter;
import com.wlw.bookkeeptool.editor_page.SeeEditorPopupWindow;
import com.wlw.bookkeeptool.tableBean.menuBean;
import com.wlw.bookkeeptool.utils.mWindowUtil;

import java.util.ArrayList;
import java.util.List;

import litepal.LitePal;

public class All_order_Fragment extends BaseFragment implements View.OnClickListener {
    private static final String SHOW_TAP_TARGET = "SHOW_TAP_TARGET";

    private CoverFlowViewPager vpConverFlow;
    private List<View> viewList = new ArrayList<>();
    private CoverFlowAdapter coverFlowAdapter;
    private String[] arrTitle = {"热", "凉", "主", "饮", "其他"};
    private int[] arrColor = {R.drawable.button_border_red, R.drawable.button_border_cyan, R.drawable.button_border_rice_white, R.drawable.button_border_green, R.drawable.button_border_yellow};
    private CoverFlowViewPager cover;
    private ImageView addMenu;
    private SeeEditorPopupWindow seeEditorPopupWindow;
    private final int IMAGE_Local = 1;
    //    private int food_type;  //用来标记食欲的大类
    private EmptyRecyclerView emptyRecyclerView;
    private String foodtype = "0"; //查询菜品的类型 {0 1 2 3 4}
    //显示数据的集合
    ArrayList<menuBean> menuBeanList = new ArrayList<>();
    private FoodMenuShow_listAdapter foodMenuShow_listAdapter;
    private LinearLayout parentLayout;

    @Override
    protected View initFragmentView(LayoutInflater inflater) {
        Toast.makeText(getActivity(),  "3", Toast.LENGTH_SHORT).show();
        view = inflater.inflate(R.layout.fg_all_order_layout, null);
//        SQLiteDatabase db = Connector.getDatabase();
        initView(view);
        initNavigation();
        return view;
    }
    private void initView(View view){
        vpConverFlow = (CoverFlowViewPager) view.findViewById(R.id.cover);
        parentLayout =  view.findViewById(R.id.parentLayout);
        for (int i = 0; i < arrTitle.length; i++) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.see_editor_vp_view, null);
            TextView vpText = v.findViewById(R.id.see_editor_vp_text);
            vpText.setBackground(getResources().getDrawable(arrColor[i]));
            vpText.setText(arrTitle[i]);
            viewList.add(v);
        }
        vpConverFlow.setViewList(viewList);
        vpConverFlow.setOnPageSelectListener(new OnPageSelectListener() {
            @Override
            public void select(int position) {
                foodtype = position+"";
                Toast.makeText(getActivity(), position+"?", Toast.LENGTH_SHORT).show();
                initdata(foodtype);
                initevent();
            }
        });
        addMenu = (ImageView) view.findViewById(R.id.add_menu);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.see_editor_rv);
        emptyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }
    private void initevent(){
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator ra = ObjectAnimator.ofFloat(v,"rotation", 0f, 180f);
                ra.setDuration(1000);
                ra.start();
                openPopuWindow_add(arrTitle);
            }
        });
        foodMenuShow_listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.menu_show_item_edit){
                    openPopuWindow_edit((menuBean) adapter.getData().get(position));
                }else if (view.getId()==R.id.delete_view){
                    openDialog((menuBean) adapter.getData().get(position),position);
                }
            }
        });
    }
    public void initdata(String foodtype){
        menuBeanList.clear();
        menuBeanList = (ArrayList<menuBean>) LitePal.where("foodtype = ?",foodtype).find(menuBean.class);
        foodMenuShow_listAdapter = new FoodMenuShow_listAdapter(getActivity(), menuBeanList);
        foodMenuShow_listAdapter.openLoadAnimation();//加载Item动画效果
        emptyRecyclerView.setAdapter(foodMenuShow_listAdapter);
    }
    //打开PopuWindow 展示 列表
    private void openPopuWindow_add(String[] arrTitle) {
        int type =  Integer.parseInt(foodtype);
        seeEditorPopupWindow = new SeeEditorPopupWindow(this,foodtype,arrTitle[type]);
        seeEditorPopupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        seeEditorPopupWindow.showAsDropDown(addMenu,0,0);//设置显示位置
        mWindowUtil.lightoff(getActivity());
        seeEditorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn(getActivity());
            }
        });
    }
    //打开PopuWindow 修改 列表
    private void openPopuWindow_edit(menuBean menuBean){
        seeEditorPopupWindow = new SeeEditorPopupWindow(this,menuBean);
        seeEditorPopupWindow.setAnimationStyle(R.style.mine_popupwindow_anim);//设置出现的动画
        seeEditorPopupWindow.showAsDropDown(addMenu,0,0);//设置显示位置
        mWindowUtil.lightoff(getActivity());
        seeEditorPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mWindowUtil.lightOn(getActivity());
            }
        });
    }
    public void bitmap_to_popu(Bitmap bitmap){
        seeEditorPopupWindow.set_img_to_popu(bitmap);
    }

    //初始化Toolbar
    public void initNavigation() {
        ChatNavigation.Builder homeBuilder = new ChatNavigation.Builder(getContext(), parentLayout);
        homeBuilder.setTitleRes("本店菜单");
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

    //打开弹窗
    private void openDialog(final menuBean menuBean, final int position) {
        MyDialog.Builde builder = new MyDialog.Builde(getActivity());
        builder.setMessage("您是否要删除此条内容？");
        builder.setTitle("提示" );
        builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                delete_from_DB(menuBean,position);//上传接收状态 返回是否成功
            }
        });
        builder.setNegativeButton("等等看", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                //将最后提交的数据在这里保存以便使用
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    //从数据库删除指定内容
    private void delete_from_DB(menuBean menuBean, int position) {
        int i = LitePal.deleteAll(menuBean.class, "foodid = ?", menuBean.getFoodid());
        if (i>0){
            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            foodMenuShow_listAdapter.remove(position);
            foodMenuShow_listAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void initFragmentChildView(View view) {


//        showTapTarget();
    }

    @Override
    protected void initFragmentData(Bundle savedInstanceState) {
        initdata(foodtype);
        initevent();
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

//    @Override
//    public void toBitmap(Bitmap bitmap) {
//        Toast.makeText(getActivity(), "图片图片", Toast.LENGTH_SHORT).show();
////    }
}