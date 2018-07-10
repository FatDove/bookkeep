package com.wlw.bookkeeptool.tableBean;

import litepal.annotation.Column;
import litepal.crud.LitePalSupport;

import java.util.Date;

/**
 * 每点一道菜的记录
 */

public class everyDishTable extends LitePalSupport {
   @Column(unique = true)
   private int id;
   String username;
   String foodname;
   int foodCount; //数量
   String totalPrice_dish; //一道菜的总和 比如 5个鸡蛋的总价
   Date startBillTime; //下单时间

   public String getUsername() {
      return username == null ? "" : username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getFoodname() {
      return foodname == null ? "" : foodname;
   }

   public void setFoodname(String foodname) {
      this.foodname = foodname;
   }

   public int getFoodCount() {
      return foodCount;
   }

   public void setFoodCount(int foodCount) {
      this.foodCount = foodCount;
   }

   public String getTotalPrice_dish() {
      return totalPrice_dish == null ? "" : totalPrice_dish;
   }

   public void setTotalPrice_dish(String totalPrice_dish) {
      this.totalPrice_dish = totalPrice_dish;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Date getStartBillTime() {
      return startBillTime;
   }

   public void setStartBillTime(Date startBillTime) {
      this.startBillTime = startBillTime;
   }
}
