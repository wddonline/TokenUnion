package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;

public class PaymentCard  implements Serializable {

    private int id;
    private String name;
    private String balance;
    private String minOut;
    private String fee;
    private String feeRate;
    private String address;
    private String memo = "";

    public PaymentCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMinOut() {
        return minOut;
    }

    public void setMinOut(String minOut) {
        this.minOut = minOut;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
