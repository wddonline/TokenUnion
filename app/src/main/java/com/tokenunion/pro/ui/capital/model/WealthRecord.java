package com.tokenunion.pro.ui.capital.model;

public class WealthRecord {

    private float amount;
    private long deadline;
    private String orderTime;
    private String periodDays;
    private String stat;
    private String statDesc;
    private String symbol;
    private String type;

    public WealthRecord() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(String periodDays) {
        this.periodDays = periodDays;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getStatDesc() {
        return statDesc;
    }

    public void setStatDesc(String statDesc) {
        this.statDesc = statDesc;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
