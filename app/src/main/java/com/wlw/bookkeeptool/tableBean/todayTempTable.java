package com.wlw.bookkeeptool.tableBean;
/**
 * 用来记录 当天的上桌情况，“打烊就清空”
 */
public class todayTempTable {
   String username;
   String deskNum;   //顾客所在的桌子号
   String dayUUID;   // 关联一天表的id
   String billUUID;  // 关联桌单的id
   String totalPrice_desk; // 这一桌的总价
   String startBillTime; //下单时间
   String endBillTime;  //每桌结账时间


}
