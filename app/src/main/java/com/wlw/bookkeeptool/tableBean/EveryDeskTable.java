package com.wlw.bookkeeptool.tableBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import litepal.annotation.Column;
import litepal.crud.LitePalSupport;

/**
 * 每一桌菜的记录（每一桌结账就会记录）
 */
public class EveryDeskTable extends LitePalSupport implements Serializable {
   @Column(unique = true)
   private int id;
   private String  staff_id;
   private String  desk_uuid;
   @Column(defaultValue = "0")//指定字段默认值 0 代表没打样 ，1表示打过样的记录
   private String  query_role_id;//0 默认为服务员 1 后厨  2 老板
   String username;
   String deskNum; //顾客所在的桌子号
   float totalPrice_desk; // 这一桌的总价
   Date startBillTime; //下单时间
   Date endBillTime;  //每桌结账时间
   @Column(defaultValue = "0")//指定字段默认值 0 代表没打样 ，1表示打过样的记录
   private String isCheckout; //买单结账
   @Column(defaultValue = "0")//指定字段默认值 0 代表没打样 ，1表示打过样的记录
   private String isEndwork; //打烊了么
   private List<EveryDishTable> everyDishTableList = new ArrayList<EveryDishTable>(); //everyDishTable 表 与 everyDeskTable 表  是 多对一的关系

   public String getUsername() {
      return username == null ? "" : username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getDeskNum() {
      return deskNum == null ? "" : deskNum;
   }

   public void setDeskNum(String deskNum) {
      this.deskNum = deskNum;
   }

   public float getTotalPrice_desk() {
      return totalPrice_desk;
   }

   public void setTotalPrice_desk(float totalPrice_desk) {
      this.totalPrice_desk = totalPrice_desk;
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

   public Date getEndBillTime() {
      return endBillTime;
   }

   public void setEndBillTime(Date endBillTime) {
      this.endBillTime = endBillTime;
   }

   public String getIsCheckout() {
      return isCheckout == null ? "" : isCheckout;
   }

   public void setIsCheckout(String isCheckout) {
      this.isCheckout = isCheckout;
   }

   public String getIsEndwork() {
      return isEndwork == null ? "" : isEndwork;
   }

   public void setIsEndwork(String isEndwork) {
      this.isEndwork = isEndwork;
   }

   public List<EveryDishTable> getEveryDishTableList() {
      if (everyDishTableList == null) {
         return new ArrayList<>();
      }
      return everyDishTableList;
   }
   public void setEveryDishTableList(List<EveryDishTable> everyDishTableList){
      this.everyDishTableList = everyDishTableList;
   }

   public String getStaff_id() {
      return staff_id;
   }

   public void setStaff_id(String staff_id) {
      this.staff_id = staff_id;
   }

   public String getDesk_uuid() {
      return desk_uuid;
   }

   public void setDesk_uuid(String desk_uuid) {
      this.desk_uuid = desk_uuid;
   }

   public String getQuery_role_id() {
      return query_role_id;
   }

   public void setQuery_role_id(String query_role_id) {
      this.query_role_id = query_role_id;
   }
}
