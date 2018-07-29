package com.wlw.bookkeeptool.tableBean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.Date;

import litepal.annotation.Column;
import litepal.crud.LitePalSupport;

/**
 * 每点一道菜的记录
 *
 * MultiItemEntity 作为多布局的ItemBean
 */

public class EveryDishTable extends LitePalSupport implements Serializable,MultiItemEntity {
   @Column(unique = true)
   private int id;
   String username;
   String foodname;
   String dish_uuid;
   String remark;
   @Column(defaultValue ="0")//指定字段默认值
   String isfinish; //0 表示没上菜 1表示上过了
   int foodCount; //数量
   float totalPrice_dish; //一道菜的总和 比如 5个鸡蛋的总价
   float unitPrice_dish; //一道菜的总和 比如 5个鸡蛋的总价
   Date startBillTime; //下单时间
   @Column(defaultValue ="0")//指定字段默认值
   int itemType; // 0 1 2   0 普通默认  1 追加的菜单样式  2 新增待确认的样式
   public EveryDishTable(String username, String foodname, String isfinish, int foodCount, float totalPrice_dish, float unitPrice_dish, Date startBillTime, int itemType, String dish_uuid, String remark) {
      this.username = username;
      this.foodname = foodname;
      this.foodCount = foodCount;
      this.isfinish = isfinish;
      this.totalPrice_dish = totalPrice_dish;
      this.unitPrice_dish = unitPrice_dish;
      this.startBillTime = startBillTime;
      this.itemType = itemType;
      this.dish_uuid = dish_uuid;
      this.remark = remark;
   }

   public String getIsfinish() {
      return isfinish;
   }

   public void setIsfinish(String isfinish) {
      this.isfinish = isfinish;
   }

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

   public float getTotalPrice_dish() {
      return totalPrice_dish;
   }

   public void setTotalPrice_dish(float totalPrice_dish) {
      this.totalPrice_dish = totalPrice_dish;
   }

   public float getUnitPrice_dish() {
      return unitPrice_dish;
   }

   public void setUnitPrice_dish(float unitPrice_dish) {
      this.unitPrice_dish = unitPrice_dish;
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

   public int getItemType() {
       return itemType;
   }

   public void setItemType(int itemType) {
      this.itemType = itemType;
   }

   public String getUuid() {
      return dish_uuid;
   }

   public void setUuid(String dish_uuid) {
      this.dish_uuid = dish_uuid;
   }

   public String getRemark() {
      return remark;
   }

   public void setRemark(String remark) {
      this.remark = remark;
   }


}
