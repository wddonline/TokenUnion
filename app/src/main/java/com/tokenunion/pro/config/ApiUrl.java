package com.tokenunion.pro.config;

import com.tokenunion.pro.BuildConfig;

public class ApiUrl {

    private static String TAG = "ApiUrl";

    public static String BASE_URL_TEST = "http://54.169.124.133:8010"; // 测试环境
//    public static String BASE_URL_TEST = "https://www.anypocket.online"; // 生产环境
//    public static String BASE_URL_TEST = "https://www.goany.net"; // 生产环境-域名test
//    public static String BASE_URL_ONLINE = "https://www.anypocket.pro"; // 生产环境-正式线上用
    public static String BASE_URL_ONLINE = "https://www.anypocket.pro"; // 生产环境-正式线上用
//    public static String BASE_URL_TEST = "http://15.164.221.70:8010"; // 生产环境


    public static String BASE_URL = BASE_URL_TEST;// = BASE_URL_TEST;//BASE_URL;

    //  =============== 用户相关接口 =================

    // 用户登录
    public static String API_USER_LOGIN = BASE_URL + "/app/login/doLogin";
    // 退出登录
    public static String API_USER_LOGOUT = BASE_URL + "/app/login/doLogout";
    // 提交注册信息
    public static String API_USER_SIGNIN = BASE_URL + "/app/signin";
    // 忘记密码
    public static String API_USER_FORGET_PASSWROD = BASE_URL + "/app/login/forget";
    // 修改登陆密码
    public static String API_USER_MODIFY_PASSWROD = BASE_URL + "/app/login/modify";

    // 国家接口列表app
    public static String API_USER_COUNTRY_LIST = BASE_URL + "/app/country/list";

    // 设置资金密码
    public static String API_USER_TRADE_PASSWORD = BASE_URL + "/app/trade/set";
    // 修改资金密码
    public static String API_USER_TRADE_PASSWORD_MODIFY = BASE_URL + "/app/trade/modify";

    // 获取短信证码
    public static String API_USER_GET_SMS_CODE = BASE_URL + "/app/sms/get";



    //  =============== 财富相关的接口 =================
    /**
     * 个人财富信息接口
     */
    public static String API_FINANCE_INFO = BASE_URL + "/app/finance/info";

    /**
     * 个人财富信息接口-理财首页（理财币种列表信息）
     */
    public static String API_FINANCE_LIST = BASE_URL + "/app/finance/list";

    /**
     * 财富收益账单----（不区分活期、定期）
     */
    public static String API_FINANCE_PROFIT_LIST = BASE_URL + "/app/finance/asset/profit/list";

    /**
     * 财富 "操作记录" （订单列表）
     */
    public static String API_FINANCE_ORDERS_LIST = BASE_URL + "/app/finance/orders/list";

    /**
     * 理财个人资产概况-产品详情页（活期理财页）-- "存入"页的数据
     */
    public static String API_FINANCE_ASSET_OVERVIEW = BASE_URL + "/app/finance/asset/overview";


    /**
     * 理财个人资产信息-购买详情页,资产信息请求接口
     */
    public static String API_FINANCE_ASSET_DEPOSIT_INFO = BASE_URL + "/app/finance/asset/info";


    /**
     * 购买理财接口---- "存入" 提交
     */
    public static String API_FINANCE_DEPOSIT = BASE_URL + "/app/finance/deposit";

    /**
     * 赎回理财接口---- "赎回" 提交
     */
    public static String API_FINANCE_REDEEM = BASE_URL + "/app/finance/redeem";

    /**
     * 理财资产详情查询接口----财富首页入口
     */
    public static String API_FINANCE_LIST_RATIO = BASE_URL + "/app/finance/asset/list/ratio";

    /**
     * 理财资产详情查询接口----定期理财的购买页的定期产品列表（锁仓）
     */
    public static String API_FINANCE_PRODUCT_LIST = BASE_URL + "/app/finance/product/list";

    /**
     * 理财定期订单查询接口----定期产品的持有记录
     */
    public static String API_FINANCE_TIMEORDER_LIST = BASE_URL + "/app/finance/asset/timeorder/list";

