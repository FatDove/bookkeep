package com.wlw.bookkeeptool.tableBean;

import litepal.annotation.Column;
import litepal.crud.LitePalSupport;

/**
 * 菜单表
 */
public class menuBean extends LitePalSupport {
   @Column(unique = true)
   private int id;
   @Column(nullable = false)
   private String username;
   @Column(nullable = false)
   private String foodname;
   @Column(nullable = false)
   private String foodid;
   @Column(nullable = false)
   private String foodtype;
   private String foodimg_path;
   @Column(nullable = false)
   private String price;
   private String description;

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

   public String getFoodid() {
      return foodid == null ? "" : foodid;
   }

   public void setFoodid(String foodid) {
      this.foodid = foodid;
   }

   public String getFoodtype() {
      return foodtype == null ? "" : foodtype;
   }

   public void setFoodtype(String foodtype) {
      this.foodtype = foodtype;
   }

   public String getFoodimg_path() {
      return foodimg_path == null ? "" : foodimg_path;
   }

   public void setFoodimg_path(String foodimg_path) {
      this.foodimg_path = foodimg_path;
   }

   public String getPrice() {
      return price == null ? "" : price;
   }

   public void setPrice(String price) {
      this.price = price;
   }

   public String getDescription() {
      return description == null ? "" : description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   @Override
   public String toString() {
      return "menuBean{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", foodname='" + foodname + '\'' +
              ", foodid='" + foodid + '\'' +
              ", foodtype='" + foodtype + '\'' +
              ", foodimg_path='" + foodimg_path + '\'' +
              ", price='" + price + '\'' +
              ", description='" + description + '\'' +
              '}';
   }
}

