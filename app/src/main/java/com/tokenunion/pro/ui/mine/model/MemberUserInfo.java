package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-07-30 22:04
 * -
 * Description:  会员用户信息，用于"我的"顶部
 */
public class MemberUserInfo  implements Serializable {

    /**
     * uid : 27
     * userName : test1675
     * inviteCode : T012F6
     * levelId : 1
     * levelCode : vip0
     * levelDesc : 小星星
     * iconPath : null
     * teamBal : 5000.0000
     * friends : 99/999
     * distribution : 19/32
     * bal : 0.0000
     * holdBal : 5000.00
     */

    private int uid;
    private String userName;
    private String inviteCode;
    private int levelId;
    private String levelCode;
    private String levelDesc;
    private Object iconPath;
    private String teamBal;
    private String friends;
    private String distribution;
    private String bal;
    private String holdBal;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public void setLevelDesc(String levelDesc) {
        this.levelDesc = levelDesc;
    }

    public Object getIconPath() {
        return iconPath;
    }

    public void setIconPath(Object iconPath) {
        this.iconPath = iconPath;
    }

    public String getTeamBal() {
        return teamBal;
    }

    public void setTeamBal(String teamBal) {
        this.teamBal = teamBal;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getBal() {
        return bal;
    }

    public void setBal(String bal) {
        this.bal = bal;
    }

    public String getHoldBal() {
        return holdBal;
    }

    public void setHoldBal(String holdBal) {
        this.holdBal = holdBal;
    }
}
