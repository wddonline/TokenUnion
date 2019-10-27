package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-09-15 18:57
 * -
 * Description: 新手专享产品查询
 */
public class NoviceCommonBean implements Serializable {

    /**
     * noviceId : 1
     * symbol : BTC
     * period : 60
     * periodString : 60 days
     * start : 0.01
     * bonus : 0.05
     * bonusString : 5.0
     * des : Welcome 0 Fee
     * orgCurr : 1
     * orgMax : 100
     * weight : 10
     */

    private int noviceId;
    private String symbol;
    private int period;
    private String periodString;
    private String start;
    private String bonus;
    private String bonusString;
    private String des;
    private String orgCurr;
    private String orgMax;
    private int weight;

    public int getNoviceId() {
        return noviceId;
    }

    public void setNoviceId(int noviceId) {
        this.noviceId = noviceId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPeriodString() {
        return periodString;
    }

    public void setPeriodString(String periodString) {
        this.periodString = periodString;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getBonusString() {
        return bonusString;
    }

    public void setBonusString(String bonusString) {
        this.bonusString = bonusString;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getOrgCurr() {
        return orgCurr;
    }

    public void setOrgCurr(String orgCurr) {
        this.orgCurr = orgCurr;
    }

    public String getOrgMax() {
        return orgMax;
    }

    public void setOrgMax(String orgMax) {
        this.orgMax = orgMax;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
