package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;
import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 16:39
 * -
 * Description: 财富总览--利率规则--利率明细 接口返回的数据
 */
public class ProfitParticularsListBean implements Serializable {

    /**
     * updateTime : 2019-08-04
     * profitDetails : [{"floorBal":"0","capBal":"300","profitRate":"0.00"},{"floorBal":"300","capBal":"500","profitRate":"2.88"},{"floorBal":"500","capBal":"3000","profitRate":"4.26"},{"floorBal":"3000","capBal":"12000","profitRate":"5.32"},{"floorBal":"12000","capBal":"30000","profitRate":"6.44"},{"floorBal":"30000","capBal":"0","profitRate":"7.26"}]
     */

    private String updateTime;
    private List<ProfitDetailsBean> profitDetails;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<ProfitDetailsBean> getProfitDetails() {
        return profitDetails;
    }

    public void setProfitDetails(List<ProfitDetailsBean> profitDetails) {
        this.profitDetails = profitDetails;
    }

    public static class ProfitDetailsBean implements Serializable {
        /**
         * floorBal : 0
         * capBal : 300
         * profitRate : 0.00
         */

        private String floorBal;
        private String capBal;
        private String profitRate;

        public String getFloorBal() {
            return floorBal;
        }

        public void setFloorBal(String floorBal) {
            this.floorBal = floorBal;
        }

        public String getCapBal() {
            return capBal;
        }

        public void setCapBal(String capBal) {
            this.capBal = capBal;
        }

        public String getProfitRate() {
            return profitRate;
        }

        public void setProfitRate(String profitRate) {
            this.profitRate = profitRate;
        }
    }
}
