package com.tokenunion.pro.ui.wallet.model;

import android.os.Build;

import java.io.Serializable;
import java.util.Objects;

/**
 * Create by: xiaohansong
 * Time: 2019-09-04 19:12
 * -
 * Description: 市场行情的bean
 */
public class MarketItemBean implements Serializable, Comparable {

    /**
     * symbol : BTC
     * toSymbol : USD
     * price : 10574.82
     * percentChange : 2.02
     * weight : 100000
     */

    private String symbol;
    private String toSymbol;
    private String price;
    private String percentChange;
    private String weight;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(String percentChange) {
        this.percentChange = percentChange;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarketItemBean bean = (MarketItemBean) o;
        return symbol.equals(bean.symbol) &&
                toSymbol.equals(bean.toSymbol) &&
                price.equals(bean.price) &&
                percentChange.equals(bean.percentChange);
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(symbol, toSymbol, price, percentChange);
        }
        return (getSymbol()+ getToSymbol()+ getPrice()).hashCode();
    }

    /**
     * 比较两个对象
     * @param o
     * @return 返回的数是0代表两个元素相同，正数说明大于，负数说明小于
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof MarketItemBean) {
            MarketItemBean p = (MarketItemBean) o;
            int r = this.getSymbol().compareTo(p.getSymbol());
            if (r == 0) {
                r = this.getToSymbol().compareTo(p.getToSymbol());//如果Symbol是一样的，我们就来判断ToSymbol是不是一样的
                if (r == 0) {
                    r = this.getPrice().compareTo(p.getPrice());
                    return r;
                }else {
                    return r;
                }
            }else {
                return r;
            }
        }
        return -1;
    }
}
