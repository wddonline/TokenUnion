package com.tokenunion.pro.ui.mine.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-08-01 11:31
 * -
 * Description: 邀请链接接口返回的数据
 */
public class InviteInfoBean  implements Serializable {

    /**
     * inviteCode : 554972
     * inviteUrl : http://54.169.124.133:3107/register
     */

    private String inviteCode;
    private String inviteUrl;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }
}
