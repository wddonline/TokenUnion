package com.iconiz.tokenunion.ui.capital.model;

public class ANYRecord {

    private long datatime;
    private String amount;
    private int totalDays;
    private int pastDays;
    private String rate;

    public ANYRecord() {
    }

    public long getDatatime() {
        return datatime;
    }

    public void setDatatime(long datatime) {
        this.datatime = datatime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getPastDays() {
        return pastDays;
    }

    public void setPastDays(int pastDays) {
        this.pastDays = pastDays;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
