package com.wlw.bookkeeptool.editor_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wlw.bookkeeptool.R;
import com.wlw.bookkeeptool.tableBean.everyDayTable;
import com.wlw.bookkeeptool.tableBean.everyDishTable;
import com.wlw.bookkeeptool.tableBean.menuBean;
import com.wlw.bookkeeptool.utils.FileMananger;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import litepal.LitePal;
import litepal.tablemanager.Connector;


/**
 * Created by wlw on 2017/8/30.
 */
public class SeeEditorPopupWindow extends PopupWindow {
    private int mWidth;
    private int mHeight;
    private View mConvertView;

    private Context context;
    private ImageButton add_food_img;
    private TextView ET1;
    private EditText food_name;
    private TextView ET2;
    private EditText UnitPrice;
    private TextView tv1;
    private EditText describe;
    private Button submit_edit;
    private final int IMAGE_Local = 1;
    private String food_type;
    private Bitmap bitmap; //用来临时存储 当前展示的图片

    private Boolean nameflag = false; //判断名称是否存在标识
    private String tempfoodname = ""; //用来存储重复的 名称
//    private String foodid;
//    private String price;
//    private String description;
//    private String local_imgpath;

    private static final int isAdd = 1;
    private static final int isEdit = 2;
    private static final int isInsert = 3;
    private static final int isUpdata = 4;
    private menuBean menuBean;
    private String local_imgpath;
    private String type_name;


    //添加时进来的构造
    public SeeEditorPopupWindow(Context context,String food_type,String type_name) {
        this.context = context;
        this.food_type = food_type;
        this.type_name = type_name;
        //制定popupwindow的宽高
        calWidthAndHeight(context);
        popu_config(context);
        //PopupWindow基本属性设置-----↑↑↑↑↑↑↑↑↑↑↑
        initViews(mConvertView);
        initEvent(isAdd);
    }

    //修改时进来的构造
    public SeeEditorPopupWindow(Context context, menuBean menuBean) {
        this.context = context;
        this.menuBean = menuBean;
        //制定popupwindow的宽高
        calWidthAndHeight(context);
        popu_config(context);
        //PopupWindow基本属性设置-----↑↑↑↑↑↑↑↑↑↑↑
        initViews(mConvertView);
        food_name.setText(menuBean.getFoodname());
        //用Glide框架
        Glide.with(context).load(menuBean.getFoodimg_path()).error(R.drawable.no_banner).diskCacheStrategy(DiskCacheStrategy.NONE)
                // .override(100, 100)
                .into(add_food_img);
        UnitPrice.setText(menuBean.getPrice());
        describe.setText(menuBean.getDescription());
        submit_edit.setText("修改完成");
        submit_edit.setBackground(context.getResources().getDrawable(R.drawable.button_border_yellow));
        initEvent(isEdit);
    }


