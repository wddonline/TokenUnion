package com.tokenunion.pro.ui.capital.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-09-05 19:19
 * -
 * Description: 首页广告banner数据
 */
public class BannerCommonBean implements Serializable {

    /**
     * bannerType : 1
     * interType : 1
     * picUrl : http://www.baidu.com
     * burl : https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS1qBF4o1PZtsjljckvWjZXdY5R26d2-qZ_QLEDNywf-rz9uN1qPA
     */

    /**
     * 0:内部跳转     1：外部链接跳转
     */
    private String bannerType;
    /**
     * WEALTH：财富,WALLET：钱包 ,EXPLORE：发现 ,INVITE ：邀请,ROYALTY：会员中心,MESSAGE：消息
     */
    private String interType;
    /**
     * 显示的图片url
     */
    private String picUrl;
    /**
     * 外部跳转url
     */
    private String burl;

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getInterType() {
        return interType;
    }

    public void setInterType(String interType) {
        this.interType = interType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
    }
}