    /**
     * 财富总览--利率规则--利率明细 接口
     */
    public static String API_FINANCE_PROFIT_PARTICULARS = BASE_URL + "/app/finance/profit/particulars";
    /**
     * 财富总览--利率规则--领导奖励 接口
     */
    public static String API_FINANCE_LEADER_PARTICULARS = BASE_URL + "/app/finance/reward/leader/particulars";


    // google验证相关 ===========================
    /**
     * 谷歌验证器绑定接口，获取any验证码
     */
    public static String API_GOOGLE_BIND_GET = BASE_URL + "/app/google/bind/get";

    /**
     * 绑定验证谷歌验证码，提交绑定
     */
    public static String API_GOOGLE_BIND_VERFICATION = BASE_URL + "/app/google/bind/verfication";
    /**
     * 解绑
     */
    public static String API_GOOGLE_UNBIND = BASE_URL + "/app/google/unbind";



    // "我的"接口 ===========================

    /**
     * 邀请好友，团队规模(时间分布、规模分布、星级分布用同一个接口，通过type参数区分)
     */
    public static String API_FINANCE_TEAM_INVITE_DISTIRBUTION = BASE_URL + "/app/finance/asset/team/invite/distirbution";
    /**
     * "我的"顶部的 用户信息
     */
    public static String API_MEM_USER_INFO = BASE_URL + "/app/mem/user/info";

    /**
     * 获取用户的邀请链接
     */
    public static String API_LOGIN_INVITE = BASE_URL + "/app/login/invite";

    /**
     * 新版本检测
     */
    public static String API_APP_VERSION_CHECK = BASE_URL + "/app/version/getversion";


    // "钱包"接口 ===========================
    /**
     * 充值请求币种列表
     */
    public static String API_WALLET_RECHARGE_LIST = BASE_URL + "/app/trade/recharge/list";
    /**
     * 提币列表请求接口
     */
    public static String API_WALLET_WITHDRAW_LIST = BASE_URL + "/app/trade/withdraw/list";

    /**
     * 提币接口
     */
    public static String API_WALLET_WITHDRAW = BASE_URL + "/app/trade/withdraw";

    /**
     * 转账
     */
    public static String API_WALLET_TRANSFER = BASE_URL + "/app/trade/transfer";

    /**
     * 交易，兑换
     */
    public static String API_WALLET_EXCHANGE = BASE_URL + "/app/act/convert/apply";
    /**
     * 兑换相关的配置信息，比如最小兑换数量、手续费率等
     */
    public static String API_WALLET_EXCHANGE_CONFIG = BASE_URL + "/app/act/convert/common";

    /**
     * 钱包资产查询
     */
    public static String API_WALLET_TRADE_BALANCE = BASE_URL + "/app/trade/balance";
    /**
     * 钱包账单列表
     */
    public static String API_ASSET_BILL_PAGE = BASE_URL + "/app/asset/bill/page";

    /**
     * 市场价格行情
     */
    public static String API_MARKET_COMMON = BASE_URL + "/app/market/common";



    // "消息中心"接口 ===========================
    /**
     * 获取最新公告
     */
    public static String API_MESSAGE_CENTER_LIST = BASE_URL + "/app/message/last";
    /**
     * 获取历史公告
     */
    public static String API_MESSAGE_CENTER_OLD = BASE_URL + "/app/message/old";


    /**
     * 获取首页banner
     */
    public static String API_BANNER_COMMON = BASE_URL + "/app/banner/common";


    // "新手"专享
    /**
     * 新手专享订单查询
     */
    public static String API_NEW_USER_HOLDING_LIST = BASE_URL + "/app/act/novice/orders/list";
    /**
     * 新手专享产品查询
     */
    public static String API_NEW_USER_NOVICE_COMMON = BASE_URL + "/app/act/novice/common";
    /**
     * 新手活动锁仓下单
     */
    public static String API_NEW_USER_NOVICE_DEPOSIT = BASE_URL + "/app/act/novice/deposit";
}
