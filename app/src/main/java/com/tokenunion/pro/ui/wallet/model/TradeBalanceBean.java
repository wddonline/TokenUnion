package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-08-03 18:05
 * -
 * Description: 资产查询接口返回数据--钱包首页资产列表
 */
public class TradeBalanceBean implements Serializable {

    /**
     * bal : 12355.00
     * symbolBals : [{"symbol":"BTC","amount":"12.02","bal":"500012.0003","marketPrice":"9803.00"},{"symbol":"ETH","amount":"3.02","bal":"4002.0003","marketPrice":"1070.0000"}]
     */

    private String bal;
    private List<SymbolBalsBean> symbolBals;

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public List<SymbolBalsBean> getSymbolBals() {
        return symbolBals;
    }

    public void setSymbolBals(List<SymbolBalsBean> symbolBals) {
        this.symbolBals = symbolBals;
    }

    public static class SymbolBalsBean implements Serializable{
        /**
         * symbol : BTC
         * amount : 12.02
         * bal : 500012.0003
         * marketPrice : 9803.00
         */

        private String symbol;
        private String amount;
        private String bal;
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

        public String getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
        }
    }
}
