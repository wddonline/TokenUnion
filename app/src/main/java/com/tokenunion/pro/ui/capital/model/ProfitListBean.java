package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-29 12:03
 * -
 * Description:
 */
public class ProfitListBean implements Serializable {

    /**
     * totalNum : 5
     * orders : [{"symbol":"BTC","settleAmount":"1.20","settlePrice":"345.0","addProfitRate":"0.0","profitRate":"10.0","amount":"2300","settleDate":"2019-07-29"},{"symbol":"BTC","settleAmount":"1.21","settlePrice":"345.1","addProfitRate":"0.1","profitRate":"10.1","amount":"2301","settleDate":"2019-07-29"},{"symbol":"BTC","settleAmount":"1.22","settlePrice":"345.2","addProfitRate":"0.2","profitRate":"10.2","amount":"2302","settleDate":"2019-07-29"},{"symbol":"BTC","settleAmount":"1.23","settlePrice":"345.3","addProfitRate":"0.3","profitRate":"10.3","amount":"2303","settleDate":"2019-07-29"},{"symbol":"BTC","settleAmount":"1.24","settlePrice":"345.4","addProfitRate":"0.4","profitRate":"10.4","amount":"2304","settleDate":"2019-07-29"}]
     */

    private int totalNum;
    private List<OrdersBean> orders;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean implements Serializable {
        /**
         * symbol : BTC
         * settleAmount : 1.20
         * settlePrice : 345.0
         * addProfitRate : 0.0
         * profitRate : 10.0
         * amount : 2300
         * settleDate : 2019-07-29
         */

        private String symbol;
        private Double settleAmount;
        private Double settlePrice;
        private Double addProfitRate;
        private Double profitRate;
        private Double amount;
        private String settleDate;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public Double getSettleAmount() {
            return settleAmount;
        }

        public void setSettleAmount(Double settleAmount) {
            this.settleAmount = settleAmount;
        }

        public Double getSettlePrice() {
            return settlePrice;
        }

        public void setSettlePrice(Double settlePrice) {
            this.settlePrice = settlePrice;
        }

        public Double getAddProfitRate() {
            return addProfitRate;
        }

        public void setAddProfitRate(Double addProfitRate) {
            this.addProfitRate = addProfitRate;
        }

        public Double getProfitRate() {
            return profitRate;
        }

        public void setProfitRate(Double profitRate) {
            this.profitRate = profitRate;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getSettleDate() {
            return settleDate;
        }

        public void setSettleDate(String settleDate) {
            this.settleDate = settleDate;
        }
    }
}