    private void popu_config(Context context) {
        mConvertView = LayoutInflater.from(context).inflate(R.layout.see_editor_popu, null);
        //PopupWindow基本属性设置-----↓↓↓↓↓↓↓↓↓↓
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);//可触摸
        setTouchable(true);//可点击
        setOutsideTouchable(true);//边缘可点击
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {//点击边缘消失
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews(View mConvertView) {
        food_name = mConvertView.findViewById(R.id.food_name);
        add_food_img = mConvertView.findViewById(R.id.add_food_img);
        UnitPrice = mConvertView.findViewById(R.id.UnitPrice);
        describe = mConvertView.findViewById(R.id.describe);
        submit_edit = mConvertView.findViewById(R.id.submit_edit);

    }

    private void initEvent(int add_or_edit) {
        add_food_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_img();
            }
        });

        if (add_or_edit == isAdd) {
            submit_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Save_to_DB(isInsert);
                }
            });
        } else if (add_or_edit == isEdit) {
            submit_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Save_to_DB(isUpdata);
                }
            });
        }
        food_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tempfoodname.equals(food_name.getText().toString())) {
                    food_name.setTextColor(Color.RED);
                } else {
                    food_name.setTextColor(Color.BLACK);
                }
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");


            }
        });
    }

    public void set_img_to_popu(Bitmap add_img_bitmap) {
        bitmap = add_img_bitmap;
        add_food_img.setImageBitmap(bitmap);
        local_imgpath = SendImageDispose(bitmap);//将Bitmap 保存到本地
    }

    private void add_img() {
        Toast.makeText(context, "相册选图片", Toast.LENGTH_SHORT).show();
        //选择图片
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        ((Activity) context).startActivityForResult(intent2, IMAGE_Local);
    }

    //将数据保存到数据库
    private void Save_to_DB(int insert_or_updata) {
        SQLiteDatabase db = Connector.getDatabase();

        if (insert_or_updata == isUpdata) {
            menuBean update_menu = new menuBean();
            update_menu.setFoodname(food_name.getText().toString());
            update_menu.setFoodimg_path(local_imgpath);
            update_menu.setPrice(UnitPrice.getText().toString());
            update_menu.setDescription(describe.getText().toString());
            int i = update_menu.updateAll("foodid = ?", menuBean.getFoodid());
            if (i > 0) {
                Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                see_and_editor_activity s = (see_and_editor_activity) this.context;
                s.initdata(menuBean.getFoodtype());
                dismiss();
            } else {
                Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
            }
        } else if (insert_or_updata == isInsert) {
            //判断是否包含
            if (isContainFood()) {
                nameflag = true;
                tempfoodname = food_name.getText().toString();
                Toast.makeText(context, "该名称已存在", Toast.LENGTH_SHORT).show();
//            名称输入框 获取焦点并弹出软键盘
                food_name.setFocusable(true);
                food_name.setFocusableInTouchMode(true);
                food_name.requestFocus();
                food_name.setTextColor(Color.RED);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(food_name, 0);
                return;
            }
            //TODO:
            //使用sqlite把参数保存起来
            food_name = mConvertView.findViewById(R.id.food_name);
            add_food_img = mConvertView.findViewById(R.id.add_food_img);
            UnitPrice = mConvertView.findViewById(R.id.UnitPrice);
            describe = mConvertView.findViewById(R.id.describe);
            Toast.makeText(context, db.getVersion() + "__@", Toast.LENGTH_SHORT).show();
            menuBean menuBean = new menuBean();
            menuBean.setFoodid(UUID.randomUUID() + "");
            menuBean.setDescription(describe.getText() + "");
            menuBean.setFoodimg_path(local_imgpath);
            menuBean.setFoodname(food_name.getText() + "");
            menuBean.setFoodtype(food_type + "");
            menuBean.setTypename(type_name+"");
            menuBean.setPrice(UnitPrice.getText() + "");
            menuBean.setUsername("嘟小四");
            if (menuBean.save()) {
                Toast.makeText(context, menuBean.getId() + "测试", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "存储成功", Toast.LENGTH_SHORT).show();
                see_and_editor_activity s = (see_and_editor_activity) this.context;
                s.initdata(food_type);
            } else {
                Toast.makeText(context, "存储失败", Toast.LENGTH_SHORT).show();
            }

        }
    }

    //判断是否包含指定内容
    private Boolean isContainFood() {
        String username = "嘟小四";
        List<menuBean> list = LitePal.where("username = ? and foodname = ?", username, food_name.getText().toString()).find(menuBean.class);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取控件宽高
     *
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        //通过上下文 获取服务  （屏幕服务）
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();//获取显示的“权利”
        wm.getDefaultDisplay().getMetrics(displayMetrics);//将权利在这个服务注册

        mWidth = displayMetrics.widthPixels;//获取屏幕宽高
        mHeight = (int) (displayMetrics.heightPixels * 0.6);
    }

    /**
     * 将从相册.拍照获取到的图片,做发送前  和 发送处理
     * 图像压缩，保存本地文件夹，保存数据库，发送出去
     *
     * @param bp
     */
    private String SendImageDispose(Bitmap bp) {
        //2 将文件保存到设置 好的路径下
        File saveimgfile = FileMananger.savePhotoToSDCard(bp, "IMFan", UUID.randomUUID() + "");
        //3 在将文件保存 到本地数据库
        //刷新后释放防止手机休眠后自动添加
        return saveimgfile.getAbsolutePath();
    }
}
