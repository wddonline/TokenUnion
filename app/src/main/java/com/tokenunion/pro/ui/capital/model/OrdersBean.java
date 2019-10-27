package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-09-03 18:23
 * -
 * Description: "操作记录"列表， 即订单列表
 */
public class OrdersBean implements Serializable {

    /**
     * totalNum : 10
     * orders : [{"symbol":"BTC","type":"Purchase","amount":"10.0000","statDesc":"Success","orderTime":"2019-09-03"},{"symbol":"BTC","type":"Redeem","amount":"5.0000","statDesc":"Success","orderTime":"2019-09-01"}]
     */

    private int totalNum;
    private List<OperaterBean> orders;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<OperaterBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OperaterBean> orders) {
        this.orders = orders;
    }

    public static class OperaterBean implements Serializable {
        /**
         * symbol : BTC
         * type : Purchase
         * amount : 10.0000
         * stat : 2 // 2:Pending
         * statDesc : Success
         * orderTime : 2019-09-03
         */

        private String symbol;
        private String type;
        private String amount;
        private int stat;
        private String statDesc;
        private String orderTime;

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
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

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }
    }
}
