package com.tokenunion.pro.ui.mine;

import com.tokenunion.pro.config.ApiUrl;
import com.tokenunion.pro.ui.mine.model.DistirbutionVosBean;
import com.tokenunion.pro.ui.mine.model.InviteInfoBean;
import com.tokenunion.pro.ui.mine.model.MemberUserInfo;
import com.tokenunion.pro.ui.mine.model.MessageBean;
import com.tokenunion.pro.ui.mine.model.VersionCheckBean;
import com.tokenunion.pro.config.ApiRequestCallback;
import com.tokenunion.pro.utils.NetRequestUtils;
import com.yidaichu.android.common.http.ActivityFragmentActive;
import com.yidaichu.android.common.http.HttpRequestEntry;

/**
 * Create by: xiaohansong
 * Time: 2019-07-30 20:51
 * -
 * Description:  "我的"模块中的网络请求接口
 */
public class MineApi extends NetRequestUtils {

    /**
     * 获取 "会员"用户信息
     * @param tag
     * @param callback
     */
    public static void getMemUserInfo(ActivityFragmentActive tag,
                                                            final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_MEM_USER_INFO);
        doRequest(tag, requestEntry, MemberUserInfo.class, callback);
    }

    /**
     * 获取 用户邀请的 团队规模，时间分布:1 规模分布:2 星级分布:3
     * 传不同的type，仅返回该类型数据
     * @param tag
     * @param callback
     */
    public static void getTeamInvitedDistirbutionList(int type, ActivityFragmentActive tag,
                                      final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setUrl(ApiUrl.API_FINANCE_TEAM_INVITE_DISTIRBUTION);
        requestEntry.addRequestParam("type", type+ "");
        doRequest(tag, requestEntry, DistirbutionVosBean.class, callback);
    }


    /**
     *
     * 获取 用户邀请链接
     * @param tag
     * @param callback
     */
    public static void getUserInviteInfo(ActivityFragmentActive tag,
        final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.GET);
        requestEntry.setUrl(ApiUrl.API_LOGIN_INVITE);
        doRequest(tag, requestEntry, InviteInfoBean.class, callback);
    }

    /**
     *
     * 检测新版本
     * @param tag
     * @param callback
     */
    public static void checkAppVersion(String version, ActivityFragmentActive tag,
                                         final ApiRequestCallback callback) {
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_APP_VERSION_CHECK);
        requestEntry.addRequestParam("version", version);
        doRequest(tag, requestEntry, VersionCheckBean.class, callback);
    }

    /**
     * 获取消息列表
     *  {"mid":1,"dataType":null,"limit":5}
     */
    public static void getMessageCenterListData(String mid, String dataType, int limit,
                                                ActivityFragmentActive tag, final ApiRequestCallback callback){
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_MESSAGE_CENTER_LIST);
        requestEntry.addRequestParam("mid", mid);
        requestEntry.addRequestParam("dataType", dataType);
        requestEntry.addRequestParam("limit", limit+ "");
        doRequest(tag, requestEntry, MessageBean.class, callback);
    }

    /**
     * 获取历史消息列表
     *  {"mid":1,"dataType":null,"limit":5}
     */
    public static void getMessageCenterListHistoryData(String mid, String dataType, int limit,
                                                ActivityFragmentActive tag, final ApiRequestCallback callback){
        HttpRequestEntry requestEntry = new HttpRequestEntry();
        requestEntry.setMethod(HttpRequestEntry.Method.POST);
        requestEntry.setUrl(ApiUrl.API_MESSAGE_CENTER_OLD);
        requestEntry.addRequestParam("mid", mid);
        requestEntry.addRequestParam("dataType", dataType);
        requestEntry.addRequestParam("limit", limit+ "");
        doRequest(tag, requestEntry, MessageBean.class, callback);
    }

}
