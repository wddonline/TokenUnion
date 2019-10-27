package com.tokenunion.pro.ui.login.model;

import java.io.Serializable;

/**
 * Create by: xiaohansong
 * Time: 2019-07-22 03:02
 * <p>
 * Description: 获取国家列表接口返回的数据item
 */
public class CountryBean implements Serializable {

    /**
     * language : CN
     * countryName : 中国
     * countryAbb : CN
     * phoneCode : 86
     */

    private String language;
    private String countryName;
    private String countryAbb;
    private String phoneCode;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryAbb() {
        return countryAbb;
    }

    public void setCountryAbb(String countryAbb) {
        this.countryAbb = countryAbb;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }
}
