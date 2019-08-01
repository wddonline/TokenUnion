package com.iconiz.tokenunion.ui.capital.model;

public class TradeRecord {

    private long datetime;
    private float value;

    public TradeRecord() {
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
