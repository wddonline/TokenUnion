package com.tokenunion.pro.ui.capital.model;

import androidx.annotation.Keep;

@Keep
public class SavingProduct {

    private int deadline;
    private String deadlineUnit;
    private String[] digest;
    private String feeRate;
    private int isTimeDeposit;
    private float minDepositAmount;
    private String minRedeemAmount;
    private String prodId;
    private String prodType;
    private float profitRate;
    private String symbol;

    public SavingProduct() {
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineUnit() {
        return deadlineUnit;
    }

    public void setDeadlineUnit(String deadlineUnit) {
        this.deadlineUnit = deadlineUnit;
    }

    public String[] getDigest() {
        return digest;
    }

    public void setDigest(String[] digest) {
        this.digest = digest;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public int getIsTimeDeposit() {
        return isTimeDeposit;
    }

    public void setIsTimeDeposit(int isTimeDeposit) {
        this.isTimeDeposit = isTimeDeposit;
    }

    public float getMinDepositAmount() {
        return minDepositAmount;
    }

    public void setMinDepositAmount(float minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
    }

    public String getMinRedeemAmount() {
        return minRedeemAmount;
    }

    public void setMinRedeemAmount(String minRedeemAmount) {
        this.minRedeemAmount = minRedeemAmount;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public float getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(float profitRate) {
        this.profitRate = profitRate;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
