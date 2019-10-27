package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-29 10:39
 * -
 * Description: 持有资产数据列表 -- 订单查询接口返回
 */
public class TimeOrderListBean implements Serializable {

    /**
     * totalNum : 21
     * timeOrders : [{"symbol":"ANY","amount":"1000.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0000","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1000.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0000","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1000.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0000","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1000.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0000","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1000.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0000","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1001.0000","deadline":"0/30","deadlineUnit":"day","profitRate":"5.00","profitAmount":"50.0500","orderTime":"2019-07-29"},{"symbol":"ANY","amount":"1.0000","deadline":"2/70","deadlineUnit":"day","profitRate":"0.00","profitAmount":"0.0000","orderTime":"2019-07-26"},{"symbol":"ANY","amount":"1.0000","deadline":"2/70","deadlineUnit":"day","profitRate":"0.00","profitAmount":"0.0000","orderTime":"2019-07-26"},{"symbol":"ANY","amount":"70000.0000","deadline":"3/30","deadlineUnit":"day","profitRate":"0.00","profitAmount":"0.0000","orderTime":"2019-07-25"},{"symbol":"ANY","amount":"10000.0000","deadline":"3/30","deadlineUnit":"day","profitRate":"0.00","profitAmount":"0.0000","orderTime":"2019-07-25"}]
     */

    private int totalNum;
    private List<TimeOrdersBean> timeOrders;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<TimeOrdersBean> getTimeOrders() {
        return timeOrders;
    }

    public void setTimeOrders(List<TimeOrdersBean> timeOrders) {
        this.timeOrders = timeOrders;
    }

    public static class TimeOrdersBean implements Serializable {
        /**
         * symbol : ANY
         * amount : 1000.0000
         * deadline : 0/30
         * deadlineUnit : day
         * profitRate : 5.00
         * profitAmount : 50.0000
         * orderTime : 2019-07-29
         */

        private String symbol;
        private String amount;
        private String deadline;
        private String deadlineUnit;
        private String profitRate;
        // 预期收益
        private String profitAmount;
        private String orderTime;

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

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getDeadlineUnit() {
            return deadlineUnit;
        }

        public void setDeadlineUnit(String deadlineUnit) {
            this.deadlineUnit = deadlineUnit;
        }

        public String getProfitRate() {
            return profitRate;
        }

        public void setProfitRate(String profitRate) {
            this.profitRate = profitRate;
        }

        public String getProfitAmount() {
            return profitAmount;
        }

        public void setProfitAmount(String profitAmount) {
            this.profitAmount = profitAmount;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }
}
