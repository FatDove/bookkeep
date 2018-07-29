package com.wlw.bookkeeptool.Kitchen;

import com.wlw.bookkeeptool.tableBean.EveryDeskTable;

public class KitchenBean  {
   String staffID;
   EveryDeskTable everyDeskdata;

    public KitchenBean(String staffID, EveryDeskTable everyDeskdata) {
        this.staffID = staffID;
        this.everyDeskdata = everyDeskdata;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public EveryDeskTable getEveryDeskdata() {
        return everyDeskdata;
    }

    public void setEveryDeskdata(EveryDeskTable everyDeskdata) {
        this.everyDeskdata = everyDeskdata;
    }
}
