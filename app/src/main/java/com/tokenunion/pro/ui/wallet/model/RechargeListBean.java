package com.tokenunion.pro.ui.wallet.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 20:08
 * -
 * Description: 充币 币种列表接口返回的数据item
 */
public class RechargeListBean  implements Serializable {

    /**
     * symbol : BTC
     * address : 2NAYcAtyWCU9m3Mgw6fTdVQspB8MegUqXd3
     */

    private String symbol;
    private String address;
    private String memo = "";

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
