package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-09-14 15:55
 * -
 * Description: 新手专享订单查询接口返回信息
 */
public class NoviceOrdersListBean implements Serializable {


    /**
     * orders : [{"amount":"string","deadline":0,"orderTime":"string","periodDays":"string","stat":0,"statDesc":"string","symbol":"string","type":"string"}]
     * totalNum : 0
     */

    private int totalNum;
    private List<NewUserOrdersBean> orders;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<NewUserOrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<NewUserOrdersBean> orders) {
        this.orders = orders;
    }

    public static class NewUserOrdersBean implements Serializable{
        /**
         * amount : string
         * deadline : 0
         * orderTime : string
         * periodDays : string
         * stat : 0
         * statDesc : string
         * symbol : string
         * type : string
         */

        private String amount;
        private int deadline;
        private String orderTime;
        private String periodDays;
        private int stat;
        private String statDesc;
        private String symbol;
        private String type;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getDeadline() {
            return deadline;
        }

        public void setDeadline(int deadline) {
            this.deadline = deadline;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getPeriodDays() {
            return periodDays;
        }

        public void setPeriodDays(String periodDays) {
            this.periodDays = periodDays;
        }

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }

        public String getStatDesc() {
            return statDesc;
        }

        public void setStatDesc(String statDesc) {
            this.statDesc = statDesc;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
