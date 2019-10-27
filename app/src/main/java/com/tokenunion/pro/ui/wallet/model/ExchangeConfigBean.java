package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-21 17:35
 * -
 * Description: "兑换"时，查询兑换参数返回的数据
 */
public class ExchangeConfigBean implements Serializable {

    /**
     * id : 1
     * fromSymbol : ANY
     * toSymbol : ETH
     * dayUsd : 400.1
     * personUsd : 50.1
     * minAmount : 100.1
     * feeRate : 0.01
     */

    private int id;
    private String fromSymbol;
    private String toSymbol;
    private double dayUsd;
    private double personUsd;
    private double minAmount;
    private double feeRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    public double getDayUsd() {
        return dayUsd;
    }

    public void setDayUsd(double dayUsd) {
        this.dayUsd = dayUsd;
    }

    public double getPersonUsd() {
        return personUsd;
    }

    public void setPersonUsd(double personUsd) {
        this.personUsd = personUsd;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }
}
