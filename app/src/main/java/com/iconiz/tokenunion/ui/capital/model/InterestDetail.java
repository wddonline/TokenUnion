package com.iconiz.tokenunion.ui.capital.model;

public class InterestDetail {

    private String balance;
    private String monthRate;
    private boolean isOutstanding = false;

    public InterestDetail() {
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(String monthRate) {
        this.monthRate = monthRate;
    }

    public boolean isOutstanding() {
        return isOutstanding;
    }

    public void setOutstanding(boolean outstanding) {
        isOutstanding = outstanding;
    }
}
