package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;

public class TopCard  implements Serializable {

    private int type;
    private boolean isPayment;
    private String balance;

    public TopCard() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPayment() {
        return isPayment;
    }

    public void setPayment(boolean payment) {
        isPayment = payment;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
