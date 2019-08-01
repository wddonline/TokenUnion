package com.iconiz.tokenunion.ui.capital.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private String name;
    private boolean isHot;
    private String rate;
    private String[] spots;
    private String balance;

    public Product() {
    }

    protected Product(Parcel in) {
        name = in.readString();
        isHot = in.readByte() != 0;
        rate = in.readString();
        spots = in.createStringArray();
        balance = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String[] getSpots() {
        return spots;
    }

    public void setSpots(String[] spots) {
        this.spots = spots;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isHot ? 1 : 0));
        dest.writeString(rate);
        dest.writeStringArray(spots);
        dest.writeString(balance);
    }
}
