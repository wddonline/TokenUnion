package com.tokenunion.pro.ui.login.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-07-22 19:07
 * -
 * Description: 登录接口返回信息
 */
public class LoginBean implements Serializable {

    /**
     * userName : test1675
     * phone : 15101545527
     * levelCode : vip0
     * levelDesc : 小星星
     * avatarPath : http://54.169.124.133/images/1_thumb.jpg
     * inviteCode : T012F6
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyNyIsImlhdCI6MTU2NDQ4NzYwMCwiZXhwIjoxNTY0NTc0MDAwfQ.kZ6XX8OMa1_eLFQT7GLA6QXpAzhR-GhpHCNbpSaMX3yHrG5jCMvzL_RmqdKYWMCa
     * expire : 0
     * isBindGoogle : 1
     */

    private String userName;
    private String phone;
    private String levelCode;
    private String levelDesc;
    private String avatarPath;
    private String inviteCode;
    private String token;
    private String expire;
    private Integer isBindGoogle;
    private Integer isBindTradePasswd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public Integer getIsBindGoogle() {
        return isBindGoogle;
    }

    public void setIsBindGoogle(Integer isBindGoogle) {
        this.isBindGoogle = isBindGoogle;
    }
    public Integer getIsBindTradePasswd() {
        return isBindTradePasswd;
    }

    public void setIsBindTradePasswd(Integer isBindTradePasswd) {
        this.isBindTradePasswd = isBindTradePasswd;
    }

    /**
     * 是否已经绑定谷歌身份验证
     * @return
     */
    public boolean isBindedGoogle(){
        if(null == isBindGoogle)
            return false;
        return (isBindGoogle == 1);
    }

    /**
     * 是否已经设置资金密码
     * @return
     */
    public boolean isSetTradePasswd(){
        if(null == isBindTradePasswd)
            return false;
        return (isBindTradePasswd == 1);
    }
}
