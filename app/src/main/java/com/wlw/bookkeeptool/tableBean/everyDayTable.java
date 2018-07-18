package com.wlw.bookkeeptool.tableBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import litepal.annotation.Column;
import litepal.crud.LitePalSupport;

/**
 * 每一天的记录（每次打烊就会记录）
 */
public class everyDayTable extends LitePalSupport implements Serializable {
   @Column(unique = true)
   private int id;
   private float totalPrice_day; //一天的流水总和
   private String deskCount; // 记录一天上了多少桌
   private Date shutDownTime; //打烊下班的时间
   private String username;
   private int year;
   private int month;
   private int day;
   @Column(defaultValue = "0")//指定字段默认值 0 代表没打样 ，1表示打过样的记录
   private String shutDownTimeStr;
   private List<everyDeskTable> everyDeskTableList = new ArrayList<everyDeskTable>(); //everyDeskTable 表 与 everyDayTable 表  是 多对一的关系

   public String getUsername() {
      return username == null ? "" : username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public float getTotalPrice_day() {
      return totalPrice_day;
   }

   public void setTotalPrice_day(float totalPrice_day) {
      this.totalPrice_day = totalPrice_day;
   }

   public String getDeskCount() {
      return deskCount == null ? "" : deskCount;
   }

   public void setDeskCount(String deskCount) {
      this.deskCount = deskCount;
   }

   public int getYear() {
      return year;
   }

   public void setYear(int year) {
      this.year = year;
   }

   public int getMonth() {
      return month;
   }

   public void setMonth(int month) {
      this.month = month;
   }

   public int getDay() {
      return day;
   }

   public void setDay(int day) {
      this.day = day;
   }

   public Date getShutDownTime() {
      return shutDownTime;
   }

   public void setShutDownTime(Date shutDownTime) {
      this.shutDownTime = shutDownTime;
   }

   public List<everyDeskTable> getEveryDeskTableList() {
      if (everyDeskTableList == null) {
         return new ArrayList<>();
      }
      return everyDeskTableList;
   }

   public void setEveryDeskTableList(List<everyDeskTable> everyDeskTableList) {
      this.everyDeskTableList = everyDeskTableList;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getShutDownTimeStr() {
      return shutDownTimeStr == null ? "" : shutDownTimeStr;
   }

   public void setShutDownTimeStr(String shutDownTimeStr) {
      this.shutDownTimeStr = shutDownTimeStr;
   }
}
