package com.tokenunion.pro.ui.capital.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Create by: xiaohansong
 * Time: 2019-07-25 16:50
 * -
 * Description:
 */
public class AssetOverviewBean implements Parcelable {

    /**
     * symbol : BTC
     * prodId : 2
     * minAmount : 0.0100
     * amount : 51101.0000
     * profitRate : 7.26000
     * addProfitRate : 0
     * sumProfit : 0.0000
     * digest : ["灵活存取"]
     */

    private String symbol;
    private int prodId;
    private String minAmount;
    private String amount;
    private String profitRate;
    private String addProfitRate;
    private String sumProfit;
    private List<String> digest;

    public AssetOverviewBean(){

    }

    protected AssetOverviewBean(Parcel in) {
        symbol = in.readString();
        prodId = in.readInt();
        minAmount = in.readString();
        amount = in.readString();
        profitRate = in.readString();
        addProfitRate = in.readString();
        sumProfit = in.readString();
        digest = in.createStringArrayList();
    }

    public static final Creator<AssetOverviewBean> CREATOR = new Creator<AssetOverviewBean>() {
        @Override
        public AssetOverviewBean createFromParcel(Parcel in) {
            return new AssetOverviewBean(in);
        }

        @Override
        public AssetOverviewBean[] newArray(int size) {
            return new AssetOverviewBean[size];
        }
    };

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public String getAddProfitRate() {
        return addProfitRate;
    }

    public void setAddProfitRate(String addProfitRate) {
        this.addProfitRate = addProfitRate;
    }

    public String getSumProfit() {
        return sumProfit;
    }

    public void setSumProfit(String sumProfit) {
        this.sumProfit = sumProfit;
    }

    public List<String> getDigest() {
        return digest;
    }

    public void setDigest(List<String> digest) {
        this.digest = digest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(symbol);
        parcel.writeInt(prodId);
        parcel.writeString(minAmount);
        parcel.writeString(amount);
        parcel.writeString(profitRate);
        parcel.writeString(addProfitRate);
        parcel.writeString(sumProfit);
        parcel.writeStringList(digest);
    }
}
