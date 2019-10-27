package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-27 17:40
 * -
 * Description: 理财资产详情查询接口返回数据。url: '/app/finance/asset/list/ratio'
 */
public class ListRatioBean implements Serializable {

    /**
     * totalBal : 1901276.4125
     * profitRate : 7.26000
     * financeAssets : [{"symbol":"ETH","amount":"11998.1020","bal":"149976.2750","ratio":"7.9","marketPrice":"12.5"},{"symbol":"BTC","amount":"40102.0110","bal":"501275.1375","ratio":"26.4","marketPrice":"12.5"},{"symbol":"ANY","amount":"100002.0000","bal":"1250025.0000","ratio":"65.7","marketPrice":"12.5"}]
     */

    private String totalBal;
    private String profitRate;
    private List<FinanceAssetsBean> financeAssets;

    public String getTotalBal() {
        return totalBal;
    }

    public void setTotalBal(String totalBal) {
        this.totalBal = totalBal;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public List<FinanceAssetsBean> getFinanceAssets() {
        return financeAssets;
    }

    public void setFinanceAssets(List<FinanceAssetsBean> financeAssets) {
        this.financeAssets = financeAssets;
    }

    public static class FinanceAssetsBean implements Serializable {
        /**
         * symbol : ETH
         * amount : 11998.1020
         * bal : 149976.2750
         * ratio : 7.9
         * marketPrice : 12.5
         */

        private String symbol;
        private String amount;
        private String bal;
        private String ratio;
        private String marketPrice;

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

        public String getBal() {
            return bal;
        }

        public void setBal(String bal) {
            this.bal = bal;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }
    }
}
