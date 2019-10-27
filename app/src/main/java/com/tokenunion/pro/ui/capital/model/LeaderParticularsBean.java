package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 19:15
 * -
 * Description: 财富总览--利率规则--领导奖励 接口返回的数据item
 */
public class LeaderParticularsBean implements Serializable {

    /**
     * levelCode : vip3
     * levelName : 小星星
     * levelNum : 3
     * rewardBal : 500
     * holdDays : 20
     */

    private String levelCode;
    private String levelName;
    private int levelNum;
    private String rewardBal;
    private int holdDays;

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public String getRewardBal() {
        return rewardBal;
    }

    public void setRewardBal(String rewardBal) {
        this.rewardBal = rewardBal;
    }

    public int getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(int holdDays) {
        this.holdDays = holdDays;
    }
}
