package com.tokenunion.pro.utils.language;

/**
 * Created by dumingwei on 2018/5/31 0031.
 */
public enum LanguageType {

    CHINESE("ch"),
    ENGLISH("en"),
    KO_RKR("ko-rKR"),
    THAILAND("th");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }

    /**
     * 根据系统语言，返回业务语言名
     * @return
     */
    public static String getLanguageName(String sysLanguage){
        switch (sysLanguage){
            case "ch":
                return "CN";
            case "en":
                return "EN";

            case "ko-rKR":
                return "KO";

            default:
                break;

        }
        return "CN";
    }
}
