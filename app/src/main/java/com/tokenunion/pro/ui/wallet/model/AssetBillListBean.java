package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-08-05 12:20
 * -
 * Description: 钱包账单接口返回的数据
 */
public class AssetBillListBean implements Serializable {

    /**
     * totalNum : 60
     * assetBills : [{"symbol":"ETH","amount":"0.1","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-08-02 08:11:37","stat":"DONE","userName":"abc"},{"symbol":"TRX","amount":"2","billType":"EXTER_IN","inOutType":"IN","billTime":"2019-07-31 16:37:55","stat":"DONE","userName":null},{"symbol":"BTC","amount":"0.01085","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-07-31 16:36:02","stat":"DONE","userName":null},{"symbol":"ETH","amount":"1","billType":"EXTER_IN","inOutType":"IN","billTime":"2019-07-31 16:32:09","stat":"DONE","userName":null},{"symbol":"BTC","amount":"0.011","billType":"EXTER_IN","inOutType":"IN","billTime":"2019-07-31 16:29:46","stat":"DONE","userName":null},{"symbol":"ALV","amount":"12283","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-07-31 16:12:31","stat":"DONE","userName":null},{"symbol":"ONG","amount":"1.9","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-07-31 16:09:56","stat":"DONE","userName":null},{"symbol":"ONG","amount":"2","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-07-31 16:08:35","stat":"DONE","userName":null},{"symbol":"ONT","amount":"1","billType":"EXTER_OUT","inOutType":"OUT","billTime":"2019-07-31 16:06:54","stat":"DONE","userName":null},{"symbol":"ALV","amount":"12333","billType":"EXTER_IN","inOutType":"IN","billTime":"2019-07-31 16:05:16","stat":"DONE","userName":null}]
     */

    private int totalNum;
    private List<AssetBillsBean> assetBills;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<AssetBillsBean> getAssetBills() {
        return assetBills;
    }

    public void setAssetBills(List<AssetBillsBean> assetBills) {
        this.assetBills = assetBills;
    }

    public static class AssetBillsBean implements Serializable {
        /**
         * symbol : ETH
         * amount : 0.1
         * billType : EXTER_OUT
         * inOutType : OUT
         * billTime : 2019-08-02 08:11:37
         * stat : DONE
         * userName : abc
         */

        private String symbol;
        private String amount;
        private String billType;
        private String inOutType;
        private String billTime;
        private String stat;
        private String userName;

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

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public String getInOutType() {
            return inOutType;
        }

        public void setInOutType(String inOutType) {
            this.inOutType = inOutType;
        }

        public String getBillTime() {
            return billTime;
        }

        public void setBillTime(String billTime) {
            this.billTime = billTime;
        }

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
