package com.jia.libutils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wlw on 2018/6/5.
 */

public class DateUtils {
    public static Date showDateDialog(Context context, final TextView view1){
        Calendar d = Calendar.getInstance(Locale.CHINA);
        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();
        // 创建一个Date实例
        d.setTime(myDate);
        // 设置日历的时间，把一个新建Date实例myDate传入
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);
        //初始化默认日期year, month, day
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            /**
             * 点击确定后，在这个方法中获取年月日
             */
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                String month="";
                String day="";
                month = monthOfYear+1+"";
                day = dayOfMonth+"";
                if ((monthOfYear+1)<10){
                    month = "0"+(monthOfYear+1);
                }
                if (dayOfMonth<10){
                    day="0"+dayOfMonth;
                }
              String date = "" + year + "-" + (month) + "-" + day;
                view1.setText(date);
//                switch (id){
//                    case R.id.startDate:
//                        startDate.setText(date);
//                        break;
//                    case R.id.endDate:
//                        endDate.setText(date);
//                        break;
//                }
            }
        },year, month, day);
        datePickerDialog.setMessage("请选择日期");
        datePickerDialog.show();
        return myDate;
    }
}
