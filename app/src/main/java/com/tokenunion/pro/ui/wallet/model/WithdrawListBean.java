package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 21:26
 * -
 * Description: 提币币种列表的item
 */
public class WithdrawListBean  implements Serializable {

    /**
     * symbol : BTC 币种
     * amount : 100 可用余额
     * minAmount : 0 最小提币数量
     * feeAmount : 0.00001 提币费用
     * feeRate : 5 提币费率（百分比值）
     */

    private String symbol;
    private String amount;
    private String minAmount;
    private String feeAmount;
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

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }
}
