package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-27 20:29
 * -
 * Description: 理财资产详情查询接口，url: '/app/finance/product/list'返回的数据
 */
public class FixedProductListBean  implements Serializable {

    /**
     * prodId : 1
     * symbol : ANY
     * prodType : 2
     * isTimeDeposit : 1
     * feeRate : 0.00
     * profitRate : 0.00
     * deadline : 30
     * deadlineUnit : day
     * minDepositAmount : 1000.0000
     * minRedeemAmount : 0.0000
     * digest : ["定期高息"]
     */

    private int prodId;
    private String symbol;
    private int prodType;
    private int isTimeDeposit;
    private String feeRate;
    private String profitRate;
    private int deadline;
    private String deadlineUnit;
    private String minDepositAmount;
    private String minRedeemAmount;
    private List<String> digest;

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getProdType() {
        return prodType;
    }

    public void setProdType(int prodType) {
        this.prodType = prodType;
    }

    public int getIsTimeDeposit() {
        return isTimeDeposit;
    }

    public void setIsTimeDeposit(int isTimeDeposit) {
        this.isTimeDeposit = isTimeDeposit;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
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

    public String getMinDepositAmount() {
        return minDepositAmount;
    }

    public void setMinDepositAmount(String minDepositAmount) {
        this.minDepositAmount = minDepositAmount;
    }

    public String getMinRedeemAmount() {
        return minRedeemAmount;
    }

    public void setMinRedeemAmount(String minRedeemAmount) {
        this.minRedeemAmount = minRedeemAmount;
    }

    public List<String> getDigest() {
        return digest;
    }

    public void setDigest(List<String> digest) {
        this.digest = digest;
    }
}
