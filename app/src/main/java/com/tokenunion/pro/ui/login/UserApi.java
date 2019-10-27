package com.tokenunion.pro.ui.login;

import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.config.Configs;
import com.tokenunion.pro.ui.login.model.CountryBean;
import com.tokenunion.pro.ui.login.model.LoginBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.config.UserAccount;
import com.tokenunion.pro.utils.Md5Utils;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpRequestEntry;

public class UserApi extends NetRequestUtils {
    /**
     * 用户登录请求
     *
     * @param userName
     * @param password
     * @param tag
     * @param callback
     * @return
     */
    public static void login(String userName, String phone, String password, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_LOGIN);
        if (UserAccount.getInstance().isLogin()) {
            requestEntry.addRequestHeader("token", UserAccount.getInstance().getUserBean().getToken());
        }
        requestEntry.addRequestHeader("language", Configs.getCurrentLanguage());

        requestEntry.addRequestParam("userName", userName);
        requestEntry.addRequestParam("phone", phone);
//        requestEntry.addRequestParam("passwd", password);
        requestEntry.addRequestParam("passwd", Md5Utils.MD5Encode(password));
        doRequest(tag, requestEntry, LoginBean.class, callback);
    }

    /**
     * 用户退出登录
     *
     * @param tag
     * @param callback
     * @return
     */
    public static void logout(ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_LOGOUT);
        doRequest(tag, requestEntry, callback);
    }

    /**
     * 用户注册
     *
     * @param countryAbb 国际域名缩写
     * @param email      邮箱
     * @param userName   用户名
     * @param phone      手机号码
     * @param smsCode    验证码
     * @param password   密码
     * @param inviteCode 邀请码
     * @param tag   "tradePasswd": "string",
     * @param callback
     */
    public static void signIn(String countryAbb, String email, String userName, String phone,
                              String smsCode, String password, String inviteCode,
                              ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_SIGNIN);
        if (UserAccount.getInstance().isLogin()) {
            requestEntry.addRequestHeader("token", UserAccount.getInstance().getUserBean().getToken());
        }
        requestEntry.addRequestHeader("language", Configs.getCurrentLanguage());

        requestEntry.addRequestParam("countryAbb", countryAbb);
        requestEntry.addRequestParam("email", email);
        requestEntry.addRequestParam("userName", userName);
        requestEntry.addRequestParam("phone", phone);
        requestEntry.addRequestParam("smsCode", smsCode);
        requestEntry.addRequestParam("passwd", Md5Utils.MD5Encode(password));
        requestEntry.addRequestParam("inviteCode", inviteCode);
        doRequest(tag, requestEntry, LoginBean.class, callback);
    }

    /**
     * 获取国家接口列表
     *
     * @param language
     * @param tag
     * @param callback
     */
    public static void getCountryList(String language, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_COUNTRY_LIST);
        requestEntry.addRequestParam("language", language);
        doRequest(tag, requestEntry, CountryBean.class, callback);
    }

    /**
     * 设置资金密码
     *
     * @param tradePasswd
     * @param tag
     * @param callback
     */
    public static void setTradePassword(String tradePasswd, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_TRADE_PASSWORD);
        requestEntry.addRequestParam("tradePasswd", tradePasswd);
        doRequest(tag, requestEntry, callback);
    }

    /**
     * 修改资金密码
     *
     * @param passwd 新密码
     * @param phone 手机号
     * @param smsCode 验证码
     * @param tag
     * @param callback
     */
    public static void modifyTradePassword(String passwd, String phone, String smsCode,
                                        ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_TRADE_PASSWORD_MODIFY);

        requestEntry.addRequestParam("passwd", passwd);
        requestEntry.addRequestParam("phone", phone);
        requestEntry.addRequestParam("smsCode", smsCode);
        doRequest(tag, requestEntry, callback);
    }


    /**
     * 忘记密码
     *
     * @param countryAbb
     * @param phone
     * @param smsCode
     * @param newPassword
     * @param tag
     * @param callback
     */
    public static void forgetLoginPassword(String countryAbb, String phone, String smsCode, String newPassword, ActivityFragmentActive tag, final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_FORGET_PASSWROD);

        requestEntry.addRequestParam("countryAbb", countryAbb);
//        requestEntry.addRequestParam("userName", userName);
        requestEntry.addRequestParam("phone", phone);
        requestEntry.addRequestParam("smsCode", smsCode);
        requestEntry.addRequestParam("passwd", Md5Utils.MD5Encode(newPassword));
//        requestEntry.addRequestParam("passwd", newPassword);

        doRequest(tag, requestEntry, callback);
    }

    /**
     * 修改登录密码
     *
     * @param oriPasswd
     * @param phone
     * @param smsCode
     * @param newPassword
     * @param tag
     * @param callback
     */
    public static void changeLoginPassword(String oriPasswd, String phone, String smsCode,
                                           String newPassword, ActivityFragmentActive tag,
                                           final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_MODIFY_PASSWROD);

        requestEntry.addRequestParam("oriPasswd", oriPasswd);
        requestEntry.addRequestParam("phone", phone);
        requestEntry.addRequestParam("smsCode", smsCode);
        requestEntry.addRequestParam("passwd", Md5Utils.MD5Encode(newPassword));

        doRequest(tag, requestEntry, callback);
    }

    /**
     * 获取 google验证码
     * @param tag
     * @param callback
     */
    public static void getGoogleCode(ActivityFragmentActive tag,
                                     final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_GOOGLE_BIND_GET);
        doRequest(tag, requestEntry, callback);
    }

    /**
     * 绑定 google验证码
     * @param googleAppCode google验证APP返回的验证码
     * @param tag
     * @param callback
     */
    public static void bindGoogleCode(String secret,  String googleAppCode,
                                      ActivityFragmentActive tag,
                                     final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_GOOGLE_BIND_VERFICATION);
        requestEntry.addRequestParam("secret", secret);
        requestEntry.addRequestParam("verficationCode", googleAppCode);

        doRequest(tag, requestEntry, callback);
    }

    /**
     * 解绑 google验证码
     * @param googleAppCode google验证APP返回的验证码
     * @param tag
     * @param callback
     */
    public static void unBindGoogleCode(String googleAppCode,
                                      ActivityFragmentActive tag,
                                      final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_GOOGLE_UNBIND);
        requestEntry.addRequestParam("verficationCode", googleAppCode);

        doRequest(tag, requestEntry, callback);
    }

    /**
     * 获取短信验证码
     * @param
     * @param tag
     * @param callback
     */
    public static void getSmsCode(String phone,
                                        String countryAbb,
                                        String phoneCode,
                                        ActivityFragmentActive tag,
                                        final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_USER_GET_SMS_CODE);
        requestEntry.addRequestParam("phone", phone);
        requestEntry.addRequestParam("phoneCode", phoneCode);
        requestEntry.addRequestParam("countryAbb", countryAbb);

        doRequest(tag, requestEntry, callback);
    }

}
