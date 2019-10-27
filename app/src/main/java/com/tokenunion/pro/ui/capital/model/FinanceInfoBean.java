package com.tokenunion.pro.ui.capital.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create by: xiaohansong
 * Time: 2019-07-24 17:16
 * -
 * Description: 个人财富信息接口-理财首页（资产信息）
 */
public class FinanceInfoBean implements Parcelable {


    /**
     * level : vip1
     * levelDesc : 小星星
     * totalBal : 2355.254
     * sumProfit : 800.45
     * lastProfit : 55.8
     * profitRate : 0.18
     * addProfitRate : 0.015
     */

    private String level;
    private String levelDesc;
    private String totalBal;
    private String sumProfit;
    private String lastProfit;
    private String profitRate;
    private String addProfitRate;

    public FinanceInfoBean(){

    }

    protected FinanceInfoBean(Parcel in) {
        level = in.readString();
        levelDesc = in.readString();
        totalBal = in.readString();
        sumProfit = in.readString();
        lastProfit = in.readString();
        profitRate = in.readString();
        addProfitRate = in.readString();
    }

    public static final Creator<FinanceInfoBean> CREATOR = new Creator<FinanceInfoBean>() {
        @Override
        public FinanceInfoBean createFromParcel(Parcel in) {
            return new FinanceInfoBean(in);
        }

        @Override
        public FinanceInfoBean[] newArray(int size) {
            return new FinanceInfoBean[size];
        }
    };

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public String getTotalBal() {
        return totalBal;
    }

    public void setTotalBal(String totalBal) {
        this.totalBal = totalBal;
    }

    public String getSumProfit() {
        return sumProfit;
    }

    public void setSumProfit(String sumProfit) {
        this.sumProfit = sumProfit;
    }

    public String getLastProfit() {
        return lastProfit;
    }

    public void setLastProfit(String lastProfit) {
        this.lastProfit = lastProfit;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(level);
        parcel.writeString(levelDesc);
        parcel.writeString(totalBal);
        parcel.writeString(sumProfit);
        parcel.writeString(lastProfit);
        parcel.writeString(profitRate);
        parcel.writeString(addProfitRate);
    }
}
