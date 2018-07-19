package com.wlw.bookkeeptool.Record;

public class Record_Info_Rv_Bean {
    public Record_Info_Rv_Bean(String name, float sum_price, int sum_count) {
        this.name = name;
        this.sum_price = sum_price;
        this.sum_count = sum_count;
    }

    String name;
    float sum_price;
    int sum_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSum_price() {
        return sum_price;
    }

    public void setSum_price(float sum_price) {
        this.sum_price = sum_price;
    }

    public int getSum_count() {
        return sum_count;
    }

    public void setSum_count(int sum_count) {
        this.sum_count = sum_count;
    }
}
