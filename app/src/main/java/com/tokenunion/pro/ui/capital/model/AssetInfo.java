package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-07-25 17:34
 * -
 * Description: 理财--币种资产详情数据。在多处用到：活期存入、赎回，定期锁仓
 */
public class AssetInfo  implements Serializable {

    /**
     * symbol : BTC
     * amount : 42102.0010
     * usbAmount : 7897.9990
     * bal : 7714349.6432
     * usbBal : 1447150.3568
     * usbRedeemAmount : 42102.0010
     * usbRedeemBal : 7714349.6432
     * minDepositAmount : 0.0100
     * minRedeemAmount : 0.0100
     * marketPrice : 183.2300
     * profitPrice : 0.03
     */
    private String symbol;
    // 持有理财数量
    private String amount;
    // 钱包可用数量
    private String usbAmount;
    // 理财持有金额
    private String bal;
    // 钱包可用金额
    private String usbBal;
    // 可赎回数量
    private String usbRedeemAmount;
    // 可赎回金额
    private String usbRedeemBal;
    // 最小购入数量
    private String minDepositAmount;
    // 最小赎回数量
    private String minRedeemAmount;
    // 行情价格
    private String marketPrice;
    // 收益行情价格,默认any行情
    private String profitPrice;

    /**
     * 赎回差异手续费。
     * 如果diffFeeRate == 0 or diffFeeRate == null
     * 前端按现有规则显示手续费，否则显示feeRate% - diffFeeRate%，比如2%-5%
     */
    private String diffFeeRate;

    private String feeRate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUsbAmount() {
        return usbAmount;
    }

    public void setUsbAmount(String usbAmount) {
        this.usbAmount = usbAmount;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getUsbBal() {
        return usbBal;
    }

    public void setUsbBal(String usbBal) {
        this.usbBal = usbBal;
    }

    public String getUsbRedeemAmount() {
        return usbRedeemAmount;
    }

    public void setUsbRedeemAmount(String usbRedeemAmount) {
        this.usbRedeemAmount = usbRedeemAmount;
    }

    public String getUsbRedeemBal() {
        return usbRedeemBal;
    }

    public void setUsbRedeemBal(String usbRedeemBal) {
        this.usbRedeemBal = usbRedeemBal;
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

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getProfitPrice() {
        return profitPrice;
    }

    public void setProfitPrice(String profitPrice) {
        this.profitPrice = profitPrice;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getDiffFeeRate() {
        return diffFeeRate;
    }

    public void setDiffFeeRate(String diffFeeRate) {
        this.diffFeeRate = diffFeeRate;
    }
}
