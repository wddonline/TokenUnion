package com.tokenunion.pro.ui.capital.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-24 17:20
 * -
 * Description: 个人财富信息接口-理财首页（理财币种列表信息）
 */
public class FinanceListBean implements Parcelable {

    /**
     * symbol : BTC
     * amount : 1.23
     * bal : 2355.254
     * marketPrice : 800.45
     * digest : ["0手续费"]
     * products : [1,23]
     */

    private String symbol;
    private String amount;
    private String bal;
    private String marketPrice;
    private List<String> digest;
    private List<String> products;

    public FinanceListBean(){

    }

    protected FinanceListBean(Parcel in) {
        symbol = in.readString();
        amount = in.readString();
        bal = in.readString();
        marketPrice = in.readString();
        digest = in.createStringArrayList();
        products = in.createStringArrayList();
    }

    public static final Creator<FinanceListBean> CREATOR = new Creator<FinanceListBean>() {
        @Override
        public FinanceListBean createFromParcel(Parcel in) {
            return new FinanceListBean(in);
        }

        @Override
        public FinanceListBean[] newArray(int size) {
            return new FinanceListBean[size];
        }
    };

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

    public List<String> getDigest() {
        return digest;
    }

    public void setDigest(List<String> digest) {
        this.digest = digest;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeString(amount);
        parcel.writeString(bal);
        parcel.writeString(marketPrice);
        parcel.writeStringList(digest);
        parcel.writeStringList(products);
    }
}
