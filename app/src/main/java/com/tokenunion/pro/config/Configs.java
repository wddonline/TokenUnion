package com.tokenunion.pro.config;

import com.anypocket.pro.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by hansongxiao
 */
public class Configs {
    // DEBUG模式。影响log级别输出
    public static final boolean DEBUG = BuildConfig.DEBUG;
    private static final String BASE_DOMAIN = ApiUrl.BASE_URL;

    /**
     * 后台系统服务的时区，太平洋时间。如果需要显示为手机当地时间，则需要转换时区
     */
    public static final String SYSTEM_SERVER_TIME_ZONE = "GMT-07";

    /**
     * 支持的充提币币种列表
     */
    public static final List<String> SURPORT_SYMBOL_LIST = new ArrayList<>();
    static {
        SURPORT_SYMBOL_LIST.add("ANY");
        SURPORT_SYMBOL_LIST.add("BTC");
        SURPORT_SYMBOL_LIST.add("ETH");
        SURPORT_SYMBOL_LIST.add("EOS");
    }

    // 用户和隐私协议
    public static final String URL_PRIVACY = BASE_DOMAIN+ "/html/ANYPrivacy.html";
    public static final String URL_USER = BASE_DOMAIN+ "/html/ANYPrivacy.html";

    // 官网地址
    public static final String URL_OFFICIAL = "https://www.anypocket.io/";

    // 游戏-掷骰子
    public static final String URL_GAME_DICE = "https://any.games/home/dice/";
    // 游戏-杰克高手
    public static final String URL_GAME_JACKSORBETTER = "https://any.games/home/JacksOrBetter";
    // 游戏-魔力转盘
    public static final String URL_GAME_MAGIC_CIRCLE = "https://any.games/home/Magic_circle";

    private static String sCurrentLanguage = "CN";

    /**
     * 请求接口时用到的语言，比如：CN，EN，KO
     * @return
     */
    public static String getCurrentLanguage() {
        return sCurrentLanguage;
    }

    public static void setCurrentLanguage(String currentLanguage) {
        sCurrentLanguage = currentLanguage;
    }

}
