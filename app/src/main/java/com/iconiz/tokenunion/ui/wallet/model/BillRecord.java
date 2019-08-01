package com.iconiz.tokenunion.ui.wallet.model;

public class BillRecord {

    public static final int TYPE_CHARGE = 0;
    public static final int TYPE_WITHDRAW = 1;
    public static final int TYPE_INTEREST = 2;
    public static final int TYPE_TRANSFER = 3;
    public static final int TYPE_SUBSIDY = 4;

    private String name;
    private String balance;
    private int type;
    private long datetime;

    public BillRecord() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
